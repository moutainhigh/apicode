package com.ycandyz.master.domain.query.miniprogram;

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
 * @Description 小程序转交接申请 检索参数类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-16
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序转交接申请-检索参数")
public class MpTransferApplyQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "企业编号")
    private Integer organizeId;

    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    @ApiModelProperty(value = "对接人")
    private String buttedPerson;

    @ApiModelProperty(value = "对接完成时间起")
    @Condition(field = "butted_finish_time", condition = ConditionEnum.GE)
    private Date buttedFinishTimeS;
    
    @ApiModelProperty(value = "对接完成时间止")
    @Condition(field = "butted_finish_time", condition = ConditionEnum.LE)
    private Date buttedFinishTimeE;

    @ApiModelProperty(value = "对接状态0、待对接；1、对接中；2、已完成")
    private Integer buttedStatus;

    @ApiModelProperty(value = "创建时间起")
    @Condition(field = "create_time", condition = ConditionEnum.GE)
    private Date createTimeS;
    
    @ApiModelProperty(value = "创建时间止")
    @Condition(field = "create_time", condition = ConditionEnum.LE)
    private Date createTimeE;

    @ApiModelProperty(value = "修改时间起")
    @Condition(field = "update_time", condition = ConditionEnum.GE)
    private Date updateTimeS;
    
    @ApiModelProperty(value = "修改时间止")
    @Condition(field = "update_time", condition = ConditionEnum.LE)
    private Date updateTimeE;



}
