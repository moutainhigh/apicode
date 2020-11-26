package com.ycandyz.master.dto.mp;

import com.ycandyz.master.entities.mp.OrganizeMpConfigPlan;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 企业小程序配置方案 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_plan")
public class OrganizeMpConfigPlanDTO extends OrganizeMpConfigPlan {

}
