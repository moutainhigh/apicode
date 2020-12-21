package com.ycandyz.master.model.coupon;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@TableName("coupon_detail")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CouponUseUserVO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "优惠券id")
    private Long couponId;
    @ApiModelProperty(value = "优惠券详情id")
    private Long couponDetailId;
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;
    @ApiModelProperty(value = "用户信息")
    private String userName;
    @ApiModelProperty(value = "优惠券来源 0推广,1活动,2购物车页面直接获取,3商品详情页直接获取")
    private Integer source;
    @ApiModelProperty(value = "优惠券状态 1:已使用,2:待使用,3:过期")
    private Integer status;
    @ApiModelProperty(value = "优惠券有效时间类型 0:时间段(自然日)，1:领券当日起计算天数，2:领券次日起计算天数")
    private Integer validityType;
    @ApiModelProperty(value = "优惠券有效时间类型-中文")
    private String validityTypeMsg;
    @ApiModelProperty(value = "优惠内容")
    private String couponTypeMsg;
    @ApiModelProperty(value = "优惠领取时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date takeTime;
    @ApiModelProperty(value = "订单编号")
    private String orderSn;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal payAmount;
    @ApiModelProperty(value = "订单状态 10-待支付  20-待发货 30-待收货 40-已收货  50-已取消 ")
    private Integer orderStatus;
    @ApiModelProperty(value = "下单时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderAtStr;
    @ApiModelProperty(value = "商品名称")
    private List<String> itemNameList;

}
