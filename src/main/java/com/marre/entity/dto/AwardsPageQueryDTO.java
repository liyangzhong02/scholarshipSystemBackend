package com.marre.entity.dto;

import lombok.Data;

/**
 * @Class: com.marre.entity.dto
 * @ClassName: AwardsDTO
 * @author: Marre
 * @creat: 9月 13
 * 获奖分页查询DTO
 */
@Data
public class AwardsPageQueryDTO {
    private String sName;
    //逻辑外键 学生年级
    private String sGrade;
    // 逻辑外键 学生年份
    private Integer sYear;
    private String awardName;
    private int page;
    private int pageSize;
}
