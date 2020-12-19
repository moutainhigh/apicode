package com.ycandyz.master.dto.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CouponUseUserDTO {

    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "优惠券id")
    private Long couponId;
    @ApiModelProperty(value = "优惠券详情id")
    private Long couponDetailId;
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;
    @ApiModelProperty(value = "用户信息")
    private String userMsg;
    @ApiModelProperty(value = "优惠券来源 0推广,1活动,2页面")
    private String source;
    @ApiModelProperty(value = "优惠券状态")
    private String couponStatus;
    @ApiModelProperty(value = "优惠券有效时间类型 0:时间段(自然日)，1:领券当日起计算天数，2:领券次日起计算天数")
    private Integer validityType;
    @ApiModelProperty(value = "优惠券有效时间类型-中文")
    private String validityTypeMsg;
    @ApiModelProperty(value = "优惠内容")
    private String couponTypeMsg;
    @ApiModelProperty(value = "优惠领取时间")
    private Date takeTime;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal realMoney;
    @ApiModelProperty(value = "订单状态 10-待支付  20-待发货 30-待收货 40-已收货  50-已取消 ")
    private Integer orderStatus;
    @ApiModelProperty(value = "下单时间")
    private Date orderAtStr;
    @ApiModelProperty(value = "商品名称")
    private List<String> itemNameList;
}
