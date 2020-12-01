package com.ycandyz.master.entities.coupon;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("coupon_activity_ticket")
@ApiModel(description="发卷宝-优惠卷")
public class CouponActivityTicket extends Model {


   @ApiModelProperty(value = "ID")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "activity_no",value = "优惠宝编号")
   private String activityNo;

   @ApiModelProperty(name = "ticket_no",value = "优惠券编号")
   private String ticketNo;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(name = "create_time",value = "创建时间")
   private Date createTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(name = "update_time",value = "更新时间")
   private Date updateTime;


}
