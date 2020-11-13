package com.ycandyz.master.service.msg.impl;

import com.ycandyz.master.entities.msg.MsgNotifyRelation;
import com.ycandyz.master.domain.query.msg.MsgNotifyRelationQuery;
import com.ycandyz.master.dao.msg.MsgNotifyRelationDao;
import com.ycandyz.master.service.msg.IMsgNotifyRelationService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 业务通知关系表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Slf4j
@Service
public class MsgNotifyRelationServiceImpl extends BaseService<MsgNotifyRelationDao,MsgNotifyRelation,MsgNotifyRelationQuery> implements IMsgNotifyRelationService {

}
