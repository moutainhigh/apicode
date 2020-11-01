package com.ycandyz.master.domain.enums.mall;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 商品 实体枚举
 * @author SanGang
 */
public class MallItemEnum {

    /**
     * 商品状态 类型枚举 10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除
     */
    public enum Status implements IEnum<Integer> {
        START_10(10, "上架中"),
        START_20(20, "已下架"),
        START_30(30, "售罄"),
        START_40(40, "仓库中"),
        START_50(50, "删除");

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
