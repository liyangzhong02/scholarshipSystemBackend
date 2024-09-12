package com.marre.controller.admin;

import com.marre.service.RuleService;
import com.marre.entity.dto.RuleDTO;
import com.marre.entity.dto.RulePageQueryDTO;
import com.marre.utils.PageResult;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
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

    @PostMapping()
    public Result save(@RequestBody RuleDTO ruleDTO){
        log.info("增加规则：{}", ruleDTO);
        ruleService.save(ruleDTO);
        return Result.success();
    }

    @DeleteMapping()
    public Result deleteByIds(@RequestParam List<Long> ids){
        log.info("删除规则：{}", ids);
        ruleService.deleteByIds(ids);

        return Result.success();
    }

    @PutMapping()
    public Result update(@RequestBody RuleDTO ruleDTO){
        log.info("修改规则：{}", ruleDTO);
        ruleService.update(ruleDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> page(RulePageQueryDTO rulePageQueryDTO){
        log.info("分页查询：{}", rulePageQueryDTO);
        PageResult page = ruleService.pageQuery(rulePageQueryDTO);

        return Result.success(page);
    }
}
