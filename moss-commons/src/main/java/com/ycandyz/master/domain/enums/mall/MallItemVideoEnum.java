package com.ycandyz.master.domain.enums.mall;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 商品视频 实体枚举
 * @author SanGang
 */
public class MallItemVideoEnum {

    /**
     * 审核状态
     */
    public enum Audit implements IEnum<Integer> {
        START_0(0, "待审核"),
        START_1(1, "通过"),
        START_2(2, "未通过");

        private Integer code;
        private String text;

        Audit(Integer code, String text) {
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

        public static Audit parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Audit value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum Status implements IEnum<Integer> {
        START_0(0, "未投诉"),
        START_1(1, "投诉通过"),
        START_2(2, "投诉状态"),
        START_3(3, "投诉拒绝通过");

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

    public enum Check implements IEnum<Integer> {
        CHECK_0(0, "通过"),
        CHECK_1(1, "拒绝");

        private Integer code;
        private String text;

        Check(Integer code, String text) {
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

        public static Check parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Check value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum Type implements IEnum<Integer> {
        TYPE_0(0, "置顶视频"),
        TYPE_1(1, "详情视频");

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
}
