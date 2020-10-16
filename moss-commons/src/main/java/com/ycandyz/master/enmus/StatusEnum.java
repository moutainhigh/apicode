package com.ycandyz.master.enmus;

public enum StatusEnum {
    DEFAULT(1,"默认");
    ;
    private Integer code;
    private String  desc;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
