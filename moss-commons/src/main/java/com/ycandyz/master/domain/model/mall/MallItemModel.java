package com.ycandyz.master.domain.model.mall;

import com.ycandyz.master.vo.MallSkuVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * @Description 商品信息 Model
 * </p>
 *
 * @author SanGang
 * @since 2020-15-10
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品信息-参数")
public class MallItemModel {

    @ApiModelProperty(value = "分类编号")
    private String categoryNo;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "封面图")
    private String itemCover;

    @ApiModelProperty(value = "轮播图")
    private String[] banners;

    // TODO
    @ApiModelProperty(value = "置顶视频")
    private String[] topVideoList;

    @ApiModelProperty(value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
    private Integer status;

    @ApiModelProperty(value = "基础销量")
    private Integer baseSales;

    @ApiModelProperty(value = "排序值")
    private Integer sortValue;

    @ApiModelProperty(value = "分享描述")
    private String shareDescr;

    @ApiModelProperty(value = "分享图片")
    private String shareImg;

    @ApiModelProperty(value = "skus")
    private MallSkuVO[] skus;

    @ApiModelProperty(value = "库存扣减方式，10: 拍下减库存，20: 付款减库存")
    private Integer subStockMethod;

    @ApiModelProperty(value = "1- 配送 2-自提")
    private Integer[] deliveryType;

    @ApiModelProperty(value = "配送方式，快递: 10, 线下配送：20")
    private Integer deliverMethod;

    @ApiModelProperty(value = "是否统一运费，0: 否，1:是")
    private Integer isUnifyShipping;

    @ApiModelProperty(value = "统一运费")
    private BigDecimal unifyShipping;

    @ApiModelProperty(value = "运费模版编号")
    private String shippingNo;

    @ApiModelProperty(value = "配送地址")
    private Integer[] pickupAddressIds;

    @ApiModelProperty(value = "限制周期，10:每天，20:每周, 30:每月，40:每年，50总身")
    private Integer limitCycleType;

    @ApiModelProperty(value = "限购数量")
    private Integer limitSkus;

    @ApiModelProperty(value = "限购订单数")
    private Integer limitOrders;

    @ApiModelProperty(value = "起购数量")
    private Integer initialPurchases;

    // TODO
    @ApiModelProperty(value = "商品介绍视频")
    private String[] detailVideoList;

    @ApiModelProperty(value = "商品描述")
    private String itemText;

    //商品封面显示
    @ApiModelProperty(value = "销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "原价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "货号")
    private String goodsNo;

    @ApiModelProperty(value = "最高销售价格")
    private BigDecimal highestSalePrice;

}
