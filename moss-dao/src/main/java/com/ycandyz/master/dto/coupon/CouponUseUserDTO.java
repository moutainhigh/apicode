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
    private Integer source;
    @ApiModelProperty(value = "优惠券状态 使用状态(0未使用,1已使用)")
    private Integer couponStatus;
    @ApiModelProperty(value = "券生效开始时间 ")
    private Date couponUserBeginTime;
    @ApiModelProperty(value = "券生效结束时间 ")
    private Date couponUserEndTime;
    @ApiModelProperty(value = "优惠券有效时间类型 0:时间段(自然日)，1:领券当日起计算天数，2:领券次日起计算天数")
    private Integer validityType;
    @ApiModelProperty(value = "券有效天数")
    private Integer days;
    @ApiModelProperty(value = "优惠券有效时间类型-中文")
    private String validityTypeMsg;
    @ApiModelProperty(value = "优惠内容")
    private String couponTypeMsg;
    @ApiModelProperty(value = "优惠领取时间")
    private Date takeTime;
    @ApiModelProperty(value = "使用门槛：0:无门槛，1:订单满减可用")
    private Integer useType;
    @ApiModelProperty(value = "订单满多少元可用")
    private BigDecimal fullMoney;
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountMoney;
    @ApiModelProperty(value = "领取页面：0:购物车页面直接获取，1:商品详情页直接获取")
    private String obtain;
    @ApiModelProperty(value = "订单编号")
    private String orderSn;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal payAmount;
    @ApiModelProperty(value = "订单状态 10->待付款； 20 已支付；30->已取消 ")
    private Integer orderStatus;
    @ApiModelProperty(value = "下单时间")
    private Date orderAtStr;
    @ApiModelProperty(value = "商品名称")
    private List<String> itemNameList;
}
