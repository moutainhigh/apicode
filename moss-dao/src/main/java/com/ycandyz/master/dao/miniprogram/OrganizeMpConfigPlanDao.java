package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.vo.OrganizeMpReleaseParamVO;
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

    OrganizeMpConfigPlan getOrganizePlanByPlanId(@Param("organizePlanId") Integer organizePlanId);

    void insertSingle(OrganizeMpConfigPlan organizeMpConfigPlan1);

    int setDelete(@Param("id") Integer id);

    OrganizeMpConfigPlan selByOrganizeIdNowNotUse(Long organizeId);

    OrganizeMpConfigPlan selByOrganizeId(Long organizeId);

    OrganizeMpConfigPlan selectByIdUsing(Long organizeId);

    void updateByOrganizePlanId(OrganizeMpConfigPlan organizeMpConfigPlan1);

}
