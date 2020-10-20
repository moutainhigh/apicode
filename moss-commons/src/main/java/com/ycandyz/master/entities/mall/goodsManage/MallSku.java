package com.ycandyz.master.entities.mall.goodsManage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("mall_sku")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_sku对象", description="sku表")
public class MallSku {

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private long id;

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

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "实际销量")
    private Integer realSales;

    @ApiModelProperty(value = "同一item_no中的sku的排序值")
    private Integer sortValue;


}
