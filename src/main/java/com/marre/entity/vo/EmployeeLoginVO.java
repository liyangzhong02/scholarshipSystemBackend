package com.marre.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoginVO {


    private Long id;

    private Long eNo;

    private String ePassword;

    private String token;
}
