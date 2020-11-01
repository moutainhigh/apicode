package com.ycandyz.master.domain.enums.ad;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 首页-分类页面 实体枚举
 * @author SanGang
 */
public class HomeCategoryEnum {

    /**
     * ShopNo 类型枚举
     */
    public enum Shop implements IEnum<Integer> {
        SHOP(1, "属于商店私有"),
        SHOP_NOT(0, "不属于商店");

        private Integer code;
        private String text;

        Shop(Integer code, String text) {
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
    }

}
