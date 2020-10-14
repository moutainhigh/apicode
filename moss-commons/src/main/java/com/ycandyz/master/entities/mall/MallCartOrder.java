package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 购物车订单 数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_cart_order")
@ApiModel(description="购物车订单")
public class MallCartOrder extends Model {


   @ApiModelProperty(value = "数据编号")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "用户编号")
   private Long userId;

   @ApiModelProperty(value = "订单编号")
   private String orderSn;

   @ApiModelProperty(value = "商店编号")
   private String shopNo;

   @ApiModelProperty(value = "订单总金额")
   private BigDecimal totalAmount;

   @ApiModelProperty(value = "应付金额（实际支付金额）")
   private BigDecimal payAmount;

   @ApiModelProperty(value = "支付方式：0->未支付；1->支付宝；2->微信")
   private Integer payType;

   @ApiModelProperty(value = "订单状态：0->待付款；1->已完成；2->已关闭；3->已取消")
   private Integer status;

   @ApiModelProperty(value = "订单备注")
   private String note;

   @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
   private Integer deleteStatus;

   @ApiModelProperty(value = "支付时间")
   private Date paymentTime;

   @ApiModelProperty(value = "创建时间")
   private Date createTime;

   @ApiModelProperty(value = "修改时间")
   private Date updateTime;


}
