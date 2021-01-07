package com.ycandyz.master.domain.enums.mall;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 商品 实体枚举
 * @author SanGang
 */
public class MallItemOriganizeEnum {


    public enum IsCopy implements IEnum<Integer> {
        Type_0(0, "是否店铺自己数据:不是"),
        Type_1(1, "是否店铺自己数据:是");

        private Integer code;
        private String text;

        IsCopy(Integer code, String text) {
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

        public static MallItemOriganizeEnum.IsCopy parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (MallItemOriganizeEnum.IsCopy value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum IsOrganize implements IEnum<Integer> {
        Type_0(0, "集团供货：否"),
        Type_1(1, "集团供货：是");

        private Integer code;
        private String text;

        IsOrganize(Integer code, String text) {
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

        public static MallItemOriganizeEnum.IsOrganize parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (MallItemOriganizeEnum.IsOrganize value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }


}
