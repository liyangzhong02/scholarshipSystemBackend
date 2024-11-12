package com.marre.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Rule entity")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique identifier for the rule", example = "1")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "The rule description", example = "Rule 101")
    private String rule;

    @JsonProperty("sYear")
    @ApiModelProperty(value = "The starting year for the rule", example = "2023")
    private Integer sYear;

    @NotNull
    @ApiModelProperty(value = "The grade associated with the rule", example = "5")
    private Integer grade;

    @ApiModelProperty(value = "The creation time of the rule", example = "2023-10-01T10:15:30")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "The last update time of the rule", example = "2023-10-01T10:15:30")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "The ID of the user who created the rule", example = "123")
    private Long createUser;

    @ApiModelProperty(value = "The ID of the user who last updated the rule", example = "456")
    private Long updateUser;
}
