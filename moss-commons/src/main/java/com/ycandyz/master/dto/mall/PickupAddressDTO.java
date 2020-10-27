package com.ycandyz.master.dto.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PickupAddressDTO {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "地址名称")
    private String name;
    @ApiModelProperty(value = "详细地址信息")
    private String address;
}
