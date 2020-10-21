package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallBuyerShippingVO {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "订单售后编号")
    private String afterSalesNo;
    @ApiModelProperty(value = "买家物流表编号")
    private String buyerShippingNo;
    @ApiModelProperty(value = "类型  0-未知  1-快递  2-线下配送")
    private Integer type;
    @ApiModelProperty(value = "物流公司编码")
    private String companyCode;
    @ApiModelProperty(value = "快递公司名")
    private String company;
    @ApiModelProperty(value = "快递单号")
    private String number;
    @ApiModelProperty(value = "运费")
    private BigDecimal shippingMoney;
    @ApiModelProperty(value = "商家收货人姓名")
    private String receiver;
    @ApiModelProperty(value = "商家收货人手机号")
    private String receiverPhone;
    @ApiModelProperty(value = "商家收货地址")
    private String receiverAddress;
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
    @ApiModelProperty(value = "状态 10-有效  20-无效")
    private Integer status;
}
