package com.ycandyz.master.domain.query.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商城 - 店铺地址表")
public class MallShopAddressQuery implements Serializable {

    @ApiModelProperty(value = "店铺地址id")
    private Long id;
    @ApiModelProperty(value = "商店编号")
    private String shopNo;
    @ApiModelProperty(value = "地址编号")
    private String shopAddressNo;
    @ApiModelProperty(value = "收货人")
    private String receiver;
    @ApiModelProperty(value = "收货人手机号")
    private String phoneNumber;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "省id")
    private Integer provinceId;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "市id")
    private Integer cityId;
    @ApiModelProperty(value = "区")
    private String district;
    @ApiModelProperty(value = "区id")
    private Integer districtId;
    @ApiModelProperty(value = "详细地址")
    private String detailAddress;
    @ApiModelProperty(value = "是否默认地址：0: 否，1: 是  默认0")
    private Integer isDefault;
    @ApiModelProperty(value = "商店地址状态：0: 正常，1: 过期  默认0")
    private Integer status;
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
