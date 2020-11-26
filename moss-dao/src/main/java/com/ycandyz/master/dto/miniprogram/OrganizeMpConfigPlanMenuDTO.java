package com.ycandyz.master.dto.miniprogram;

import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanMenu;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 企业小程序配置-菜单配置 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_plan_menu")
public class OrganizeMpConfigPlanMenuDTO extends OrganizeMpConfigPlanMenu {

    @ApiModelProperty(value = "菜单编号")
    private Integer id;

    @ApiModelProperty(value = "菜单名称")
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

    @ApiModelProperty(value = "是否可布局0：不可布局；1、可布局")
    private Integer canLayout;

    @ApiModelProperty(value = "是否可布局0：不可布局；1、可布局")
    private Integer canDelete;

}
