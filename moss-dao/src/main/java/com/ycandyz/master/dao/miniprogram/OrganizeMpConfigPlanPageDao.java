package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.dto.miniprogram.MpConfigPlanPageBaseDTO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanPageDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 企业小程序配置-页面配置 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 */
public interface OrganizeMpConfigPlanPageDao extends BaseMapper<OrganizeMpConfigPlanPage> {

    List<OrganizeMpConfigPlanPageDTO> selectByMenuId(@Param("menuId") Integer menuId);

    /**
     * 获取菜单模块元素
     * @param moduleSort
     * @param baseIds
     * @return
     */
    List<MpConfigPlanPageBaseDTO> getMenuModuleElement(@Param("moduleSort") Integer moduleSort, @Param("baseIds") List<Integer> baseIds);

    void insertSingle(OrganizeMpConfigPlanPage o);
}
