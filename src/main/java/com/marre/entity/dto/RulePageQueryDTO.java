package com.marre.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RulePageQueryDTO {

    private String rule;

    private Integer sYear;

    private Integer grade;

    private int page;

    private int pageSize;
}
