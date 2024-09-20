package com.marre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.service.ApiListing;

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
    private Long id; // 主键，自增ID

    // 学生学号
    private Long sNo; // 学生学号

    // 学生姓名
    private String sName; // 学生姓名

    // 学生密码（MD5加密存储）
    private String sPassword; // 学生密码

    // 学生总成绩
    private Double sTotals; // 学生总成绩

    // 学生获奖与否 0否 1是
    private Boolean isPrice; // 是否获奖

    // 学生年级（1，2，3）
    private Integer sGrade; // 学生年级

    // 学生应届年份
    private Integer sYear; // 应届年份

    // 获奖类别
    private String awardName;

    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Long createUser; // 创建用户ID
    private Long updateUser; // 更新用户ID
}
