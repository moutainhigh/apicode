package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("mall_shop_shipping_poll_log")
@ApiModel(description="商城 - 商家寄出的快递物流日志表")
public class MallShopShippingPollLog extends Model {

    /**id*/
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;
    /**订单号 mall_order表*/
    @ApiModelProperty(value = "订单号 mall_order表")
    private String orderNo;
    /**商家快递表编号 mall_shop_shipping表*/
    @ApiModelProperty(value = "商家快递表编号 mall_shop_shipping表")
    private String shopShippingNo;
    /**订阅失败返回信息*/
    @ApiModelProperty(value = "订阅失败返回信息")
    private String log;
    /**商家快递表编号*/
    @ApiModelProperty(value = "日志记录时间")
    private Date createdTime;
}
