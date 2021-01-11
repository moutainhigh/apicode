package com.ycandyz.master.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.dto.mall.MallOrderDetailDTO;
import com.ycandyz.master.model.mall.uApp.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "uapp订单列表", description = "uapp订单列表查询DTO")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MallOrderUAppVO {

    @ApiModelProperty(value = "id")
    private Integer id;
    /**
     * 商店编号
     */
    @ApiModelProperty(value = "商店编号")
    private String shopNo;
    /**
     * 交易流水
     */
    @ApiModelProperty(value = "交易流水")
    private String tradeNo;
    /**
     * 下单用户id
     */
    @ApiModelProperty(value = "下单用户id")
    private Integer userId;
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    /**
     * 支付方式：0-未知  1-支付宝 2-微信  3-银联
     */
    @ApiModelProperty(value = "支付方式：0-未知  1-支付宝 2-微信  3-银联")
    private Integer payType;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private Integer payedAt;
    /**
     * 待付款，买家取消订单原因  501010：多拍/拍错/不想要  501011：不喜欢/效果不好  501012：做工/质量问题  501013：商家发错货  501014：未按约定时间发货  501015：与商品描述严重不符  501016：收到商品少件/破损/污渍  501099：其他
     * 待发货，买家取消订单原因 503010-多拍/拍错/不想要  503011-协商一致退款  503012-缺货  503013-未按约定时间发货  503099-其他 待发货，商家取消订单原因 504010-暂停售卖  504011-缺货/断货  504099-其他
     */
    @ApiModelProperty(value = "待付款，买家取消订单原因  501010：多拍/拍错/不想要  501011：不喜欢/效果不好  501012：做工/质量问题  501013：商家发错货  501014：未按约定时间发货  501015：与商品描述严重不符  501016：收到商品少件/破损/污渍  501099：其他 待发货，买家取消订单原因 503010-多拍/拍错/不想要  503011-协商一致退款  503012-缺货  503013-未按约定时间发货  503099-其他 待发货，商家取消订单原因 504010-暂停售卖  504011-缺货/断货  504099-其他 ")
    private java.lang.Integer cancelReason;
    /**
     * 取消订单时间
     */
    @ApiModelProperty(value = "取消订单时间")
    private java.lang.Integer cancelAt;
    /**
     * 售后状态：0-否  1-处理中  2-已完成
     */
    @ApiModelProperty(value = "售后状态：0-否  1-处理中  2-已完成 ")
    private java.lang.Integer afterSalesStatus;
    /**
     * 该笔订单售后结束时间
     */
    @ApiModelProperty(value = "该笔订单售后结束时间")
    private java.lang.Integer afterSalesEndAt;
    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private java.math.BigDecimal totalMoney;
    /**
     * 实际支付金额
     */
    @ApiModelProperty(value = "实际支付金额")
    private java.math.BigDecimal realMoney;
    /**
     * 总金额（包含real_money和shop_shipping中的运费）
     */
    @ApiModelProperty(value = "总金额（包含real_money和shop_shipping中的运费）")
    private java.math.BigDecimal allMoney;
    /**
     * 用户下单备注
     */
    @ApiModelProperty(value = "用户下单备注")
    private java.lang.String buyerRemark;
    /**
     * 下单时间
     */
    @ApiModelProperty(value = "下单时间")
    private java.lang.Integer orderAt;
    /**
     * 商家发货时间
     */
    @ApiModelProperty(value = "商家发货时间")
    private java.lang.Integer sendAt;
    /**
     * 10-待支付  20-待发货 30-待收货 40-已收货  50-已取消
     */
    @ApiModelProperty(value = "10-待支付  20-待发货 30-待收货 40-已收货  50-已取消 ")
    private java.lang.Integer status;
    /**
     * 1010-初始化 - 待支付  1020-支付失败 - 待支付  1030-支付取消- 待支付 2010-待发货   2020-退款中(并非支付那边的退款中，而是本地的) 2030-无需发货  3010-待收货 4010-系统自动收货  4020-用户手动收货  4030-退款中(并非支付那边的退款中，而是本地的)
     * 4040-退款成功  4050-退款关闭 5010-用户主动取消(待支付)  5020-系统超时取消(待支付)  5030-用户主动取消(待发货)  5040-卖家取消(待发货)  6010-用户删除  6020-商家删除
     */
    @ApiModelProperty(value = "1010-初始化 - 待支付  1020-支付失败 - 待支付  1030-支付取消- 待支付 2010-待发货   2020-退款中(并非支付那边的退款中，而是本地的) 2030-无需发货  3010-待收货 4010-系统自动收货  4020-用户手动收货  4030-退款中(并非支付那边的退款中，而是本地的) 4040-退款成功  4050-退款关闭 5010-用户主动取消(待支付)  5020-系统超时取消(待支付)  5030-用户主动取消(待发货)  5040-卖家取消(待发货)  6010-用户删除  6020-商家删除")
    private java.lang.Integer subStatus;
    /**
     * 订单关闭时间
     */
    @ApiModelProperty(value = "订单关闭时间")
    private java.lang.Integer closeAt;
    /**
     * 配送方式，快递: 10, 线下配送：20
     */
    @ApiModelProperty(value = "配送方式，快递: 10, 线下配送：20")
    private java.lang.Integer deliverMethod;
    /**
     * 0-未删除  1-由用户删除  2-由商家删除
     */
    @ApiModelProperty(value = "0-未删除  1-由用户删除  2-由商家删除")
    private java.lang.Integer isDel;
    /**
     * 发货方式:1-配送 2-自提
     */
    @ApiModelProperty(value = "1-配送 2-自提")
    private java.lang.Integer deliverType;
    /**
     * 自提码
     */
    @ApiModelProperty(value = "自提码")
    private java.lang.String pickupNo;
    /**
     * 自提地址名称
     */
    @ApiModelProperty(value = "自提地址名称")
    private java.lang.String pickUpAddressName;
    /**
     * 自提地址
     */
    @ApiModelProperty(value = "自提地址")
    private java.lang.String pickUpAddressDetail;
    /**
     * 预留电话
     */
    @ApiModelProperty(value = "预留电话")
    private java.lang.String prePhone;
    /**
     * 购物车订单编号
     */
    @ApiModelProperty(value = "购物车订单编号")
    private java.lang.String cartOrderSn;
    /**
     * 订单类型1->神州通
     */
    @ApiModelProperty(value = "订单类型1->商城，2->神州通")
    private Integer orderType;
    /**
     * 购物车订单主键
     */
    @ApiModelProperty(value = "购物车订单主键")
    private java.lang.String cartOrderId;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private List<String> itemName;
    /**
     * 购买用户
     */
    @ApiModelProperty(value = "购买用户")
    private java.lang.String payuser;
    /**
     * 收货人
     */
    @ApiModelProperty(value = "收货人")
    private java.lang.String receiverName;
    /**
     * 收货人手机
     */
    @ApiModelProperty(value = "收货人手机")
    private java.lang.String receiverPhone;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private java.util.Date paymentTime;
    /**
     * 货号
     */
    @ApiModelProperty(value = "货号")
    private List<String> goodsNo;
    /**
     * SKU数量
     */
    @ApiModelProperty(value = "购买数量")
    private java.lang.Integer quantity;
    /**
     * 该笔订单折扣金额
     */
    @ApiModelProperty(value = "该笔订单折扣金额")
    private BigDecimal discountMoney;
    /**
     * 关联订单详情
     */
    @ApiModelProperty(value = "关联订单详情")
    private List<MallOrderDetailUAppVO> details;
    /**
     * 售后
     */
    @ApiModelProperty(value = "售后 100: 暂无：还在有效期内，目前还没有申请售后; 110: 否：超过有效期，但是没有申请售后; 111: 是：申请了售后就是，跟有效期无关")
    private Integer asStatus;
    @ApiModelProperty(value = "运费")
    private BigDecimal shippingMoney;
    @ApiModelProperty(value = "所属企业")
    private String organizeName;
    /**
     * receiveAt
     */
    @ApiModelProperty(value = "receiveAt")
    private java.lang.Integer receiveAt;
    /**
     * 关联卖家物流日志表
     */
    @ApiModelProperty(value = "关联卖家物流日志表")
    private List<MallShopShippingLogUAppVO> shopShippingLog;
    /**
     * 关联买家寄出的快递表
     */
    @ApiModelProperty(value = "关联买家寄出的快递表")
    private List<MallBuyerShippingUAppVO> buyerShipping;
    /**
     * 关联买家寄出的快递物流日志表
     */
    @ApiModelProperty(value = "关联买家寄出的快递物流日志表")
    private List<MallBuyerShippingLogUAppVO> buyerShippingLog;
    /**
     * 关联佣金流水表
     */
    @ApiModelProperty(value = "关联佣金流水表")
    private List<MallSocialShareFlowUAppVO> shareInfo;
    /**
     * 关联售后
     */
    @ApiModelProperty(value = "关联售后")
    private List<MallAfterSalesUAppVO> afterSales;
    /**
     * 关联售后日志
     */
    @ApiModelProperty(value = "关联售后日志")
    private List<MallAfterSalesLogUAppVO> afterSalesLog;
    /**
     * 关联卖家物流表
     */
    @ApiModelProperty(value = "关联卖家物流表")
    private MallShopShippingUAppVO shopShipping;
    /**
     * 关联商店
     */
    @ApiModelProperty(value = "关联商店")
    private MallShopUAppVO shopInfo;
    /**
     * 下单用户名
     */
    @ApiModelProperty(value = "下单用户名")
    private String payuserName;
    /**
     * 下单用户手机号
     */
    @ApiModelProperty(value = "下单用户手机号")
    private String payuserPhone;
    /**
     * 下单用户头像
     */
    @ApiModelProperty(value = "下单用户头像")
    private String payuserHeading;
    @ApiModelProperty(value = "管理佣金")
    private BigDecimal shareManageMoney;
    @ApiModelProperty(value = "分销佣金")
    private BigDecimal shareDistributionMoney;
    /**
     * 头部展示文案
     */
    @ApiModelProperty(value = "头部展示文案")
    private String headField;

    /**
     * 支付时间字符串
     */
    @ApiModelProperty(value = "支付时间字符串")
    private String payedAtStr;
    /**
     * 取消订单时间字符串
     */
    @ApiModelProperty(value = "取消订单时间字符串")
    @Getter(AccessLevel.NONE)
    private String cancelAtStr;
    /**
     * 该笔订单售后结束时间字符串
     */
    @ApiModelProperty(value = "该笔订单售后结束时间字符串")
    @Getter(AccessLevel.NONE)
    private String afterSalesEndAtStr;
    /**
     * 下单时间字符串
     */
    @ApiModelProperty(value = "下单时间字符串")
    private String orderAtStr;
    /**
     * 收货时间字符串
     */
    @ApiModelProperty(value = "收货时间字符串")
    private String receiveAtStr;
    /**
     * 订单关闭时间字符串
     */
    @ApiModelProperty(value = "订单关闭时间字符串")
    @Getter(AccessLevel.NONE)
    private String closeAtStr;
    /**
     * 商家发货字符串
     */
    @ApiModelProperty(value = "商家发货字符串")
    private String sendAtStr;

    @ApiModelProperty(value = "退款流水（对外展示的）")
    private String refundNo;

    /**
     * 待付款，买家取消订单原因  501010：多拍/拍错/不想要  501011：不喜欢/效果不好  501012：做工/质量问题  501013：商家发错货  501014：未按约定时间发货  501015：与商品描述严重不符  501016：收到商品少件/破损/污渍  501099：其他
     * 待发货，买家取消订单原因 503010-多拍/拍错/不想要  503011-协商一致退款  503012-缺货  503013-未按约定时间发货  503099-其他 待发货，商家取消订单原因 504010-暂停售卖  504011-缺货/断货  504099-其他
     */
    @ApiModelProperty(value = "待付款，买家取消订单原因  501010：多拍/拍错/不想要  501011：不喜欢/效果不好  501012：做工/质量问题  501013：商家发错货  501014：未按约定时间发货  501015：与商品描述严重不符  501016：收到商品少件/破损/污渍  501099：其他 待发货，买家取消订单原因 503010-多拍/拍错/不想要  503011-协商一致退款  503012-缺货  503013-未按约定时间发货  503099-其他 待发货，商家取消订单原因 504010-暂停售卖  504011-缺货/断货  504099-其他 ")
    private String cancelReasonName;

    @ApiModelProperty(value = "是否集团供货")
    private Integer isGroupSupply;

    @ApiModelProperty(value = "门店是否可维护订单")
    private Integer allowOperating;

    public String getCancelAtStr() {
        try {
            if (cancelAt != 0) {
                Long at = Long.valueOf(cancelAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.cancelAtStr = sd.format(date);
            } else {
                this.cancelAtStr = "-";
            }
        } catch (Exception e) {
            cancelAtStr = "-";
        }
        return cancelAtStr;
    }

    public String getAfterSalesEndAtStr() {
        try {
            if (afterSalesEndAt != 0) {
                Long at = Long.valueOf(afterSalesEndAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.afterSalesEndAtStr = sd.format(date);
            } else {
                this.afterSalesEndAtStr = "-";
            }
        } catch (Exception e) {
            afterSalesEndAtStr = "-";
        }
        return afterSalesEndAtStr;
    }

    public String getCloseAtStr() {
        try {
            if (closeAt != 0) {
                Long at = Long.valueOf(closeAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.closeAtStr = sd.format(date);
            } else {
                this.closeAtStr = "-";
            }
        } catch (Exception e) {
            closeAtStr = "-";
        }
        return closeAtStr;
    }
}
