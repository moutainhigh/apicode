package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanMenuDTO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanPageDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 企业小程序配置-菜单配置 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 */
public interface OrganizeMpConfigPlanMenuDao extends BaseMapper<OrganizeMpConfigPlanMenu> {

    OrganizeMpConfigPlanMenuDTO selByIdAndName(@Param("organizePlanId") Integer id,@Param("title") String name);

    List<OrganizeMpConfigPlanMenuDTO> selByOrGanizeMoudleId(Integer organizePlanId);

    List<Integer> selIdByOrGanizeMoudleId(Integer organizePlanId);

    OrganizeMpConfigPlanMenuDTO selectMenuById(Integer menuId);

    void insertSingle(OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu);

    int delById(Integer menuId);

    OrganizeMpConfigPlanMenuDTO selByMoudleMenuId(@Param("organizePlanId") Integer organizePlanId,@Param("menuId") Integer menuId);
}
