package com.ycandyz.master.service.miniprogram.impl;

import cn.hutool.core.bean.BeanUtil;
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
import com.ycandyz.master.vo.OrganizeMenuMpRequestVO;
import com.ycandyz.master.vo.OrganizeMpConfigModuleBaseVo;
import com.ycandyz.master.vo.OrganizeMpConfigPageMenuVo;
import com.ycandyz.master.vo.OrganizeMpRequestVO;
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
    public OrganizeMpConfigMenuVO selById(Integer id) {
        organizeMpConfigPlanMenuDao.selById(id);
        return null;
    }

    @Override
    public OrganizeMpConfigMenuVO selByOrGanizeMoudleId(Integer id) {
        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selByOrGanizeMoudleId(id);
        OrganizeMpConfigMenuVO organizeMpConfigMenuVO = new OrganizeMpConfigMenuVO();
        if (organizeMpConfigPlanMenuDTO != null) {
            BeanUtils.copyProperties(organizeMpConfigPlanMenuDTO, organizeMpConfigMenuVO);
        }
        return organizeMpConfigMenuVO;
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
    public void saveSingle(OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        Integer id = organizeMenuMpRequestVO.getId();
        //OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.getOrganizePlanById(organizeId,id);
        if (id == null){
            //第一次新增
            //查询草稿
            OrganizeMpConfigPlan organizeMpConfigPlan0 = organizeMpConfigPlanDao.selectByOrganizeIdStatus(organizeId,0,0);
            if (organizeMpConfigPlan0 != null){
                //删除之前的草稿
                int i = organizeMpConfigPlanDao.setDelete(organizeMpConfigPlan0.getId());
            }
            //plan表
            OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
            organizeMpConfigPlan1.setMpPlanId(organizeMenuMpRequestVO.getMpPlanId());
            organizeMpConfigPlan1.setPlanName(organizeMenuMpRequestVO.getPlanName());
            organizeMpConfigPlan1.setOrganizeId(organizeId);
            organizeMpConfigPlan1.setStatus(0);
            organizeMpConfigPlan1.setLogicDelete(0);
            organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);
            //菜单表
            OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu = new OrganizeMpConfigPlanMenu();
            MpConfigPlanMenu mpConfigPlanMenu = mpConfigPlanMenuDao.selectMenuById(organizeMenuMpRequestVO.getMenuId());
            BeanUtils.copyProperties(mpConfigPlanMenu, organizeMpConfigPlanMenu);
            OrganizeMpConfigPlan organizeMpConfigPlan2 = organizeMpConfigPlanDao.selectByOrganizeIdStatus(organizeId,0,0);
            organizeMpConfigPlanMenu.setOrganizePlanId(organizeMpConfigPlan2.getId());
            organizeMpConfigPlanMenuDao.insertSingle(organizeMpConfigPlanMenu);
            //page表
            OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
            List<OrganizeMpConfigPlanPage> list = new ArrayList<>();
            List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
            for (OrganizeMpConfigPageMenuVo o : modules) {
                List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
                    organizeMpConfigPlanPage.setMenuId(organizeMenuMpRequestVO.getMenuId());
                    organizeMpConfigPlanPage.setModuleId(o.getModuleId());
                    organizeMpConfigPlanPage.setModuleBaseId(omcmb.getId());
                    organizeMpConfigPlanPage.setShowLayout(omcmb.getShowLayout()); //暂存元素的展示样式
                    organizeMpConfigPlanPage.setSortModule(o.getSortModule());
                    organizeMpConfigPlanPage.setSortBase(omcmb.getSortBase());
                    organizeMpConfigPlanPage.setBaseName(omcmb.getBaseName());
                    list.add(organizeMpConfigPlanPage);
                }
            }
            for (OrganizeMpConfigPlanPage o : list) {
                organizeMpConfigPlanPageDao.insertSingle(o);
            }
        }else {

            //修改
            List<OrganizeMpConfigPageMenuVo> modules = organizeMenuMpRequestVO.getModules();
            for (OrganizeMpConfigPageMenuVo o : modules) {
                List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
                    Integer id1 = omcmb.getId();
                    String baseName = omcmb.getBaseName();
                    organizeMpConfigPlanPageDao.updateBaseNameById(id1,baseName);
                }
            }
        }
     }

    @Override
    public void saveAll(OrganizeMpRequestVO organizeMpRequestVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        Integer id = organizeMpRequestVO.getId();
        if (id == null){
            //保存plan
            OrganizeMpConfigPlan organizeMpConfigPlan1 = new OrganizeMpConfigPlan();
            organizeMpConfigPlan1.setMpPlanId(organizeMpRequestVO.getMpPlanId());
            organizeMpConfigPlan1.setPlanName(organizeMpRequestVO.getPlanName());
            organizeMpConfigPlan1.setOrganizeId(organizeId);
            organizeMpConfigPlan1.setStatus(organizeMpRequestVO.getStatus());
            organizeMpConfigPlanDao.insertSingle(organizeMpConfigPlan1);

            List<OrganizeMenuMpRequestVO> organizeMenuMpRequestVOS = organizeMpRequestVO.getOrganizeMenuMpRequestVOS();
            for (OrganizeMenuMpRequestVO omm : organizeMenuMpRequestVOS) {
                //保存菜单
                OrganizeMpConfigPlanMenu organizeMpConfigPlanMenu = new OrganizeMpConfigPlanMenu();
                OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selectMenuById(omm.getMenuId());
                BeanUtils.copyProperties(organizeMpConfigPlanMenuDTO, organizeMpConfigPlanMenu);
                //OrganizeMpConfigPlan> organizeMpConfigPlan2 = organizeMpConfigPlanDao.selectByOrganizeId(organizeId);
                //organizeMpConfigPlanMenu.setOrganizePlanId(organizeMpConfigPlan2.getId());
                organizeMpConfigPlanMenuDao.insertSingle(organizeMpConfigPlanMenu);

                OrganizeMpConfigPlanPage organizeMpConfigPlanPage = new OrganizeMpConfigPlanPage();
                List<OrganizeMpConfigPlanPage> list = new ArrayList<>();
                List<OrganizeMpConfigPageMenuVo> modules = omm.getModules();
                for (OrganizeMpConfigPageMenuVo o : modules) {
                    List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                    for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
                        organizeMpConfigPlanPage.setMenuId(omm.getMenuId());
                        organizeMpConfigPlanPage.setModuleId(o.getModuleId());
                        organizeMpConfigPlanPage.setModuleBaseId(omcmb.getId());
                        organizeMpConfigPlanPage.setShowLayout(omcmb.getShowLayout()); //元素的展示样式
                        organizeMpConfigPlanPage.setSortModule(o.getSortModule());
                        organizeMpConfigPlanPage.setSortBase(omcmb.getSortBase());
                        organizeMpConfigPlanPage.setBaseName(omcmb.getBaseName());
                        list.add(organizeMpConfigPlanPage);
                    }
                }
                for (OrganizeMpConfigPlanPage o : list) {
                    organizeMpConfigPlanPageDao.insertSingle(o);
                }
            }
        }else{
            //修改
            List<OrganizeMenuMpRequestVO> organizeMenuMpRequestVOS = organizeMpRequestVO.getOrganizeMenuMpRequestVOS();
            for (OrganizeMenuMpRequestVO omm: organizeMenuMpRequestVOS) {
                List<OrganizeMpConfigPageMenuVo> modules = omm.getModules();
                for (OrganizeMpConfigPageMenuVo o : modules) {
                    List<OrganizeMpConfigModuleBaseVo> baseInfo = o.getBaseInfo();
                    for (OrganizeMpConfigModuleBaseVo omcmb : baseInfo) {
                        Integer id1 = omcmb.getId();
                        String baseName = omcmb.getBaseName();
                        organizeMpConfigPlanPageDao.updateBaseNameById(id1,baseName);
                    }
                }
            }
        }


    }

    @Override
    public Integer get(Integer id) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        if (id == null) {
            OrganizeMpConfigPlan organizeMpConfigPlan = organizeMpConfigPlanDao.getByOrganizeId(organizeId);
            if (organizeMpConfigPlan == null) {
                return 0;
            }
        }
        return 1;
    }
}
