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
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;
import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlan;
import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanMenu;
import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanPage;
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

    @Override
    public Integer saveSingle(OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        Integer id = organizeMenuMpRequestVO.getId();
        Integer flag = organizeMenuMpRequestVO.getFlag();
<<<<<<< HEAD
            //新增
            if (id == null){
=======
        if (flag == null ){
            log.error("flag为空");
        }
            //新增
            if (flag != null && flag == 0 && id == null){
>>>>>>> dev-mp-lz
                //第一次新增
                //查询草稿 未删 status:0
                OrganizeMpConfigPlan organizeMpConfigPlan0 = organizeMpConfigPlanDao.selectByOrganizeIdStatus(organizeId);
                if (organizeMpConfigPlan0 != null){
                    //删除之前的草稿
                    int i = organizeMpConfigPlanDao.setDelete(organizeMpConfigPlan0.getId());
                }
                //保存plan表
                OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
                organizeMpConfigPlan1.setMpPlanId(organizeMenuMpRequestVO.getMpPlanId());
<<<<<<< HEAD
                organizeMpConfigPlan1.setPlanName(organizeMenuMpRequestVO.getPlanName());
                organizeMpConfigPlan1.setOrganizeId(organizeId);
                organizeMpConfigPlan1.setStatus(0);
                organizeMpConfigPlan1.setLogicDelete(0);
=======
                organizeMpConfigPlan1.setOrganizeId(organizeId);
                organizeMpConfigPlan1.setCurrentUsing(0);
                organizeMpConfigPlan1.setLogicDelete(0);
                log.info("企业小程序单个菜单页面-plan-保存草稿入参:{}",organizeMpConfigPlan1);
>>>>>>> dev-mp-lz
                organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);
                id = saveMenuAndPage(id,organizeMenuMpRequestVO, organizeId);
                return id;
            }else {
<<<<<<< HEAD
                if (flag == null ){
                    log.error("flag为空");
                }
=======
>>>>>>> dev-mp-lz
                if (flag != null && flag == 0){
                    //新增
                    //查询草稿
                    OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selectByIdStatus(id);
                    if (organizeMpConfigPlan != null){
                        saveMenuAndPage(id,organizeMenuMpRequestVO, organizeId);
                    }else {
                        log.error("企业小程序plan不存在:{}",id);
                    }
                }else if (flag != null && flag == 1){
                    //修改
                    OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selectById(id);
                    if (organizeMpConfigPlan != null){
                        List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
                        for (OrganizeMpConfigPageMenuVo o : modules) {
                            List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                            for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
                                Integer id1 = omcmb.getOrganizeMpConfigPlanPageId();
                                String baseName = omcmb.getBaseName();
                                Integer sortModule = o.getSortModule();
                                Integer sortBase = omcmb.getSortBase();
<<<<<<< HEAD
                                organizeMpConfigPlanPageDao.updateBaseNameById(id1,baseName,sortModule,sortBase);
=======
                                Integer isDel = organizeMenuMpRequestVO.getIsDel();
                                organizeMpConfigPlanPageDao.updateBaseNameById(id1,baseName,sortModule,sortBase,isDel);
>>>>>>> dev-mp-lz
                            }
                        }
                    }
                }
            }
            return null;
        }

    private Integer saveMenuAndPage(Integer id,OrganizeMenuMpRequestVO organizeMenuMpRequestVO, Long organizeId) {
        //保存菜单表
        OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu = new OrganizeMpConfigPlanMenu();
        MpConfigPlanMenu mpConfigPlanMenu = mpConfigPlanMenuDao.selectMenuById(organizeMenuMpRequestVO.getMenuId());
        if (mpConfigPlanMenu != null){
            BeanUtils.copyProperties(mpConfigPlanMenu, organizeMpConfigPlanMenu);
        }
        if (id == null){
            OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.selectByOrganizeIdStatus(organizeId);
            id = organizeMpConfigPlan2.getId();
        }
        organizeMpConfigPlanMenu.setOrganizePlanId(id);
<<<<<<< HEAD
=======
        log.info("企业小程序单个菜单页面-menu-保存草稿入参:{}",organizeMpConfigPlanMenu);
>>>>>>> dev-mp-lz
        organizeMpConfigPlanMenuDao.insertSingle(organizeMpConfigPlanMenu);
        //保存page表
        List<OrganizeMpConfigPlanPage> list = new ArrayList<>();
        List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selByIdAndName(id, mpConfigPlanMenu.getTitle());
        for (OrganizeMpConfigPageMenuVo o : modules) {
            List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
            for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
                OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                if (organizeMpConfigPlanMenuDTO != null){
                    organizeMpConfigPlanPage.setMenuId(organizeMpConfigPlanMenuDTO.getId());
                }
                organizeMpConfigPlanPage.setModuleId(o.getModuleId());
                organizeMpConfigPlanPage.setModuleBaseId(omcmb.getId());
<<<<<<< HEAD
                organizeMpConfigPlanPage.setShowLayout(omcmb.getShowLayout()); //暂存元素的展示样式
                organizeMpConfigPlanPage.setSortModule(o.getSortModule());
                organizeMpConfigPlanPage.setSortBase(omcmb.getSortBase());
                organizeMpConfigPlanPage.setBaseName(omcmb.getBaseName());
=======
                organizeMpConfigPlanPage.setShowLayout(omcmb.getShowLayout());
                organizeMpConfigPlanPage.setSortModule(o.getSortModule());
                organizeMpConfigPlanPage.setSortBase(omcmb.getSortBase());
                organizeMpConfigPlanPage.setBaseName(omcmb.getBaseName());
                organizeMpConfigPlanPage.setLogicDelete(o.getIsDel());
>>>>>>> dev-mp-lz
                list.add(organizeMpConfigPlanPage);
            }
        }
        for (OrganizeMpConfigPlanPage o : list) {
<<<<<<< HEAD
=======
            log.info("企业小程序单个菜单页面-page-保存草稿入参:{}",o);
>>>>>>> dev-mp-lz
            organizeMpConfigPlanPageDao.insertSingle(o);
        }
        return id;
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
            organizeMpConfigPlan1.setPlanName(organizeMpRequestVO.getPlanName());
            organizeMpConfigPlan1.setOrganizeId(organizeId);
            organizeMpConfigPlan1.setCurrentUsing(organizeMpRequestVO.getStatus());
            organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);

            List<OrganizeMenuMpVO> organizeMenuMpVOS = organizeMpRequestVO.getAllmenus();
            for (OrganizeMenuMpVO omm : organizeMenuMpVOS) {
                OrganizeMenuMpRequestVO organizeMenuMpRequestVO = new OrganizeMenuMpRequestVO();
<<<<<<< HEAD
                if (organizeMenuMpRequestVO != null){
=======
                if (omm != null){
>>>>>>> dev-mp-lz
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
<<<<<<< HEAD
                            organizeMpConfigPlanPageDao.updateBaseNameById(id1,baseName,sortModule,sortBase);
=======
                            Integer isDel = omm.getIsDel();
                            organizeMpConfigPlanPageDao.updateBaseNameById(id1,baseName,sortModule,sortBase,isDel);
>>>>>>> dev-mp-lz
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
<<<<<<< HEAD
        if (id == null) {
            //查询全部
            OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.getByOrganizeId(organizeId);
            if (organizeMpConfigPlan == null) {
                return 0;
=======
            //查询全部
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.getByOrganizeId(organizeId);
        if (organizeMpConfigPlan != null) {
            Integer currentUsing = organizeMpConfigPlan.getCurrentUsing();
            //草稿
            if (currentUsing == 0){
                return organizeMpConfigPlan.getId();
            }
            //正在应用
            if (currentUsing == 1){
                return organizeMpConfigPlan.getId();
>>>>>>> dev-mp-lz
            }
        }
        return null;
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
