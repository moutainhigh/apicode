package com.ycandyz.master.service.organize.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.entities.organize.OrganizeGroup;
import com.ycandyz.master.domain.query.organize.OrganizeGroupQuery;
import com.ycandyz.master.dao.organize.OrganizeGroupDao;
import com.ycandyz.master.exception.BusinessException;
import com.ycandyz.master.service.organize.IOrganizeGroupService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SanGang
 * @version 2.0
 * @Description 集团表 业务类
 * @since 2020-10-23
 */
@Slf4j
@Service
public class OrganizeGroupServiceImpl extends BaseService<OrganizeGroupDao, OrganizeGroup, OrganizeGroupQuery> implements IOrganizeGroupService {
    @Resource
    private OrganizeGroupDao organizeGroupDao;

    public OrganizeGroup getByOrganizeId() {
        UserVO user = getUser();
        Long organizeId = user.getOrganizeId();
        AssertUtils.notNull(organizeId, "集团编号为空");
        List<OrganizeGroup> organizeGroups = organizeGroupDao.selectList(new QueryWrapper<OrganizeGroup>().eq("organize_id", organizeId));
        if (CollectionUtil.isEmpty(organizeGroups)) {
            throw new BusinessException("集团信息不存在！");
        }
        return organizeGroups.get(0);
    }

    public boolean update(OrganizeGroup entity) {
        log.info("集团修改是否开启门店可维护订单开始");
        AssertUtils.notNull(entity, "修改失败，集团信息为空");
        return organizeGroupDao.update(entity, new UpdateWrapper<OrganizeGroup>().set("is_open_maintainable", entity.getIsOpenMaintainable()).eq("id",entity.getId())) > 0;
    }
}
