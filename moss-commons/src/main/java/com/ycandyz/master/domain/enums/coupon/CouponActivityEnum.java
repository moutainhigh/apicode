package com.ycandyz.master.domain.enums.coupon;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 发卷宝 实体枚举
 * @author SanGang
 */
public class CouponActivityEnum {

    public enum Type implements IEnum<Integer> {
        TYPE_0(0, "全部"),
        TYPE_1(1, "已选");

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

    public enum Enabled implements IEnum<Integer> {
        TYPE_0(0, "禁止"),
        TYPE_1(1, "启用");

        private Integer code;
        private String text;

        Enabled(Integer code, String text) {
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

        public static Enabled parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Enabled value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * 活动状态 类型枚举
     */
    public enum Status implements IEnum<Integer> {
        TYPE_0(0, "默认"),
        TYPE_1(1, "未开始"),
        TYPE_2(2, "进行中"),
        TYPE_3(3, "已结束"),
        TYPE_4(4, "已停止");

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

    /**
     * 活动参与人 枚举
     * @author SanGang
     */
    public enum UserType implements IEnum<Integer> {
        TYPE_0(0, "全部客户"),
        TYPE_1(1, "仅老客户"),
        TYPE_2(2, "仅新客户");

        private Integer code;
        private String text;

        UserType(Integer code, String text) {
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

        public static UserType parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (UserType value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

}
