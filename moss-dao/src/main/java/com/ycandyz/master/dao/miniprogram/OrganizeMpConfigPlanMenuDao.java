package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanMenuDTO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanPageDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

    void selById(Integer id);

    OrganizeMpConfigPlanMenuDTO selByOrGanizeMoudleId(Integer id);

    OrganizeMpConfigPlanMenuDTO selectMenuById(Integer menuId);

    void insertSingle(OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu);
}
