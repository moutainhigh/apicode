package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 订单列表搜索传参类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="订单表-检索参数")
public class MallOrderQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号",name="orderNo",required=true)
    @Getter(AccessLevel.NONE)
    private String orderNo;
    @ApiModelProperty(value = "商品名称",name="mallName",required=true)
    @Getter(AccessLevel.NONE)
    private String mallName;
    @ApiModelProperty(value = "用户信息(用户名称，手机号)",name="userMes",required=true)
    @Getter(AccessLevel.NONE)
    private String userMes;
    @ApiModelProperty(value = "收货人(用户名称，手机号)",name="consignor",required=true)
    @Getter(AccessLevel.NONE)
    private String consignor;
    @ApiModelProperty(value = "发货方式:1-配送 2-自提",name="deliverType",required=true)
    private Integer deliverType;
    @ApiModelProperty(value = "是否分销:1-是 0-否",name="distribution",required=true)
    private Integer distribution;
    @ApiModelProperty(value = "合伙人名称和手机号",name="socialPartner",required=true)
    @Getter(AccessLevel.NONE)
    private String socialPartner;
    @ApiModelProperty(value = "下单开始时间",name="orderAtForm",required=true)
    private Integer orderAtForm;
    @ApiModelProperty(value = "下单结束时间",name="orderAtTo",required=true)
    private Integer orderAtTo;
    @ApiModelProperty(value = "支付开始时间",name="payedAtForm",required=true)
    private Integer payedAtForm;
    @ApiModelProperty(value = "支付结束时间",name="payedAtTo",required=true)
    private Integer payedAtTo;
    @ApiModelProperty(value = "收货开始时间",name="receiveAtForm",required=true)
    private Integer receiveAtForm;
    @ApiModelProperty(value = "收货结束时间",name="receiveAtTo",required=true)
    private Integer receiveAtTo;
    @ApiModelProperty(value = "是否售后:0-否，1-是",name="afterSalesStatus",required=true)
    private Integer afterSalesStatus;
    @ApiModelProperty(value = "商店标号",name="shopNo",required=true)
    private List<String> shopNo;
    @ApiModelProperty(value = "10-待支付  20-待发货 30-待收货 40-已收货  50-已取消",name="status",required=true)
    private Integer status;
    @ApiModelProperty(value = "发货开始时间",name="sendAtForm",required=true)
    private Integer sendAtForm;
    @ApiModelProperty(value = "发货结束时间",name="sendAtTo",required=true)
    private Integer sendAtTo;
    @ApiModelProperty(value = "取消订单开始时间",name="cancelAtForm",required=true)
    private Integer cancelAtForm;
    @ApiModelProperty(value = "取消订单结束时间",name="cancelAtTo",required=true)
    private Integer cancelAtTo;
    @ApiModelProperty(value = "待付款，买家取消订单原因  501010：多拍/拍错/不想要  501011：不喜欢/效果不好  501012：做工/质量问题  501013：商家发错货  501014：未按约定时间发货  501015：与商品描述严重不符  501016：收到商品少件/破损/污渍  501099：其他\\n\\n待发货，买家取消订单原因 503010-多拍/拍错/不想要  503011-协商一致退款  503012-缺货  503013-未按约定时间发货  503099-其他\\n\\n待发货，商家取消订单原因 504010-暂停售卖  504011-缺货/断货  504099-其他\\n",name="cancel_reason",required=true)
    private Integer cancelReason;
    @ApiModelProperty(value = "是否是集团 1-是；0-否",name="isGroup",required=true)
    private String isGroup;
    @ApiModelProperty(value = "企业id",name="childOrganizeId",required=true)
    private String childOrganizeId;
    @ApiModelProperty(value = "使用优惠券，使用传1，未使用传0",name="isCoupon",required=true)
    private Integer isCoupon;
    @ApiModelProperty(value = "是否集团供货，1:是，0:否",name="isGroupSupply",required=true)
    private Integer isGroupSupply;

    public String getOrderNo(){
        if (orderNo!=null){
            orderNo = orderNo.trim();
        }
        return orderNo;
    }

    public String getMallName(){
        if (mallName!=null){
            mallName = mallName.trim();
        }
        return mallName;
    }

    public String getUserMes(){
        if (userMes!=null){
            userMes = userMes.trim();
        }
        return userMes;
    }

    public String getConsignor(){
        if (consignor!=null){
            consignor = consignor.trim();
        }
        return consignor;
    }

    public String getSocialPartner(){
        if (socialPartner!=null){
            socialPartner = socialPartner.trim();
        }
        return socialPartner;
    }

}
