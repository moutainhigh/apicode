package com.ycandyz.master.domain.model.miniprogram;


import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="创建方案内模块-参数")
public class PlanModuleModel implements Serializable {

    @ApiModelProperty(value = "菜单编号")
    @NotNull(message = "菜单编号不能为空",groups = {ValidatorContract.OnCreate.class})
    private Integer menuId;

    @ApiModelProperty(value = "菜单下模块")
    private List<ModuleWithinMenu> modules;

}
