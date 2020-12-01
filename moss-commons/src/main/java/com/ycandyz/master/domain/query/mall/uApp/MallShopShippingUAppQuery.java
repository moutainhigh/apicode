package com.ycandyz.master.domain.query.mall.uApp;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商城 - 商家寄出的快递表")
public class MallShopShippingUAppQuery {

    @Size(max = 64, message = "订单编号长度不能大于64。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotNull(message = "订单编号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(value = "订单编号", name = "order_no")
    @JsonAlias(value = {"order_no", "orderNo"})
    private String orderNo;
    @ApiModelProperty(value = "商家快递表编号", name = "shop_shipping_no")
    @JsonAlias(value = {"shop_shipping_no", "shopShippingNo"})
    private String shopShippingNo;
    @NotNull(message = "快递公司编码不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(value = "快递公司编码", name = "company_code")
    @JsonAlias(value = {"company_code", "companyCode"})
    private String companyCode;
    @ApiModelProperty(value = "快递公司名", name = "company")
    private String company;

    @NotNull(message = "快递单号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(value = "快递单号", name = "number")
    private String number;
}
