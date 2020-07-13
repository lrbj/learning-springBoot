package com.example.common.constant;

/**
 * 数据库操作符类型
 */
public enum DBOperator {

    AND(0),
    OR(1);

    private int value;

    private DBOperator(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
