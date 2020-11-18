package com.ycandyz.master.domain.response.miniprogram;

import com.ycandyz.master.domain.model.miniprogram.MenuWithinPlan;
import com.ycandyz.master.entities.miniprogram.MpConfigModule;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * @Description 小程序配置-菜单配置 Resp
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_plan_menu")
public class MpConfigPlanMenuResp implements Serializable {

    @ApiModelProperty(value = "菜单编号")
    private Integer menuId;

    @ApiModelProperty(value = "页面模块")
    private List<MpConfigPlanPageResp> modules;
}
