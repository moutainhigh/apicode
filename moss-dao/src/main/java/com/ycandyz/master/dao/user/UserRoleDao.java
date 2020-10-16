package com.ycandyz.master.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.user.UserRoleDTO;
import com.ycandyz.master.entities.user.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {

//    UserRoleDTO queryUserRoleByUserId
}
