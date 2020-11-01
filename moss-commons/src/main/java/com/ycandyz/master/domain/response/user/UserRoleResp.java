package com.ycandyz.master.domain.response.user;

import com.ycandyz.master.entities.user.UserRole;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 用户关联角色表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-10-31
 * @version 2.0
 */
@Getter
@Setter
@TableName("user_role")
public class UserRoleResp extends UserRole {

}
