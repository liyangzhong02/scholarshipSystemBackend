package com.marre.mapper;

import com.github.pagehelper.Page;
import com.marre.annotation.AutoFill;
import com.marre.entity.Rule;
import com.marre.entity.dto.RulePageQueryDTO;
import com.marre.entity.vo.RuleVO;
import com.marre.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/4
 */
@Mapper
public interface RuleMapper {

    @AutoFill(OperationType.INSERT)
    public void insert(Rule rule);

    void deleteByIds(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(Rule rule);

    Page<RuleVO> pageQuery(RulePageQueryDTO rulePageQueryDTO);
}
