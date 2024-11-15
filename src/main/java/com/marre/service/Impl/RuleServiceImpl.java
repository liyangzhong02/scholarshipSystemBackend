package com.marre.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.marre.entity.Rule;
import com.marre.entity.dto.GetRuleDTO;
import com.marre.entity.dto.RuleDTO;
import com.marre.entity.dto.RulePageQueryDTO;
import com.marre.entity.vo.RuleVO;
import com.marre.exception.RuleNotFoundException;
import com.marre.mapper.RuleMapper;
import com.marre.service.RuleService;
import com.marre.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/4
 */
@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleMapper ruleMapper;

    @Override
    public void save(RuleDTO ruleDTO) {
        Rule rule = new Rule();
        BeanUtils.copyProperties(ruleDTO, rule);
        ruleMapper.insert(rule);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        ruleMapper.deleteByIds(ids);
    }

    @Override
    public void update(RuleDTO ruleDTO) {
        Rule rule = new Rule();
        BeanUtils.copyProperties(ruleDTO, rule);
        ruleMapper.update(rule);
    }

    @Override
    public PageResult pageQuery(RulePageQueryDTO rulePageQueryDTO) {
        PageHelper.startPage(rulePageQueryDTO.getPage(), rulePageQueryDTO.getPageSize());
        Page<RuleVO> page = ruleMapper.pageQuery(rulePageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());

    }

    @Override
    public RuleVO getByYearAndGrade(GetRuleDTO getRuleDTO) {
        Rule rule = ruleMapper.getByYearAndGrade(getRuleDTO);
        if (rule == null) {
            throw new RuleNotFoundException("未找到该规则");
        }
        RuleVO ruleVO = new RuleVO();
        BeanUtils.copyProperties(rule, ruleVO);
        return ruleVO;
    }
}
