package com.ycandyz.master.domain.response.mall;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.domain.model.mall.MallSpecsModel;
import com.ycandyz.master.entities.mall.MallItem;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.entities.mall.MallSku;
import com.ycandyz.master.entities.mall.MallSkuSpec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * @Description 商品表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-12-19
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="商品表-Resp")
public class MallItemResp {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "category_no", value = "分类编号")
   private String categoryNo;

   @ApiModelProperty(name = "category_txt", value = "分类名称")
   private String categoryTxt;

   @ApiModelProperty(name = "children_organize_name", value = "集团名称")
   private String childrenOrganizeName;

   @ApiModelProperty(name = "created_str", value = "创建时间 yyyy-MM-dd HH:mm:ss")
   private String createdStr;

   @ApiModelProperty(name = "goods_no", value = "货号")
   private String goodsNo;

   @ApiModelProperty(name = "goods_no_list", value = "全部货号")
   private String goodsNoList;

   @ApiModelProperty(name = "highest_sale_price", value = "最高销售价格")
   private BigDecimal highestSalePrice;

   @ApiModelProperty(name = "is_screen", value = "是否系统屏蔽 0-通过 1-屏蔽")
   private Boolean isScreen;

   @ApiModelProperty(name = "item_cover", value = "封面图")
   private String itemCover;

   @ApiModelProperty(name = "item_name", value = "商品名称")
   private String itemName;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "lowest_sale_price", value = "最低销售价格")
   private BigDecimal lowestSalePrice;

   @ApiModelProperty(name = "organize_id", value = "机构ID")
   private Long organizeId;

   @ApiModelProperty(name = "real_sales", value = "实际销量")
   private Integer realSales;

   @ApiModelProperty(name = "sale_price",value = "商品-售价")
   private BigDecimal salePrice;

   @ApiModelProperty(name = "price", value = "原价格")
   private BigDecimal price;

   @ApiModelProperty(name = "sales", value = "销量")
   private Integer sales;

   @ApiModelProperty(name = "shop_no", value = "商店编号")
   private String shopNo;

   @ApiModelProperty(name = "sort_value", value = "排序值")
   private Integer sortValue;

   @ApiModelProperty(name = "status", value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
   private Integer status;

   @ApiModelProperty(name = "stock", value = "库存")
   private Integer stock;

   //非销售类商品
   @ApiModelProperty(name = "type",value = "商品类型(0非商品,1商品)")
   private Integer type;

   @ApiModelProperty(name = "non_price_type",value = "非销售商品-价格类型(0不显示,1显示)")
   private Integer nonPriceType;

   @ApiModelProperty(name = "non_sale_price",value = "非销售商品-售价")
   private String nonSalePrice;

   @ApiModelProperty(name = "non_price",value = "非销售商品-原价")
   private String nonPrice;




   @ApiModelProperty(name = "category_no_name", value = "分类名称")
   private String categoryNoName;

   @ApiModelProperty(name = "frozen_stock", value = "冻结库存")
   private Integer frozenStock;

   @ApiModelProperty(name = "base_sales", value = "基础销量")
   private Integer baseSales;

   @ApiModelProperty(name = "created_time", value = "添加时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @ApiModelProperty(name = "item_text", value = "商品描述")
   private String itemText;

   @ApiModelProperty(name = "banners", value = "轮播图，jsonarray")
   private List<String> banners;

   @ApiModelProperty(name = "share_descr", value = "分享描述")
   private String shareDescr;

   @ApiModelProperty(name = "share_img", value = "分享图片")
   private String shareImg;

   @ApiModelProperty(name = "sub_stock_method", value = "库存扣减方式，10: 拍下减库存，20: 付款减库存")
   private Integer subStockMethod;

   @ApiModelProperty(name = "deliver_method", value = "配送方式，快递: 10, 线下配送：20")
   private Integer deliverMethod;

   @ApiModelProperty(name = "is_unify_shipping", value = "是否统一运费，0: 否，1:是")
   private Boolean isUnifyShipping;

   @ApiModelProperty(name = "shipping_no", value = "运费模版编号")
   private String shippingNo;

   @ApiModelProperty(name = "unify_shipping", value = "统一定价")
   private BigDecimal unifyShipping;

   @ApiModelProperty(name = "initial_purchases", value = "起购数量")
   private Integer initialPurchases;

   @ApiModelProperty(name = "limit_cycle_type", value = "限制周期，10:每天，20:每周, 30:每月，40:每年，50总身")
   private Integer limitCycleType;

   @ApiModelProperty(name = "limit_skus", value = "限购数量")
   private Integer limitSkus;

   @ApiModelProperty(name = "limit_orders", value = "限购订单数")
   private Integer limitOrders;

   @ApiModelProperty(name = "qr_code_url", value = "二维码")
   private String qrCodeUrl;



   private Date updatedTime;

   @ApiModelProperty(name = "visits", value = "访问数")
   private Integer visits;

   @ApiModelProperty(name = "is_updated_share", value = "是否设置过分销")
   private Boolean isUpdatedShare;

   @ApiModelProperty(name = "is_enable_share", value = "是否开启分销 0:不开启 1:开启")
   private Boolean isEnableShare;

   @ApiModelProperty(name = "share_method", value = "U团长分销佣金的方式0:按比例 1:按金额")
   private Boolean shareMethod;

   @ApiModelProperty(name = "share_rate", value = "U团长分销佣金按比例")
   private BigDecimal shareRate;

   @ApiModelProperty(name = "share_amount", value = "U团长分销佣金按金额")
   private BigDecimal shareAmount;

   @ApiModelProperty(name = "share_level_method", value = "U团长管理佣金的方式0:按比例 1:按金额")
   private Boolean shareLevelMethod;

   @ApiModelProperty(name = "share_level_rate", value = "U团长管理佣金按比例")
   private BigDecimal shareLevelRate;

   @ApiModelProperty(name = "share_level_amount", value = "U团长管理佣金按金额")
   private BigDecimal shareLevelAmount;

   @ApiModelProperty(name = "share_second_method", value = "U达人参与分佣的方式0:按比例 1:按金额 ")
   private Boolean shareSecondMethod;

   @ApiModelProperty(name = "share_second_level_method", value = "U达人参与管理佣金的方式0:按比例 1:按金额 ")
   private Boolean shareSecondLevelMethod;

   @ApiModelProperty(name = "share_second_rate", value = "U达人分销佣金按比例")
   private BigDecimal shareSecondRate;

   @ApiModelProperty(name = "share_second_amount", value = "U达人分销佣金按金额")
   private BigDecimal shareSecondAmount;

   @ApiModelProperty(name = "share_second_level_rate", value = "U达人管理佣金按比例")
   private BigDecimal shareSecondLevelRate;

   @ApiModelProperty(name = "share_second_level_amount", value = "U达人管理佣金按金额")
   private BigDecimal shareSecondLevelAmount;

   @ApiModelProperty(name = "share_third_method", value = "U掌柜参与分佣的方式0:按比例 1:按金额 ")
   private Boolean shareThirdMethod;

   @ApiModelProperty(name = "share_third_level_method", value = "U掌柜参与管理佣金的方式0:按比例 1:按金额 ")
   private Boolean shareThirdLevelMethod;

   @ApiModelProperty(name = "share_third_rate", value = "U掌柜分销佣金按比例")
   private BigDecimal shareThirdRate;

   @ApiModelProperty(name = "share_third_amount", value = "U掌柜分销佣金按金额")
   private BigDecimal shareThirdAmount;

   @ApiModelProperty(name = "share_third_level_rate", value = "U掌柜管理佣金按比例")
   private BigDecimal shareThirdLevelRate;

   @ApiModelProperty(name = "share_third_level_amount", value = "U掌柜管理佣金按金额")
   private BigDecimal shareThirdLevelAmount;

   @ApiModelProperty(name = "pickup_address_ids", value = "配送地址")
   private List<Integer> pickupAddressIds;

   @ApiModelProperty(name = "delivery_type", value = "1-配送 2-自提")
   private List<Integer> deliveryType;



   @ApiModelProperty(name = "audit_status", value = "审核状态 0待审核 1人工审核通过 2人工审核不通过（屏蔽）")
   private Boolean auditStatus;

   @ApiModelProperty(name = "audited_at", value = "审核时间")
   private Integer auditedAt;

   @ApiModelProperty(name = "auditor_id", value = "审核人ID 待审核时为0")
   private Long auditorId;


   @ApiModelProperty(value = "skus")
   @TableField(exist = false)
   private List<MallSku> skus;

   private List<MallItemVideo> topVideoList;

   private List<MallItemVideo> detailVideoList;

   private List<MallSpecsModel> specs;


}

