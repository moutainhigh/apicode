package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="售后表", description="售后表查询VO")
public class MallAfterSalesVO {

    @ApiModelProperty(value = "id")
    private Long id;
    /**订单编号*/
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    /**商家编号*/
    @ApiModelProperty(value = "商家编号")
    private String shopNo;
    /**用户id*/
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**订单详情售后编号*/
    @ApiModelProperty(value = "订单详情售后编号")
    private String afterSalesNo;
    /**退款原因  10：多拍/拍错/不想要  11：不喜欢/效果不好  12：做工/质量问题  13：商家发错货  14：未按约定时间发货  15：与商品描述严重不符  16：收到商品少件/破损/污渍  99：其他'*/
    @ApiModelProperty(value = "退款原因  10：多拍/拍错/不想要  11：不喜欢/效果不好  12：做工/质量问题  13：商家发错货  14：未按约定时间发货  15：与商品描述严重不符  16：收到商品少件/破损/污渍  99：其他'")
    private Integer reason;
    /**退款备注*/
    @ApiModelProperty(value = "退款备注")
    private String remark;
    /**退款凭证图，jsonarray*/
    @ApiModelProperty(value = "退款凭证图，jsonarray")
    private String photos;
    /**SKU数量*/
    @ApiModelProperty(value = "SKU数量")
    private Integer skuQuantity;
    /**SKU单价*/
    @ApiModelProperty(value = "SKU单价")
    private BigDecimal skuPrice;
    /**商家退款给用户的金额（商家输入的退款金额）*/
    @ApiModelProperty(value = "商家退款给用户的金额（商家输入的退款金额）")
    private BigDecimal money;
    /**类型 10-退货退款  20-仅退款*/
    @ApiModelProperty(value = "类型 10-退货退款  20-仅退款")
    private Integer status;
    /**1010:一次审核中  1020:一次审核不通过  1030:一次审核通过/待用户退货  1040:超时未退货，系统关闭  1050:用户已退货/退款中  1060:二次审核不通过/拒绝退款  1070:二次审核通过   1080:退款成功  1090:退款失败  2010:退款中  2020:审核通过  2030:退款成功  2040：退款失败  2050：退款拒绝'*/
    @ApiModelProperty(value = "1010:一次审核中  1020:一次审核不通过  1030:一次审核通过/待用户退货  1040:超时未退货，系统关闭  1050:用户已退货/退款中  1060:二次审核不通过/拒绝退款  1070:二次审核通过   1080:退款成功  1090:退款失败  2010:退款中  2020:审核通过  2030:退款成功  2040：退款失败  2050：退款拒绝'")
    private Integer subStatus;
    /**卖家收货时间*/
    @ApiModelProperty(value = "卖家收货时间")
    private Long receiveAt;
    /**售后申请时间*/
    @ApiModelProperty(value = "售后申请时间")
    private Long createdAt;
    /**售后关闭时间（退款成功时间）*/
    @ApiModelProperty(value = "售后关闭时间（退款成功时间）")
    private Long closeAt;
    /**createdTime*/
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    /**updatedTime*/
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
    /**申请时间*/
    @ApiModelProperty(value = "申请时间")
    private Long applyAt;
    /**一次审核时间*/
    @ApiModelProperty(value = "一次审核时间")
    private Long auditFirstAt;
    /**买家发货时间*/
    @ApiModelProperty(value = "买家发货时间")
    private Long sendAt;
    /**二次审核时间*/
    @ApiModelProperty(value = "二次审核时间")
    private Long auditSecondAt;
    @ApiModelProperty(value = "商品名称")
    private String itemName;
    @ApiModelProperty(value = "货号")
    private String goodsNo;
    @ApiModelProperty(value = "实付金额")
    private BigDecimal orderRealMoney;
    @ApiModelProperty(value = "支付类型")
    private Integer payType;
    @ApiModelProperty(value = "实际购买SKU数量")
    private Integer orderQuantity;
    @ApiModelProperty(value = "购买用户")
    private String userName;
    @ApiModelProperty(value = "SKU编号")
    private String skuNo;
}
