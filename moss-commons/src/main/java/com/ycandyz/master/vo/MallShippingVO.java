package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MallShippingVO {

    @ApiModelProperty(value = "商店编号")
    private String shopNo;

    @ApiModelProperty(value = "运费模版名称")
    private String shippingName;

    @ApiModelProperty(value = "运费方式, 1: 按件")
    private Integer shippingMethod;

    @ApiModelProperty(value = "区域及价格")
    private MallShippingRegionVO[] regions;

    @ApiModelProperty(value = "运费模版编号")
    private String shippingNo;

}
