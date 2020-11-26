package com.ycandyz.master.service.miniprogram;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.domain.model.miniprogram.PlanModuleModel;
import com.ycandyz.master.domain.response.miniprogram.MpConfigPlanMenuResp;
import com.ycandyz.master.domain.response.miniprogram.MpConfigPlanPageResp;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;

/**
 * <p>
 * @Description 小程序配置-页面配置 业务接口类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
public interface IMpConfigPlanPageService extends IService<MpConfigPlanPage>{

    /**
     * 添加模块下元素信息
     * @param model
     * @return
     */
    Boolean add(MpConfigPlanPageModel model);


    /**
     * 获取菜单下模块元素
     * @param menuId
     * @param moduleId
     * @return
     */
    MpConfigPlanPageResp getPlanMenuModule(Integer menuId, Integer moduleSort, Integer moduleId);

    /**
     * 添加模块下元素信息
     * @param model
     * @return
     */
    Boolean addBatch(PlanModuleModel model);


    /**
     * 获取菜单下页面模块
     * @param menuId
     * @return
     */
    MpConfigPlanMenuResp getModuleByMenuId(Integer menuId);


    /**
     * 删除页面下模块
     * @param moduleId
     * @param moduleSort
     * @return
     */
    Boolean removePageModule(Integer moduleId, Integer menuId, Integer moduleSort);
}
