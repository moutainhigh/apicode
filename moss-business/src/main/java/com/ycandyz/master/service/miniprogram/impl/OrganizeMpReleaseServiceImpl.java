package com.ycandyz.master.service.miniprogram.impl;

import com.ycandyz.master.dto.miniprogram.OrganizeMpReleaseDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.ycandyz.master.domain.query.miniprogram.OrganizeMpReleaseQuery;
import com.ycandyz.master.dao.miniprogram.OrganizeMpReleaseDao;
import com.ycandyz.master.model.miniprogram.OrganizeMpReleaseVO;
import com.ycandyz.master.service.miniprogram.IOrganizeMpReleaseService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * @Description 企业小程序发布记录 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Slf4j
@Service
public class OrganizeMpReleaseServiceImpl extends BaseService<OrganizeMpReleaseDao,OrganizeMpRelease,OrganizeMpReleaseQuery> implements IOrganizeMpReleaseService {

    @Autowired
    private OrganizeMpReleaseDao organizeMpReleaseDao;

    @Override
    public List<OrganizeMpReleaseVO> listAll() {
        List<OrganizeMpReleaseDTO> organizeMpReleaseDTOS = organizeMpReleaseDao.listAll();
        List<OrganizeMpReleaseVO> list = new ArrayList<>();
        if (organizeMpReleaseDTOS != null && organizeMpReleaseDTOS.size() > 0){
            for (OrganizeMpReleaseDTO o:organizeMpReleaseDTOS) {
                OrganizeMpReleaseVO organizeMpReleaseVO = new OrganizeMpReleaseVO();
                BeanUtils.copyProperties(o,organizeMpReleaseVO);
                list.add(organizeMpReleaseVO);
            }
        }
        return list;
    }
}
