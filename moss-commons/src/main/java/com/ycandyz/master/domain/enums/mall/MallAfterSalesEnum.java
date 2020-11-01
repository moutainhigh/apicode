package com.ycandyz.master.domain.enums.mall;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 售后枚举
 */
public class MallAfterSalesEnum {


    /**
     * 订单配送状态枚举
     * 物流和线下配送
     * 自提
     */
    public enum ORDER_DELIVER_TYPE implements IEnum<Integer> {
        SEND(1, "配送"),
        ZITI(2, "自提");

        private Integer code;
        private String text;

        ORDER_DELIVER_TYPE(Integer code, String text) {
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

        public static ORDER_DELIVER_TYPE parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (ORDER_DELIVER_TYPE value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum Status implements IEnum<Integer> {
        THTK(10, "退货退款"),
        TK(20, "仅退款");

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
     * 退货退款原因
     * 物流和线下配送 自提(公用)
     */
    public enum THTK implements IEnum<Integer> {

        THTK_10402010(10402010, "多拍/拍错/不想要"),
        THTK_10402020(10402020, "不喜欢/效果不好"),
        THTK_10402030(10402030, "做工/质量问题"),
        THTK_10402040(10402040, "商家发错货"),
        THTK_10402050(10402050, "未按约定时间发货"),
        THTK_10402060(10402060, "与商品描述严重不符"),
        THTK_10402070(10402070, "收到商品少件/破损/污渍"),
        THTK_10402099(10402099, "其他");

        private Integer code;
        private String text;

        THTK(Integer code, String text) {
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

        public static THTK parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (THTK value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * 仅退款 货物状态枚举
     */
    public enum TK implements IEnum<Integer>{
        TK_RECEIVE_NOT(0,"未收到货"),
        TK_RECEIVE(1,"已收到货");

        private Integer code;
        private String text;

        TK(Integer code, String text) {
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

        public static TK parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (TK value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * 仅退款原因 未收到货
     */
    public enum TK_RECEIVE_NOT implements IEnum<Integer> {

        TK_RECEIVE_NOT_20401010(20401010, "空包裹"),
        TK_RECEIVE_NOT_20401020(20401020, "快递/物流一直未到"),
        TK_RECEIVE_NOT_20401030(20401030, "快递/物流无跟踪记录"),
        TK_RECEIVE_NOT_20401040(20401040, "货物破损/变质");

        private Integer code;
        private String text;

        TK_RECEIVE_NOT(Integer code, String text) {
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

        public static TK_RECEIVE_NOT parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (TK_RECEIVE_NOT value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * 仅退款原因 已收到货
     * 物流和线下配送 自提(公用)
     *
     */
    public enum TK_RECEIVE implements IEnum<Integer> {

        TK_RECEIVE_20402010(20402010, "不喜欢/效果不好"),
        TK_RECEIVE_20402020(20402020, "做工/质量问题"),
        TK_RECEIVE_20402030(20402030, "商家发错货未按约定时间发货"),
        TK_RECEIVE_20402040(20402040, "与商品描述严重不符"),
        TK_RECEIVE_20402050(20402050, "收到商品少件/破损/污渍"),
        TK_RECEIVE_20402099(20402099, "其他");

        private Integer code;
        private String text;

        TK_RECEIVE(Integer code, String text) {
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

        public static TK_RECEIVE parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (TK_RECEIVE value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * 仅退款原因
     */
    public enum JTK implements IEnum<Integer> {
        TK_RECEIVE_20402010(20402010, "不喜欢/效果不好"),
        TK_RECEIVE_20402020(20402020, "做工/质量问题"),
        TK_RECEIVE_20402030(20402030, "商家发错货未按约定时间发货"),
        TK_RECEIVE_20402040(20402040, "与商品描述严重不符"),
        TK_RECEIVE_20402050(20402050, "收到商品少件/破损/污渍"),
        TK_RECEIVE_20402099(20402099, "其他"),
        TK_RECEIVE_NOT_20401010(20401010, "空包裹"),
        TK_RECEIVE_NOT_20401020(20401020, "快递/物流一直未到"),
        TK_RECEIVE_NOT_20401030(20401030, "快递/物流无跟踪记录"),
        TK_RECEIVE_NOT_20401040(20401040, "货物破损/变质");

        private Integer code;
        private String text;

        JTK(Integer code, String text) {
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

        public static JTK parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (JTK value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * 售后状态
     */
    public enum SUB_STATUS implements IEnum<Integer> {
        SUB_STATUS_1010(1010, "一次审核中"),
        SUB_STATUS_1020(1020, "一次审核不通过"),
        SUB_STATUS_1030(1030, "一次审核通过/待用户退货"),
        SUB_STATUS_1040(1040, "超时未退货，系统关闭"),
        SUB_STATUS_1050(1050, "用户已退货/退款中"),
        SUB_STATUS_1060(1060, "二次审核不通过/拒绝退款"),
        SUB_STATUS_1070(1070, "二次审核通过"),
        SUB_STATUS_1080(1080, "退款成功"),
        SUB_STATUS_1090(1090, "退款失败"),
        SUB_STATUS_2010(2010, "退款中"),
        SUB_STATUS_2020(2020, "审核通过"),
        SUB_STATUS_2030(2030, "退款成功"),
        SUB_STATUS_2040(2040, "退款失败"),
        SUB_STATUS_2050(2050, "退款拒绝");

        private Integer code;
        private String text;

        SUB_STATUS(Integer code, String text) {
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

        public static SUB_STATUS parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (SUB_STATUS value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * 售后 订单状态
     */
    public enum ORDER_AFTER_SALES_STATE implements IEnum<Integer> {
        AUDIT_WAIT(1, "待审核"),
        SEND_WAIT(2, "待买家退货"),
        AUDIT2_WAIT(3, "待确认退款"),
        SUCCESS(4, "退款成功"),
        FAILED(5, "退款失败"),
        CLOSE(6, "退款关闭");

        private Integer code;
        private String text;

        ORDER_AFTER_SALES_STATE(Integer code, String text) {
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

        public static ORDER_AFTER_SALES_STATE parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (ORDER_AFTER_SALES_STATE value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }

    //退款
    // 退款失败
    public static Integer PAY_REFUND_FAILED = 60000;
    //退款已经成功
    public static Integer PAY_REFUND_HAS_SUCCESS = 60001;


}
