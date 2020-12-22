package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description sku表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-20
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="sku表-检索参数")
public class MallSkuQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(name = "item_no", value = "商品编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemNo;

    @ApiModelProperty(name = "sku_no", value = "sku编号")
    @Condition(condition = ConditionEnum.EQ)
    private String skuNo;

    @ApiModelProperty(name = "sku_name", value = "SKU名字（预留字段）")
    @Condition(condition = ConditionEnum.EQ)
    private String skuName;

    @ApiModelProperty(name = "sku_img", value = "SKU图片（预留字段）")
    @Condition(condition = ConditionEnum.EQ)
    private String skuImg;

    @ApiModelProperty(name = "sale_price", value = "销售价格")
    @Condition(condition = ConditionEnum.EQ)
    private BigDecimal salePrice;

    @ApiModelProperty(name = "price", value = "原价格")
    @Condition(condition = ConditionEnum.EQ)
    private BigDecimal price;

    @ApiModelProperty(name = "stock", value = "库存")
    @Condition(condition = ConditionEnum.EQ)
    private Integer stock;

    @ApiModelProperty(name = "frozen_stock", value = "冻结库存")
    @Condition(condition = ConditionEnum.EQ)
    private Integer frozenStock;

    @ApiModelProperty(name = "goods_no", value = "货号")
    @Condition(condition = ConditionEnum.EQ)
    private String goodsNo;

    @ApiModelProperty(name = "created_time", value = "起")
    @Condition(field = "created_time", condition = ConditionEnum.GE)
    private Date createdTimeS;
    
    @ApiModelProperty(name = "created_time", value = "止")
    @Condition(field = "created_time", condition = ConditionEnum.LE)
    private Date createdTimeE;

    @ApiModelProperty(name = "updated_time", value = "起")
    @Condition(field = "updated_time", condition = ConditionEnum.GE)
    private Date updatedTimeS;
    
    @ApiModelProperty(name = "updated_time", value = "止")
    @Condition(field = "updated_time", condition = ConditionEnum.LE)
    private Date updatedTimeE;

    @ApiModelProperty(name = "real_sales", value = "实际销量")
    @Condition(condition = ConditionEnum.EQ)
    private Integer realSales;

    @ApiModelProperty(name = "sort_value", value = "同一item_no中的sku的排序值")
    @Condition(condition = ConditionEnum.EQ)
    private Integer sortValue;



}
