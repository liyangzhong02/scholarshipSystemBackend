package com.marre.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: Application
 * @author: Marre
 * @creat: 2024/9/12 09:45
 * 审核实体类
 */
@Data
public class Application {

    private Long id;

    //逻辑外键 与学号绑定
    private Long sNo;

    private Boolean status;

    private String tips;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
