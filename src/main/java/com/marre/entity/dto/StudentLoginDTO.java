package com.marre.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */
@Data
public class StudentLoginDTO {

    private Long sNo;

    private String sPassword;
}
