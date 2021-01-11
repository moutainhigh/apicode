package com.ycandyz.master.domain.response.mall;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * @Description 商品分页 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-12-19
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="商品表-Resp")
public class MallItemPageResp {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "category_no", value = "分类编号")
   private String categoryNo;

   //已处理
   @ApiModelProperty(name = "category_txt", value = "分类名称")
   private String categoryTxt;

   //？
   @ApiModelProperty(name = "children_organize_name", value = "集团名称")
   private String childrenOrganizeName;

   //已处理
   @ApiModelProperty(name = "created_str", value = "创建时间 yyyy-MM-dd HH:mm:ss")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdStr;

   @ApiModelProperty(name = "created_time", value = "添加时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @ApiModelProperty(name = "goods_no", value = "货号")
   private String goodsNo;

   //已处理
   @ApiModelProperty(name = "goods_no_list", value = "全部货号")
   private List<String> goodsNoList;

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

   @ApiModelProperty(name = "organize_item_no", value = "商品编号")
   private String organizeItemNo;

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

   //不确定字段
   @ApiModelProperty(name = "qr_code_url", value = "二维码")
   private String qrCodeUrl;

   @ApiModelProperty(name = "is_group_supply",value = "是否集团供货(0否,1是)")
   private Integer isGroupSupply;

   @ApiModelProperty(name = "is_copy", value = "是否店铺自己数据(0:不是,1:是)")
   private Integer isCopy;

   @ApiModelProperty(name = "editable", value = "是否可编辑(0:不是,1:是)")
   private Integer editable;



}

