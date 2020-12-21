package com.ycandyz.master.domain.model.mall;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.vo.MallSkuVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(max = 40, message = "活动名称不能大于20个字。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotBlank(message = "活动名称不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "category_no",value = "分类编号")
    private String categoryNo;

    @Size(max = 40, message = "活动名称不能大于20个字。",groups = {ValidatorContract.OnUpdate.class})
    @NotBlank(message = "活动名称不能为空",groups = {ValidatorContract.OnUpdate.class})
    @ApiModelProperty(name = "item_no",value = "商品编号")
    private String itemNo;

    @Size(max = 40, message = "活动名称不能大于20个字。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotBlank(message = "活动名称不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "item_name",value = "商品名称")
    private String itemName;

    @ApiModelProperty(name = "item_cover",value = "封面图")
    private String itemCover;

    @ApiModelProperty(value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
    private Integer status;

    @ApiModelProperty(name = "base_sales",value = "基础销量")
    private Integer baseSales;

    @ApiModelProperty(name = "sort_value",value = "排序值")
    private Integer sortValue;

    @ApiModelProperty(name = "share_descr",value = "分享描述")
    private String shareDescr;

    @ApiModelProperty(name = "share_img",value = "分享图片")
    private String shareImg;

    @ApiModelProperty(name = "sub_stock_method",value = "库存扣减方式，10: 拍下减库存，20: 付款减库存")
    private Integer subStockMethod;

    @ApiModelProperty(name = "deliver_method",value = "配送方式，快递: 10, 线下配送：20")
    private Integer deliverMethod;

    @ApiModelProperty(name = "is_unify_shipping",value = "是否统一运费，0: 否，1:是")
    private Integer isUnifyShipping;

    @ApiModelProperty(name = "unify_shipping",value = "统一运费")
    private BigDecimal unifyShipping;

    @ApiModelProperty(name = "shipping_no",value = "运费模版编号")
    private String shippingNo;

    @ApiModelProperty(name = "limit_cycle_type",value = "限制周期，10:每天，20:每周, 30:每月，40:每年，50总身")
    private Integer limitCycleType;

    @Range(min = 1, max = 1000000, message = "赠送上限人数范围为1-1000000之间", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotNull(message = "活赠送上限人数不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "limit_skus",value = "限购数量")
    private Integer limitSkus;

    @ApiModelProperty(name = "limit_orders",value = "限购订单数")
    private Integer limitOrders;

    @ApiModelProperty(name = "initial_purchases",value = "起购数量")
    private Integer initialPurchases;

    @ApiModelProperty(name = "item_text",value = "商品描述")
    private String itemText;

    //商品封面显示
    @ApiModelProperty(name = "sale_price",value = "销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "原价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(name = "goods_no",value = "货号")
    private String goodsNo;

    @ApiModelProperty(name = "highest_sale_price",value = "最高销售价格")
    private BigDecimal highestSalePrice;

    //非销售类商品
    @ApiModelProperty(name = "type",value = "商品类型(0非商品,1商品)")
    private Integer type;

    @ApiModelProperty(name = "non_price_type",value = "非销售商品-价格类型(0不显示,1显示)")
    private Integer nonPriceType;

    @ApiModelProperty(name = "non_sale_price",value = "非销售商品-售价")
    private String nonSalePrice;

    @ApiModelProperty(name = "non_price",value = "非销售商品-原价")
    private String nonPrice;


    @ApiModelProperty(value = "轮播图")
    private String[] banners;

    @ApiModelProperty(name = "top_video_list",value = "商品置顶视频")
    private MallItemVideoModel topVideoList;

    @ApiModelProperty(value = "skus")
    private MallSkuVO[] skus;

    @ApiModelProperty(name = "delivery_type",value = "1- 配送 2-自提")
    private Integer[] deliveryType;

    @ApiModelProperty(name = "pickup_address_ids",value = "配送地址")
    private Integer[] pickupAddressIds;

    @ApiModelProperty(name = "detail_video_list",value = "商品介绍视频")
    private MallItemVideoModel detailVideoList;

}
