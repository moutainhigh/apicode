package com.ycandyz.master.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PickupAddress {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "地址名称")
    private String name;
    @ApiModelProperty(value = "省份信息")
    private String province;
    @ApiModelProperty(value = "省份id")
    private Integer provinceId;
    @ApiModelProperty(value = "城市信息")
    private String city;
    @ApiModelProperty(value = "城市id")
    private Integer cityId;
    @ApiModelProperty(value = "地区信息")
    private String district;
    @ApiModelProperty(value = "地区id")
    private Integer districtId;
    @ApiModelProperty(value = "详细地址信息")
    private String detailInfo;
    @ApiModelProperty(value = "店铺编号")
    private String shop_no;
    @ApiModelProperty(value = "是否删除 0-否 1-删除")
    private Integer isDel;
    @ApiModelProperty(value = "是否默认 0-非默认 1-默认")
    private Integer isDefault;
}
