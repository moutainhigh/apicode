package com.ycandyz.master.service.msg.impl;

import com.ycandyz.master.entities.msg.MsgNotify;
import com.ycandyz.master.domain.query.msg.MsgNotifyQuery;
import com.ycandyz.master.dao.msg.MsgNotifyDao;
import com.ycandyz.master.service.msg.IMsgNotifyService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 业务通知表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Slf4j
@Service
public class MsgNotifyServiceImpl extends BaseService<MsgNotifyDao,MsgNotify,MsgNotifyQuery> implements IMsgNotifyService {

}
