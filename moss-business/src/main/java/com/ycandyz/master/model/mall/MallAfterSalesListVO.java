package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class MallAfterSalesListVO {

    /**订单详情售后编号*/
    @ApiModelProperty(value = "订单详情售后编号")
    private String afterSalesNo;
    /**订单编号*/
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    /**商品名称*/
    @ApiModelProperty(value = "商品名称")
    private String itemName;
    @ApiModelProperty(value = "货号")
    private String goodsNo;
    /**1010:一次审核中  1020:一次审核不通过  1030:一次审核通过/待用户退货  1040:超时未退货，系统关闭  1050:用户已退货/退款中  1060:二次审核不通过/拒绝退款  1070:二次审核通过   1080:退款成功  1090:退款失败  2010:退款中  2020:审核通过  2030:退款成功  2040：退款失败  2050：退款拒绝'*/
    @ApiModelProperty(value = "1010:一次审核中  1020:一次审核不通过  1030:一次审核通过/待用户退货  1040:超时未退货，系统关闭  1050:用户已退货/退款中  1060:二次审核不通过/拒绝退款  1070:二次审核通过   1080:退款成功  1090:退款失败  2010:退款中  2020:审核通过  2030:退款成功  2040：退款失败  2050：退款拒绝'")
    private Integer subStatus;
    @ApiModelProperty(value = "SKU编号")
    private String skuNo;
    @ApiModelProperty(value = "退款数量")
    private Integer quantity;
    @ApiModelProperty(value = "退款价格")
    private BigDecimal price;
    @ApiModelProperty(value = "退款金额")
    private BigDecimal money;
    @ApiModelProperty(value = "实付金额")
    private BigDecimal orderRealMoney;
    @ApiModelProperty(value = "支付方式")
    private Integer order_pay_type;
    @ApiModelProperty(value = "购买数量")
    private Integer order_quantity;
    @ApiModelProperty(value = "购买用户：用户名")
    private String userName;
    @ApiModelProperty(value = "购买用户：手机号")
    private String user_phone;
    @ApiModelProperty(value = "申请售后时间 - 时间戳")
    private int createdAt;
    @ApiModelProperty(value = "申请售后时间 - 字符串")
    private String createdAtStr;
}
