package com.ycandyz.master.domain.model.miniprogram;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: WangYang
 * @Date: 2020/11/13 18:00
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序配置创建方案内菜单-参数")
public class MpConfigPlanMenuModel implements Serializable {

    @ApiModelProperty(value = "编号")
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

    @ApiModelProperty(value = "小程序方案编号")
    private Integer planId;

    @ApiModelProperty(value = "是否可布局0：不可布局；1、可布局")
    private Boolean canLayout;

    @ApiModelProperty(value = "是否可删除0：不可删除，1：可删除")
    private Boolean canDelete;
}
