package com.marre.entity.dto;

import com.marre.enumeration.AuditStatus;
import lombok.Data;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: AuditDTO
 * @author: Marre
 * @creat: 2024/9/12 16:48
 * 审核申请DTO
 */
@Data
public class AuditDTO {

    private Long id;

    private AuditStatus status;
}
