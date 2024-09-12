package com.marre.controller.admin;

import com.marre.entity.dto.AuditDTO;
import com.marre.enumeration.AuditStatus;
import com.marre.service.ScholarshipApplicationService;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ApplicationController
 * @author: Marre
 * @creat: 2024/9/12 17:13
 * 奖学金申请Controller
 */
@Controller
@RestController("/admin/application")
@Api(tags = "管理员申请奖学金接口")
@Slf4j
public class ApplicationController {

    @Autowired
    ScholarshipApplicationService applicationService;

    /**
     * 审核通过申请
     * @param auditDTO
     * @return
     */
    @PostMapping
    public Result auditApplication(@RequestBody AuditDTO auditDTO){
        log.info("正在审核申请");
        applicationService.auditApplication(auditDTO);
        return Result.success();
    }

    /**
     * 查询审核状态
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<AuditStatus> getApplicationStatus(@PathVariable Long id){
        log.info("查询审核状态：{}", id);
        AuditStatus status = applicationService.getApplicationStatus(id);
        return Result.success(status);
    }
}
