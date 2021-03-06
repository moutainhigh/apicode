package com.ycandyz.master.dto.mall;

import com.ycandyz.master.entities.PickupAddress;
import com.ycandyz.master.vo.MallSkuVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MallItemDTO {

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "分类编号")
    private String categorNo;

    @ApiModelProperty(value = "商品描述")
    private String itemText;

    @ApiModelProperty(value = "轮播图，jsonarray")
    private String[] banners;

    @ApiModelProperty(value = "基础销量")
    private Integer baseSales;

    @ApiModelProperty(value = "排序值")
    private Integer sortValue;

    @ApiModelProperty(value = "分享描述")
    private String sharDescr;

    @ApiModelProperty(value = "分享图片")
    private String shareImg;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "库存扣减方式，10: 拍下减库存，20: 付款减库存")
    private Integer subStockMethod;

    @ApiModelProperty(value = "货号")
    private String goodsNo;

    @ApiModelProperty(value = "配送方式，快递: 10, 线下配送：20")
    private Integer deliverMethod;

    @ApiModelProperty(value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
    private Integer status;

    @ApiModelProperty(value = "是否统一运费，0: 否，1:是")
    private Integer isUnifyShipping;

    @ApiModelProperty(value = "统一定价")
    private BigDecimal unifyShipping;

    @ApiModelProperty(value = "运费模版编号")
    private String shippingNo;

    @ApiModelProperty(value = "起购数量")
    private Integer initialPurchases;

    @ApiModelProperty(value = "限制周期，10:每天，20:每周, 30:每月，40:每年，50总身")
    private Integer limitCycleType;

    @ApiModelProperty(value = "限购数量")
    private Integer limitSkus;

    @ApiModelProperty(value = "限购订单数")
    private Integer limitOrders;

    @ApiModelProperty(value = "SKU列表")
    private MallSkuVO[] skus;

    @ApiModelProperty(value = "商品编号")
    private String itemNo;

    @ApiModelProperty(value = "规格列表")
    private Integer specs;  ///

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "父分类编号")
    private String parentCategoryNo;

    @ApiModelProperty(value = "父分类名称")
    private String parentCategoryName;

    @ApiModelProperty(value = "自提地址")
    private PickupAddress[] pickupAddressIds;

    @ApiModelProperty(value = "自提地址类型： '[1,2]'")
    private String deliveryType;
}
