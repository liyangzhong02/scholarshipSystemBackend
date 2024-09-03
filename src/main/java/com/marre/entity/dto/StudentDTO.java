package com.marre.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */
@Data
public class StudentDTO {


    private Long id;

    private Long sNo;

    private String sName;

    private String sGrade;

    private Integer sYear;


}
