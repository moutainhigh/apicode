package com.ycandyz.master.enums;

import lombok.Getter;


public enum LayerEnum {
    FITRSTLAYER(0,"第一级"),
    SECONDLAYER(1,"第二级");
    ;
    private Integer code;
    private String  desc;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    LayerEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
