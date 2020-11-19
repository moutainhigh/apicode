package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小程序配置-菜单配置 Mapper 接口
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-13
 */
public interface MpConfigPlanMenuDao extends BaseMapper<MpConfigPlanMenu> {


    /**
     * 排序获取方案下菜单
     * @param planId
     * @return
     */
    List<MpConfigPlanMenu> getMenusByPlanId(@Param("planId") Integer planId, @Param("logicDelete") Boolean logicDelete);
}
