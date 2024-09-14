package com.marre.entity;

import com.marre.enumeration.AuditStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: Application
 * @author: Marre
 * @creat: 2024/9/12 09:45
 * 审核实体类
 */
@Entity
@Data
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键，自增ID

    // 逻辑外键 与学号绑定
    private Long sNo; // 学生学号

    // 逻辑外键：由id来获取sName
    private String sName; // 学生姓名

    // 审核进行情况 enum
    private AuditStatus status; // 审核状态

    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Long createUser; // 创建用户ID
    private Long updateUser; // 更新用户ID
}
