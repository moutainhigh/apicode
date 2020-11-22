package com.ycandyz.master.service.miniprogram.impl;

import com.ycandyz.master.dao.miniprogram.OrganizeMpConfigPlanMenuDao;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanMenuDTO;
import com.ycandyz.master.dto.miniprogram.OrganizeMpConfigPlanPageDTO;
import com.ycandyz.master.model.miniprogram.OrganizeChooseMpConfigPage;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import com.ycandyz.master.service.miniprogram.MpChooseStyleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MpChooseStyleServiceImpl implements MpChooseStyleService {

    @Autowired
    private OrganizeMpConfigPlanMenuDao organizeMpConfigPlanMenuDao;

    @Override
    public OrganizeMpConfigMenuVO selById(Integer id) {
        organizeMpConfigPlanMenuDao.selById(id);
        return null;
    }

    @Override
    public OrganizeMpConfigMenuVO selByOrGanizeMoudleId(Integer id) {
        OrganizeMpConfigPlanMenuDTO organizeMpConfigPlanMenuDTO = organizeMpConfigPlanMenuDao.selByOrGanizeMoudleId(id);
        OrganizeMpConfigMenuVO organizeMpConfigMenuVO = new OrganizeMpConfigMenuVO();
        if (organizeMpConfigPlanMenuDTO != null){
            BeanUtils.copyProperties(organizeMpConfigPlanMenuDTO,organizeMpConfigMenuVO);
        }
        return organizeMpConfigMenuVO;
    }

    @Override
    public OrganizeChooseMpConfigPage selectMenuById(Integer menuId) {
        OrganizeMpConfigPlanPageDTO organizeMpConfigPlanPageDTO =  organizeMpConfigPlanMenuDao.selectMenuById(menuId);
        return null;
    }
}
