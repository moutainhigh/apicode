package com.ycandyz.master.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.entities.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 用户信息
 * @Author: Wang Yang
 * @Date:   2020-09-23
 * @Version: V1.0
 */

@Mapper
public interface UserDao extends BaseMapper<User> {

}
