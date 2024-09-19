package com.marre.enumeration;

import lombok.Data;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: AuditStatus
 * @author: Marre
 * @creat: 2024/9/12 15:41
 * 审核状态枚举
 */
public enum AuditStatus {
    PENDING(0),
    APPROVED(1),
    REJECTED(2);


    private final int value;

    AuditStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AuditStatus fromValue(int value) {
        for (AuditStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid AuditStatus value: " + value);
    }

}
