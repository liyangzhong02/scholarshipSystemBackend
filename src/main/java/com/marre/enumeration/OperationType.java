package com.marre.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库操作类型
 */
@Getter
@AllArgsConstructor
public enum OperationType {

    /**
     * 更新操作
     */
    UPDATE,

    /**
     * 插入操作
     */
    INSERT

}
