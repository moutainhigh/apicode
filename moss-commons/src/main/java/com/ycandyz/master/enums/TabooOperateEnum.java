package com.ycandyz.master.enums;

import com.ycandyz.master.interfaces.CodeEnum;
import lombok.Getter;

@Getter
public enum TabooOperateEnum implements CodeEnum {
    PASS(0,"通过"),
    BLOCK(2,"屏蔽"),
    ;
    private Integer code;
    private String  desc;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    TabooOperateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
