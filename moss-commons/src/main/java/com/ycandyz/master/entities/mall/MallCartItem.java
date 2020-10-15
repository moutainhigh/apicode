package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 商城购物车
 * @Author: Wang Yang
 * @Date:   2020-10-13
 * @Version: V1.0
 */
@Data
@TableName("mall_cart_item")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_cart_item对象", description="商城购物车")
public class MallCartItem implements Serializable{
    
	/**数据编号*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "数据编号")
	private Integer id;
	/**店铺编号*/
    @ApiModelProperty(value = "店铺编号")
	private String shopNo;
	/**用户编号*/
    @ApiModelProperty(value = "用户编号")
	private Integer userId;
	/**商品分类*/
    @ApiModelProperty(value = "商品分类编号")
	private String categoryNo;
	/**商品编号*/
    @ApiModelProperty(value = "商品编号")
	private String itemNo;
	/**商品名称*/
    @ApiModelProperty(value = "商品名称")
	private String itemName;
	/**商品图片*/
    @ApiModelProperty(value = "商品图片")
	private String itemCover;
	/**SKU编号*/
    @ApiModelProperty(value = "SKU编号")
	private String skuNo;
	/**购买数量*/
    @ApiModelProperty(value = "购买数量")
	private Integer quantity;
	/**添加到购物车的价格*/
    @ApiModelProperty(value = "添加到购物车的价格")
	private java.math.BigDecimal price;
	/**删除状态 0：未删除，1：已删除*/
    @ApiModelProperty(value = "删除状态 0：未删除，1：已删除")
	private Integer deleteStatus;
	/**商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]*/
    @ApiModelProperty(value = "商品销售属性")
	private String itemSpec;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**修改时间*/
    @ApiModelProperty(value = "修改时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;
}
