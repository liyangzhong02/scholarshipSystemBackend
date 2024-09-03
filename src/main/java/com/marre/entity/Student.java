package com.marre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.service.ApiListing;

import java.time.LocalDateTime;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Long id;

    private Long sNo;

    private String sName;

    private String sPassword;

    private Double sTotals;

    private Boolean isPrice;

    private String sGrade;

    private Integer sYear;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

}
