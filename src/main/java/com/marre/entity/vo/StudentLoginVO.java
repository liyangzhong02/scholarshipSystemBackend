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
public class StudentLoginVO {

    private Long id;

    private Long sNo;

    private String sPassword;

    private String token;
}
