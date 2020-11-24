package com.ycandyz.master.service.miniprogram.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.miniprogram.*;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanMenuQuery;
import com.ycandyz.master.dto.miniprogram.MpConfigPlanPageBaseDTO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanMenuDTO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanPageDTO;
import com.ycandyz.master.entities.miniprogram.*;
import com.ycandyz.master.model.miniprogram.OrganizeChooseMpConfigPage;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigModuleBaseVO;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigPageMenuVO;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.miniprogram.MpChooseStyleService;
import com.ycandyz.master.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MpChooseStyleServiceImpl implements MpChooseStyleService {

    @Autowired
    private OrganizeMpConfigPlanMenuDao organizeMpConfigPlanMenuDao;

    @Autowired
    private OrganizeMpConfigPlanPageDao organizeMpConfigPlanPageDao;

    @Autowired
    private OrganizeMpConfigPlanDao organizeMpConfigPlanDao;

    @Autowired
    private MpConfigPlanMenuDao mpConfigPlanMenuDao;

    @Autowired
    private MpConfigPlanDao mpConfigPlanDao;

    @Autowired
    private MpConfigPlanPageDao mpConfigPlanPageDao;

    @Override
    public List<OrganizeMpConfigMenuVO> selByOrGanizeMoudleId(Integer organizePlanId) {
        List<OrganizeMpConfigPlanMenuDTO> organizeMpConfigPlanMenuDTOS = organizeMpConfigPlanMenuDao.selByOrGanizeMoudleId(organizePlanId);
        List<OrganizeMpConfigMenuVO> lsit = new ArrayList<>();
        if (organizeMpConfigPlanMenuDTOS != null && organizeMpConfigPlanMenuDTOS.size() > 0) {
            for (OrganizeMpConfigPlanMenuDTO o: organizeMpConfigPlanMenuDTOS) {
                OrganizeMpConfigMenuVO organizeMpConfigMenuVO = new OrganizeMpConfigMenuVO();
                BeanUtils.copyProperties(o, organizeMpConfigMenuVO);
                lsit.add(organizeMpConfigMenuVO);
            }
        }
        return lsit;
    }

    @Override
    public OrganizeChooseMpConfigPage selectMenuById(Integer menuId) {
        OrganizeChooseMpConfigPage result = new OrganizeChooseMpConfigPage();
        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selectMenuById(menuId);
        result.setMenuId(menuId);
        result.setMenuName(organizeMpConfigPlanMenuDTO.getTitle());
        List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS = organizeMpConfigPlanPageDao.selectByMenuId(menuId);
        List<OrganizeMpConfigPageMenuVO> modules = new ArrayList<>();
        for (OrganizeMpConfigPlanPageDTO dto : organizeMpConfigPlanPageDTOS) {
            OrganizeMpConfigPageMenuVO module = new OrganizeMpConfigPageMenuVO();
            module.setModuleId(dto.getModuleId());
            module.setModuleName(dto.getModuleName());
            module.setSortModule(dto.getSortModule());
            module.setDisplayNum(dto.getDisplayNum());
            List<Integer> baseIds = new ArrayList<>();
            if (dto.getModuleBaseIds() != null) {
                for (String id : dto.getModuleBaseIds().split(",")) {
                    baseIds.add(Integer.parseInt(id));
                }
            }
            List<OrganizeMpConfigPlanPageDTO> configPlanPageBaseDTOList = organizeMpConfigPlanPageDao.getMenuModuleElement(dto.getSortModule(), baseIds);
            List<OrganizeMpConfigModuleBaseVO> baseInfoList = new ArrayList<>();
            for (OrganizeMpConfigPlanPageDTO dtoBase : configPlanPageBaseDTOList) {
                OrganizeMpConfigModuleBaseVO resp = new OrganizeMpConfigModuleBaseVO();
                BeanUtil.copyProperties(dtoBase, resp);
                resp.setId(dtoBase.getModuleBaseId());
                baseInfoList.add(resp);
                module.setBaseInfo(baseInfoList);
            }
            modules.add(module);
        }
        result.setModules(modules);
        return result;
    }

//    @Override
//    public Integer saveSingle(OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
//        UserVO currentUser = UserRequest.getCurrentUser();
//        Long organizeId = currentUser.getOrganizeId();
//        Integer id = organizeMenuMpRequestVO.getId();
//        Integer flag = organizeMenuMpRequestVO.getFlag();
//        if (flag == null ){
//            log.error("flag为空");
//        }
//            //新增
//            if (flag != null && flag == 0 && id == null){
//                //第一次新增
//                //查询草稿 未删 status:0
//                OrganizeMpConfigPlan organizeMpConfigPlan0 = organizeMpConfigPlanDao.selectByOrganizeIdStatus(organizeId);
//                if (organizeMpConfigPlan0 != null){
//                    //删除之前的草稿
//                    int i = organizeMpConfigPlanDao.setDelete(organizeMpConfigPlan0.getId());
//                }
//                //保存plan表
//                OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
//                organizeMpConfigPlan1.setMpPlanId(organizeMenuMpRequestVO.getMpPlanId());
//                organizeMpConfigPlan1.setOrganizeId(organizeId);
//                organizeMpConfigPlan1.setCurrentUsing(0);
//                organizeMpConfigPlan1.setLogicDelete(0);
//                log.info("企业小程序单个菜单页面-plan-保存草稿入参:{}",organizeMpConfigPlan1);
//                organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);
//                id = saveMenuAndPage(id,organizeMenuMpRequestVO, organizeId);
//                return id;
//            }else {
//                if (flag != null && flag == 0){
//                    //新增
//                    //查询草稿
//                    OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selectByIdStatus(id);
//                    if (organizeMpConfigPlan != null){
//                        saveMenuAndPage(id,organizeMenuMpRequestVO, organizeId);
//                    }else {
//                        log.error("企业小程序plan不存在:{}",id);
//                    }
//                }else if (flag != null && flag == 1){
//                    //修改
//                    OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selectById(id);
//                    if (organizeMpConfigPlan != null){
//                        List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
//                        for (OrganizeMpConfigPageMenuVo o : modules) {
//                            List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
//                            for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
//                                Integer id1 = omcmb.getOrganizeMpConfigPlanPageId();
//                                String baseName = omcmb.getBaseName();
//                                Integer sortModule = o.getSortModule();
//                                Integer sortBase = omcmb.getSortBase();
//                                Integer isDel = organizeMenuMpRequestVO.getIsDel();
//                                organizeMpConfigPlanPageDao.updateBaseNameById(id1,baseName,sortModule,sortBase,isDel);
//                            }
//                        }
//                    }
//                }
//            }
//            return null;
//        }

    @Override
    public void saveSingle2(OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.getByOrganizeId(organizeId);
        if (organizeMpConfigPlan != null){
            //有草稿则删除之前的草稿
            Integer organizePlanId = organizeMpConfigPlan.getId();
            List<Integer> menuIds = organizeMpConfigPlanMenuDao.selIdByOrGanizeMoudleId(organizePlanId);
            if (menuIds != null && menuIds.size()>0){
                for (Integer menuId :menuIds) {
                    //删除page中的菜单
                    int i = organizeMpConfigPlanPageDao.delByMenuId(menuId);
                    //删除menu中的菜单
                    int i2 = organizeMpConfigPlanMenuDao.delById(menuId);
                }
            }
            //更新plan表
            OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
            organizeMpConfigPlan1.setId(organizePlanId);
            organizeMpConfigPlan1.setMpPlanId(organizeMenuMpRequestVO.getMpPlanId());
            organizeMpConfigPlan1.setOrganizeId(organizeId);
            organizeMpConfigPlan1.setCurrentUsing(0);
            organizeMpConfigPlan1.setLogicDelete(0);
            log.info("企业小程序单个菜单页面-plan-更新草稿入参:{}",organizeMpConfigPlan1);
            organizeMpConfigPlanDao.updateByOrganizePlanId(organizeMpConfigPlan1);
            //新增page和menu
            saveMenuAndPage(organizePlanId,organizeMenuMpRequestVO,organizeId);
        }else {
            //无草稿新增草稿
            //保存plan表
            OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
            organizeMpConfigPlan1.setMpPlanId(organizeMenuMpRequestVO.getMpPlanId());
            organizeMpConfigPlan1.setOrganizeId(organizeId);
            organizeMpConfigPlan1.setCurrentUsing(0);
            organizeMpConfigPlan1.setLogicDelete(0);
            log.info("企业小程序单个菜单页面-plan-保存草稿入参:{}",organizeMpConfigPlan1);
            organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);
            saveMenuAndPage(null,organizeMenuMpRequestVO,organizeId);
        }



    }

    private void saveMenuAndPage(Integer organizePlanId,OrganizeMenuMpRequestVO organizeMenuMpRequestVO, Long organizeId) {
        //保存菜单表
        MpConfigPlan mpConfigPlan = mpConfigPlanDao.selectById(organizeMenuMpRequestVO.getMpPlanId());
        if (mpConfigPlan != null) {
            Integer planId = mpConfigPlan.getId();
            List<MpConfigPlanMenu> mpConfigPlanMenus = mpConfigPlanMenuDao.selByPlanId(planId);
            if (mpConfigPlanMenus != null && mpConfigPlanMenus.size() > 0) {
                if (organizePlanId == null) {
                    OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.getByOrganizeId(organizeId);
                    organizePlanId = organizeMpConfigPlan2.getId();
                }
                Integer finalOrganizePlanId = organizePlanId;
                mpConfigPlanMenus.stream().forEach(m -> {
                    OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu = new OrganizeMpConfigPlanMenu();
                    BeanUtils.copyProperties(m, organizeMpConfigPlanMenu);
                    organizeMpConfigPlanMenu.setOrganizePlanId(finalOrganizePlanId);
                    log.info("企业小程序单个菜单页面-menu-保存全部菜单页面入参:{}", organizeMpConfigPlanMenu);
                    organizeMpConfigPlanMenuDao.insertSingle(organizeMpConfigPlanMenu);
                });
                mpConfigPlanMenus.stream().forEach(mp -> {
                    if (organizeMenuMpRequestVO.getMenuId() == mp.getId()) {
                        List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
                        for (OrganizeMpConfigPageMenuVo o : modules) {
                            List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                            for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
                                OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                                OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selByIdAndName(finalOrganizePlanId, mp.getTitle());
                                if (organizeMpConfigPlanMenuDTO != null) {
                                    organizeMpConfigPlanPage.setMenuId(organizeMpConfigPlanMenuDTO.getId());
                                }
                                organizeMpConfigPlanPage.setModuleId(o.getModuleId());
                                organizeMpConfigPlanPage.setModuleBaseId(omcmb.getId());
                                organizeMpConfigPlanPage.setShowLayout(omcmb.getShowLayout());
                                organizeMpConfigPlanPage.setSortModule(o.getSortModule());
                                organizeMpConfigPlanPage.setSortBase(omcmb.getSortBase());
                                organizeMpConfigPlanPage.setBaseName(omcmb.getBaseName());
                                organizeMpConfigPlanPage.setLogicDelete(o.getIsDel());
                                log.info("企业小程序单个菜单页面-page-保存当前菜单页面入参:{}", o);
                                organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
                            }
                        }
                    } else {
                        OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selByIdAndName(finalOrganizePlanId, mp.getTitle());
                        if (organizeMpConfigPlanMenuDTO != null) {
                            organizeMpConfigPlanPage.setMenuId(organizeMpConfigPlanMenuDTO.getId());
                        }
                        MpConfigPlanPage mpConfigPlanPage = mpConfigPlanPageDao.selByMenuId(mp.getId());
                        if (mpConfigPlanPage != null) {
                            BeanUtils.copyProperties(mpConfigPlanPage, organizeMpConfigPlanPage);
                        }
                        log.info("企业小程序单个菜单页面-page-保存其他菜单页面入参:{}", organizeMpConfigPlanPage);
                        organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
                    }
                });
            }
        }
    }
    @Override
    public void saveAll(OrganizeMpRequestVO organizeMpRequestVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        Integer id = organizeMpRequestVO.getId();
        if (id == null){
            OrganizeMpConfigPlan organizeMpConfigPlan0 = organizeMpConfigPlanDao.selectByOrganizeIdStatus(organizeId);
            if (organizeMpConfigPlan0 != null){
                //删除之前的草稿
                int i = organizeMpConfigPlanDao.setDelete(organizeMpConfigPlan0.getId());
            }
            //保存plan
            OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
            organizeMpConfigPlan1.setMpPlanId(organizeMpRequestVO.getMpPlanId());
            organizeMpConfigPlan1.setOrganizeId(organizeId);
            organizeMpConfigPlan1.setCurrentUsing(organizeMpRequestVO.getStatus());
            organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);

            List<OrganizeMenuMpVO> organizeMenuMpVOS = organizeMpRequestVO.getAllmenus();
            for (OrganizeMenuMpVO omm : organizeMenuMpVOS) {
                OrganizeMenuMpRequestVO organizeMenuMpRequestVO = new OrganizeMenuMpRequestVO();
                if (omm != null){
                    BeanUtils.copyProperties(omm,organizeMenuMpRequestVO);
                }
                saveMenuAndPage(id,organizeMenuMpRequestVO, organizeId);
            }
        }else{
            //修改
            OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selectById(id);
            if (organizeMpConfigPlan != null){
                List<OrganizeMenuMpVO> organizeMenuMpVOS = organizeMpRequestVO.getAllmenus();
                for (OrganizeMenuMpVO omm: organizeMenuMpVOS) {
                    List<OrganizeMpConfigPageMenuVo> modules = omm.getModules();
                    for (OrganizeMpConfigPageMenuVo o : modules) {
                        List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                        for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
                            Integer id1 = omcmb.getOrganizeMpConfigPlanPageId();
                            String baseName = omcmb.getBaseName();
                            Integer sortModule = o.getSortModule();
                            Integer sortBase = omcmb.getSortBase();
                            Integer isDel = omm.getIsDel();
                            organizeMpConfigPlanPageDao.updateBaseNameById(id1,baseName,sortModule,sortBase,isDel);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Integer get() {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
            //查询全部
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.getByOrganizeId(organizeId);
        if (organizeMpConfigPlan != null) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<OrganizeMpConfigMenuVO> select() {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeId(organizeId);
        if (organizeMpConfigPlan != null){
            List<OrganizeMpConfigPlanMenuDTO> organizeMpConfigPlanMenuDTOS = organizeMpConfigPlanMenuDao.selByOrGanizeMoudleId(organizeMpConfigPlan.getId());
            List<OrganizeMpConfigMenuVO> lsit = new ArrayList<>();
            if (organizeMpConfigPlanMenuDTOS != null && organizeMpConfigPlanMenuDTOS.size() > 0) {
                for (OrganizeMpConfigPlanMenuDTO o: organizeMpConfigPlanMenuDTOS) {
                    OrganizeMpConfigMenuVO organizeMpConfigMenuVO = new OrganizeMpConfigMenuVO();
                    BeanUtils.copyProperties(o, organizeMpConfigMenuVO);
                    lsit.add(organizeMpConfigMenuVO);
                }
            }
            return lsit;
        }
        return null;
    }
}
