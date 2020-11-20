package com.ycandyz.master.domain.query.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单列表搜索传参类,UApp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="订单表-检索参数-UApp")
public class MallOrderUAppQuery implements Serializable {

    @ApiModelProperty(value = "订单列表入参：订单号，收货用户/手机，用户昵称/账号，预留手机号",name="mallOrderQuery",required=true)
    private String mallOrderQuery;
    @ApiModelProperty(value = "商店标号",name="shopNo",required=true)
    private String shopNo;
    @ApiModelProperty(value = "10-待支付  20-待发货 30-待收货 40-已收货  50-已取消",name="status",required=true)
    private Integer status;
}
