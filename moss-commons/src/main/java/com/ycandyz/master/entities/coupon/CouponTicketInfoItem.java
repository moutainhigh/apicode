package com.ycandyz.master.entities.coupon;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 优惠券详情关联商品表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_ticket_info_item")
@ApiModel(description="优惠券详情关联商品表")
public class CouponTicketInfoItem extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "商品编号")
   private String itemNo;

   @ApiModelProperty(value = "优惠券详情编号")
   private String ticketInfoNo;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "创建时间")
   private Date createTime;


}
