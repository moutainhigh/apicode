package com.ycandyz.master.domain.enums.mall;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 买家寄出的快递表 实体枚举
 * @author SanGang
 */
public class MallBuyerShippingEnum {

    /**
     * 快递订阅推送状态
     */
    public enum PollState implements IEnum<Integer> {
        NOT(0, "买家未确认退回"),
        SUCCESS(1, "成功"),
        FAILED(2, "失败");

        private Integer code;
        private String text;

        PollState(Integer code, String text) {
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
