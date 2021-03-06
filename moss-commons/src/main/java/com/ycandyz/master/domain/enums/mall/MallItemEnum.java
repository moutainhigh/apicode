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

    public enum IsScreen implements IEnum<Integer> {
        START_0(0, "正常"),
        START_1(1, "风控");

        private Integer code;
        private String text;

        IsScreen(Integer code, String text) {
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

        public static MallItemEnum.IsScreen parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (MallItemEnum.IsScreen value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum Type implements IEnum<Integer> {
        Type_0(0, "非销售商品"),
        Type_1(1, "商品");

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

        public static MallItemEnum.Type parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (MallItemEnum.Type value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum NonPriceType implements IEnum<Integer> {
        Type_0(0, "非销售商品-价格类型-不显示"),
        Type_1(1, "非销售商品-价格类型-显示");

        private Integer code;
        private String text;

        NonPriceType(Integer code, String text) {
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

        public static MallItemEnum.NonPriceType parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (MallItemEnum.NonPriceType value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum IsGroupSupply implements IEnum<Integer> {
        Type_0(0, "集团供货：否"),
        Type_1(1, "集团供货：是");

        private Integer code;
        private String text;

        IsGroupSupply(Integer code, String text) {
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

        public static MallItemEnum.IsGroupSupply parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (MallItemEnum.IsGroupSupply value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum IsAll implements IEnum<Integer> {
        Type_0(0, "全部门店"),
        Type_1(1, "指定门店");

        private Integer code;
        private String text;

        IsAll(Integer code, String text) {
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

        public static MallItemEnum.IsAll parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (MallItemEnum.IsAll value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }


}
