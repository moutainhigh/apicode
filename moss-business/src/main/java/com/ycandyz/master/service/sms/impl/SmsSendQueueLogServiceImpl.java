package com.ycandyz.master.service.sms.impl;

import com.ycandyz.master.entities.sms.SmsSendQueueLog;
import com.ycandyz.master.domain.query.sms.SmsSendQueueLogQuery;
import com.ycandyz.master.dao.sms.SmsSendQueueLogDao;
import com.ycandyz.master.service.sms.ISmsSendQueueLogService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description  业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-12
 * @version 2.0
 */
@Slf4j
@Service
public class SmsSendQueueLogServiceImpl extends BaseService<SmsSendQueueLogDao,SmsSendQueueLog,SmsSendQueueLogQuery> implements ISmsSendQueueLogService {

}
