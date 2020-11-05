package com.ycandyz.master.dto.mall;

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
public class MallAfterSalesDTO {

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
    /**退款商品数量*/
    @ApiModelProperty(value = "退款商品数量")
    private Integer skuQuantity;
    /**退款商品的单价*/
    @ApiModelProperty(value = "退款商品的单价")
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
    @ApiModelProperty(value = "退款中关联订单")
    private MallOrderByAfterSalesDTO order;
    @ApiModelProperty(value = "订单详情")
    private String orderDetailNo;
    @ApiModelProperty(value = "退款中关联订单详情")
    private MallOrderDetailByAfterSalesDTO details;
    @ApiModelProperty(value = "售后状态 1-待审核 2-待买家退货 3-待确认退款 4-退款成功 5-退款失败 6-退款关闭")
    private Integer state;
    @ApiModelProperty(value = "关联售后日志表")
    private List<MallAfterSalesLogDTO> asLog;
    @ApiModelProperty(value = "关联商家寄出的快递表")
    private MallShopShippingDTO shippingInfo;
    @ApiModelProperty(value = "关联商家寄出的快递物流日志表")
    private List<MallShopShippingLogDTO> shippinglog;
    @ApiModelProperty(value = "关联商店表")
    private MallShopDTO shop;
    @ApiModelProperty(value = "总计金额")
    private BigDecimal allMoney;
    @ApiModelProperty(value = "关联买家快递表")
    private MallBuyerShippingDTO buyerShipping;
    @ApiModelProperty(value = "关联买家寄出快递日志表")
    private List<MallBuyerShippingLogDTO> buyerShippingLog;
    @ApiModelProperty(value = "本次退款的金额")
    private BigDecimal refundMoney;

    /**卖家收货时间字符串*/
    @ApiModelProperty(value = "卖家收货时间字符串")
    @Getter(AccessLevel.NONE)
    private String receiveAtStr;
    @ApiModelProperty(value = "售后申请时间字符串")
    @Getter(AccessLevel.NONE)
    private String createdAtStr;
    /**售后关闭时间（退款成功时间）*/
    @ApiModelProperty(value = "售后关闭时间（退款成功时间）字符串")
    @Getter(AccessLevel.NONE)
    private String closeAtStr;
    /**申请时间字符串*/
    @ApiModelProperty(value = "申请时间字符串")
    @Getter(AccessLevel.NONE)
    private String applyAtStr;
    /**一次审核时间字符串*/
    @ApiModelProperty(value = "一次审核时间字符串")
    @Getter(AccessLevel.NONE)
    private String auditFirstAtStr;
    /**买家发货时间字符串*/
    @ApiModelProperty(value = "买家发货时间字符串")
    @Getter(AccessLevel.NONE)
    private String sendAtStr;
    /**二次审核时间字符串*/
    @ApiModelProperty(value = "二次审核时间字符串")
    @Getter(AccessLevel.NONE)
    private String auditSecondAtStr;

    /**退款原因，文字内容*/
    @ApiModelProperty(value = "退款原因，文字内容")
    private String reasonStr;
    /**退款凭证图jsonarray转换的list*/
    @ApiModelProperty(value = "退款凭证图jsonarray转换的list")
    private List<String> photosArray;

    public String getReceiveAtStr(){
        try {
            if(receiveAt!=0) {
                Long at = Long.valueOf(receiveAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.receiveAtStr = sd.format(date);
            }else {
                this.receiveAtStr = "-";
            }
        }catch (Exception e){
            receiveAtStr = "-";
        }
        return receiveAtStr;
    }

    public String getCreatedAtStr(){
        try {
//            if(createdAt!=0) {
//                Long at = Long.valueOf(createdAt) * 1000;
//                Date date = new Date(at);
//                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                this.createdAtStr = sd.format(date);
//            }else {
//                this.createdAtStr = "-";
//            }
            if(createdTime!=null){
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.createdAtStr = sd.format(createdTime);
            }
        }catch (Exception e){
            createdAtStr = "-";
        }
        return createdAtStr;
    }

    public String getCloseAtStr(){
        try {
            if(closeAt!=0) {
                Long at = Long.valueOf(closeAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.closeAtStr = sd.format(date);
            }else {
                this.closeAtStr = "-";
            }
        }catch (Exception e){
            closeAtStr = "-";
        }
        return closeAtStr;
    }

    public String getApplyAtStr(){
        try {
            if(applyAt!=0) {
                Long at = Long.valueOf(applyAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.applyAtStr = sd.format(date);
            }else {
                this.applyAtStr = "-";
            }
        }catch (Exception e){
            applyAtStr = "-";
        }
        return applyAtStr;
    }

    public String getAuditFirstAtStr(){
        try {
            if(auditFirstAt!=0) {
                Long at = Long.valueOf(auditFirstAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.auditFirstAtStr = sd.format(date);
            }else {
                this.auditFirstAtStr = "-";
            }
        }catch (Exception e){
            auditFirstAtStr = "-";
        }
        return auditFirstAtStr;
    }

    public String getSendAtStr(){
        try {
            if(sendAt!=0) {
                Long at = Long.valueOf(sendAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.sendAtStr = sd.format(date);
            }else {
                this.sendAtStr = "-";
            }
        }catch (Exception e){
            sendAtStr = "-";
        }
        return sendAtStr;
    }

    public String getAuditSecondAtStr(){
        try {
            if(auditSecondAt!=0) {
                Long at = Long.valueOf(auditSecondAt) * 1000;
                Date date = new Date(at);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.auditSecondAtStr = sd.format(date);
            }else {
                this.auditSecondAtStr = "-";
            }
        }catch (Exception e){
            auditSecondAtStr = "-";
        }
        return auditSecondAtStr;
    }
}
