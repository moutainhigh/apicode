package com.ycandyz.master.domain.response.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @Description 传播商品分页 Resp
 * @author WangWx
 * @since 2021-01-11
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="传播商品表-Resp")
public class SpreadMallItemPageResp {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "item_id", value = "商品编号")
   private Long itemId;

   @ApiModelProperty(name = "highest_sale_price", value = "最高销售价格")
   private BigDecimal highestSalePrice;

   @ApiModelProperty(name = "is_screen", value = "是否系统屏蔽 0-通过 1-屏蔽")
   private Integer isScreen;

   @ApiModelProperty(name = "item_cover", value = "封面图")
   private String itemCover;

   @ApiModelProperty(name = "item_name", value = "商品名称")
   private String itemName;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "lowest_sale_price", value = "最低销售价格")
   private BigDecimal lowestSalePrice;

   //？
   @ApiModelProperty(name = "organize_id", value = "机构ID")
   private Long organizeId;

   //lowest_sale_price
   @ApiModelProperty(name = "sale_price",value = "商品-售价")
   private BigDecimal salePrice;

   @ApiModelProperty(name = "price", value = "原价格")
   private BigDecimal price;

   @ApiModelProperty(name = "base_sales", value = "销量")
   private Integer baseSales;

   @ApiModelProperty(name = "real_sales", value = "销量")
   private Integer realSales;
   //已处理
   @ApiModelProperty(name = "sales", value = "销量")
   private Integer sales;

   @ApiModelProperty(name = "shop_no", value = "商店编号")
   private String shopNo;


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

   @ApiModelProperty(name = "share_descr",value = "分享描述")
   private String shareDescr;

   @ApiModelProperty(name = "share_img",value = "分享图片")
   private String shareImg;

   @ApiModelProperty(name = "share_method",value = "U团长分销佣金的方式0:按比例 1:按金额")
   private Integer shareMethod;

   @ApiModelProperty(name = "share_rate",value = "U团长分销佣金按比例")
   private BigDecimal shareRate;

   @ApiModelProperty(name = "share_amount",value = "U团长分销佣金按金额")
   private BigDecimal shareAmount;

   @ApiModelProperty(name = "is_enable_share",value = "是否开启分销 0:不开启 1:开启")
   private Integer isEnableShare;

}

