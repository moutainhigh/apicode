package com.ycandyz.master.service.user.impl;

import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.domain.query.user.UserNodeQuery;
import com.ycandyz.master.domain.query.user.UserQuery;
import com.ycandyz.master.domain.response.user.UserNodeResp;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.service.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<UserNodeResp> selectUserNode(UserNodeQuery query) {
        return baseMapper.selectUserNode(query);
    }
}
