package com.ycandyz.master.domain.query.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="退款表")
public class MallafterSalesQuery implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "订单详情售后编号")
    private String afterSalesNo;
    @ApiModelProperty(value = "类型 10-退货退款  20-仅退款")
    private Integer status;
    @ApiModelProperty(value = "1010:一次审核中  1020:一次审核不通过  1030:一次审核通过/待用户退货  1040:超时未退货，系统关闭  1050:用户已退货/退款中  " +
            "1060:二次审核不通过/拒绝退款  1070:二次审核通过   1080:退款成功  1090:退款失败  2010:退款中  2020:审核通过  2030:退款成功  2040：退款失败  2050：退款拒绝")
    private Integer subStatus;
    @ApiModelProperty(value = "退款时间开始")
    private Date applyAtFrom;
    @ApiModelProperty(value = "退款时间结束")
    private Date applyAtTo;
    @ApiModelProperty(value = "商品信息 商品名或货号")
    private String shopItem;
    @ApiModelProperty(value = "用户信息 用户名或手机号")
    private String userName;
    @ApiModelProperty(value = "退款单号")
    private String refundNo;
    @ApiModelProperty(value = "收货地址")
    private String receiverAddress;
    @ApiModelProperty(value = "收货人手机号")
    private String receiverPhone;
    @ApiModelProperty(value = "收货人")
    private String receiver;
    @ApiModelProperty(value = "商家编号")
    private String shopNo;
}
