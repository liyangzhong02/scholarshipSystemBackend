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

    //逻辑外键 与学号绑定
    private Long sNo;

    //由id来获取sName
    private String sName;

    private AuditStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
