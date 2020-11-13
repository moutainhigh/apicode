package com.ycandyz.master.service.msg.impl;

import com.ycandyz.master.entities.msg.MsgNotifyRole;
import com.ycandyz.master.domain.query.msg.MsgNotifyRoleQuery;
import com.ycandyz.master.dao.msg.MsgNotifyRoleDao;
import com.ycandyz.master.service.msg.IMsgNotifyRoleService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 业务通知权限表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Slf4j
@Service
public class MsgNotifyRoleServiceImpl extends BaseService<MsgNotifyRoleDao,MsgNotifyRole,MsgNotifyRoleQuery> implements IMsgNotifyRoleService {

}
