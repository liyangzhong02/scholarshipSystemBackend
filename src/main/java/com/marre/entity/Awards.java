package com.marre.entity;

import lombok.Data;

/**
 * @Class: com.marre.entity
 * @ClassName: Awards
 * @author: Marre
 * @creat: 9月 13
 * 获奖表
 */
@Data
public class Awards {
    private Long id;
    // 逻辑外键 指向获奖学生
    private Long sNo;
    // 逻辑外键 学生姓名
    private String sName;
    //逻辑外键 学生年级
    private String sGrade;
    // 逻辑外键 学生年份
    private Integer sYear;
    //逻辑外键 学生分数
    private Double sTotals;
    private String awardName;
}
