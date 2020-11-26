package com.ycandyz.master.domain.model.miniprogram;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author: WangYang
 * @Date: 2020/11/15 23:26
 * @Description: 方案下菜单的模块信息
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序配置菜单内模块-参数")
public class MpConfigPlanPageModel implements Serializable {

    @ApiModelProperty(value = "菜单编号")
    private Integer menuId;

    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "模块排序")
    private Integer sortModule;

    @ApiModelProperty(value = "模块下元素")
    private List<ElementWithinModule> elements;

}
