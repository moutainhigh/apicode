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
@ApiModel(description="创建方案内菜单-参数")
public class PlanMenuModel implements Serializable {

    @ApiModelProperty(value = "小程序方案编号")
    @NotNull(message = "方案编号不能为空",groups = {ValidatorContract.OnCreate.class})
    private Integer planId;

    @ApiModelProperty(value = "方案内菜单")
    private List<MenuWithinPlan> menus;
}
