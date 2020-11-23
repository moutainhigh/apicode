package com.ycandyz.master.service.miniprogram.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.ycandyz.master.domain.model.miniprogram.ElementWithinModule;
import com.ycandyz.master.domain.model.miniprogram.ModuleWithinMenu;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.domain.model.miniprogram.PlanModuleModel;
import com.ycandyz.master.domain.response.miniprogram.MpConfigModuleBaseResp;
import com.ycandyz.master.domain.response.miniprogram.MpConfigPlanMenuResp;
import com.ycandyz.master.domain.response.miniprogram.MpConfigPlanPageResp;
import com.ycandyz.master.dto.miniprogram.MpConfigPlanPageBaseDTO;
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
import org.checkerframework.checker.units.qual.A;
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
        BeanUtil.copyProperties(configPlanPageDTO,result);
        //查询模块元素信息
        List<Integer> baseIds = new ArrayList<Integer>();
        if(configPlanPageDTO.getModuleBaseIds() != null){
            for(String id: configPlanPageDTO.getModuleBaseIds().split(",")){
                baseIds.add(Integer.parseInt(id));
            }
        }
        List<MpConfigPlanPageBaseDTO> configPlanPageBaseDTOList = this.baseMapper.getMenuModuleElement(menuId,moduleSort,baseIds);
        List<MpConfigModuleBaseResp> baseInfoList = new ArrayList<MpConfigModuleBaseResp>();
        for(MpConfigPlanPageBaseDTO base: configPlanPageBaseDTOList){
            MpConfigModuleBaseResp resp = new MpConfigModuleBaseResp();
            BeanUtil.copyProperties(base,resp);
            baseInfoList.add(resp);
        }

        result.setBaseInfo(baseInfoList);
        return result;
    }

    @Override
    public Boolean addBatch(PlanModuleModel model) {

        List<ModuleWithinMenu> modules = model.getModules();
        List<MpConfigPlanPage> planPageList = new ArrayList<MpConfigPlanPage>();
        for(ModuleWithinMenu module: modules){
            List<ElementWithinModule> elements = module.getBaseInfo();
            for(ElementWithinModule element: elements){
                MpConfigPlanPage planPage = new MpConfigPlanPage();
                if(element.getId() != null){
                    planPage.setId(element.getId());
                }
                planPage.setMenuId(model.getMenuId());
                planPage.setModuleId(module.getModuleId());
                planPage.setSortModule(module.getSortModule());
                planPage.setModuleBaseId(element.getModuleBaseId());
                planPage.setShowLayout(element.getShowLayout());
                planPage.setSortBase(element.getSortBase());
                planPageList.add(planPage);
            }
        }
        return this.saveOrUpdateBatch(planPageList);
    }

    @Override
    public MpConfigPlanMenuResp getModuleByMenuId(Integer menuId) {

        MpConfigPlanMenuResp result = new MpConfigPlanMenuResp();
        MpConfigPlanMenu menuInfo = configPlanMenuService.getById(menuId);
        result.setMenuId(menuId);
        result.setMenuName(menuInfo.getTitle());

        List<MpConfigPlanPageDTO> planPageDTOList = this.baseMapper.getMenuModuleList(menuId);
        List<MpConfigPlanPageResp> modules = new ArrayList<MpConfigPlanPageResp>();
        for(MpConfigPlanPageDTO dto: planPageDTOList){
            MpConfigPlanPageResp module = new MpConfigPlanPageResp();
            module.setModuleId(dto.getModuleId());
            module.setModuleName(dto.getModuleName());
            module.setSortModule(dto.getSortModule());
            module.setDisplayNum(dto.getDisplayNum());
            //查询模块元素信息
            List<Integer> baseIds = new ArrayList<Integer>();
            if(dto.getModuleBaseIds() != null){
                for(String id: dto.getModuleBaseIds().split(",")){
                    baseIds.add(Integer.parseInt(id));
                }
            }
            List<MpConfigPlanPageBaseDTO> configPlanPageBaseDTOList = this.baseMapper.getMenuModuleElement(menuId,dto.getSortModule(),baseIds);
            List<MpConfigModuleBaseResp> baseInfoList = new ArrayList<MpConfigModuleBaseResp>();
            for(MpConfigPlanPageBaseDTO dtoBase: configPlanPageBaseDTOList){
                MpConfigModuleBaseResp resp = new MpConfigModuleBaseResp();
                BeanUtil.copyProperties(dtoBase,resp);
                baseInfoList.add(resp);
                module.setBaseInfo(baseInfoList);
            }
            modules.add(module);
        }

        result.setModules(modules);
        return result;

    }

    @Override
    public Boolean removePageModule(Integer moduleId, Integer menuId, Integer moduleSort) {

        List<MpConfigPlanPage> planPageDTOList = this.baseMapper.getMenuSortModule(menuId,moduleSort,moduleId);
        for(MpConfigPlanPage pageModule: planPageDTOList){
            pageModule.setLogicDelete(true);
        }
        return this.updateBatchById(planPageDTOList);
    }


}
