package com.ycandyz.master.service.organize.impl;

import com.ycandyz.master.entities.organize.OrganizeGroup;
import com.ycandyz.master.domain.query.organize.OrganizeGroupQuery;
import com.ycandyz.master.dao.organize.OrganizeGroupDao;
import com.ycandyz.master.service.organize.IOrganizeGroupService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 集团表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Slf4j
@Service
public class OrganizeGroupServiceImpl extends BaseService<OrganizeGroupDao,OrganizeGroup,OrganizeGroupQuery> implements IOrganizeGroupService {

}
