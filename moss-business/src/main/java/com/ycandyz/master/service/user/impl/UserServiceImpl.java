package com.ycandyz.master.service.user.impl;

import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.domain.query.user.UserQuery;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.service.user.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 用户表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Service
public class UserServiceImpl extends BaseService<UserDao,User,UserQuery> implements IUserService {

}
