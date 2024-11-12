package com.marre.controller.student;

import com.marre.entity.dto.GetRuleDTO;
import com.marre.entity.vo.RuleVO;
import com.marre.service.RuleService;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class: com.marre.controller.student
 * @ClassName: StudentRuleController
 * @author: Marre
 * @creat: 9月 19
 * 学生端规则接口
 */
@RestController
@RequestMapping("student/rule")
@Api(tags = "学生端规则接口")
@Slf4j
public class StudentRuleController {

    @Autowired
    RuleService ruleService;

    /**
     * 根据年份年级提取规则
     *
     * @param getRuleDTO
     * @return
     */
    @GetMapping
    @ApiOperation("根据年份年级提取规则")
    public Result<RuleVO> getByYearAndGrade(GetRuleDTO getRuleDTO) {
        log.info("Searching the rule according to Year and Grade");
        RuleVO ruleVO = ruleService.getByYearAndGrade(getRuleDTO);
        return Result.success(ruleVO);
    }
}
