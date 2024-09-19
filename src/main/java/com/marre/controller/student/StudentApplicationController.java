package com.marre.controller.student;

import com.marre.entity.dto.SubmitApplicationDTO;
import com.marre.enumeration.AuditStatus;
import com.marre.service.ScholarshipApplicationService;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: StudentApplicationController
 * @author: Marre
 * @creat: 2024/9/12 17:16
 * 学生申请奖学金接口
 */
@RestController
@RequestMapping("/student/application")
@Api(tags = "学生操作申请奖学金接口")
@Slf4j
public class StudentApplicationController {
    @Autowired
    ScholarshipApplicationService applicationService;

    /**
     * 提交奖学金审核
     * @param submitApplicationDTO
     * @return
     */
    @PostMapping
    @ApiOperation("学生提交奖学金申请")
    public Result submitApplication(@RequestBody SubmitApplicationDTO submitApplicationDTO){
        log.info("The operator's id of submitApplication：{}", submitApplicationDTO.getSNo());
        applicationService.submitApplication(submitApplicationDTO);
        return Result.success();
    }

    /**
     * 查询审核状态
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("学生查询审核状态")
    public Result<AuditStatus> getApplicationStatus(@PathVariable Long id){
        log.info("checking the audit status:{}", id);
        AuditStatus status = applicationService.getApplicationStatus(id);
        return Result.success(status);
    }

    /**
     * 学生撤销申请
     * @param id
     * @return
     */
    @PostMapping("/withdraw/{id}")
    @ApiOperation("学生撤销申请")
    public Result withdrawApplication(@PathVariable Long id){
        log.info("withDrawing the application!{}", id);
        applicationService.withdrawApplication(id);
        return Result.success();
    }
}
