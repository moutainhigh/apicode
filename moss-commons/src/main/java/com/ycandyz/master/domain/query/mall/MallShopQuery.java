package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 商城 - 店铺表 检索参数类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商城 - 店铺表")
public class MallShopQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(value = "企业id")
    private Integer organizeId;

    @ApiModelProperty(value = "商店编号")
    private String shopNo;

    @ApiModelProperty(value = "商店名称")
    private String shopName;

    @ApiModelProperty(value = "商店背景图")
    private String bgImg;

    @ApiModelProperty(value = "入口名称")
    private String entryName;

    @ApiModelProperty(value = "可售后天数")
    private Integer asHoldDays;

    @ApiModelProperty(value = "客服联系人")
    private String csContact;

    @ApiModelProperty(value = "客服电话号码")
    private String csPhoneNumber;

    @ApiModelProperty(value = "客服微信号")
    private String csWeixinNo;

    @ApiModelProperty(value = "商店状态 10: 正常  20：过期")
    private Integer status;

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

    @ApiModelProperty(value = "商店经营状态 10:营业  20:休息 30:待启用")
    private Integer operateStatus;



}
