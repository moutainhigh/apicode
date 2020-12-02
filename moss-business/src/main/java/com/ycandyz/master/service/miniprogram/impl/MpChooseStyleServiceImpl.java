package com.ycandyz.master.service.miniprogram.impl;

import com.alibaba.fastjson.JSON;
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

import java.math.BigDecimal;
import java.util.*;


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
    private MallCategoryDao mallCategoryDao;

    @Autowired
    private MpConfigModuleBaseDao mpConfigModuleBaseDao;

    @Autowired
    private OrganizeMpReleaseDao organizeMpReleaseDao;

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
    public OrganizeMpConfigPageSingleMenuVO selectMenuById(Integer menuId){
        OrganizeMpConfigPageSingleMenuVO organizeMpConfigPageSingleMenuVO = new OrganizeMpConfigPageSingleMenuVO();
        organizeMpConfigPageSingleMenuVO.setMenuId(menuId);
        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selectMenuById(menuId);
        if (organizeMpConfigPlanMenuDTO != null){
            organizeMpConfigPageSingleMenuVO.setMenuName(organizeMpConfigPlanMenuDTO.getTitle());
            OrganizeMpConfigPlan organizePlan = organizeMpConfigPlanDao.getOrganizePlanByPlanId(organizeMpConfigPlanMenuDTO.getOrganizePlanId());
            if (organizePlan != null){
                organizeMpConfigPageSingleMenuVO.setMpPlanId(organizePlan.getMpPlanId());
            }
        }
        List<OrganizeMpConfigModuleVO> moudles = new ArrayList<>();
        List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS = organizeMpConfigPlanPageDao.selectByMenuId(menuId);
        for (OrganizeMpConfigPlanPageDTO dto :organizeMpConfigPlanPageDTOS) {
            OrganizeMpConfigModuleVO organizeMpConfigModuleVO = new OrganizeMpConfigModuleVO();
            organizeMpConfigModuleVO.setModuleId(dto.getModuleId());
            organizeMpConfigModuleVO.setModuleName(dto.getModuleName());
            organizeMpConfigModuleVO.setSortModule(dto.getSortModule());
            organizeMpConfigModuleVO.setDisplayNum(dto.getDisplayNum());
            List<Integer> pageIds = new ArrayList<>();
            if(dto.getPageIds() != null){
                for(String id: dto.getPageIds().split(",")){
                    pageIds.add(Integer.parseInt(id));
                }
            }
            List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOs = organizeMpConfigPlanPageDao.selectByIds(menuId,dto.getSortModule(),pageIds);
            List<OrganizeMpConfigModuleBaseVO> baseInfoList = new ArrayList<>();
            for(OrganizeMpConfigPlanPageDTO dtoBase: organizeMpConfigPlanPageDTOs){
                OrganizeMpConfigModuleBaseVO organizeMpConfigModuleBaseVO = new OrganizeMpConfigModuleBaseVO();
                organizeMpConfigModuleBaseVO.setModuleBaseId(dtoBase.getModuleBaseId());
                organizeMpConfigModuleBaseVO.setBaseCode(dtoBase.getBaseCode());
                organizeMpConfigModuleBaseVO.setBaseName(dtoBase.getBaseName());
                organizeMpConfigModuleBaseVO.setSortBase(dtoBase.getSortBase());
                organizeMpConfigModuleBaseVO.setShowLayout(dtoBase.getShowLayout());
                organizeMpConfigModuleBaseVO.setReplacePicUrl(dtoBase.getReplacePicUrl());
                organizeMpConfigModuleBaseVO.setId(dtoBase.getId());
                baseInfoList.add(organizeMpConfigModuleBaseVO);
            }
            organizeMpConfigModuleVO.setBaseInfo(baseInfoList);
            moudles.add(organizeMpConfigModuleVO);
        }
        organizeMpConfigPageSingleMenuVO.setModules(moudles);
        return organizeMpConfigPageSingleMenuVO;
    }

    /**
     * 保存单个菜单页面
     * 没有草稿：新建草稿plan，存储四个菜单menu，只保存当前菜单页，其他菜单页不管
     * 有草稿：更新草稿plan，menu不变，删除当前page，新增page
     * @param organizeMenuMpRequestVO
     */
    @Override
    public void saveSinglePage(OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        boolean check = check(organizeMenuMpRequestVO);
        if (check){
            log.error("企业小程序单页保存参数为空");
        }
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        Integer reselectMoudle = organizeMenuMpRequestVO.getReselectMoudle();
        //重新选择模版保存会删除当前草稿
        if(reselectMoudle != null && reselectMoudle == 1){
            delMenuAndPage(organizeId);
        }
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
        if (organizeMpConfigPlan != null){
            //有草稿
            Integer organizePlanId = organizeMpConfigPlan.getId();
            Integer mpPlanId = organizeMenuMpRequestVO.getMpPlanId();
            //更新plan，menu不变;删除page
            //新增page和menu
            //查询模版底部菜单保存
            saveMenu(organizePlanId, organizeId, mpPlanId);
            //保存page
            savePage(organizePlanId,organizeMenuMpRequestVO,organizeId,2);
        }else {
            //无草稿新增草稿
            //保存plan表
            OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
            organizeMpConfigPlan1.setMpPlanId(organizeMenuMpRequestVO.getMpPlanId());
            organizeMpConfigPlan1.setOrganizeId(organizeId);
            organizeMpConfigPlan1.setCurrentUsing(0);
            organizeMpConfigPlan1.setLogicDelete(0);
            log.info("企业小程序单个菜单页面-plan-保存草稿入参:{}", JSON.toJSONString(organizeMpConfigPlan1));
            organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);
            //新增page和menu
            //查询模版底部菜单保存
            Integer organizePlanId = 0;
            OrganizeMpConfigPlan organizeMpConfigPlan3 = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
            if (organizeMpConfigPlan3 != null){
                organizePlanId = organizeMpConfigPlan3.getId();
            }
            Integer mpPlanId = organizeMenuMpRequestVO.getMpPlanId();
            //保存菜单
            saveMenu(organizePlanId, organizeId, mpPlanId);
            //保存当前菜单页
            savePage(organizePlanId,organizeMenuMpRequestVO,organizeId,2);
            //保存其他菜单页
            saveOther(organizeMpConfigPlan3, organizeMenuMpRequestVO);
        }
        //保存分类一级图片
        List<OrganizeMallCategoryVO> organizeMallCategoryVOs = organizeMenuMpRequestVO.getImgurls();
        if (organizeMallCategoryVOs !=  null && organizeMallCategoryVOs.size() > 0){
            for (OrganizeMallCategoryVO o:organizeMallCategoryVOs) {
                mallCategoryDao.updateParentCategoryImg(o);
            }
        }

    }

    private boolean check(OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        if (organizeMenuMpRequestVO == null){
            return true;
        }
        if (organizeMenuMpRequestVO.getMpPlanId() == null){
            return true;
        }
        return false;
    }

    private void savePage(Integer organizePlanId,OrganizeMenuMpRequestVO organizeMenuMpRequestVO, Long organizeId,int flag) {
        //删除草稿当前page，保存当前菜单page
        if (organizePlanId == null) {
            OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
            if (organizeMpConfigPlan2 != null){
                organizePlanId = organizeMpConfigPlan2.getId();
            }
        }

        Integer menuId = organizeMenuMpRequestVO.getMenuId();
        menuId = moudleOrNowMenu(organizePlanId, menuId);

        //删除page
        List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS2 = organizeMpConfigPlanPageDao.selectByMenuId(menuId);
        if (organizeMpConfigPlanPageDTOS2 != null && organizeMpConfigPlanPageDTOS2.size() > 0) {
            organizeMpConfigPlanPageDao.delByMenuId(menuId);
        }

        //新增page
        List<OrganizeMpConfigPlanPage> newList = new ArrayList<>();
        List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
            for (OrganizeMpConfigPageMenuVo o : modules) {
                List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                for (OrganizeMpConfigModuleBaseVo base : baseInfo) {
                    OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                    organizeMpConfigPlanPage.setMenuId(menuId);
                    organizeMpConfigPlanPage.setModuleId(o.getModuleId());
//                    MpConfigPlanPage mpConfigPlanPage = mpConfigPlanPageDao.selectById(base.getId());
//                    if (mpConfigPlanPage != null){
//                        organizeMpConfigPlanPage.setModuleBaseId(mpConfigPlanPage.getModuleBaseId());
//                    }
                    organizeMpConfigPlanPage.setModuleBaseId(base.getModuleBaseId());
                    organizeMpConfigPlanPage.setShowLayout(base.getShowLayout());
                    organizeMpConfigPlanPage.setSortModule(o.getSortModule());
                    organizeMpConfigPlanPage.setSortBase(base.getSortBase());
                    organizeMpConfigPlanPage.setBaseName(base.getBaseName());
                    organizeMpConfigPlanPage.setLogicDelete(o.getIsDel());
                    organizeMpConfigPlanPage.setReplacePicUrl(base.getReplacePicUrl());
                    organizeMpConfigPlanPage.setBaseCode(base.getBaseCode());
                    newList.add(organizeMpConfigPlanPage);
                    log.info("企业小程序单个菜单页面-page-保存当前菜单页面入参:{}", JSON.toJSONString(organizeMpConfigPlanPage));
                    organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
                }
            }

        }

    private Integer moudleOrNowMenu(Integer organizePlanId, Integer menuId) {
        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selByMoudleMenuId(organizePlanId, menuId);
        //不等于null，说明是第一次选模版，
        if (organizeMpConfigPlanMenuDTO != null ){
            menuId = organizeMpConfigPlanMenuDTO.getId();
        }else {
            OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO2 = organizeMpConfigPlanMenuDao.selectMenuById(menuId);
            if (organizeMpConfigPlanMenuDTO2 != null){
                menuId = organizeMpConfigPlanMenuDTO2.getId();
            }
        }
        return menuId;
    }


    private void saveMenu(Integer organizePlanId, Long organizeId, Integer mpPlanId) {
        List<MpConfigPlanMenu> mpConfigPlanMenus = mpConfigPlanMenuDao.selByPlanId(mpPlanId);
        if (mpConfigPlanMenus != null && mpConfigPlanMenus.size() > 0) {
            if (organizePlanId == null || organizePlanId == 0) {
                OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
                organizePlanId = organizeMpConfigPlan2.getId();
            }
            Integer finalOrganizePlanId = organizePlanId;
            //保存模版底部菜单到企业小程序
            for (MpConfigPlanMenu m: mpConfigPlanMenus) {
                OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selByIdAndName(finalOrganizePlanId, m.getTitle());
                if (organizeMpConfigPlanMenuDTO == null){
                    OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu = new OrganizeMpConfigPlanMenu();
                    BeanUtils.copyProperties(m, organizeMpConfigPlanMenu);
                    organizeMpConfigPlanMenu.setOrganizePlanId(finalOrganizePlanId);
                    if (organizeMpConfigPlanMenuDTO != null){
                        organizeMpConfigPlanMenu.setId(organizeMpConfigPlanMenuDTO.getId());
                    }else {
                        organizeMpConfigPlanMenu.setId(null);
                    }
                    organizeMpConfigPlanMenu.setOldMenuId(null);
                    organizeMpConfigPlanMenu.setModuleMenuId(m.getId());
                    log.info("企业小程序单个菜单页面-menu-保存全部菜单页面入参:{}", JSON.toJSONString(organizeMpConfigPlanMenu));
                    organizeMpConfigPlanMenuDao.insertSingle(organizeMpConfigPlanMenu);
                }
            }
        }
    }

    private void delMenuAndPage(Long organizeId) {
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
        if (organizeMpConfigPlan != null) {
            //有草稿则删除之前的草稿
            //删除plan
            organizeMpConfigPlanDao.setDelete(organizeMpConfigPlan.getId());
            Integer organizePlanId = organizeMpConfigPlan.getId();
            List<Integer> menuIds = organizeMpConfigPlanMenuDao.selIdByOrGanizeMoudleId(organizePlanId);
            if (menuIds != null && menuIds.size() > 0) {
                for (Integer menuId : menuIds) {
                    //删除page中的菜单
                    int i = organizeMpConfigPlanPageDao.delByMenuId(menuId);
                    //删除menu中的菜单
                    int i2 = organizeMpConfigPlanMenuDao.delById(menuId);
                }
            }
        }
    }

    @Override
    public Integer get() {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
            //查询全部草稿
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
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
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
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

    /**
     * 保存草稿
     *      第一种：没有草稿：新建plan、menu、page
     *        用户没有保存过单个菜单页，直接点保存草稿
     *        保存模版的4个menu和所有page到草稿
     *      第二种：有草稿：正常时plan有；4个menu也应该有，page会不全有
     *        用户进来直接点保存草稿，查询模版补全page
     *保存发布
     *      没有草稿：新建plan、menu、page
     *        用户没有保存过单个菜单页，直接点保存草稿
     *        保存模版的4个menu和所有page到草稿
     *      有草稿：
     */
    @Override
    public void saveDraftOrPublish(OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        Integer reselectMoudle = organizeMenuMpRequestVO.getReselectMoudle();
        Integer publish = organizeMenuMpRequestVO.getPublish();
        Integer mpPlanId = organizeMenuMpRequestVO.getMpPlanId();
        //重新选择模版保存会删除当前草稿
        if(reselectMoudle != null && reselectMoudle == 1){
            delMenuAndPage(organizeId);
        }
        if (publish != null && publish == 0){
            OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
            if (organizeMpConfigPlan == null) {
                //没有草稿
                //保存plan表
                organizeMpConfigPlan = saveDraft(mpPlanId, organizeId);
            }
            //有草稿 查询page
            //获取草稿menu
            OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
            Integer organizePlanId = organizeMpConfigPlan2.getId();
            Integer menuId = organizeMenuMpRequestVO.getMenuId();
            menuId = moudleOrNowMenu(organizePlanId, menuId);
            List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS2 = organizeMpConfigPlanPageDao.selPageByMenuId(menuId);
            if (organizeMpConfigPlanPageDTOS2 != null && organizeMpConfigPlanPageDTOS2.size() > 0) {
                int i = organizeMpConfigPlanPageDao.delByMenuId(menuId);
            }
            saveOnePage(organizeMenuMpRequestVO, menuId);
            saveOther(organizeMpConfigPlan, organizeMenuMpRequestVO);
        }else if (publish != null && publish == 1){
            //保存发布
            OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
            if (organizeMpConfigPlan == null) {
                //没有草稿
                //保存模版为草稿plan表
                organizeMpConfigPlan = saveDraft(mpPlanId, organizeId);
            }
            OrganizeMpConfigPlan organizeMpConfigPlanOldUsing = organizeMpConfigPlanDao.selectByIdUsing(organizeId);
            if (organizeMpConfigPlanOldUsing != null) {
                //有发布的记录则删除
                organizeMpConfigPlanDao.setDelete(organizeMpConfigPlanOldUsing.getId());
                Integer organizePlanId = organizeMpConfigPlanOldUsing.getId();
                List<Integer> menuIds = organizeMpConfigPlanMenuDao.selIdByOrGanizeMoudleId(organizePlanId);
                if (menuIds != null && menuIds.size() > 0) {
                    for (Integer menuId : menuIds) {
                        //删除page中的菜单
                        int i = organizeMpConfigPlanPageDao.delByMenuId(menuId);
                        //删除menu中的菜单
                        int i2 = organizeMpConfigPlanMenuDao.delById(menuId);
                    }
                }
            }
            //当前plan还是为草稿;
            //保存草稿单个page
            OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
            Integer menuId = organizeMenuMpRequestVO.getMenuId();
            Integer organizePlanId = null;
            if (organizeMpConfigPlan2 != null){
                organizePlanId = organizeMpConfigPlan2.getId();
            }
            //删除当前页
            menuId = moudleOrNowMenu(organizePlanId, menuId);
            List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS2 = organizeMpConfigPlanPageDao.selPageByMenuId(menuId);
            if (organizeMpConfigPlanPageDTOS2 != null && organizeMpConfigPlanPageDTOS2.size() > 0) {
                int i = organizeMpConfigPlanPageDao.delByMenuId(menuId);
            }
            //新增当前页page
            saveOnePage(organizeMenuMpRequestVO, menuId);
            //saveOther(organizeMpConfigPlan2, organizeMenuMpRequestVO);
            //另保存当前草稿plan为一份新的plan:正在使用
            OrganizeMpConfigPlan organizeMpConfigPlanDraft = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
            OrganizeMpConfigPlan organizeMpConfigPlanNowUsing = new OrganizeMpConfigPlan();
            if (organizeMpConfigPlanDraft != null){
                BeanUtils.copyProperties(organizeMpConfigPlanDraft,organizeMpConfigPlanNowUsing);
            }
            organizeMpConfigPlanNowUsing.setCurrentUsing(1);
            organizeMpConfigPlanNowUsing.setLogicDelete(0);
            organizeMpConfigPlanNowUsing.setId(null);
            log.info("企业小程序单个菜单页面-plan-保存发布草稿为正在使用入参:{}",organizeMpConfigPlanNowUsing);
            organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlanNowUsing);

            //获取正在使用的plan
            OrganizeMpConfigPlan organizeMpConfigPlanNowUsing2 = organizeMpConfigPlanDao.selectByIdUsing(organizeId);
            //获取草稿的planId
            Integer oldOrganizePlanId = organizeMpConfigPlanDraft.getId();
            //获取正在使用的planId
            Integer newOrganizePlanId = organizeMpConfigPlanNowUsing2.getId();
            //草稿menuId
            //查询草稿menu
            List<OrganizeMpConfigPlanMenuDTO> organizeMpConfigMenuDrafts = organizeMpConfigPlanMenuDao.selByOrGanizeMoudleId(oldOrganizePlanId);
            for (OrganizeMpConfigPlanMenuDTO o: organizeMpConfigMenuDrafts) {
                OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu = new OrganizeMpConfigPlanMenuDTO();
                if (o != null && organizeMpConfigPlanNowUsing2 != null){
                    BeanUtils.copyProperties(o,organizeMpConfigPlanMenu);
                    organizeMpConfigPlanMenu.setId(null);
                    organizeMpConfigPlanMenu.setOrganizePlanId(newOrganizePlanId);
                    organizeMpConfigPlanMenu.setOldMenuId(o.getId());
                    organizeMpConfigPlanMenu.setModuleMenuId(o.getModuleMenuId());
                    organizeMpConfigPlanMenuDao.insertSingle(organizeMpConfigPlanMenu);
                }
            }
            //查询正在使用menu保存page
            List<OrganizeMpConfigPlanMenuDTO> organizeMpConfigMenuUsings = organizeMpConfigPlanMenuDao.selByOrGanizeMoudleId(newOrganizePlanId);
            //遍历草稿menuId
            if (organizeMpConfigMenuUsings != null && organizeMpConfigMenuUsings.size() > 0){
                for (OrganizeMpConfigPlanMenuDTO newMenu: organizeMpConfigMenuUsings) {
                    //查询草稿page
                    List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS = organizeMpConfigPlanPageDao.selPageByMenuId(newMenu.getOldMenuId());
                    for (OrganizeMpConfigPlanPageDTO o: organizeMpConfigPlanPageDTOS) {
                        if (o != null){
                            OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                            BeanUtils.copyProperties(o,organizeMpConfigPlanPage);
                            organizeMpConfigPlanPage.setId(null);
                            organizeMpConfigPlanPage.setMenuId(newMenu.getId());
                            organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
                        }
                    }
                }
            }
            //保存发布记录
            List<OrganizeMpReleaseDTO> organizeMpReleaseDTOS = organizeMpReleaseDao.selByOrganizeId(organizeId);
            if (organizeMpReleaseDTOS == null || (organizeMpReleaseDTOS != null && organizeMpReleaseDTOS.size() == 0)){
                OrganizeMpReleaseParamVO organizeMpReleaseParamVO = new OrganizeMpReleaseParamVO();
                organizeMpReleaseParamVO.setOrganizeId(organizeId);
                BigDecimal bigDecimal = new BigDecimal("1.0");
                organizeMpReleaseParamVO.setVersion(bigDecimal.toString());
                organizeMpReleaseParamVO.setPlanId(newOrganizePlanId);
                log.info("企业小程序第一次保存发布入数据库入参:{}",organizeMpReleaseParamVO);
                organizeMpReleaseDao.insertVersion(organizeMpReleaseParamVO);
            }else {
                String version = organizeMpReleaseDTOS.get(0).getVersion();
                BigDecimal bigDecimal = new BigDecimal("0.1");
                String versionStr = null;
                if (version != null){
                    BigDecimal bigDecimal2 = new BigDecimal(version);
                    versionStr = bigDecimal2.add(bigDecimal).toString();
                }
                OrganizeMpReleaseParamVO organizeMpReleaseParamVO = new OrganizeMpReleaseParamVO();
                organizeMpReleaseParamVO.setOrganizeId(organizeId);
                organizeMpReleaseParamVO.setVersion(versionStr);
                organizeMpReleaseParamVO.setPlanId(newOrganizePlanId);
                log.info("企业小程序保存发布入参:{}",organizeMpReleaseParamVO);
                organizeMpReleaseDao.insertVersion(organizeMpReleaseParamVO);
            }
        }
    }

    private void saveOther(OrganizeMpConfigPlan organizeMpConfigPlan, OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        Integer menuId = organizeMenuMpRequestVO.getMenuId();
        menuId = moudleOrNowMenu(organizeMpConfigPlan.getId(), menuId);
        List<OrganizeMpConfigPlanMenuDTO> organizeMpConfigPlanMenuDTOS = organizeMpConfigPlanMenuDao.selByOrGanizeMoudleId(organizeMpConfigPlan.getId());
        if (organizeMpConfigPlanMenuDTOS != null && organizeMpConfigPlanMenuDTOS.size() > 0) {
            for (OrganizeMpConfigPlanMenuDTO m : organizeMpConfigPlanMenuDTOS) {
                if (m.getId() == menuId){
                    continue;
                }
                List<OrganizeMpConfigPlanPageDTO> organizeMpConfigPlanPageDTOS = organizeMpConfigPlanPageDao.selPageByMenuId(m.getId());
                if (organizeMpConfigPlanPageDTOS == null || (organizeMpConfigPlanPageDTOS != null && organizeMpConfigPlanPageDTOS.size() == 0)) {
                    List<OrganizeMpConfigPlanPage > mpConfigPlanPages = mpConfigPlanPageDao.selByMenuId(m.getModuleMenuId());
                    if (mpConfigPlanPages != null && mpConfigPlanPages.size() > 0) {
                        for (OrganizeMpConfigPlanPage  mp : mpConfigPlanPages) {
                            if (mp != null) {
                                OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                                BeanUtils.copyProperties(mp, organizeMpConfigPlanPage);
                                organizeMpConfigPlanPage.setMenuId(m.getId());
                                organizeMpConfigPlanPage.setId(null);
                                organizeMpConfigPlanPage.setBaseCode(mp.getBaseCode());
                                organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
                            }
                        }
                    }
                }
            }

        }
    }

    private void saveOnePage(OrganizeMenuMpRequestVO organizeMenuMpRequestVO, Integer menuId) {
        List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
        for (OrganizeMpConfigPageMenuVo o : modules) {
            List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
            for (OrganizeMpConfigModuleBaseVo base : baseInfo) {
                OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                organizeMpConfigPlanPage.setMenuId(menuId);
                organizeMpConfigPlanPage.setModuleId(o.getModuleId());
//                MpConfigPlanPage mpConfigPlanPage = mpConfigPlanPageDao.selectById(base.getId());
//                if (mpConfigPlanPage != null){
//                    organizeMpConfigPlanPage.setModuleBaseId(mpConfigPlanPage.getModuleBaseId());
//                }
                organizeMpConfigPlanPage.setModuleBaseId(base.getModuleBaseId());
                organizeMpConfigPlanPage.setShowLayout(base.getShowLayout());
                organizeMpConfigPlanPage.setSortModule(o.getSortModule());
                organizeMpConfigPlanPage.setSortBase(base.getSortBase());
                organizeMpConfigPlanPage.setBaseName(base.getBaseName());
                organizeMpConfigPlanPage.setLogicDelete(o.getIsDel());
                organizeMpConfigPlanPage.setReplacePicUrl(base.getReplacePicUrl());
                organizeMpConfigPlanPage.setBaseCode(base.getBaseCode());
                log.info("企业小程序单个菜单页面-page-保存当前菜单页面入参:{}", JSON.toJSONString(organizeMpConfigPlanPage));
                organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
            }
        }
    }

    @Override
    public void del() {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
        if (organizeMpConfigPlan != null){
            //有草稿则删除之前的草稿
            //删除plan
            organizeMpConfigPlanDao.setDelete(organizeMpConfigPlan.getId());
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
        }
    }

    private OrganizeMpConfigPlan saveDraft(Integer mpPlanId, Long organizeId) {
        OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
        organizeMpConfigPlan1.setMpPlanId(mpPlanId);
        organizeMpConfigPlan1.setOrganizeId(organizeId);
        organizeMpConfigPlan1.setCurrentUsing(0);
        organizeMpConfigPlan1.setLogicDelete(0);
        log.info("企业小程序单个菜单页面-plan-保存草稿入参:{}", JSON.toJSONString(organizeMpConfigPlan1));
        organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);
        OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.selByOrganizeIdNowNotUse(organizeId);
        Integer organizePlanId = 0;
        if (organizeMpConfigPlan2 != null) {
            //获取模版menu并保存
            organizePlanId = organizeMpConfigPlan2.getId();
            saveMenu(organizePlanId, organizeId, mpPlanId);
        }
        //查询模版page保存page
        List<OrganizeMpConfigPlanMenuDTO> organizeMpConfigPlanMenuDTOS = organizeMpConfigPlanMenuDao.selByOrGanizeMoudleId(organizePlanId);
        if (organizeMpConfigPlanMenuDTOS != null && organizeMpConfigPlanMenuDTOS.size() > 0) {
            for (OrganizeMpConfigPlanMenuDTO m : organizeMpConfigPlanMenuDTOS) {
                if (m != null) {
                    List<OrganizeMpConfigPlanPage> mpConfigPlanPages = mpConfigPlanPageDao.selByMenuId(m.getModuleMenuId());
                    if (mpConfigPlanPages != null && mpConfigPlanPages.size() > 0) {
                        for (OrganizeMpConfigPlanPage mp : mpConfigPlanPages) {
                            if (mp != null) {
                                OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                                BeanUtils.copyProperties(mp, organizeMpConfigPlanPage);
                                organizeMpConfigPlanPage.setMenuId(m.getId());
                                organizeMpConfigPlanPage.setId(null);
                                organizeMpConfigPlanPageDao.insertSingle(organizeMpConfigPlanPage);
                            }
                        }
                    }
                }
            }
        }
        return organizeMpConfigPlan1;
    }
}