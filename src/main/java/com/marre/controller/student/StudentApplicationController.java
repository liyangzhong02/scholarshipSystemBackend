package com.marre.controller.student;

import com.marre.entity.Rule;
import com.marre.entity.dto.ApplicationDTO;
import com.marre.entity.dto.RuleApplicationDTO;
import com.marre.enumeration.AuditStatus;
import com.marre.service.ScholarshipApplicationService;
import com.marre.utils.BaseContext;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: StudentApplicationController
 * @author: Marre
 * @creat: 2024/9/12 17:16
 * 学生申请奖学金接口
 */
@Controller
@RestController("/student/application")
@Api(tags = "学生操作申请奖学金接口")
@Slf4j
public class StudentApplicationController {
    @Autowired
    ScholarshipApplicationService applicationService;

    /**
     * 提交奖学金审核
     * @param applicationDTO
     * @return
     */
    @PostMapping
    @ApiOperation("学生提交奖学金申请")
    public Result submitApplication(@RequestBody ApplicationDTO applicationDTO, @RequestBody RuleApplicationDTO ruleApplicationDTO){
        applicationDTO.setSNo(BaseContext.getCurrentId());
        log.info("The operator's id of submitApplication：{}", applicationDTO.getSNo());
        applicationService.submitApplication(applicationDTO);
        applicationService.calculate(ruleApplicationDTO);
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
    public Result withdrawApplication(@PathVariable Long id){
        log.info("withDrawing the application!{}", id);
        applicationService.withdrawApplication(id);
        return Result.success();
    }
}
