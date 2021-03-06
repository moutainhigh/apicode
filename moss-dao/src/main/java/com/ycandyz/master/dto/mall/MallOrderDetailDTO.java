package com.ycandyz.master.dto.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallOrderDetailDTO {

    @ApiModelProperty(value = "id")
    private Integer id;
    /**商店编号*/
    @ApiModelProperty(value = "商店编号")
    private String shopNo;
    /**下单用户id*/
    @ApiModelProperty(value = "下单用户id")
    private Integer userId;
    /**订单编号*/
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "10-待支付  20-待发货 30-待收货 40-已收货  50-已取消 ")
    private java.lang.Integer status;
    /**1010-初始化 - 待支付  1020-支付失败 - 待支付  1030-支付取消- 待支付 2010-待发货   2020-退款中(并非支付那边的退款中，而是本地的) 2030-无需发货  3010-待收货 4010-系统自动收货  4020-用户手动收货  4030-退款中(并非支付那边的退款中，而是本地的)
     4040-退款成功  4050-退款关闭 5010-用户主动取消(待支付)  5020-系统超时取消(待支付)  5030-用户主动取消(待发货)  5040-卖家取消(待发货)  6010-用户删除  6020-商家删除*/
    @ApiModelProperty(value = "1010-初始化 - 待支付  1020-支付失败 - 待支付  1030-支付取消- 待支付 2010-待发货   2020-退款中(并非支付那边的退款中，而是本地的) 2030-无需发货  3010-待收货 4010-系统自动收货  4020-用户手动收货  4030-退款中(并非支付那边的退款中，而是本地的) 4040-退款成功  4050-退款关闭 5010-用户主动取消(待支付)  5020-系统超时取消(待支付)  5030-用户主动取消(待发货)  5040-卖家取消(待发货)  6010-用户删除  6020-商家删除")
    private java.lang.Integer subStatus;
    /**支付时间*/
    @ApiModelProperty(value = "支付时间")
    private Integer payedAt;
    /**下单时间*/
    @ApiModelProperty(value = "下单时间")
    private java.lang.Integer orderAt;
    /**订单关闭时间*/
    @ApiModelProperty(value = "订单关闭时间")
    private java.lang.Integer closeAt;
    /**商品编号*/
    @ApiModelProperty(value = "商品编号")
    private java.lang.String itemNo;
    /**商品名称*/
    @ApiModelProperty(value = "商品名称")
    private java.lang.String itemName;
    /**商品封面图*/
    @ApiModelProperty(value = "商品封面图")
    private java.lang.String itemCover;
    /**货号*/
    @ApiModelProperty(value = "货号")
    private java.lang.String goodsNo;
    /**订单金额*/
    @ApiModelProperty(value = "订单金额")
    private java.lang.String totalMoney;
    /**实际支付金额*/
    @ApiModelProperty(value = "实际支付金额")
    private java.lang.String realMoney;
    /**SKU数量*/
    @ApiModelProperty(value = "SKU数量")
    private java.lang.Integer skuQuantity;
    /**SKU价格*/
    @ApiModelProperty(value = "SKU价格")
    private java.math.BigDecimal skuPrice;
    /**运费*/
    @ApiModelProperty(value = "运费")
    private java.math.BigDecimal shipMoney;
    /**总金额（包含real_money和shop_shipping中的运费）*/
    @ApiModelProperty(value = "总金额（包含real_money和shop_shipping中的运费）")
    private java.math.BigDecimal allMoney;
    /**订单详情编号*/
    @ApiModelProperty(value = "订单详情编号")
    private String orderDetailNo;
    /**SKU编号*/
    @ApiModelProperty(value = "SKU编号")
    private String skuNo;
    /**关联订单详情规格值表*/
    @ApiModelProperty(value = "关联订单详情规格值表")
    private List<MallOrderDetailSpecDTO> specs;
    /**是否可以分销*/
    @ApiModelProperty(value = "是否可以分销")
    private Integer isEnableShare;
    /**分佣金额*/
    @ApiModelProperty(value = "分佣金额")
    private Integer shareAmount;
    /**一层分销合伙人用户id*/
    @ApiModelProperty(value = "一层分销合伙人用户id")
    private Integer shareUserId;
    /**所有分销人集合*/
    @ApiModelProperty(value = "所有分销人集合")
    private List<MallSocialShareFlowDTO> shareFlowInfo;
    /**该笔订单售后结束时间*/
    @ApiModelProperty(value = "该笔订单售后结束时间")
    private Long moAfterSalesEndAt;
}
