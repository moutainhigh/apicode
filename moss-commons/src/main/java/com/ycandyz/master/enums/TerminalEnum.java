package com.ycandyz.master.enums;

import com.ycandyz.master.interfaces.CodeEnum;
import lombok.Data;
import lombok.Getter;

@Getter
public enum TerminalEnum implements CodeEnum {
    //1-U客企业后台 2-有传运营后台
    FOOTPRINT(1,"U客企业后台"),
    ORGANIZENEWS(2,"有传运营后台"),
    ;
    private Integer code;
    private String  desc;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    TerminalEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
