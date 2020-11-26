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
 * @Date: 2020/11/13 16:26
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序配置创建方案-参数")
public class ConfigPlanAndMenuModel implements Serializable {

    @ApiModelProperty(value = "方案名称")
    private String planName;

    @ApiModelProperty(value = "是否同步到U客 false：不可同步，true：可同步")
    private Boolean syncUke;

    @ApiModelProperty(value = "方案展示图")
    private String stylePicUrl;

    @ApiModelProperty(value = "方案内菜单")
    private List<MenuWithinPlan> menus;
}
