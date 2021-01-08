package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.vo.MallSkuVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品信息-参数")
public class MallItemModel {

    private Long id;

    @ApiModelProperty(name = "shop_no",value = "商店编号")
    private String shopNo;

    @NotBlank(message = "分类编号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "category_no",value = "分类编号")
    private String categoryNo;

    @ApiModelProperty(name = "item_no",value = "商品编号")
    private String itemNo;

    @Size(max = 100, message = "商品名称不能大于50个字。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotBlank(message = "商品名称不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
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

    @ApiModelProperty(name = "bar_code",value = "商品条码 ")
    private String barCode;

    @ApiModelProperty(name = "lowest_sale_price",value = "最低销售价格")
    private BigDecimal lowestSalePrice;

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

    @ApiModelProperty(name = "is_organize",value = "是否集团供货(0否,1是)")
    private Integer isOrganize;

    @ApiModelProperty(name = "is_all",value = "全部/指定(0全部,1指定)")
    private Integer isAll;

    @ApiModelProperty(name = "shop_nos",value = "店铺编号")
    private List<String> shopNos;

    @NotEmpty(message = "轮播图不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(value = "轮播图")
    private List<String> banners;

    @ApiModelProperty(name = "top_video_list",value = "商品置顶视频")
    private List<MallItemVideoModel> topVideoList;

    @ApiModelProperty(value = "skus")
    private List<MallItemSkuModel> skus;

    @ApiModelProperty(name = "delivery_type",value = "1- 配送 2-自提")
    private List<Integer> deliveryType;

    @ApiModelProperty(name = "pickup_addr_ids",value = "配送地址")
    private List<Integer> pickupAddrIds;

    @ApiModelProperty(name = "detail_video_list",value = "商品介绍视频")
    private List<MallItemVideoModel> detailVideoList;

}
