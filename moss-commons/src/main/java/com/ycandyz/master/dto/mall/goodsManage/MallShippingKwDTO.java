package com.ycandyz.master.dto.mall.goodsManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MallShippingKwDTO {

    @ApiModelProperty(value = "运费模版编号")
    private String shippingNo;

    @ApiModelProperty(value = "运费模版名称")
    private String shippingName;

    @ApiModelProperty(value = "运费方式, 1: 按件")
    private Integer shippingMethod;

    @ApiModelProperty(value = "地区")
    private MallShippingRegionDTO[] regions;

    @ApiModelProperty(value = "createdStr")
    private String createdStr;

    @ApiModelProperty(value = "updatedStr")
    private String updatedStr;
}
