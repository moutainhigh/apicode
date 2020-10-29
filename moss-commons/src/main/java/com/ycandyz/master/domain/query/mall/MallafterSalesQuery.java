package com.ycandyz.master.domain.query.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="退款表")
public class MallafterSalesQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "订单编号")
    @Getter(AccessLevel.NONE)
    private String orderNo;
    @ApiModelProperty(value = "订单详情售后编号")
    @Getter(AccessLevel.NONE)
    private String afterSalesNo;
    @ApiModelProperty(value = "类型 10-退货退款  20-仅退款")
    private Integer status;
    @ApiModelProperty(value = "1010:一次审核中  1020:一次审核不通过  1030:一次审核通过/待用户退货  1040:超时未退货，系统关闭  1050:用户已退货/退款中  " +
            "1060:二次审核不通过/拒绝退款  1070:二次审核通过   1080:退款成功  1090:退款失败  2010:退款中  2020:审核通过  2030:退款成功  2040：退款失败  2050：退款拒绝")
    private Integer subStatus;
    @ApiModelProperty(value = "售后申请时间开始")
    private Integer applyAtFrom;
    @ApiModelProperty(value = "售后申请时间结束")
    private Integer applyAtTo;
    @ApiModelProperty(value = "商品信息 商品名或货号")
    @Getter(AccessLevel.NONE)
    private String shopItem;
    @ApiModelProperty(value = "用户信息 用户名或手机号")
    @Getter(AccessLevel.NONE)
    private String userName;
    @ApiModelProperty(value = "退款单号")
    @Getter(AccessLevel.NONE)
    private String refundNo;
    @ApiModelProperty(value = "收货地址")
    @Getter(AccessLevel.NONE)
    private String receiverAddress;
    @ApiModelProperty(value = "收货人手机号")
    @Getter(AccessLevel.NONE)
    private String receiverPhone;
    @ApiModelProperty(value = "收货人")
    @Getter(AccessLevel.NONE)
    private String receiver;
    @ApiModelProperty(value = "商家编号")
    @Getter(AccessLevel.NONE)
    private String shopNo;
    @ApiModelProperty(value = "售后状态 1-待审核 2-待买家退货 3-待确认退款 4-退款成功 5-退款失败 6-退款关闭")
    private Integer state;

    public String getOrderNo() {
        if (orderNo!=null){
            orderNo = orderNo.trim();
        }
        return orderNo;
    }

    public String getAfterSalesNo() {
        if (afterSalesNo!=null){
            afterSalesNo = afterSalesNo.trim();
        }
        return afterSalesNo;
    }

    public String getShopItem() {
        if (shopItem!=null){
            shopItem = shopItem.trim();
        }
        return shopItem;
    }

    public String getUserName() {
        if (userName!=null){
            userName = userName.trim();
        }
        return userName;
    }

    public String getRefundNo() {
        if (refundNo!=null){
            refundNo = refundNo.trim();
        }
        return refundNo;
    }

    public String getReceiverAddress() {
        if (receiverAddress!=null){
            receiverAddress = receiverAddress.trim();
        }
        return receiverAddress;
    }

    public String getReceiverPhone() {
        if (receiverPhone!=null){
            receiverPhone = receiverPhone.trim();
        }
        return receiverPhone;
    }

    public String getReceiver() {
        if (receiver!=null){
            receiver = receiver.trim();
        }
        return receiver;
    }

    public String getShopNo() {
        if (shopNo!=null){
            shopNo = shopNo.trim();
        }
        return shopNo;
    }
}
