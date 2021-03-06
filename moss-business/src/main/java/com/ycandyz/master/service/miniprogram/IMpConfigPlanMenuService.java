package com.ycandyz.master.service.miniprogram;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanMenuModel;
import com.ycandyz.master.domain.model.miniprogram.PlanMenuModel;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanMenuQuery;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;

import java.util.List;

/**
 * <p>
 * @Description 小程序配置-菜单配置 业务接口类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-13
 * @version 2.0
 */
public interface IMpConfigPlanMenuService extends IService<MpConfigPlanMenu>{

    /**
     * 新增方案下菜单
     * @param model
     * @return
     */
    Boolean add(MpConfigPlanMenuModel model);

    /**
     * 修改方案下菜单
     * @param mpConfigPlanMenu
     * @return
     */
    Boolean updatePlanMenu(MpConfigPlanMenu mpConfigPlanMenu);

    /**
     * 逻辑删除方案菜单
     * @param id
     * @return
     */
    Boolean removePlanMenu(Integer id);

    /**
     * 批量新增方案下菜单
     * @param model
     * @return
     */
    Boolean addBatch(PlanMenuModel model);

    /**
     * 查询方案下菜单列表
     * @param planId
     * @return
     */
    List<MpConfigPlanMenu> getMenusByPlanId(MpConfigPlanMenuQuery query);
}
