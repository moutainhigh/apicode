package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 商品表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_item")
@ApiModel(description="商品表")
public class MallItem extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "商店编号")
   private String shopNo;

   @ApiModelProperty(value = "分类编号")
   private String categoryNo;

   @ApiModelProperty(value = "商品编号")
   private String itemNo;

   @ApiModelProperty(value = "商品名称")
   private String itemName;

   @ApiModelProperty(value = "商品描述")
   private String itemText;

   @ApiModelProperty(value = "封面图")
   private String itemCover;

   @ApiModelProperty(value = "轮播图，jsonarray")
   private String banners;

   @ApiModelProperty(value = "基础销量")
   private Integer baseSales;

   @ApiModelProperty(value = "实际销量")
   private Integer realSales;

   @ApiModelProperty(value = "排序值")
   private Integer sortValue;

   @ApiModelProperty(value = "分享描述")
   private String shareDescr;

   @ApiModelProperty(value = "分享图片")
   private String shareImg;

   @ApiModelProperty(value = "原价格")
   private BigDecimal price;

   @ApiModelProperty(value = "最低销售价格")
   private BigDecimal lowestSalePrice;

   @ApiModelProperty(value = "最高销售价格")
   private BigDecimal highestSalePrice;

   @ApiModelProperty(value = "库存")
   private Integer stock;

   @ApiModelProperty(value = "冻结库存")
   private Integer frozenStock;

   @ApiModelProperty(value = "库存扣减方式，10: 拍下减库存，20: 付款减库存")
   private Integer subStockMethod;

   @ApiModelProperty(value = "货号")
   private String goodsNo;

   @ApiModelProperty(name = "bar_code",value = "商品条码.")
   private String barCode;

   @ApiModelProperty(value = "配送方式，快递: 10, 线下配送：20")
   private Integer deliverMethod;

   @ApiModelProperty(value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
   private Integer status;

   @ApiModelProperty(value = "是否统一运费，0: 否，1:是")
   private Integer isUnifyShipping;

   @ApiModelProperty(value = "运费模版编号")
   private String shippingNo;

   @ApiModelProperty(value = "统一定价")
   private BigDecimal unifyShipping;

   @ApiModelProperty(value = "起购数量")
   private Integer initialPurchases;

   @ApiModelProperty(value = "限制周期，10:每天，20:每周, 30:每月，40:每年，50总身")
   private Integer limitCycleType;

   @ApiModelProperty(value = "限购数量")
   private Integer limitSkus;

   @ApiModelProperty(value = "限购订单数")
   private Integer limitOrders;

   @ApiModelProperty(value = "二维码")
   private String qrCodeUrl;

   private Date createdTime;

   private Date updatedTime;

   @ApiModelProperty(value = "是否开启分销 0:不开启 1:开启")
   private Integer isEnableShare;

   @ApiModelProperty(value = "参与分佣的方式0:按比例 1:按金额")
   private Integer shareMethod;

   @ApiModelProperty(value = "分销比例")
   private BigDecimal shareRate;

   @ApiModelProperty(value = "分销金额")
   private BigDecimal shareAmount;

   @ApiModelProperty(value = "访问数")
   private Integer visits;

   @ApiModelProperty(value = "是否设置过分销")
   private Integer isUpdatedShare;

   @ApiModelProperty(value = "U达人参与分佣的方式0:按比例 1:按金额 ")
   private Integer shareSecondMethod;

   @ApiModelProperty(value = "U达人参与管理佣金的方式0:按比例 1:按金额 ")
   private Integer shareSecondLevelMethod;

   @ApiModelProperty(value = "U达人分销佣金按比例")
   private BigDecimal shareSecondRate;

   @ApiModelProperty(value = "U达人分销佣金按金额")
   private BigDecimal shareSecondAmount;

   @ApiModelProperty(value = "U达人管理佣金按比例")
   private BigDecimal shareSecondLevelRate;

   @ApiModelProperty(value = "U达人管理佣金按金额")
   private BigDecimal shareSecondLevelAmount;

   @ApiModelProperty(value = "U掌柜参与分佣的方式0:按比例 1:按金额 ")
   private Integer shareThirdMethod;

   @ApiModelProperty(value = "U掌柜参与管理佣金的方式0:按比例 1:按金额 ")
   private Integer shareThirdLevelMethod;

   @ApiModelProperty(value = "U掌柜分销佣金按比例")
   private BigDecimal shareThirdRate;

   @ApiModelProperty(value = "U掌柜分销佣金按金额")
   private BigDecimal shareThirdAmount;

   @ApiModelProperty(value = "U掌柜管理佣金按比例")
   private BigDecimal shareThirdLevelRate;

   @ApiModelProperty(value = "U掌柜管理佣金按金额")
   private BigDecimal shareThirdLevelAmount;

   @ApiModelProperty(value = "1- 配送 2-自提")
   private String deliveryType;

   @ApiModelProperty(value = "配送地址")
   private String pickupAddressIds;

   @ApiModelProperty(value = "1-配送 2-自提")
   @TableField(exist = false)
   private String deliveryTypeBak;

   @ApiModelProperty(value = "是否系统屏蔽 0-通过 1-屏蔽")
   private Integer isScreen;

   //非销售类商品
   @ApiModelProperty(name = "type",value = "商品类型(0非商品,1商品)")
   private Integer type;

   @ApiModelProperty(name = "non_price_type",value = "非销售商品-价格类型(0不显示,1显示)")
   private Integer nonPriceType;

   @ApiModelProperty(name = "non_sale_price",value = "非销售商品-售价")
   private String nonSalePrice;

   @ApiModelProperty(name = "non_price",value = "非销售商品-原价")
   private String nonPrice;

   @ApiModelProperty(name = "audit_status",value = "审核状态 0待审核 1人工审核通过 2人工审核不通过（屏蔽）")
   private Integer auditStatus;

   @ApiModelProperty(name = "audited_at",value = "审核时间")
   private Integer auditedAt;

   @ApiModelProperty(name = "auditor_id",value = "审核人ID 待审核时为0")
   private Long auditorId;

   @ApiModelProperty(name = "share_level_method",value = "U团长管理佣金的方式0:按比例 1:按金额")
   private Integer shareLevelMethod;

   @ApiModelProperty(name = "share_level_rate",value = "U团长管理佣金按比例")
   private BigDecimal shareLevelRate;

   @ApiModelProperty(name = "share_level_amount",value = "U团长管理佣金按金额")
   private BigDecimal shareLevelAmount;
}
