package com.ycandyz.master.enums;

/**
 * 启用/禁用枚举
 */
public enum EnabledEnum {

    ENABLED(0, "启用"),
    DISABLE(1, "禁用");

    private String desc;
    private int value;

    public int value() {
        return value;
    }

    EnabledEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
