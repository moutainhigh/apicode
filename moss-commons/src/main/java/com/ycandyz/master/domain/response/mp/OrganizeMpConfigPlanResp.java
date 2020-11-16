package com.ycandyz.master.domain.response.mp;

import com.ycandyz.master.entities.mp.OrganizeMpConfigPlan;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 企业小程序配置方案 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_plan")
public class OrganizeMpConfigPlanResp extends OrganizeMpConfigPlan {

}
