package com.marre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Long id;

    private Long eNo;

    private String eName;

    private String ePassword;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

}
