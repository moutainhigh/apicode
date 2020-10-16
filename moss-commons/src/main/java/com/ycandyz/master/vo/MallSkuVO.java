package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MallSkuVO {

    @ApiModelProperty(value = "销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal price;

    @ApiModelProperty(value = "货号")
    private String goodsNo;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "规格模版")
    private MallSkuSpecsVO[] skuSpecs;

}
