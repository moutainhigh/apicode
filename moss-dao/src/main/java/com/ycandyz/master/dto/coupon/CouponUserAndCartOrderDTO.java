package com.ycandyz.master.dto.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * coupon_detail_user和mall_cart_order表数据
 */
@Getter
@Setter
public class CouponUserAndCartOrderDTO {

    @ApiModelProperty(value = "优惠券领取记录表id")
    private Long id;
    @ApiModelProperty(value = "优惠券id")
    private Long couponId;
    @ApiModelProperty(value = "购物车订单编号")
    private String orderSn;
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "应付金额（实际支付金额）")
    private BigDecimal payAmount;
    @ApiModelProperty(value = "优惠券抵扣金额")
    private BigDecimal couponDeducted;
    @ApiModelProperty(value = "支付方式：0->未支付；1->支付宝；2->微信")
    private Integer payType;
    @ApiModelProperty(value = "订单状态：10->待付款； 20 已支付；30->已取消")
    private Integer status;
}
