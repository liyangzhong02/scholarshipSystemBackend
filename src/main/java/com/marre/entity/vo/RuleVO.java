package com.marre.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleVO {

    private Long id;

    private String rule;

    private Integer sYear;

    private Integer grade;

}
