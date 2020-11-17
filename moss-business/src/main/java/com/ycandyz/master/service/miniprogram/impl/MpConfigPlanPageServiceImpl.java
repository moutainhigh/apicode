package com.ycandyz.master.service.miniprogram.impl;

import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.domain.response.miniprogram.MpConfigModuleBaseResp;
import com.ycandyz.master.domain.response.miniprogram.MpConfigPlanPageResp;
import com.ycandyz.master.dto.miniprogram.MpConfigPlanPageDTO;
import com.ycandyz.master.entities.miniprogram.MpConfigModule;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanPageQuery;
import com.ycandyz.master.dao.miniprogram.MpConfigPlanPageDao;
import com.ycandyz.master.service.miniprogram.IMpConfigMenuService;
import com.ycandyz.master.service.miniprogram.IMpConfigModuleService;
import com.ycandyz.master.service.miniprogram.IMpConfigPlanMenuService;
import com.ycandyz.master.service.miniprogram.IMpConfigPlanPageService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * @Description 小程序配置-页面配置 业务类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Slf4j
@Service
public class MpConfigPlanPageServiceImpl extends BaseService<MpConfigPlanPageDao,MpConfigPlanPage,MpConfigPlanPageQuery> implements IMpConfigPlanPageService {

    @Resource
    private IMpConfigPlanMenuService configPlanMenuService;
    @Resource
    private IMpConfigModuleService configModuleService;

    @Override
    public Boolean add(MpConfigPlanPageModel model) {
        return null;
    }

    @Override
    public MpConfigPlanPageResp getPlanMenuModule(Integer menuId, Integer moduleSort, Integer moduleId) {

        MpConfigPlanPageResp result = new MpConfigPlanPageResp();

        //查询菜单模块信息
        MpConfigPlanPageDTO configPlanPageDTO = this.baseMapper.getMenuModule(menuId,moduleSort,moduleId);
        //查询模块元素信息


        System.out.println("===========================");


        return null;
    }


}
