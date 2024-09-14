package com.marre.entity.dto;

import com.marre.enumeration.AuditStatus;
import lombok.Data;

/**
 * @Class: com.marre.entity.dto
 * @ClassName: SubmitApplicationDTO
 * @author: Marre
 * @creat: 9月 14
 * 学生提交奖学金申请DTO
 */
@Data
public class SubmitApplicationDTO {
    // application表 逻辑外键
    private Long id;
    // student表 逻辑外键
    private String sName;
    // student表 逻辑外键
    private Long sNo;
    // student表 逻辑外键
    private Integer grade;
    // Rule表 逻辑外键
    private String rule;
}
