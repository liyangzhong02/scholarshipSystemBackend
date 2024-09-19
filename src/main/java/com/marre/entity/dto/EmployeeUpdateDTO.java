package com.marre.entity.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @Class: com.marre.entity.dto
 * @ClassName: EmployeeUpdateDTO
 * @author: Marre
 * @creat: 9月 19
 * 员工修改DTO
 */
@Data
public class EmployeeUpdateDTO {
    // 教师工号
    private Long eNo; // 教师工号

    // 教师姓名
    private String eName; // 教师姓名

    // 教师密码（MD5加密存储）
    private String ePassword; // 教师密码

}
