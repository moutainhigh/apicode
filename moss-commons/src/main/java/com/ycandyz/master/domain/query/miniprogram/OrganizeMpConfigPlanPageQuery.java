package com.ycandyz.master.domain.query.miniprogram;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 企业小程序配置-页面配置 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="企业小程序配置-页面配置-检索参数")
public class OrganizeMpConfigPlanPageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "菜单编号")
    private Integer menuId;

    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "元素编号")
    private Integer moduleBaseId;

    @ApiModelProperty(value = "展示样式")
    private Integer showLayout;

    @ApiModelProperty(value = "模块排序")
    private Integer sortModule;

    @ApiModelProperty(value = "元素排序")
    private Integer sortBase;

    @ApiModelProperty(value = "元素名称")
    private String baseName;



}
