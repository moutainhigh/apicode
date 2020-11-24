package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    OrganizeMpConfigPlan getOrganizePlanById(@Param("organizeId") Long organizeId, @Param("id") Integer id);

    void insertSingle(OrganizeMpConfigPlan organizeMpConfigPlan1);

    List<OrganizeMpConfigPlan> selectByOrganizeId(Long organizeId);

    OrganizeMpConfigPlan selectByOrganizeIdStatus(@Param("organizeId") Long organizeId);

    OrganizeMpConfigPlan selectByIdStatus (@Param("id") Integer id);

    OrganizeMpConfigPlan getByOrganizeId(Long organizeId);

    int setDelete(@Param("id") Integer id);

    OrganizeMpConfigPlan selByOrganizeId(Long organizeId);

    OrganizeMpConfigPlan selectByIdUsing(Integer id);

    OrganizeMpConfigPlan selectPlanById(Integer id);
}
