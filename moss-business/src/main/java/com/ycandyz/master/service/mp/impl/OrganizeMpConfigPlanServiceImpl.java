package com.ycandyz.master.service.mp.impl;

import com.ycandyz.master.entities.mp.OrganizeMpConfigPlan;
import com.ycandyz.master.domain.query.mp.OrganizeMpConfigPlanQuery;
import com.ycandyz.master.dao.mp.OrganizeMpConfigPlanDao;
import com.ycandyz.master.service.mp.IOrganizeMpConfigPlanService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 企业小程序配置方案 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Slf4j
@Service
public class OrganizeMpConfigPlanServiceImpl extends BaseService<OrganizeMpConfigPlanDao,OrganizeMpConfigPlan,OrganizeMpConfigPlanQuery> implements IOrganizeMpConfigPlanService {

}
