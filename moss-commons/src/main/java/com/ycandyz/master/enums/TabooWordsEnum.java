package com.ycandyz.master.enums;

import com.ycandyz.master.interfaces.CodeEnum;
import lombok.Getter;

/**
 * 敏感词表
 */
@Getter
public enum TabooWordsEnum implements CodeEnum {

    POLITICS(0, "政治类"),
    PORNOGRAPHIC(1, "色情类"),
    GRAYINDUSTRIAL(2, "灰色产业");
    ;


    private Integer code;
    private String desc;


    public int value() {
        return code;
    }

    TabooWordsEnum(int value, String desc) {
        this.code = value;
        this.desc = desc;
    }
}
