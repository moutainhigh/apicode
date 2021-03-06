package com.ycandyz.master.dto.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * 售后详情接口中返回的订单表信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallOrderByAfterSalesDTO {

    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "店铺编号")
    private String shopNo;
    @ApiModelProperty(value = "订单状态 10-待支付 20-待发货 30-待收货 40-已收货 50-已取消 60-已删除")
    private Integer orderStatus;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalMoney;
    @ApiModelProperty(value = "支付金额")
    private BigDecimal realMoney;
    @ApiModelProperty(value = "配送方式 10-快递 20-线下配送")
    private String deliverMethod;
    @ApiModelProperty(value = "商家发货快递运费")
    private String shippingMoney;
    @ApiModelProperty(value = "支付方式：0-未知  1-支付宝 2-微信  3-银联")
    private Integer payType;
    @ApiModelProperty(value = "订单类型1->商城，2->神州通")
    private Integer orderType;
    @ApiModelProperty(value = "总计金额")
    private BigDecimal allMoney;
    @ApiModelProperty(value = "发货方式:1-配送 2-自提")
    private Boolean deliverType;
    @ApiModelProperty(value = "是否集团供货，1:是，0:否")
    private Integer isGroupSupply;
}
