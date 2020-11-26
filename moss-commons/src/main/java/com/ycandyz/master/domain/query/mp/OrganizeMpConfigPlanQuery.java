package com.ycandyz.master.domain.query.mp;

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
 * @Description 企业小程序配置方案 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="企业小程序配置方案-检索参数")
public class OrganizeMpConfigPlanQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "企业编号")
    private Integer organizeId;

    @ApiModelProperty(value = "小程序名称")
    private String planName;

    @ApiModelProperty(value = "小程序模板方案编号")
    private Integer planId;

    @ApiModelProperty(value = "逻辑删除0：未删除；1、删除")
    private Boolean logicDelete;

    @ApiModelProperty(value = "当前应用0：未应用；1、应用")
    private Boolean currentUsing;

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
