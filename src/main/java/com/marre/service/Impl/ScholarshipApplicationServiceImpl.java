package com.marre.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.marre.constant.GradeConstant;
import com.marre.entity.Application;
import com.marre.entity.Student;
import com.marre.entity.dto.*;
import com.marre.enumeration.AuditStatus;
import com.marre.exception.AccountNotFoundException;
import com.marre.exception.NotAllowZeroException;
import com.marre.mapper.ApplicationMapper;
import com.marre.mapper.ApplicationRepository;
import com.marre.mapper.StudentMapper;
import com.marre.service.AwardService;
import com.marre.service.ScholarshipApplicationService;
import com.marre.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static com.marre.constant.MessageConstant.NOT_ALLOW_ZERO;
import static java.lang.Math.max;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ScholarshipApplicationServiceImpl
 * @author: Marre
 * @creat: 2024/9/12 09:42
 * 奖学金审核实现类
 */
@Service
@Slf4j
public class ScholarshipApplicationServiceImpl implements ScholarshipApplicationService {

    private static final String AUDIT_QUEUE_KEY = "audit:queue";
    private static final String AUDIT_STATUS_KEY = "audit:status";

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private AwardService awardService;

    /**
     * 学生提交审核
     *
     * @param submitApplicationDTO
     */
    @Override
    @Transactional
    public void submitApplication(SubmitApplicationDTO submitApplicationDTO) {

        if (submitApplicationDTO == null) {
            throw new EntityNotFoundException("submitApplicationDTO cannot be null");
        }

        Application application = new Application();
        BeanUtils.copyProperties(submitApplicationDTO, application);
        application.setCreateTime(LocalDateTime.now());
        application.setUpdateTime(LocalDateTime.now());
        application.setCreateUser(application.getSNo());
        application.setUpdateUser(application.getSNo());
        application.setStatus(AuditStatus.PENDING);

        // 检查数据库中是否存在未处理的申请
        AuditStatus existingApplication = applicationMapper.getBySnoAndStatus(submitApplicationDTO.getSNo());
        if (existingApplication == AuditStatus.PENDING) {
            log.error("Already have an application yet.");
            throw new RuntimeException("Application already submitted for SNo: " + submitApplicationDTO.getSNo());
        }

        //保存到SQL
        applicationRepository.save(application);
        Long generatedId = application.getId();
        submitApplicationDTO.setId(application.getId());

        try {
            redisTemplate.opsForList().leftPush(AUDIT_QUEUE_KEY, generatedId.toString());
            redisTemplate.opsForHash().put(AUDIT_STATUS_KEY, generatedId.toString(), AuditStatus.PENDING.toString());
        } catch (Exception e) {
            log.error("Failed to push to Redis", e);
            throw new RuntimeException("Failed to push to Redis", e);
        }

        // 计算成绩
        Double totalScore = calculate(submitApplicationDTO);

        // 把成绩插入学生表
        Student student = studentMapper.getBySno(submitApplicationDTO.getSNo());
        if (student != null) {
            student.setSTotals(totalScore);
            studentMapper.update(student);
            log.info("Updated score for student: {}", student.getSNo());
        } else {
            // 处理找不到学生的情况
            log.error("Student NOT FOUND for SNo: {}", submitApplicationDTO.getSNo());
            throw new EntityNotFoundException("Student not found");
        }
    }

    /**
     * 学生撤回申请
     * @param id
     */
    @Override
    public void withdrawApplication(Long id) {
        // 从mysql中查询申请
        Application application = applicationRepository.findById(id).orElseThrow(() -> new RuntimeException("申请不存在"));

        // 从redis list中移除
        redisTemplate.opsForList().remove(AUDIT_QUEUE_KEY, 1, id.toString());
        // 从redis hash中移除
        redisTemplate.opsForHash().delete(AUDIT_STATUS_KEY, id.toString());
        //从mysql中删除
        applicationRepository.delete(application);
    }

    /**
     * 根据规则计算总分
     * @param submitApplicationDTO
     * @return
     */
    @Override
    public Double calculate(SubmitApplicationDTO submitApplicationDTO) {
        JSONObject jsonObject = JSONObject.parseObject(submitApplicationDTO.getRule());
        Integer grade = submitApplicationDTO.getGrade();
        double totalScore = 0.0;
        if (grade == GradeConstant.GRADE_1) {
            totalScore = calculateForGrade1(jsonObject);
        } else if (grade == GradeConstant.GRADE_2) {
            totalScore = calculateForGrade2(jsonObject);
        } else if (grade == GradeConstant.GRADE_3) {
            totalScore = calculateForGrade3(jsonObject);
        }

        return totalScore;
    }

    private double calculateForGrade3(JSONObject jsonObject) {
        return ideologicalPerformance(jsonObject)
                + researchCapacity(jsonObject)
                + socialSecurity(jsonObject);
    }

    private double calculateForGrade2(JSONObject jsonObject) {
        return academicPerformance(jsonObject)
                + ideologicalPerformance(jsonObject)
                + researchCapacity(jsonObject)
                + socialSecurity(jsonObject);
    }

    private double calculateForGrade1(JSONObject jsonObject) {
        return academicPerformance(jsonObject);
    }

    /**
     * 学业成绩板块（上限20分）
     * 学业成绩=学位课成绩总成绩÷学位课数量×20%
     * @param jsonObject
     * @return
     */
    private double academicPerformance(JSONObject jsonObject) {
        JSONObject degreeJson = jsonObject.getJSONObject("学业成绩");
        double courseScore = degreeJson.getDouble("学位课成绩总成绩");
        int courseNum = degreeJson.getIntValue("学位课数量");
        double courseWeight = degreeJson.getDouble("学位成绩权重");
        if (courseNum == 0) {
            throw new NotAllowZeroException("学位课数量" + NOT_ALLOW_ZERO);
        }
        if(courseWeight == 0.0){
            throw new NotAllowZeroException("学位成绩权重" + NOT_ALLOW_ZERO);
        }
        double result = (courseScore / courseNum) * courseWeight;
        return Math.min(result, 20.0);
    }

    /**
     * 思政表现（上限 30 分）
     * 满分（30 分）=基础分（20 分）+荣誉分（5 分）+导师组评价分（5 分）
     * @param jsonObject
     * @return
     */
    private double ideologicalPerformance(JSONObject jsonObject){
        JSONObject degreeJson = jsonObject.getJSONObject("思政表现");
        double baseScore = Math.max(20 - (degreeJson.getIntValue("通报批评")), 0);
        double meritScore = degreeJson.getDouble("荣誉分");
        double evaluationScore = degreeJson.getDouble("导师组评价分");
        double result = baseScore + meritScore + evaluationScore;
        return Math.min(result, 30.0);
    }

    /**
     * 科研能力（上限 30 分）
     * @param jsonObject
     * @return
     */
    private double researchCapacity(JSONObject jsonObject){
        JSONObject degreeJson = jsonObject.getJSONObject("科研能力");
        double paperScore = degreeJson.
                getJSONObject("学术论文").getDouble("CCF推荐A类国际学术期刊")
                + degreeJson.getJSONObject("学术论文").getDouble("CCF推荐B类国际学术期刊")
                + degreeJson.getJSONObject("学术论文").getDouble("CCF推荐C类国际学术期刊")
                + degreeJson.getJSONObject("学术论文").getDouble("CCF高质量中文期刊");
        // ...剩余省略
        double result = paperScore;
        return Math.min(result, 30.0);
    }

    /**
     * 社会服务（上限 20 分）
     * 满分（20 分）=基础分（15 分）+附加分（5 分）
     * @param jsonObject
     * @return
     */
    private double socialSecurity(JSONObject jsonObject){
        //...省略功能
        double result = 0.0;
        return Math.min(result, 30.0);
    }


    /**
     * 处理申请审核
     * @param auditDTO
     */
    @Override
    @Transactional
    public void processApplication(AuditDTO auditDTO) {
        Long id = auditDTO.getId();
        AuditStatus newStatus = auditDTO.getStatus();

        // 1. 更新MySQL数据库
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("申请信息id未找到： " + id));

        application.setStatus(newStatus);
        applicationRepository.save(application);

        // 2. 更新Redis（使用 Redis 事务）
        try {
            redisTemplate.execute(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    operations.opsForHash().put(AUDIT_STATUS_KEY, id.toString(), newStatus.toString());
                    operations.opsForList().remove(AUDIT_QUEUE_KEY, 0, id.toString());
                    return operations.exec();
                }
            });
        } catch (Exception e) {
            // Redis 操作失败，回滚事务
            throw new RuntimeException("更新redis失败", e);
        }
        // 如果是通过，则更新奖学金得主
        if(auditDTO.getStatus() == AuditStatus.APPROVED){
            log.info("updating the awarded student.");
            awardService.award();
        }
    }

    /**
     * 查询审核状态
     * @param id
     * @return
     */
    @Override
    public AuditStatus getApplicationStatus(Long id) {

        //首先查询redis
        Object status = redisTemplate.opsForHash().get(AUDIT_STATUS_KEY, id.toString());

        if(status != null) {
            log.info("The status in redis:{}", status.toString());
            return AuditStatus.valueOf(status.toString());
        }else{
            log.warn("Can't find the application in redis");
        }

        //redis没查询到 才去sql查询

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("申请信息id未找到: " + id));
        return application.getStatus();
    }

    /**
     * 奖学金申请分页查询
     * @param applicationPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(ApplicationPageQueryDTO applicationPageQueryDTO) {
        PageHelper.startPage(applicationPageQueryDTO.getPage(), applicationPageQueryDTO.getPageSize());
        Page<Application> page = applicationMapper.pageQuery(applicationPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
