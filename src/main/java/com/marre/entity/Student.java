package com.marre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 学生学号
    private Long sNo;

    // 学生姓名
    private String sName;

    // 学生密码（MD5加密存储）
    private String sPassword;

    // 学生总成绩
    private Double sTotals;

    // 学生获奖与否 0否 1是
    private Boolean isPrice;

    // 学生年级（1，2，3）
    private Integer sGrade;

    // 学生应届年份
    private Integer sYear;

    // 获奖类别
    private String awardName;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
}
