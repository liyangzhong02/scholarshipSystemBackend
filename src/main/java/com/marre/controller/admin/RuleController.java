package com.marre.controller.admin;

import com.marre.entity.dto.GetRuleDTO;
import com.marre.entity.dto.RuleDTO;
import com.marre.entity.dto.RulePageQueryDTO;
import com.marre.entity.vo.RuleVO;
import com.marre.service.RuleService;
import com.marre.utils.PageResult;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/4
 */

@RestController
@RequestMapping("/admin/rule")
@Api(tags = "规则接口")
@Slf4j
public class RuleController {

    @Autowired
    private RuleService ruleService;

    /**
     * 新增规则
     *
     * @param ruleDTO
     * @return
     */
    @PostMapping()
    @ApiOperation("新增规则")
    public Result save(@RequestBody RuleDTO ruleDTO) {
        ruleService.save(ruleDTO);
        return Result.success();
    }

    /**
     * 删除规则
     *
     * @param ids
     * @return
     */
    @DeleteMapping()
    @ApiOperation("删除规则")
    public Result deleteByIds(@RequestParam List<Long> ids) {
        ruleService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 修改规则
     *
     * @param ruleDTO
     * @return
     */
    @PutMapping()
    @ApiOperation("修改规则")
    public Result update(@RequestBody RuleDTO ruleDTO) {
        ruleService.update(ruleDTO);
        return Result.success();
    }

    /**
     * 分页查询规则
     *
     * @param rulePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询规则")
    public Result<PageResult> page(RulePageQueryDTO rulePageQueryDTO) {
        PageResult page = ruleService.pageQuery(rulePageQueryDTO);
        return Result.success(page);
    }

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
