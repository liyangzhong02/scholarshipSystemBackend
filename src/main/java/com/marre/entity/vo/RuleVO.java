package com.marre.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RuleVO {

    private Long id;

    private String rule;
    @JsonProperty("sYear")
    private Integer sYear;

    private Integer grade;

}
