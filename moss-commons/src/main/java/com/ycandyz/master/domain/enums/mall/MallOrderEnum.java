package com.ycandyz.master.domain.enums.mall;


import com.ycandyz.master.domain.enums.IEnum;

/**
 * 订单 实体枚举
 * @author SanGang
 */
public class MallOrderEnum {

    /**
     * 售后状态类型枚举
     */
    public enum AfterSalesStatus implements IEnum<Integer> {
        NO(0, "不是售后"),
        START(1, "开始处理中"),
        END(2, "已结束");

        private Integer code;
        private String text;

        AfterSalesStatus(Integer code, String text) {
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
