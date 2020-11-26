package com.ycandyz.master.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.domain.query.user.UserNodeQuery;
import com.ycandyz.master.domain.response.user.UserNodeResp;
import com.ycandyz.master.dto.user.UserForExport;
import com.ycandyz.master.entities.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    List<UserNodeResp> selectUserNode(UserNodeQuery query);

    UserForExport selectForExport(Long id);
}
