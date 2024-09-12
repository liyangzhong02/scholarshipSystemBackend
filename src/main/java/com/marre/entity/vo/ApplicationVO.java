package com.marre.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ApplicationVO
 * @author: Marre
 * @creat: 2024/9/12 09:51
 * 审核VO
 */
@Data
public class ApplicationVO {

    private Long id;

    private Boolean status;

    private String tips;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
