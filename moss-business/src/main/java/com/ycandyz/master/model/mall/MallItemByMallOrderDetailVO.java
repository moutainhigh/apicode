package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 售后中拼接的商品信息表，数据来源为mall_order_detail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="售后商品信息表", description="售后商品信息表-通过订单详情查询VO")
public class MallItemByMallOrderDetailVO {

    @ApiModelProperty(value = "商品编号")
    private String itemNo;
    @ApiModelProperty(value = "商品名")
    private String itemName;
    @ApiModelProperty(value = "商品封面图")
    private String itemCover;
    @ApiModelProperty(value = "货号")
    private String goodsNo;
    @ApiModelProperty(value = "购买商品的数量")
    private Integer quantity;
    @ApiModelProperty(value = "购买商品的价格")
    private BigDecimal price;
    @ApiModelProperty(value = "商品规格")
    private List<MallItemSpecByMallOrderDetailSpecVO> spec;
}
