package com.ycandyz.master.domain.response.miniprogram;

import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanMenu;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 企业小程序配置-菜单配置 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_plan_menu")
public class OrganizeMpConfigPlanMenuResp extends OrganizeMpConfigPlanMenu {

}
