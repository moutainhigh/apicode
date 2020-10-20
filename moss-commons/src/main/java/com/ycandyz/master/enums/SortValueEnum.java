package com.ycandyz.master.enums;

public enum SortValueEnum {
    DEFAULT(0,"默认");
    ;
    private Integer code;
    private String  desc;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    SortValueEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
