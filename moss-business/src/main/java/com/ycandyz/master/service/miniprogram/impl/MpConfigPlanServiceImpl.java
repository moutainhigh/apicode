package com.ycandyz.master.service.miniprogram.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ycandyz.master.domain.model.miniprogram.ConfigPlanAndMenuModel;
import com.ycandyz.master.domain.model.miniprogram.MenuWithinPlan;
import com.ycandyz.master.entities.miniprogram.MpConfigMenu;
import com.ycandyz.master.entities.miniprogram.MpConfigPlan;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanQuery;
import com.ycandyz.master.dao.miniprogram.MpConfigPlanDao;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;
import com.ycandyz.master.service.miniprogram.IMpConfigMenuService;
import com.ycandyz.master.service.miniprogram.IMpConfigPlanMenuService;
import com.ycandyz.master.service.miniprogram.IMpConfigPlanService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * @Description 小程序配置方案 业务类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
@Slf4j
@Service
public class MpConfigPlanServiceImpl extends BaseService<MpConfigPlanDao,MpConfigPlan,MpConfigPlanQuery> implements IMpConfigPlanService {

    @Resource
    private IMpConfigPlanMenuService mpConfigPlanMenuService;
    @Resource
    private IMpConfigMenuService configMenuService;

    @Override
    public MpConfigPlan add(ConfigPlanAndMenuModel model) {

        //保存方案
        MpConfigPlan mpConfigPlan = new MpConfigPlan();
        mpConfigPlan.setSyncUke(model.getSyncUke());
        mpConfigPlan.setPlanName(model.getPlanName());
        mpConfigPlan.setStylePicUrl(model.getStylePicUrl());
        this.save(mpConfigPlan);

        //批量保存菜单
        List<MenuWithinPlan> menuWithinPlanList = model.getMenus();
        List<MpConfigPlanMenu> configPlanMenuList = new ArrayList<MpConfigPlanMenu>();
        for(MenuWithinPlan menuWithinPlan: menuWithinPlanList){
            MpConfigPlanMenu mpConfigPlanMenu = new MpConfigPlanMenu();
            mpConfigPlanMenu.setPlanId(mpConfigPlan.getId());
            BeanUtil.copyProperties(menuWithinPlan,mpConfigPlanMenu);
            configPlanMenuList.add(mpConfigPlanMenu);
        }
        mpConfigPlanMenuService.saveBatch(configPlanMenuList);

        return mpConfigPlan;
    }

    @Override
    public Boolean initPlan(String planName) {
        MpConfigPlan mpConfigPlan = new MpConfigPlan();
        mpConfigPlan.setPlanName(planName);
        this.save(mpConfigPlan);
        //查询默认菜单
        List<MpConfigMenu> baseMenus = configMenuService.list();
        List<MpConfigPlanMenu> configPlanMenuList = new ArrayList<MpConfigPlanMenu>();
        int sortMenu = 0;
        for(MpConfigMenu mpConfigMenu: baseMenus){
            mpConfigMenu.setCreateTime(null);
            mpConfigMenu.setUpdateTime(null);
            MpConfigPlanMenu mpConfigPlanMenu = new MpConfigPlanMenu();
            mpConfigPlanMenu.setPlanId(mpConfigPlan.getId());
            mpConfigPlanMenu.setSortNum(sortMenu);
            BeanUtil.copyProperties(mpConfigMenu,mpConfigPlanMenu);
            configPlanMenuList.add(mpConfigPlanMenu);
            sortMenu ++;
        }
        return mpConfigPlanMenuService.saveBatch(configPlanMenuList);
    }

}
