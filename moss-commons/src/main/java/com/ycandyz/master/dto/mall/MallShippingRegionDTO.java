package com.ycandyz.master.dto.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MallShippingRegionDTO {

    @ApiModelProperty(value = "首件数量")
    private Integer firstCount;

    @ApiModelProperty(value = "首件运费价格")
    private BigDecimal firstPrice;

    @ApiModelProperty(value = "续件数量")
    private Integer moreCount;

    @ApiModelProperty(value = "续件运费价格")
    private BigDecimal morePrice;

    @ApiModelProperty(value = "省列表")
    private String[] provinces;
}
