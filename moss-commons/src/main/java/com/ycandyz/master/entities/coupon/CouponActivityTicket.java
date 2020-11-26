package com.ycandyz.master.entities.coupon;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 发卷宝-优惠卷 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_activity_ticket")
@ApiModel(description="发卷宝-优惠卷")
public class CouponActivityTicket extends Model {


   @ApiModelProperty(value = "ID")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "优惠宝编号")
   private String activityNo;

   @ApiModelProperty(value = "优惠券编号")
   private String ticketNo;

   @ApiModelProperty(value = "创建时间")
   private Long createdAt;

   @ApiModelProperty(value = "更新时间")
   private Long updatedAt;


}
