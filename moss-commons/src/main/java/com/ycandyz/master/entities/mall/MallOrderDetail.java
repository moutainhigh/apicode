package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 商城订单详情
 * @Author: Wang Yang
 * @Date:   2020-09-19
 * @Version: V1.0
 */
@Data
@TableName("mall_order_detail")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_order_detail对象", description="商城订单详情")
public class MallOrderDetail {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**订单编号*/
    @ApiModelProperty(value = "订单编号")
	private String orderNo;
	/**订单详情编号*/
    @ApiModelProperty(value = "订单详情编号")
	private String orderDetailNo;
	/**商品编号*/
    @ApiModelProperty(value = "商品编号")
	private String itemNo;
	/**SKU编号*/
    @ApiModelProperty(value = "SKU编号")
	private String skuNo;
	/**商品分类（商城名-商品分类）*/
    @ApiModelProperty(value = "商品分类（商城名-商品分类）")
	private String itemCategory;
	/**商品名称*/
    @ApiModelProperty(value = "商品名称")
	private String itemName;
	/**商品封面图*/
    @ApiModelProperty(value = "商品封面图")
	private String itemCover;
	/**SKU数量*/
    @ApiModelProperty(value = "SKU数量")
	private Integer quantity;
	/**SKU价格*/
    @ApiModelProperty(value = "SKU价格")
	private java.math.BigDecimal price;
	/**货号*/
    @ApiModelProperty(value = "货号")
	private String goodsNo;
	/**订单金额*/
    @ApiModelProperty(value = "订单金额")
	private java.math.BigDecimal totalMoney;
	/**实际支付金额*/
    @ApiModelProperty(value = "实际支付金额")
	private java.math.BigDecimal realMoney;
	/**createdTime*/
    @ApiModelProperty(value = "createdTime")
	private Date createdTime;
	/**updatedTime*/
    @ApiModelProperty(value = "updatedTime")
	private Date updatedTime;
	/**userId*/
    @ApiModelProperty(value = "userId")
	private Integer userId;
	/**是否可以分销*/
    @ApiModelProperty(value = "是否可以分销")
	private Integer isEnableShare;
	/**参与分佣的方式0:按比例 1:按金额*/
    @ApiModelProperty(value = "参与分佣的方式0:按比例 1:按金额")
	private Integer shareMethod;
	/**一个商品的分销金额*/
    @ApiModelProperty(value = "一个商品的分销金额")
	private java.math.BigDecimal shareAmount;
	/**分销比例*/
    @ApiModelProperty(value = "分销比例")
	private java.math.BigDecimal shareRate;
}
