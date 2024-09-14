package com.marre.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Class: com.marre.entity
 * @ClassName: Awards
 * @author: Marre
 * @creat: 9月 13
 * 获奖表
 */
@Entity
@Data
public class Awards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键，自增ID

    // 逻辑外键 指向获奖学生
    private Long sNo; // 学生学号

    // 逻辑外键 学生姓名
    private String sName; // 学生姓名

    // 逻辑外键 学生年级
    private String sGrade; // 学生年级

    // 逻辑外键 学生年份
    private Integer sYear; // 学生应届年份

    // 逻辑外键 学生分数
    private Double sTotals; // 学生总分数

    // 奖项名称
    private String awardName; // 奖项名称
}
