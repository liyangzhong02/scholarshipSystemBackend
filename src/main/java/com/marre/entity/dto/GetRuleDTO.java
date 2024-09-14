package com.marre.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Class: com.marre.entity.dto
 * @ClassName: GetRuleDTO
 * @author: Marre
 * @creat: 9月 14
 * 前端获取Rule的DTO
 */
@Data
public class GetRuleDTO {

    private Integer year;

    private Integer grade;
}
