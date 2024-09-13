package com.marre.controller.student;

import com.marre.entity.dto.ApplicationDTO;
import com.marre.service.ScholarshipApplicationService;
import com.marre.utils.BaseContext;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Result submitApplication(@RequestBody ApplicationDTO applicationDTO){
        applicationDTO.setSNo(BaseContext.getCurrentId());
        log.info("当前操作者学号：{}", applicationDTO.getSNo());
        applicationService.submitApplication(applicationDTO);
        return Result.success();
    }
}
