package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.dto.miniprogram.MpConfigPlanPageBaseDTO;
import com.ycandyz.master.dto.miniprogram.MpConfigPlanPageDTO;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小程序配置-页面配置 Mapper 接口
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 */
public interface MpConfigPlanPageDao extends BaseMapper<MpConfigPlanPage> {

    /**
     * 获取菜单页面模块
     * @param menuId
     * @param moduleSort
     * @param moduleId
     * @return
     */
    MpConfigPlanPageDTO getMenuModule(@Param("menuId") Integer menuId, @Param("moduleSort") Integer moduleSort, @Param("moduleId") Integer moduleId);


    /**
     * 获取菜单模块元素
     * @param moduleSort
     * @param baseIds
     * @return
     */
    List<MpConfigPlanPageBaseDTO> getMenuModuleElement(@Param("menuId") Integer menuId, @Param("moduleSort") Integer moduleSort, @Param("baseIds") List<Integer> baseIds);


    /**
     * 获取菜单页面所有模块
     * @param menuId
     * @return
     */
    List<MpConfigPlanPageDTO> getMenuModuleList(@Param("menuId") Integer menuId);


    /**
     * 获取指定排序值的模块
     * @param menuId
     * @param moduleSort
     * @param moduleId
     * @return
     */
    List<MpConfigPlanPage> getMenuSortModule(@Param("menuId") Integer menuId, @Param("moduleSort") Integer moduleSort, @Param("moduleId") Integer moduleId);


    List<MpConfigPlanPage> selByMenuId(Integer id);

    /**
     * 删除菜单下配置模块
     * @param menuId
     * @return
     */
    Boolean deleteByMenuId(@Param("menuId") Integer menuId);
}
