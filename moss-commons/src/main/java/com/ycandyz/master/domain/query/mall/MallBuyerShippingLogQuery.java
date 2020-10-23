package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

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
 * @Description 买家寄出的快递物流日志表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="买家寄出的快递物流日志表-检索参数")
public class MallBuyerShippingLogQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(value = "买家物流表编号")
    private String buyerShippingNo;

    @ApiModelProperty(value = "物流公司编码")
    private String companyCode;

    @ApiModelProperty(value = "快递公司名")
    private String company;

    @ApiModelProperty(value = "快递单号")
    private String number;

    @ApiModelProperty(value = "内容")
    private String context;

    @ApiModelProperty(value = "快递单当前状态，包括 0 在途，1 揽收，2 疑难，3 签收，4 退签，5 派件，6 退回，7 转单，10 待清关，11 清关中，12 已清关，13 清关异常，14 收件人拒签等 ")
    private Integer status;

    @ApiModelProperty(value = "是否签收标记  0-否  1-是")
    private Integer isCheck;

    @ApiModelProperty(value = "物流节点时间")
    private String logAt;

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



}
