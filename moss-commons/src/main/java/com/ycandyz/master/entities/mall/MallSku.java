package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * @Description sku表 数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-21
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("mall_sku")
@ApiModel(description="sku表")
public class MallSku extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "商品编号")
   private String itemNo;

   @ApiModelProperty(value = "sku编号")
   private String skuNo;

   @ApiModelProperty(value = "SKU名字（预留字段）")
   private String skuName;

   @ApiModelProperty(value = "SKU图片（预留字段）")
   private String skuImg;

   @ApiModelProperty(value = "销售价格")
   private BigDecimal salePrice;

   @ApiModelProperty(value = "原价格")
   private BigDecimal price;

   @ApiModelProperty(value = "库存")
   private Integer stock;

   @ApiModelProperty(value = "冻结库存")
   private Integer frozenStock;

   @ApiModelProperty(value = "货号")
   private String goodsNo;

   @ApiModelProperty(name = "bar_code",value = "商品条码 ")
   private String barCode;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date updatedTime;

   @ApiModelProperty(value = "实际销量")
   private Integer realSales;

   @ApiModelProperty(value = "同一item_no中的sku的排序值")
   private Integer sortValue;

   @ApiModelProperty(value = "skuSpecs")
   @TableField(exist = false)
   private List<MallSkuSpec> skuSpecs;


}
