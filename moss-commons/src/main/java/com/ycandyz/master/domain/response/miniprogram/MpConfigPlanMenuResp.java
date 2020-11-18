package com.ycandyz.master.domain.response.miniprogram;

import com.ycandyz.master.domain.model.miniprogram.MenuWithinPlan;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
public class MpConfigPlanMenuResp extends MenuWithinPlan {

    @ApiModelProperty(value = "方案编号")
    private Integer planId;
}
