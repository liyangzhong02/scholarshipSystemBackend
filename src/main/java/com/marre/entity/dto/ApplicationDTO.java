package com.marre.entity.dto;

import com.marre.enumeration.AuditStatus;
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

    private Long id;

    private String sName;

    private Long sNo;

    private AuditStatus status;

}
