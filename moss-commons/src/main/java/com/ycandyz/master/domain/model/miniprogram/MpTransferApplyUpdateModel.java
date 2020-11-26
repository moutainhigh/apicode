package com.ycandyz.master.domain.model.miniprogram;


import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author: WangYang
 * @Date: 2020/11/15 23:26
 * @Description: 方案下菜单的模块信息
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序配置转交接信息")
public class MpTransferApplyUpdateModel implements Serializable {


    @ApiModelProperty(value = "对接人姓名")
    @NotNull(message = "对接人姓名不能为空",groups = {ValidatorContract.OnUpdate.class})
    private String buttedPerson;

    @ApiModelProperty(value = "对接状态0、待对接；1、对接中；2、已完成")
    @NotNull(message = "对接状态不能为空",groups = {ValidatorContract.OnUpdate.class})
    private Integer buttedStatus;

}
