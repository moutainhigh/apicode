package com.ycandyz.master.domain.enums.ad;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 首页-轮播图 实体枚举
 * @author SanGang
 */
public class AdvertisingEnum {

    /**
     * Type 类型枚举
     */
    public enum Type implements IEnum<Integer> {
        GOODS(0, "商品"),
        PAGE(1, "页面");

        private Integer code;
        private String text;

        Type(Integer code, String text) {
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

        public static Type parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Type value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * 启用/禁用枚举
     * @author SanGang
     */
    public enum EnabledEnum implements IEnum<Integer> {
        DISABLE(0, "启用"),
        ENABLE(1, "不启用");

        private Integer code;
        private String text;

        EnabledEnum(Integer code, String text) {
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

        public static EnabledEnum parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (EnabledEnum value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

}
