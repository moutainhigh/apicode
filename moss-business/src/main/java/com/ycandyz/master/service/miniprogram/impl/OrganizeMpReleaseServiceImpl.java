package com.ycandyz.master.service.miniprogram.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.response.risk.BaseTabooWordsRep;
import com.ycandyz.master.dto.miniprogram.OrganizeMpReleaseDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.ycandyz.master.domain.query.miniprogram.OrganizeMpReleaseQuery;
import com.ycandyz.master.dao.miniprogram.OrganizeMpReleaseDao;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.model.miniprogram.OrganizeMpReleaseVO;
import com.ycandyz.master.request.UserRequest;
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
    public Page<OrganizeMpReleaseVO> listAll(Long page_size,Long page) {
        UserVO currentUser = UserRequest.getCurrentUser();
        Long organizeId = currentUser.getOrganizeId();
        Page pageQuery = new Page(page,page_size);
        Page<OrganizeMpReleaseDTO> pages = null;
        Page<OrganizeMpReleaseVO> page1 =  new Page<>();
        try {
            pages = organizeMpReleaseDao.listAll(pageQuery, organizeId);
            List<OrganizeMpReleaseDTO> records = pages.getRecords();
            List<OrganizeMpReleaseVO> list = new ArrayList<>();
            if (records != null && records.size() > 0) {
                for (OrganizeMpReleaseDTO o : records) {
                    OrganizeMpReleaseVO organizeMpReleaseVO = new OrganizeMpReleaseVO();
                    BeanUtils.copyProperties(o, organizeMpReleaseVO);
                    list.add(organizeMpReleaseVO);
                }
            }
            BeanUtils.copyProperties(pages,page1);
            page1.setRecords(list);
        }catch (Exception e){
            log.error("error:{}",e.getMessage());
            page1 = new Page<>(0,10,0);
        }
        return page1;
    }
}
