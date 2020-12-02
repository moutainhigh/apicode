package com.ycandyz.master.domain.enums.mall;


import com.ycandyz.master.domain.enums.IEnum;
import com.ycandyz.master.domain.enums.ad.SpecialEnum;

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

    public enum CancelReason implements IEnum<Integer> {
        CR_501010(501010, "多拍/拍错/不想要"),
        CR_501011(501011, "不喜欢/效果不好"),
        CR_501012(501012, "做工/质量问题"),
        CR_501013(501013, "商家发错货"),
        CR_501014(501014, "未按约定时间发货"),
        CR_501015(501015, "与商品描述严重不符"),
        CR_501016(501016, "收到商品少件/破损/污渍"),
        CR_501099(501099, "其他"),
        CR_503010(503010, "多拍/拍错/不想要"),
        CR_503011(503011, "协商一致退款"),
        CR_503012(503012, "缺货"),
        CR_503013(503013, "未按约定时间发货"),
        CR_503099(503099, "其他"),
        CR_504010(504010, "暂停售卖"),
        CR_504011(504011, "缺货/断货"),
        CR_504099(504099, "其他");

        private Integer code;
        private String text;

        CancelReason(Integer code, String text) {
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

        public static MallOrderEnum.CancelReason parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (MallOrderEnum.CancelReason value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }
}
