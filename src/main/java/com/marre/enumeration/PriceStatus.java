package com.marre.enumeration;

/**
 * @Class: com.marre.enumeration
 * @ClassName: PriceStatus
 * @author: Marre
 * @creat: 9月 14
 * 获奖与否枚举类
 */
public enum PriceStatus {
    NOT_AWARDED(0), // 0: 未获奖
    AWARDED(1);     // 1: 已获奖


    private final int value;

    PriceStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
