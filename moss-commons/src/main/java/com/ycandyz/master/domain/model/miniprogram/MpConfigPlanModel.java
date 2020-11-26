package com.ycandyz.master.domain.model.miniprogram;


import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="创建方案内菜单-参数")
public class MpConfigPlanModel {

    @ApiModelProperty(value = "方案名称")
    @NotNull(message = "方案名称不能为空",groups = {ValidatorContract.OnUpdate.class})
    private String planName;

    @ApiModelProperty(value = "是否同步U客")
    @NotNull(message = "是否同步U客不能为空",groups = {ValidatorContract.OnUpdate.class})
    private Boolean syncUke;

    @ApiModelProperty(value = "方案展示图")
    @NotNull(message = "方案展示图不能为空",groups = {ValidatorContract.OnUpdate.class})
    private String stylePicUrl;
}
