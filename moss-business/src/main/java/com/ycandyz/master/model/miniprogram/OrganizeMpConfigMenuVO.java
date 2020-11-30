package com.ycandyz.master.model.miniprogram;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.entities.miniprogram.MpConfigModule;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * @Description  VO
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_module")
public class OrganizeMpConfigMenuVO extends MpConfigModule {

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
    private Boolean canLayout;

    @ApiModelProperty(value = "是否可删除0：不可删除；1、可删除")
    private Boolean canDelete;
}
