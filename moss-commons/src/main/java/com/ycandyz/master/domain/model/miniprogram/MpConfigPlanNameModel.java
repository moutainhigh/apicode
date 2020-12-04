package com.ycandyz.master.domain.model.miniprogram;


import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MpConfigPlanNameModel {

    @ApiModelProperty(value = "方案名称")
    @NotNull(message = "方案名称不能为空",groups = {ValidatorContract.OnUpdate.class})
    private String planName;
}
