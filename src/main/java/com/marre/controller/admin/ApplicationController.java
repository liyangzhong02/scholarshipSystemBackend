package com.marre.controller.admin;

import com.marre.entity.dto.ApplicationPageQueryDTO;
import com.marre.entity.dto.AuditDTO;
import com.marre.enumeration.AuditStatus;
import com.marre.service.ScholarshipApplicationService;
import com.marre.utils.PageResult;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ApplicationController
 * @author: Marre
 * @creat: 2024/9/12 17:13
 * 奖学金申请Controller
 */
@RestController
@RequestMapping("/admin/application")
@Api(tags = "管理员申请奖学金接口")
@Slf4j
public class ApplicationController {

    @Autowired
    ScholarshipApplicationService applicationService;

    /**
     * 处理申请审核
     * @param auditDTO status
     * @return
     */
    @PostMapping
    @ApiOperation("处理申请审核")
    public Result auditApplication(@RequestBody AuditDTO auditDTO){
        log.info("auditing application.");
        applicationService.processApplication(auditDTO);
        return Result.success();
    }

    /**
     * 查询审核状态
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("查询审核状态")
    public Result<AuditStatus> getApplicationStatus(@PathVariable Long id){
        log.info("查询审核状态：{}", id);
        AuditStatus status = applicationService.getApplicationStatus(id);
        return Result.success(status);
    }

    /**
     * 奖学金申请分页查询
     * @param applicationPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询申请表")
    public Result<PageResult> page(ApplicationPageQueryDTO applicationPageQueryDTO){
        PageResult result = applicationService.pageQuery(applicationPageQueryDTO);
        return Result.success(result);
    }
}
