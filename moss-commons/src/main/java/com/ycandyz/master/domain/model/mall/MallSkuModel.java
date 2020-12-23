package com.ycandyz.master.domain.model.mall;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.Serializable;

/**
 * <p>
 * @Description sku表 Model
 * </p>
 *
 * @author SanGang
 * @since 2020-12-20
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="sku表-Model")
public class MallSkuModel implements Serializable {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "sku_no", value = "sku编号")
   private String skuNo;

   @ApiModelProperty(name = "sku_name", value = "SKU名字（预留字段）")
   private String skuName;

   @ApiModelProperty(name = "sku_img", value = "SKU图片（预留字段）")
   private String skuImg;

   @ApiModelProperty(name = "sale_price", value = "销售价格")
   private BigDecimal salePrice;

   @ApiModelProperty(name = "price", value = "原价格")
   private BigDecimal price;

   @ApiModelProperty(name = "stock", value = "库存")
   private Integer stock;

   @ApiModelProperty(name = "frozen_stock", value = "冻结库存")
   private Integer frozenStock;

   @ApiModelProperty(name = "goods_no", value = "货号")
   private String goodsNo;

   private Date createdTime;

   private Date updatedTime;

   @ApiModelProperty(name = "real_sales", value = "实际销量")
   private Integer realSales;

   @ApiModelProperty(name = "sort_value", value = "同一item_no中的sku的排序值")
   private Integer sortValue;


}

