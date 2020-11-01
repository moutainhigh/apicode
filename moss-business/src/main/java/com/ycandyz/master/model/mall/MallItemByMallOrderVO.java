package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 售后中拼接的商品信息表，数据来源为mall_order
 * 拼接展示地址mall_order
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="商品信息表", description="商品信息表-通过订单表查询VO")
public class MallItemByMallOrderVO {

    @ApiModelProperty(value = "商品编号")
    private String itemNo;
    @ApiModelProperty(value = "商品名称")
    private String itemName;
    @ApiModelProperty(value = "sku编号")
    private String skuNo;
}
