package com.ycandyz.master.service.miniprogram.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ycandyz.master.domain.model.miniprogram.MenuWithinPlan;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanMenuModel;
import com.ycandyz.master.domain.model.miniprogram.PlanMenuModel;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanMenuQuery;
import com.ycandyz.master.dao.miniprogram.MpConfigPlanMenuDao;
import com.ycandyz.master.service.miniprogram.IMpConfigPlanMenuService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * @Description 小程序配置-菜单配置 业务类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-13
 * @version 2.0
 */
@Slf4j
@Service
public class MpConfigPlanMenuServiceImpl extends BaseService<MpConfigPlanMenuDao,MpConfigPlanMenu,MpConfigPlanMenuQuery> implements IMpConfigPlanMenuService {

    @Override
    public Boolean add(MpConfigPlanMenuModel model) {
        MpConfigPlanMenu params = new MpConfigPlanMenu();
        BeanUtil.copyProperties(model,params);
        return this.save(params);
    }

    @Override
    public Boolean updatePlanMenu(MpConfigPlanMenu mpConfigPlanMenu) {
        return this.updateById(mpConfigPlanMenu);
    }

    @Override
    public Boolean removePlanMenu(Integer id) {
        MpConfigPlanMenu deleteParams = new MpConfigPlanMenu();
        deleteParams.setId(id);
        deleteParams.setLogicDelete(true);
        return this.updateById(deleteParams);
    }

    @Override
    public Boolean addBatch(PlanMenuModel model) {

        List<MenuWithinPlan> menus = model.getMenus();
        List<MpConfigPlanMenu> planMenuList = new ArrayList<MpConfigPlanMenu>();
        for(MenuWithinPlan menu: menus){
            MpConfigPlanMenu planMenu = new MpConfigPlanMenu();
            planMenu.setPlanId(model.getPlanId());
            BeanUtil.copyProperties(menu,planMenu);
            planMenuList.add(planMenu);
        }
        return this.saveOrUpdateBatch(planMenuList);
    }


}
