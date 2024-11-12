package com.marre.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.marre.constant.GradeConstant;
import com.marre.entity.Application;
import com.marre.entity.Student;
import com.marre.entity.dto.ApplicationPageQueryDTO;
import com.marre.entity.dto.AuditDTO;
import com.marre.entity.dto.SubmitApplicationDTO;
import com.marre.enumeration.AuditStatus;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.marre.constant.MessageConstant.NOT_ALLOW_ZERO;

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
    private final Map<Integer, GradeStrategy> gradeStrategies;

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
     * 策略模式计算分数
     *
     */
    public ScholarshipApplicationServiceImpl() {
        gradeStrategies = new HashMap<>();
        gradeStrategies.put(GradeConstant.GRADE_1, new Grade1Strategy());
        gradeStrategies.put(GradeConstant.GRADE_2, new Grade2Strategy());
        gradeStrategies.put(GradeConstant.GRADE_3, new Grade3Strategy());
    }
    private interface GradeStrategy {
        double calculate(JSONObject jsonObject);
    }
    /**
     * 根据规则计算总分
     *
     * @param submitApplicationDTO
     * @return
     */
    @Override
    public Double calculate(SubmitApplicationDTO submitApplicationDTO) {
        JSONObject jsonObject = JSONObject.parseObject(submitApplicationDTO.getRule());
        Integer grade = submitApplicationDTO.getGrade();
        GradeStrategy strategy = gradeStrategies.get(grade);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的年级" + grade);
        }
        return strategy.calculate(jsonObject);
    }
    private static class Grade1Strategy implements GradeStrategy {
        @Override
        public double calculate(JSONObject jsonObject) {
            return academicPerformance(jsonObject);
        }
    }

    private static class Grade2Strategy implements GradeStrategy {
        @Override
        public double calculate(JSONObject jsonObject) {
            return academicPerformance(jsonObject)
                    + ideologicalPerformance(jsonObject)
                    + researchCapacity(jsonObject)
                    + socialSecurity(jsonObject);
        }
    }

    private static class Grade3Strategy implements GradeStrategy {
        @Override
        public double calculate(JSONObject jsonObject) {
            return ideologicalPerformance(jsonObject)
                    + researchCapacity(jsonObject)
                    + socialSecurity(jsonObject);
        }
    }
    private static double academicPerformance(JSONObject jsonObject) {
        JSONObject degreeJson = jsonObject.getJSONObject("学业成绩");
        double courseScore = degreeJson.getDouble("学位课成绩总成绩");
        int courseNum = degreeJson.getIntValue("学位课数量");
        double courseWeight = degreeJson.getDouble("学位成绩权重");
        if (courseNum == 0) {
            throw new NotAllowZeroException("学位课数量" + NOT_ALLOW_ZERO);
        }
        if (courseWeight == 0.0) {
            throw new NotAllowZeroException("学位成绩权重" + NOT_ALLOW_ZERO);
        }
        double result = (courseScore / courseNum) * courseWeight;
        return Math.min(result, 20.0);
    }
    private static double ideologicalPerformance(JSONObject jsonObject) {
        JSONObject degreeJson = jsonObject.getJSONObject("思政表现");
        double baseScore = Math.max(20 - (degreeJson.getIntValue("通报批评")), 0);
        double meritScore = degreeJson.getDouble("荣誉分");
        double evaluationScore = degreeJson.getDouble("导师组评价分");
        double result = baseScore + meritScore + evaluationScore;
        return Math.min(result, 30.0);
    }
    private static double researchCapacity(JSONObject jsonObject) {
        JSONObject degreeJson = jsonObject.getJSONObject("科研能力");
        double paperScore = degreeJson.getJSONObject("学术论文").getDouble("CCF推荐A类国际学术期刊")
                + degreeJson.getJSONObject("学术论文").getDouble("CCF推荐B类国际学术期刊")
                + degreeJson.getJSONObject("学术论文").getDouble("CCF推荐C类国际学术期刊")
                + degreeJson.getJSONObject("学术论文").getDouble("CCF高质量中文期刊");
        return Math.min(paperScore, 30.0);
    }
    private static double socialSecurity(JSONObject jsonObject) {
        //...省略功能
        double result = 0.0;
        return Math.min(result, 30.0);
    }

    /**
     * 学生提交审核
     *
     * @param submitApplicationDTO
     */
    @Override
    @Transactional
    public void submitApplication(SubmitApplicationDTO submitApplicationDTO) {
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
            throw new RuntimeException("该学生拥有未处理的申请");
        }
        // 保存到数据库
        applicationRepository.save(application);
        Long generatedId = application.getId();
        submitApplicationDTO.setId(generatedId);
        // 将申请ID推送到Redis队列和状态哈希表
        try {
            redisTemplate.opsForList().leftPush(AUDIT_QUEUE_KEY, generatedId.toString());
            redisTemplate.opsForHash().put(AUDIT_STATUS_KEY, generatedId.toString(), AuditStatus.PENDING.toString());
        } catch (Exception e) {
            throw new RuntimeException("推送缓存失败", e);
        }
        // 计算成绩并更新学生表
        Double totalScore = calculate(submitApplicationDTO);
        Student student = studentMapper.getBySno(submitApplicationDTO.getSNo());
        if (student != null) {
            student.setSTotals(totalScore);
            studentMapper.update(student);
        } else {
            throw new EntityNotFoundException("找不到学生");
        }
    }


    /**
     * 学生撤回申请
     *
     * @param id
     */
    @Override
    public void withdrawApplication(Long id) {
        // 从mysql中查询申请
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("申请不存在"));
        // 从redis中移除
        redisTemplate.opsForList().remove(AUDIT_QUEUE_KEY, 1, id.toString());
        redisTemplate.opsForHash().delete(AUDIT_STATUS_KEY, id.toString());
        // 从mysql中移除
        applicationRepository.delete(application);
    }

    /**
     * 处理申请审核
     *
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
            throw new RuntimeException("更新redis失败", e);
        }
        // 如果是通过，则更新奖学金得主
        if (auditDTO.getStatus() == AuditStatus.APPROVED) {
            awardService.award();
        }
    }

    /**
     * 查询审核状态
     *
     * @param id
     * @return
     */
    @Override
    public AuditStatus getApplicationStatus(Long id) {
        // 查询redis
        Object status = redisTemplate.opsForHash().get(AUDIT_STATUS_KEY, id.toString());
        if (status != null) {
            return AuditStatus.valueOf(status.toString());
        }
        // redis未命中 去MySQL查询
        Application application = applicationRepository.findById(id).get();
        return application.getStatus();
    }

    /**
     * 奖学金申请分页查询
     *
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
