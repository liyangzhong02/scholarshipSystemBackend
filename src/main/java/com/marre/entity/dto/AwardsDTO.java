package com.marre.entity.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Class: com.marre.entity.dto
 * @ClassName: AwardsDTO
 * @author: Marre
 * @creat: 9月 19
 * 奖学金评定dto
 */
@Data
public class AwardsDTO {

    // 逻辑外键 指向获奖学生
    private Long sNo; // 学生学号

    // 逻辑外键 学生年级
    private String sGrade; // 学生年级

    // 逻辑外键 学生年份
    private Integer sYear; // 学生应届年份

    // 逻辑外键 学生分数
    private Double sTotals; // 学生总分数

}
