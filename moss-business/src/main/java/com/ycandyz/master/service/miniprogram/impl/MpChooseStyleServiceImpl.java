package com.ycandyz.master.service.miniprogram.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.ycandyz.master.dao.mall.goodsManage.MallCategoryDao;
import com.ycandyz.master.dao.miniprogram.*;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanMenuDTO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanPageDTO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpReleaseDTO;
import com.ycandyz.master.entities.miniprogram.*;
import com.ycandyz.master.model.miniprogram.*;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.miniprogram.MpChooseStyleService;
import com.ycandyz.master.vo.*;
import com.ycandyz.master.vo.OrganizeMpReleaseParamVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Float.parseFloat;

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
    private MallCategoryDao mallCategoryDao;

    @Autowired
    private MpConfigModuleBaseDao mpConfigModuleBaseDao;

    @Autowired
    private OrganizeMpReleaseDao organizeMpReleaseDao;

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
    public OrganizeMpConfigPageSingleMenuVO selectMenuById(Integer menuId) {
        OrganizeMpConfigPageSingleMenuVO organizeMpConfigPageSingleMenuVO = new OrganizeMpConfigPageSingleMenuVO();
        organizeMpConfigPageSingleMenuVO.setMenuId(menuId);
        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selectMenuById(menuId);
        if (organizeMpConfigPlanMenuDTO != null){
            organizeMpConfigPageSingleMenuVO.setMenuName(organizeMpConfigPlanMenuDTO.getTitle());
        }
        List<OrganizeMpConfigModuleVO> moudles = new ArrayList<>();
        List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS = organizeMpConfigPlanPageDao.selectByMenuId(menuId);
        for (OrganizeMpConfigPlanPageDTO dto : organizeMpConfigPlanPageDTOS) {
            List<Integer> baseIds = new ArrayList<>();
            OrganizeMpConfigModuleVO organizeMpConfigModuleVO = new OrganizeMpConfigModuleVO();
            organizeMpConfigModuleVO.setModuleId(dto.getModuleId());
            organizeMpConfigModuleVO.setModuleName(dto.getModuleName());
            organizeMpConfigModuleVO.setSortModule(dto.getSortModule());
            organizeMpConfigModuleVO.setDisplayNum(dto.getDisplayNum());
            if (dto.getModuleBaseIds() != null) {
                List<OrganizeMpConfigModuleBaseVO> baseInfoList = new ArrayList<>();
                for (String id : dto.getModuleBaseIds().split(",")) {
                    baseIds.add(Integer.parseInt(id));
                    MpConfigModuleBase mpConfigModuleBase = mpConfigModuleBaseDao.selectByBaseId(Integer.parseInt(id));
                    if (mpConfigModuleBase != null){
                        OrganizeMpConfigModuleBaseVO organizeMpConfigModuleBaseVO = new OrganizeMpConfigModuleBaseVO();
                        organizeMpConfigModuleBaseVO.setBaseCode(mpConfigModuleBase.getBaseCode());
                        organizeMpConfigModuleBaseVO.setBaseName(dto.getBaseName());
                        organizeMpConfigModuleBaseVO.setSortBase(dto.getSortBase());
                        organizeMpConfigModuleBaseVO.setShowLayout(dto.getShowLayout());
                        organizeMpConfigModuleBaseVO.setDisplayNum(mpConfigModuleBase.getDisplayNum());
                        organizeMpConfigModuleBaseVO.setId(dto.getId());
                        organizeMpConfigModuleBaseVO.setReplacePicUrl(dto.getReplacePicUrl());
                        organizeMpConfigModuleBaseVO.setModuleBaseId(mpConfigModuleBase.getId());
                        baseInfoList.add(organizeMpConfigModuleBaseVO);
                    }
                }
                organizeMpConfigModuleVO.setBaseInfo(baseInfoList);
            }
            moudles.add(organizeMpConfigModuleVO);
        }
        Map<Integer,List<OrganizeMpConfigModuleBaseVO>> map = new HashMap<>();
        for (OrganizeMpConfigModuleVO o: moudles) {
            if (map.containsKey(o.getModuleId())){
                List<OrganizeMpConfigModuleBaseVO> baseInfos2 = map.get(o.getModuleId());
                baseInfos2.addAll(o.getBaseInfo());
                map.put(o.getModuleId(),baseInfos2);
            }else {
                map.put(o.getModuleId(),o.getBaseInfo());
            }
        }

        List<OrganizeMpConfigModuleVO> moudles2 = new ArrayList<>();
        for (OrganizeMpConfigModuleVO o: moudles) {
            OrganizeMpConfigModuleVO organizeMpConfigModuleVO = new OrganizeMpConfigModuleVO();
            if (map.get(o.getModuleId()) != null){
                organizeMpConfigModuleVO.setModuleId(o.getModuleId());
                organizeMpConfigModuleVO.setModuleName(o.getModuleName());
                organizeMpConfigModuleVO.setDisplayNum(o.getDisplayNum());
                organizeMpConfigModuleVO.setSortModule(o.getSortModule());
                organizeMpConfigModuleVO.setBaseInfo(map.get(o.getModuleId()));
            }
            moudles2.add(organizeMpConfigModuleVO);
        }
        Map<Integer,OrganizeMpConfigModuleVO> map2 = new HashMap<>();
        for (OrganizeMpConfigModuleVO o: moudles2) {
                map2.put(o.getModuleId(),o);
        }
        List<OrganizeMpConfigModuleVO> moudles3 = new ArrayList<>();
        for (Map.Entry<Integer,OrganizeMpConfigModuleVO> mapsss: map2.entrySet()) {
            moudles3.add(mapsss.getValue());
        }
        organizeMpConfigPageSingleMenuVO.setModules(moudles3);
        return organizeMpConfigPageSingleMenuVO;
    }

    @Override
    public void saveSingle(OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.getByOrganizeId(organizeId);
        Integer reselectMoudle = organizeMenuMpRequestVO.getReselectMoudle();
        if (organizeMpConfigPlan != null){
            //有草稿则删除之前的草稿
            Integer organizePlanId = organizeMpConfigPlan.getId();
            Integer mpPlanId = organizeMenuMpRequestVO.getMpPlanId();
            selAndUpdatePlan(mpPlanId, organizeId, organizePlanId,reselectMoudle);
            //新增page和menu
            saveMenuAndPage(organizePlanId,organizeMenuMpRequestVO,organizeId,2);
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
            saveMenuAndPage(null,organizeMenuMpRequestVO,organizeId,2);
        }
        //保存分类一级图片
        List<OrganizeMallCategoryVO> organizeMallCategoryVOs = organizeMenuMpRequestVO.getImgurls();
        if (organizeMallCategoryVOs !=  null && organizeMallCategoryVOs.size() > 0){
            for (OrganizeMallCategoryVO o:organizeMallCategoryVOs) {
                mallCategoryDao.updateParentCategoryImg(o);
            }
        }

    }

    private void selAndUpdatePlan(Integer mpPlanId, Long organizeId, Integer organizePlanId,Integer reselectMoudle) {
        if(reselectMoudle != null && reselectMoudle == 1){
            List<Integer> menuIds = organizeMpConfigPlanMenuDao.selIdByOrGanizeMoudleId(organizePlanId);
            if (menuIds != null && menuIds.size()>0){
                for (Integer menuId :menuIds) {
                    //删除page中的菜单
                    int i = organizeMpConfigPlanPageDao.delByMenuId(menuId);
                    //删除menu中的菜单
                    int i2 = organizeMpConfigPlanMenuDao.delById(menuId);
                }
            }
        }
        //更新plan表
        OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
        organizeMpConfigPlan1.setId(organizePlanId);
        organizeMpConfigPlan1.setMpPlanId(mpPlanId);
        organizeMpConfigPlan1.setOrganizeId(organizeId);
        organizeMpConfigPlan1.setCurrentUsing(0);
        organizeMpConfigPlan1.setLogicDelete(0);
        log.info("企业小程序单个菜单页面-plan-更新草稿入参:{}",organizeMpConfigPlan1);
        organizeMpConfigPlanDao.updateByOrganizePlanId(organizeMpConfigPlan1);
    }

    private void saveMenuAndPage(Integer organizePlanId,OrganizeMenuMpRequestVO organizeMenuMpRequestVO, Long organizeId,int flag) {
        //保存菜单表
        MpConfigPlan mpConfigPlan = mpConfigPlanDao.selectById(organizeMenuMpRequestVO.getMpPlanId());
        if (mpConfigPlan != null) {
            Integer planId = mpConfigPlan.getId();
            //查询模版底部菜单
            List<MpConfigPlanMenu> mpConfigPlanMenus = mpConfigPlanMenuDao.selByPlanId(planId);
            if (mpConfigPlanMenus != null && mpConfigPlanMenus.size() > 0) {
                if (organizePlanId == null) {
                    OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.getByOrganizeId(organizeId);
                    organizePlanId = organizeMpConfigPlan2.getId();
                }
                Integer finalOrganizePlanId = organizePlanId;
                //保存模版底部菜单到企业小程序
                for (MpConfigPlanMenu m: mpConfigPlanMenus) {
                    OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selByIdAndName(finalOrganizePlanId, m.getTitle());
                    if (organizeMpConfigPlanMenuDTO != null){
                        organizeMpConfigPlanMenuDao.delById(organizeMpConfigPlanMenuDTO.getId());
                    }
                    OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu = new OrganizeMpConfigPlanMenu();
                    BeanUtils.copyProperties(m, organizeMpConfigPlanMenu);
                    organizeMpConfigPlanMenu.setOrganizePlanId(finalOrganizePlanId);
                    if (organizeMpConfigPlanMenuDTO != null){
                        organizeMpConfigPlanMenu.setId(organizeMpConfigPlanMenuDTO.getId());
                    }else {
                        organizeMpConfigPlanMenu.setId(null);
                    }
                    log.info("企业小程序单个菜单页面-menu-保存全部菜单页面入参:{}", organizeMpConfigPlanMenu);
                    organizeMpConfigPlanMenuDao.insertSingle(organizeMpConfigPlanMenu);
                }
                //保存page
                //1。先清除当前要保存的单个菜单page
                //根据planid和title查询menuid
                OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO2 = organizeMpConfigPlanMenuDao.selByIdAndName(finalOrganizePlanId, organizeMenuMpRequestVO.getMenuName());
                Integer menuId2 = 0;
                if (organizeMpConfigPlanMenuDTO2 != null) {
                    menuId2 = organizeMpConfigPlanMenuDTO2.getId();
                }
                //根据menuid查询page,清除page
                List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS2 = organizeMpConfigPlanPageDao.selectByMenuId(menuId2);
                if (organizeMpConfigPlanPageDTOS2 != null && organizeMpConfigPlanPageDTOS2.size() > 0) {
                    organizeMpConfigPlanPageDao.delByMenuId(menuId2);
                }
                //2。保存当前菜单page
                List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
                for (OrganizeMpConfigPageMenuVo o : modules) {
                    List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                    for (OrganizeMpConfigModuleBaseVo base : baseInfo) {
                        OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                        organizeMpConfigPlanPage.setMenuId(menuId2);
                        organizeMpConfigPlanPage.setModuleId(o.getModuleId());
                        organizeMpConfigPlanPage.setModuleBaseId(base.getId());
                        organizeMpConfigPlanPage.setShowLayout(base.getShowLayout());
                        organizeMpConfigPlanPage.setSortModule(o.getSortModule());
                        organizeMpConfigPlanPage.setSortBase(base.getSortBase());
                        organizeMpConfigPlanPage.setBaseName(base.getBaseName());
                        organizeMpConfigPlanPage.setLogicDelete(o.getIsDel());
                        organizeMpConfigPlanPage.setReplacePicUrl(o.getReplacePicUrl());
                        //organizeMpConfigPlanPage.setId(base.getOrganizeMpConfigPlanPageId());
                        log.info("企业小程序单个菜单页面-page-保存当前菜单页面入参:{}", o);
                        organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
                    }
                }
                //3。保存其他菜单page
//                if (flag == 0){
//                    //flag == 0  保存单个页面的标识
//                    for (MpConfigPlanMenu  mp: mpConfigPlanMenus) {
//                        //查询所有菜单
//                        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO3 = organizeMpConfigPlanMenuDao.selByIdAndName(finalOrganizePlanId, mp.getTitle());
//                        if (organizeMpConfigPlanMenuDTO3 != null){
//                            if (!organizeMpConfigPlanMenuDTO3.getTitle().equals(organizeMenuMpRequestVO.getMenuName())){
//                                List<MpConfigPlanPage> mpConfigPlanPage = mpConfigPlanPageDao.selByMenuId(mp.getId());
//                                if (mpConfigPlanPage != null && mpConfigPlanPage.size()>0) {
//                                    for (MpConfigPlanPage m: mpConfigPlanPage) {
//                                        OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
//                                        BeanUtils.copyProperties(m, organizeMpConfigPlanPage);
//                                        MpConfigModuleBase mpConfigModuleBase = mpConfigModuleBaseDao.selectById(m.getModuleBaseId());
//                                        if (mpConfigModuleBase != null){
//                                            organizeMpConfigPlanPage.setBaseName(mpConfigModuleBase.getBaseName());
//                                        }
//                                        organizeMpConfigPlanPage.setMenuId(organizeMpConfigPlanMenuDTO3.getId());
//                                        organizeMpConfigPlanPage.setId(null);
//                                        log.info("企业小程序单个菜单页面-page-保存其他菜单页面入参:{}", organizeMpConfigPlanPage);
//                                        organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }
    }
    @Override
    public void saveAll(OrganizeMpRequestVO organizeMpRequestVO) {
        List<OrganizeMenuMpVO> allmenus = organizeMpRequestVO.getAllmenus();
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        Integer status = organizeMpRequestVO.getStatus();
        Integer mpPlanId = organizeMpRequestVO.getMpPlanId();
        if (status == 0){
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
                organizeMpConfigPlan1.setMpPlanId(mpPlanId);
                organizeMpConfigPlan1.setOrganizeId(organizeId);
                organizeMpConfigPlan1.setCurrentUsing(0);
                organizeMpConfigPlan1.setLogicDelete(0);
                log.info("企业小程序单个菜单页面-plan-更新草稿入参:{}",organizeMpConfigPlan1);
                organizeMpConfigPlanDao.updateByOrganizePlanId(organizeMpConfigPlan1);
                saveAllPage(allmenus, mpPlanId,organizeId, organizePlanId);
            }else {
                //无草稿新增草稿
                //保存plan表
                OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
                organizeMpConfigPlan1.setMpPlanId(organizeMpRequestVO.getMpPlanId());
                organizeMpConfigPlan1.setOrganizeId(organizeId);
                organizeMpConfigPlan1.setCurrentUsing(0);
                organizeMpConfigPlan1.setLogicDelete(0);
                log.info("企业小程序单个菜单页面-plan-保存草稿入参:{}",organizeMpConfigPlan1);
                organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);
                saveAllPage(allmenus,mpPlanId, organizeId, null);
            }
        }else {
            OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selectByIdUsing(organizeId);
            if (organizeMpConfigPlan != null) {
                //有发布的记录则删除
                organizeMpConfigPlanDao.setDelete(organizeMpConfigPlan.getId());
            }
            //当前plan还是为草稿;
            //另保存当前plan为一份新的plan:正在使用
            OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
            organizeMpConfigPlan1.setMpPlanId(organizeMpRequestVO.getMpPlanId());
            organizeMpConfigPlan1.setOrganizeId(organizeId);
            organizeMpConfigPlan1.setCurrentUsing(1);
            organizeMpConfigPlan1.setLogicDelete(0);
            log.info("企业小程序单个菜单页面-plan-保存发布草稿为正在使用入参:{}",organizeMpConfigPlan1);
            organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);
            OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.selectByIdUsing(organizeId);
            Integer organizePlanId = 0;
            if (organizeMpConfigPlan2 != null){
                organizePlanId = organizeMpConfigPlan2.getId();
            }
            saveAllPage(allmenus, mpPlanId,organizeId ,organizePlanId);
            //保存发布记录
            List<OrganizeMpReleaseDTO> organizeMpReleaseDTOS = organizeMpReleaseDao.selByOrganizeId(organizeId);
            if (organizeMpReleaseDTOS == null || (organizeMpReleaseDTOS != null && organizeMpReleaseDTOS.size() == 0)){
                OrganizeMpReleaseParamVO organizeMpReleaseParamVO = new OrganizeMpReleaseParamVO();
                organizeMpReleaseParamVO.setOrganizeId(organizeId);
                organizeMpReleaseParamVO.setVersion(Float.toString(1.0f));
                organizeMpReleaseParamVO.setPlanId(organizePlanId);
                log.info("企业小程序第一次保存发布入参:{}",organizeMpConfigPlan1);
                organizeMpReleaseDao.insertVersion(organizeMpReleaseParamVO);
            }else {
                String version = organizeMpReleaseDTOS.get(0).getVersion();
                float v = 0.0f;
                if (version != null){
                    v = parseFloat(version) + 0.1f;
                }
                String versionStr = Float.toString(v);
                OrganizeMpReleaseParamVO organizeMpReleaseParamVO = new OrganizeMpReleaseParamVO();
                organizeMpReleaseParamVO.setOrganizeId(organizeId);
                organizeMpReleaseParamVO.setVersion(versionStr);
                organizeMpReleaseParamVO.setPlanId(organizePlanId);
                log.info("企业小程序保存发布入参:{}",organizeMpConfigPlan1);
                organizeMpReleaseDao.insertVersion(organizeMpReleaseParamVO);
            }

        }
    }

    private void saveAllPage(List<OrganizeMenuMpVO> allmenus,Integer mpPlanId, Long organizeId, Integer organizePlanId) {
        allmenus.stream().forEach(menu -> {
            OrganizeMenuMpRequestVO organizeMenuMpRequestVO = new OrganizeMenuMpRequestVO();
            if (menu != null) {
                BeanUtils.copyProperties(menu, organizeMenuMpRequestVO);
                //新增page和menu
                organizeMenuMpRequestVO.setMpPlanId(mpPlanId);
                int flag = 1;
                saveMenuAndPage(organizePlanId, organizeMenuMpRequestVO, organizeId,flag);
            }
        });
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
    @Override
    public List<OrganizeMpConfigMenuVO> select2() {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeIdNotUse(organizeId);
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

    @Override
    public void saveAndePublish() {
        //保存草稿
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();


        //保存发布
    }

}
