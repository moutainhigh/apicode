package com.ycandyz.master.domain.enums.coupon;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 发卷宝 实体枚举
 * @author SanGang
 */
public class CouponActivityUserEnum {

    public enum Status implements IEnum<Integer> {
        TYPE_0(0, "待使用"),
        TYPE_1(1, "过期"),
        TYPE_2(2, "已使用");

        private Integer code;
        private String text;

        Status(Integer code, String text) {
            this.code = code;
            this.text = text;
        }

        @Override
        public Integer getCode() {
            return code;
        }
        @Override
        public String getText() {
            return text;
        }

        public static Status parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Status value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }


}
