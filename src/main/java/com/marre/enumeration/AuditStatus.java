package com.marre.enumeration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: AuditStatus
 * @author: Marre
 * @creat: 2024/9/12 15:41
 * 审核状态枚举
 */
@Getter
@AllArgsConstructor
public enum AuditStatus {
    // 待审核
    PENDING(0),
    // 审核通过
    APPROVED(1),
    // 审核不通过
    REJECTED(2);

    private final int value;
}
