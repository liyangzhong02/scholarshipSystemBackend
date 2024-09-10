package com.marre.Service;

import com.marre.entity.dto.RuleDTO;
import com.marre.entity.dto.RulePageQueryDTO;
import com.marre.utils.PageResult;

import java.util.List;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/4
 */

public interface RuleService {


    void save(RuleDTO ruleDTO);

    void deleteByIds(List<Long> ids);

    void update(RuleDTO ruleDTO);

    PageResult pageQuery(RulePageQueryDTO rulePageQueryDTO);
}
