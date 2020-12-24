package com.ycandyz.master.domain.response.coupon;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * @Description 发卷宝-优惠卷 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CouponActivityCouponResp {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "begin_time",value = "券生效开始时间")
    private Date beginTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "end_time",value = "券生效结束时间")
    private Date endTime;

    @ApiModelProperty(value = "优惠券详情编码")
    private String couponDetailNo;

    @ApiModelProperty(value = "标记优惠券关联详情：0-以前；1-当前")
    private Integer status;

    @ApiModelProperty(value = "券有效天数")
    private Integer days;

    @ApiModelProperty(value = "领取人限制：0:所有用户")
    private Integer useType;

    @ApiModelProperty(value = "订单满多少元可用")
    private BigDecimal fullMoney;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountMoney;

    @ApiModelProperty(value = "券有效期类型：0:时间段(自然日)，1:领券当日起计算天数，2:领券次日起计算天数")
    private Integer validityType;

    @ApiModelProperty(name = "activity_coupon_id",value = "卷宝-优惠卷 ID")
    private Long activityCouponId;

    @ApiModelProperty(value = "是否选中")
    private Boolean selected;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠券编号")
    private String couponNo;

    @ApiModelProperty(value = "领取数量")
    private Integer obtainNum;

    @ApiModelProperty(value = "核销数量")
    private Integer useNum;

    @ApiModelProperty(value = "优惠券状态：0:停止,1:开始")
    private Integer couponStatus;

    @ApiModelProperty(name = "surplus_num",value = "优惠卷剩余数量")
    private Integer surplusNum;

}
