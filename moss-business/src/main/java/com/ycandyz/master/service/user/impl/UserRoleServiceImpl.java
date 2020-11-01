package com.ycandyz.master.service.user.impl;

import com.ycandyz.master.entities.user.UserRole;
import com.ycandyz.master.domain.query.user.UserRoleQuery;
import com.ycandyz.master.dao.user.UserRoleDao;
import com.ycandyz.master.service.user.IUserRoleService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 用户关联角色表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-31
 * @version 2.0
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends BaseService<UserRoleDao,UserRole,UserRoleQuery> implements IUserRoleService {

}
