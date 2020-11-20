package com.ycandyz.master.domain.model.miniprogram;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="配置菜单模块-参数")
public class ModuleWithinMenu implements Serializable {

    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "模块排序")
    private Integer sortModule;

    @ApiModelProperty(value = "模块下元素")
    private List<ElementWithinModule> baseInfo;
}
