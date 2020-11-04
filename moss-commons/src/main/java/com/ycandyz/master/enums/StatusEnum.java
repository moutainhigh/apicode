package com.ycandyz.master.enums;

import com.ycandyz.master.interfaces.CodeEnum;
import lombok.Getter;

@Getter
public enum StatusEnum implements CodeEnum {
    //delProperty(value = "10-待支付  20-待发货 30-待收货 40-已收货  50-已取消",name="status",required=true)
    DEFAULT(1,"默认"),
    ALL(9,"全部"), //包含已取消
    TOBEPAID(10,"待支付"),
    TOBEDELIVERED(20,"待发货"),
    TOBERECEIVED(30,"待收货"),
    RECEIVED(40,"已收货"),
    CaANCEELED(50,"已取消"),
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
