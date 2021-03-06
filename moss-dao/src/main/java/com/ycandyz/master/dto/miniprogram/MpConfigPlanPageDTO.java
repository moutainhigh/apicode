package com.ycandyz.master.dto.miniprogram;

import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 小程序配置-页面配置 实体类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_plan_page")
public class MpConfigPlanPageDTO {

    @ApiModelProperty(value = "页面模块元素编号")
    private Integer id;

    @ApiModelProperty(value = "模板菜单编号")
    private Integer menuId;

    @ApiModelProperty(value = "模板菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "菜单模块名称")
    private String moduleName;

    @ApiModelProperty(value = "菜单模块排序")
    private Integer sortModule;

    @ApiModelProperty(value = "菜单模块显示数量")
    private Integer displayNum;

    @ApiModelProperty(value = "菜单模块元素编号")
    private String moduleBaseIds;

}
