package com.ycandyz.master.domain.query.mall.uApp;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商城 - 商家寄出的快递表")
public class MallShopShippingUAppQuery {

    @ApiModelProperty(value = "订单编号", name = "order_no")
    @JsonAlias(value = {"order_no", "orderNo"})
    private String orderNo;
    @ApiModelProperty(value = "商家快递表编号", name = "shop_shipping_no")
    @JsonAlias(value = {"shop_shipping_no", "shopShippingNo"})
    private String shopShippingNo;
    @ApiModelProperty(value = "快递公司编码", name = "company_code")
    @JsonAlias(value = {"company_code", "companyCode"})
    private String companyCode;
    @ApiModelProperty(value = "快递公司名", name = "company")
    private String company;
    @ApiModelProperty(value = "快递单号", name = "number")
    private String number;
}
