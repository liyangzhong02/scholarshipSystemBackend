package com.marre.entity.dto;

import lombok.Data;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ApplicationPageQueryDTO
 * @author: Marre
 * @creat: 2024/9/13 09:24
 * 奖学金申请的分页查询DTO
 */
@Data
public class ApplicationPageQueryDTO {
    private String sName;
    private int page;
    private int pageSize;
}
