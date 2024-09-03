package com.marre.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */
@Data
public class EmployeeLoginDTO {

    private Long eNo;

    private String ePassword;

}
