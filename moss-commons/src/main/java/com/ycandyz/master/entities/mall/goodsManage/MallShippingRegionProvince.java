package com.ycandyz.master.entities.mall.goodsManage;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("mall_shipping_region_province")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_shipping_region_province对象", description="运费地区-省表")
public class MallShippingRegionProvince {

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "地区编号")
    private String regionNo;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "createdTime")
    private Date createdTime;

    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
