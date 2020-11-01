package com.ycandyz.master.domain.enums.mall;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 商品分类 实体枚举
 * @author SanGang
 */
public class MallCategoryEnum {

    /**
     * 分类状态
     */
    public enum Status implements IEnum<Integer> {
        NO(0, "无效"),
        YES(1, "正常");

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
    }
}
