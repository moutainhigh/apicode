package com.ycandyz.master.enums;

public enum PlatformEnum {

    TYPE_0(0, "企业"),
    TYPE_1(1, "前端"),
    TYPE_2(2, "代理商"),
    TYPE_3(3, "有传"),
    TYPE_4(4, "U客后台"),
    TYPE_5(5, "U客APP");

    private Integer code;
    private String text;

    PlatformEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static PlatformEnum parseCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (PlatformEnum value : values()) {
            if (code.equals(value.code)) {
                return value;
            }
        }
        return null;
    }
}
