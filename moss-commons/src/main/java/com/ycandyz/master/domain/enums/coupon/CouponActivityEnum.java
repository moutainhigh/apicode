package com.ycandyz.master.domain.enums.coupon;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 发卷宝 实体枚举
 * @author SanGang
 */
public class CouponActivityEnum {

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
    public enum JoinType implements IEnum<Integer> {
        TYPE_0(0, "全部用户"),
        TYPE_1(1, "仅老用户"),
        TYPE_2(2, "仅新用户");

        private Integer code;
        private String text;

        JoinType(Integer code, String text) {
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

        public static JoinType parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (JoinType value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

}
