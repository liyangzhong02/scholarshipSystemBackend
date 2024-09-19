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
    // application表id 逻辑外键
    private Long id;
    // student表学号 逻辑外键
    private Long sNo;
    // student表学生姓名 逻辑外键
    private String sName;
    // student表学生年级 逻辑外键
    private Integer grade;
    // Rule表具体规则 逻辑外键
    private String rule;
}
