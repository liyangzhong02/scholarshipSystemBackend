package com.marre.service.Impl;

import com.marre.entity.Application;
import com.marre.entity.dto.ApplicationDTO;
import com.marre.entity.dto.AuditDTO;
import com.marre.enumeration.AuditStatus;
import com.marre.mapper.ApplicationRepository;
import com.marre.service.ScholarshipApplicationService;
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

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ScholarshipApplicationServiceImpl
 * @author: Marre
 * @creat: 2024/9/12 09:42
 * 奖学金审核实现类
 */
@Service
public class ScholarshipApplicationServiceImpl implements ScholarshipApplicationService {

    private static final String AUDIT_QUEUE_KEY = "audit:queue";
    private static final String AUDIT_STATUS_KEY = "audit:status";

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * 学生提交审核
     * @param applicationDTO
     */
    @Override
    public void submitApplication(ApplicationDTO applicationDTO) {

        Application application = new Application();
        BeanUtils.copyProperties(applicationDTO, application);
        application.setCreateTime(LocalDateTime.now());
        application.setUpdateTime(LocalDateTime.now());
        application.setCreateUser(application.getId());
        application.setUpdateUser(application.getId());

        //保存到SQL
        application.setStatus(AuditStatus.PENDING);
        applicationRepository.save(application);

        //推入redis队列
        redisTemplate.opsForList().leftPush(AUDIT_QUEUE_KEY, applicationDTO.getId().toString());
        //设置初始状态
        redisTemplate.opsForHash().put(AUDIT_STATUS_KEY, applicationDTO.getId().toString(), AuditStatus.PENDING.toString());

    }

    /**
     * 审核申请
     * @param auditDTO
     */
    @Override
    @Transactional
    public void auditApplication(AuditDTO auditDTO) {
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
            return AuditStatus.valueOf(status.toString());
        }

        //redis没查询到 才去sql查询

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("申请信息id未找到: " + id));
        return application.getStatus();
    }
}
