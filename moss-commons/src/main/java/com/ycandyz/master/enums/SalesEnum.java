package com.ycandyz.master.enums;

public enum SalesEnum {

    /** 售后 */
    ORDER_AFTER_SALES_STATUS_ILLEGAL("售后类型不在指定范围内"),
    ORDER_AFTER_SALES_SKU_NO_NONE("SKU编号为空"),
    ORDER_AFTER_SALES_SKU_NONE("SKU为空"),
    ORDER_AFTER_SALES_QUANTITY_NONE("退款数量为空"),
    ORDER_AFTER_SALES_REMARK_NONE("退款说明为空"),
    ORDER_AFTER_SALES_NONE("售后为空"),
    ORDER_AFTER_SALES_NO_NONE("售后编号为空"),
//    ORDER_AFTER_SALES_STATUS_ILLEGAL("售后类型非法"),
    ORDER_AFTER_SALES_REASON_ILLEGAL("{}原因不在指定范围内"),
    ORDER_AFTER_SALES_SUB_STATUS_ERR("售后状态不对"),

    ORDER_AFTER_SALES_RECEIVER_NONE("收件人为空"),
    ORDER_AFTER_SALES_RECEIVER_PHONE_NONE("收件电话为空"),
    ORDER_AFTER_SALES_RECEIVER_ADDRESS_NONE("收件地址为空"),
    ORDER_AFTER_SALES_COMPANY_NONE("物流公司为空"),
    ORDER_AFTER_SALES_NUMBER_NONE("物流单号为空"),

    ORDER_AFTER_SALES_LOG_APPLY("您发起了售后申请，退款原因：{}，退款数量：{}件，退款金额：¥{}"),
    ORDER_AFTER_SALES_LOG_AUDIT_FIRST_PASS("商家已同意了本次售后服务申请，请及时退货并填写物流信息。商家退货地址：王王王，13465869040，广东省深圳市福田区新天地大厦A栋301室"),
    ORDER_AFTER_SALES_LOG_AUDIT_FIRST_REFUSE("退款已关闭，商家拒绝了您的售后申请，建议与商家客服进一步沟通解决"),
    ORDER_AFTER_SALES_LOG_BUYER_SEND("您已退货并填写了物流信息，物流公司：韵达快递，物流单号：430555782923"),
    ORDER_AFTER_SALES_LOG_AUDIT_SECOND_PASS("商家已同意了退款申请，退款金额¥{}"),
    ORDER_AFTER_SALES_LOG_AUDIT_SECOND_REFUSE("退款已关闭，商家拒绝了您的售后申请，建议与商家客服进一步沟通解决"),


    /** 退货退款，售后协商日志，买家视角 */
    ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_APPLY("您发起了售后申请，退款原因：{}，退款数量：{}件，退款金额：¥{}"),
    ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_AUDIT_FIRST_PASS("商家已同意本次售后服务申请，请及时退货并填写物流信息。商家退货地址：{ADDRESS} 退款金额¥{MONEY}"),
    ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_AUDIT_FIRST_REFUSE("商家拒绝了售后申请，退款已关闭。建议与商家客服进一步沟通解决"),
    ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_SYS_CLOSE("由于您超时未退货/未填写退货信息，退款已关闭"),
    ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_SEND("您已退货并填写了物流信息，物流公司：{},物流单号{}"),
    ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_AUDIT_SECOND_PASS("商家已同意退款申请，退款金额￥{}"),
    ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_AUDIT_SECOND_REFUSE("退款已关闭，商家拒绝了您的售后申请，建议与商家客服进一步沟通解决"),

    /** 退货退款，售后协商日志，商家视角 */
    ORDER_AFTER_SALES_LOG_THTK_SHOP_ANGLE_APPLY("买家发起了退货退款申请，退款原因：{}，退款件数：{}件，退款金额¥{}"),
    ORDER_AFTER_SALES_LOG_THTK_SHOP_ANGLE_AUDIT_FIRST_PASS("商家已同意本次退货退款申请，退货地址：{}。退款金额¥{}"),
    ORDER_AFTER_SALES_LOG_THTK_SHOP_ANGLE_AUDIT_FIRST_REFUSE("商家拒绝了退货退款申请，退款已关闭"),
    ORDER_AFTER_SALES_LOG_THTK_SHOP_ANGLE_SYS_CLOSE("由于买家超时未退货/未填写退货信息，退款已关闭"),
    ORDER_AFTER_SALES_LOG_THTK_SHOP_ANGLE_SEND("买家已退货并填写了物流信息，物流公司：{},物流单号{}"),
    ORDER_AFTER_SALES_LOG_THTK_SHOP_ANGLE_AUDIT_SECOND_PASS("商家已同意退款申请，退款金额¥{}"),
    ORDER_AFTER_SALES_LOG_THTK_SHOP_ANGLE_AUDIT_SECOND_REFUSE("商家拒绝了退货退款申请，退款已关闭"),

    /** 仅退款，售后协商日志，买家视角 */
    ORDER_AFTER_SALES_LOG_TK_BUYER_ANGLE_APPLY("您发起了售后申请，货物状态：{}，退款原因：{}，退款数量：{}件，退款金额：¥{}"),
    ORDER_AFTER_SALES_LOG_TK_BUYER_ANGLE_AUDIT_PASS("商家同意了退款申请退款金额：¥{}"),
    ORDER_AFTER_SALES_LOG_TK_BUYER_ANGLE_AUDIT_REFUSE("商家拒绝了您的仅退款申请，建议与商家客服进一步沟通解决"),

    /** 仅退款，售后协商日志，商家视角 */
    ORDER_AFTER_SALES_LOG_TK_SHOP_ANGLE_APPLY("买家发起了售后申请，货物状态：{}，退款原因：{}，退款数量：{}件，退款金额：¥{}"),
    ORDER_AFTER_SALES_LOG_TK_SHOP_ANGLE_AUDIT_PASS("商家已同意了仅退款申请，退款金额：¥{}"),
    ORDER_AFTER_SALES_LOG_TK_SHOP_ANGLE_AUDIT_REFUSE("商家拒绝了仅退款申请，退款已关闭"),

    /** 仅退款，是否售后 */
    ORDER_AFTER_SALES_RECEIVE_YES("已收货"),
    ORDER_AFTER_SALES_RECEIVE_NO("未收货");

    private String description;

    public String description() {
        return description;
    }

    SalesEnum(String description) {
        this.description = description;
    }

}
