package com.ycandyz.master.dto.mall.goodsManage;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@TableName("mall_shipping_region_province")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_shipping_region_province对象", description="运费地区-省表")
public class MallShippingRegionProvinceDTO {

    @ApiModelProperty(value = "地区编号")
    private String region;

    @ApiModelProperty(value = "省")
    private String[] province;
}
