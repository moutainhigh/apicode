package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单列表搜索传参类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="订单表-检索参数")
public class MallOrderQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号",name="orderNo",required=true)
    private String orderNo;
    @ApiModelProperty(value = "商品名称",name="mallName",required=true)
    private String mallName;
    @ApiModelProperty(value = "用户信息(用户名称，手机号)",name="userMes",required=true)
    private String userMes;
    @ApiModelProperty(value = "收货人(用户名称，手机号)",name="consignor",required=true)
    private String consignor;
    @ApiModelProperty(value = "发货方式:1-配送 2-自提",name="deliverType",required=true)
    private Integer deliverType;
    @ApiModelProperty(value = "是否分销:1-是 0-否",name="distribution",required=true)
    private Integer distribution;
    @ApiModelProperty(value = "合伙人名称和手机号",name="socialPartner",required=true)
    private String socialPartner;
    @ApiModelProperty(value = "下单开始时间",name="orderAtForm",required=true)
    private Integer orderAtForm;
    @ApiModelProperty(value = "下单结束时间",name="orderAtTo",required=true)
    private Integer orderAtTo;
    @ApiModelProperty(value = "支付开始时间",name="payedAtForm",required=true)
    private Integer payedAtForm;
    @ApiModelProperty(value = "支付结束时间",name="payedAtTo",required=true)
    private Integer payedAtTo;
    @ApiModelProperty(value = "收货开始时间",name="receiveAtForm",required=true)
    private Integer receiveAtForm;
    @ApiModelProperty(value = "收货结束时间",name="receiveAtTo",required=true)
    private Integer receiveAtTo;
    @ApiModelProperty(value = "是否售后:0-否，1-是",name="afterSalesStatus",required=true)
    private Integer afterSalesStatus;
    @ApiModelProperty(value = "商店标号",name="shopNo",required=true)
    private String shopNo;
    @ApiModelProperty(value = "10-待支付  20-待发货 30-待收货 40-已收货  50-已取消",name="status",required=true)
    private Integer status;
    @ApiModelProperty(value = "企业id",name="organizeId",required=true)
    private Long organizeId;
}
