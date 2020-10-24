package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 售后中拼接的商品信息规格表，数据来源为mall_order_detail_spec
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="售后商品信息规格表", description="售后商品信息表-通过订单详情规格查询VO")
public class MallItemSpecByMallOrderDetailSpecVO {

    @ApiModelProperty(value = "规格值")
    private String specValue;
}
