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
public class MpTransferApplyModel implements Serializable {

    @ApiModelProperty(value = "企业编号")
    @NotNull(message = "企业编号不能为空",groups = {ValidatorContract.OnCreate.class})
    private Integer organizeId;

    @ApiModelProperty(value = "联系人姓名")
    @NotNull(message = "联系人姓名不能为空",groups = {ValidatorContract.OnCreate.class})
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    @NotNull(message = "联系人电话不能为空",groups = {ValidatorContract.OnCreate.class})
    @Pattern(regexp = "^1(3([0-35-9]\\d|4[1-8])|4[14-9]\\d|5([0-35689]\\d|7[1-79])|66\\d|7[2-35-8]\\d|8\\d{2}|9[13589]\\d)\\d{7}$", message = "手机号格式有误" ,groups = {ValidatorContract.OnCreate.class})
    private String contactPhone;

}
