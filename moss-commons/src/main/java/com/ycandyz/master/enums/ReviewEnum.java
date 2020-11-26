package com.ycandyz.master.enums;

import com.ycandyz.master.interfaces.CodeEnum;
import lombok.Getter;

@Getter
public enum ReviewEnum implements CodeEnum {
    MALLITEM(0,"mallItem"),
    FOOTPRINT(1,"footprint"),
    ORGANIZENEWS(2,"organize_news"),
    ;
    private Integer code;
    private String  desc;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    ReviewEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
