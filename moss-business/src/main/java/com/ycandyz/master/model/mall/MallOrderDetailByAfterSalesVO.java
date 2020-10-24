package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 售后详情接口中返回的订单详情表信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallOrderDetailByAfterSalesVO {

    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "订单详情编号")
    private String orderDetailNo;
    @ApiModelProperty(value = "商品编号")
    private String itemNo;
    @ApiModelProperty(value = "商品名称")
    private String itemName;
    @ApiModelProperty(value = "商品封面图")
    private String itemCover;
    @ApiModelProperty(value = "货号")
    private String goodsNo;
    @ApiModelProperty(value = "SKU编号")
    private String skuNo;
    @ApiModelProperty(value = "SKU数量")
    private Integer quantity;
    @ApiModelProperty(value = "SKU价格")
    private BigDecimal price;
    @ApiModelProperty(value = "订单详情关联规格")
    private List<MallOrderDetailSpecVO> specs;
}
