package com.ycandyz.master.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.query.user.UserNodeQuery;
import com.ycandyz.master.domain.response.user.UserNodeResp;
import com.ycandyz.master.entities.user.User;

import java.util.List;

/**
 * <p>
 * @Description 用户表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
public interface IUserService extends IService<User>{

    List<UserNodeResp> selectUserNode(UserNodeQuery query);
	
}
