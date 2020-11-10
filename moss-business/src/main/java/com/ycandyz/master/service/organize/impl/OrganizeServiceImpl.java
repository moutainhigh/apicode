package com.ycandyz.master.service.organize.impl;

import com.ycandyz.master.entities.organize.Organize;
import com.ycandyz.master.domain.query.organize.OrganizeQuery;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.service.organize.IOrganizeService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 组织表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-09
 * @version 2.0
 */
@Slf4j
@Service
public class OrganizeServiceImpl extends BaseService<OrganizeDao,Organize,OrganizeQuery> implements IOrganizeService {

}
