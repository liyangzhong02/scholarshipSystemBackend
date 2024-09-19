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
    private Long id;

    // Student表学号 逻辑外键
    private Long sNo;

    // Student表学生姓名 逻辑外键
    private String sName;

    // Enum类 审核进行状态
    private AuditStatus status;

    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Long createUser; // 创建用户ID
    private Long updateUser; // 更新用户ID
}
