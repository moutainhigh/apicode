package com.ycandyz.master.domain.model.miniprogram;

import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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


    @ApiModelProperty(value = "菜单名称")
    @NotNull(message = "菜单名称不能为空",groups = {ValidatorContract.OnCreate.class,ValidatorContract.OnUpdate.class})
    private String title;

    @ApiModelProperty(value = "选中图片地址")
    @NotNull(message = "图片地址不能为空",groups = {ValidatorContract.OnCreate.class,ValidatorContract.OnUpdate.class})
    private String pictureSelect;

    @ApiModelProperty(value = "选中颜色")
    @NotNull(message = "颜色不能为空",groups = {ValidatorContract.OnCreate.class,ValidatorContract.OnUpdate.class})
    private String colorSelect;

    @ApiModelProperty(value = "未选中图片地址")
    @NotNull(message = "图片地址不能为空",groups = {ValidatorContract.OnCreate.class,ValidatorContract.OnUpdate.class})
    private String pictureNotSelect;

    @ApiModelProperty(value = "未选中颜色")
    @NotNull(message = "颜色不能为空",groups = {ValidatorContract.OnCreate.class,ValidatorContract.OnUpdate.class})
    private String colorNotSelect;

    @ApiModelProperty(value = "排序值")
    private Integer sortNum;

    @ApiModelProperty(value = "小程序方案编号")
    @NotNull(message = "方案编号不能为空",groups = {ValidatorContract.OnCreate.class})
    private Integer planId;

}
