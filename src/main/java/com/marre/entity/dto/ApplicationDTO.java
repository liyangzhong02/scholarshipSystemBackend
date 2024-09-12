package com.marre.entity.dto;

import lombok.Data;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ApplicationDTO
 * @author: Marre
 * @creat: 2024/9/12 09:43
 * 奖学金审核DTO
 */
@Data
public class ApplicationDTO {

    //1通过0未通过
    private Boolean status;

    //备注消息
    private String tips;
}
