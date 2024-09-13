package com.marre.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Class: com.marre.entity.dto
 * @ClassName: RuleApplicationDTO
 * @author: Marre
 * @creat: 9月 13
 * 提交申请时附带的RuleDTO
 */
@Data
public class RuleApplicationDTO {

    private Long id;

    private String rule;

    private Integer grade;

}
