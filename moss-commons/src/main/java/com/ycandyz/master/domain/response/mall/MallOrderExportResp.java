package com.ycandyz.master.domain.response.mall;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: WangYang
 * @Date: 2020/10/15 16:59
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MallOrderExportResp implements Serializable {

    /**店铺编号*/
    @ApiModelProperty(value = "店铺编号")
    private String shopNo;

    /**商品编号*/
    @ApiModelProperty(value = "商品编号")
    private String itemNo;

    /**商品名称*/
    @ApiModelProperty(value = "商品名称")
    private String itemName;

    /**商品图片*/
    @ApiModelProperty(value = "商品图片")
    private String itemCover;

    /**购买数量*/
    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

    /**添加到购物车的价格*/
    @ApiModelProperty(value = "添加到购物车的价格")
    private java.math.BigDecimal price;

    /**商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]*/
    @ApiModelProperty(value = "商品销售属性")
    @JsonRawValue
    private String itemSpec;
}
