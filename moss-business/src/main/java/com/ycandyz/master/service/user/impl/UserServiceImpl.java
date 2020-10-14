package com.ycandyz.master.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.service.user.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户信息
 * @Author: Wang Yang
 * @Date:   2020-09-23
 * @Version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

}
