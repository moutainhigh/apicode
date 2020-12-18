package com.ycandyz.master.domain.model.coupon;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.Serializable;

/**
 * <p>
 * @Description 用户领取优惠券记录表 Model
 * </p>
 *
 * @author SanGang
 * @since 2020-12-18
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="用户领取优惠券记录表-Model")
public class CouponDetailUserModel implements Serializable {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "user_id", value = "用户id")
   private Long userId;

   @ApiModelProperty(name = "coupon_id", value = "优惠券id")
   private Long couponId;

   @ApiModelProperty(name = "coupon_detail_id", value = "优惠券详情ID")
   private Long couponDetailId;

   @ApiModelProperty(name = "status", value = "使用状态(0未使用,1已使用)")
   private Integer status;

   @ApiModelProperty(name = "order_sn", value = "订单编号")
   private String orderSn;

   @ApiModelProperty(name = "used_time", value = "使用时间")
   private Date usedTime;

   @ApiModelProperty(name = "create_time", value = "领券时间")
   private Date createTime;

   @ApiModelProperty(name = "update_time", value = "修改时间")
   private Date updateTime;

   @ApiModelProperty(name = "begin_time", value = "券生效开始时间")
   private Date beginTime;

   @ApiModelProperty(name = "end_time", value = "券生效结束时间")
   private Date endTime;

   @ApiModelProperty(name = "source", value = "领取来源(0推广,1活动,2页面)")
   private Integer source;

   @ApiModelProperty(name = "activity_id", value = "活动ID")
   private Long activityId;


}

