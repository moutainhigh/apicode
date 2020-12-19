package com.ycandyz.master.domain.model.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * @Description 商品SKu Model
 * </p>
 *
 * @author SanGang
 * @since 2020-12-18
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品SKU-参数")
public class MallItemSkuModel {

    @ApiModelProperty(name = "sale_price",value = "销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty(name = "price",value = "原价")
    private BigDecimal price;

    @ApiModelProperty(name = "goods_no",value = "货号")
    private String goodsNo;

    @ApiModelProperty(name = "stock",value = "库存")
    private Integer stock;

    @ApiModelProperty(name = "sku_img",value = "SKU图片")
    private String skuImg;

    @ApiModelProperty(name = "sku_specs",value = "规格List")
    private List<MallItemSkuSpecModel> skuSpecs;

}
