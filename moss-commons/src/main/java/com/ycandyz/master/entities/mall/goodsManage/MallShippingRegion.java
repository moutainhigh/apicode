package com.ycandyz.master.entities.mall.goodsManage;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("mall_shipping_region")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_shipping_region对象", description="运费地区表")
public class MallShippingRegion {

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "地区编号")
    private String regionNo;

    @ApiModelProperty(value = "运费模版编号")
    private String shippingNo;

    @ApiModelProperty(value = "首件数量")
    private Integer firstCount;

    @ApiModelProperty(value = "首件运费价格")
    private BigDecimal firstPrice;

    @ApiModelProperty(value = "续件数量")
    private Integer moreCount;

    @ApiModelProperty(value = "续件运费价格")
    private BigDecimal morePrice;

    @ApiModelProperty(value = "createdTime")
    private Date createdTime;

    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
