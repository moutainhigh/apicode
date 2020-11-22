package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 企业小程序配置方案 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 */
public interface OrganizeMpConfigPlanDao extends BaseMapper<OrganizeMpConfigPlan> {

    OrganizeMpConfigPlan getOrganizePlan(@Param("organizeId") Long organizeId, @Param("id") Integer id);

    OrganizeMpConfigPlan getOrganizePlanById(@Param("organizeId") Long organizeId, @Param("id") Integer id)

    void insertSingle(OrganizeMpConfigPlan organizeMpConfigPlan1);

    OrganizeMpConfigPlan selectByOrganizeId(Long organizeId););
}
