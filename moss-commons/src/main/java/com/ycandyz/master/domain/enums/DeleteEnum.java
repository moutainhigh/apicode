package com.ycandyz.master.domain.enums;

/**
 * 删除标识枚举
 * @author SanGang
 */
public enum DeleteEnum {

    ENABLED(0, "未删除"),
    DISABLE(1, "已删除");

    private String desc;
    private int value;

    public int value() {
        return value;
    }

    DeleteEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
