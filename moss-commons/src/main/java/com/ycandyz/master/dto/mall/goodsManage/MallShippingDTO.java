package com.ycandyz.master.dto.mall.goodsManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MallShippingDTO {

    @ApiModelProperty(value = "运费模版名称")
    private String shippingName;

    @ApiModelProperty(value = "运费方式, 1: 按件")
    private Integer shippingMethod;

    @ApiModelProperty(value = "地区")
    private MallShippingRegionDTO[] regions;
}
