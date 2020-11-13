package com.ycandyz.master.model.msg;

import com.ycandyz.master.entities.msg.MsgNotifyRole;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 业务通知权限表 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("msg_notify_role")
public class MsgNotifyRoleVO extends MsgNotifyRole {

}
