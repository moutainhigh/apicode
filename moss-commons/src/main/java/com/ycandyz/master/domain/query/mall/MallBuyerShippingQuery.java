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
 * @Description 买家寄出的快递表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="买家寄出的快递表-检索参数")
public class MallBuyerShippingQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(value = "订单售后编号")
    private String afterSalesNo;

    @ApiModelProperty(value = "买家物流表编号")
    private String buyerShippingNo;

    @ApiModelProperty(value = "类型  0-未知  1-快递  2-线下配送")
    private Boolean type;

    @ApiModelProperty(value = "物流公司编码")
    private String companyCode;

    @ApiModelProperty(value = "快递公司名")
    private String company;

    @ApiModelProperty(value = "快递单号")
    private String number;

    @ApiModelProperty(value = "运费")
    private BigDecimal shippingMoney;

    @ApiModelProperty(value = "商家收货人姓名")
    private String receiver;

    @ApiModelProperty(value = "商家收货人手机号")
    private String receiverPhone;

    @ApiModelProperty(value = "商家收货地址")
    private String receiverAddress;

    @ApiModelProperty(value = "起")
    @Condition(field = "created_time", condition = ConditionEnum.GE)
    private Date createdTimeS;
    
    @ApiModelProperty(value = "止")
    @Condition(field = "created_time", condition = ConditionEnum.LE)
    private Date createdTimeE;

    @ApiModelProperty(value = "起")
    @Condition(field = "updated_time", condition = ConditionEnum.GE)
    private Date updatedTimeS;
    
    @ApiModelProperty(value = "止")
    @Condition(field = "updated_time", condition = ConditionEnum.LE)
    private Date updatedTimeE;

    @ApiModelProperty(value = "状态 10-有效  20-无效")
    private Integer status;

    @ApiModelProperty(value = "快递订阅推送状态 0-买家未确认退回 1-成功 2-失败")
    private Boolean pollState;



}
