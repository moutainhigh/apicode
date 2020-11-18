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
 * @Description 企业小程序配置-菜单配置 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="企业小程序配置-菜单配置-检索参数")
public class OrganizeMpConfigPlanMenuQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "选中图片地址")
    private String pictureSelect;

    @ApiModelProperty(value = "选中颜色")
    private String colorSelect;

    @ApiModelProperty(value = "未选中图片地址")
    private String pictureNotSelect;

    @ApiModelProperty(value = "未选中颜色")
    private String colorNotSelect;

    @ApiModelProperty(value = "排序值")
    private Integer sortNum;

    @ApiModelProperty(value = "企业小程序方案编号")
    private Integer organizePlanId;

    @ApiModelProperty(value = "逻辑删除0：未删除；1、删除")
    private Boolean logicDelete;

    @ApiModelProperty(value = "是否可布局0：不可布局；1、可布局")
    private Boolean canLayout;

    @ApiModelProperty(value = "是否可删除0：不可删除，1：可删除")
    private Boolean canDelete;

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
