package com.marre.entity.dto;

import lombok.Data;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */
@Data
public class StudentPageQueryDTO {

    private String sName;

    private int page;

    private int pageSize;
}
