package com.ycandyz.master.service.miniprogram.impl;

import com.ycandyz.master.entities.miniprogram.MpTransferApply;
import com.ycandyz.master.domain.query.miniprogram.MpTransferApplyQuery;
import com.ycandyz.master.dao.miniprogram.MpTransferApplyDao;
import com.ycandyz.master.service.miniprogram.IMpTransferApplyService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 小程序转交接申请 业务类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-16
 * @version 2.0
 */
@Slf4j
@Service
public class MpTransferApplyServiceImpl extends BaseService<MpTransferApplyDao,MpTransferApply,MpTransferApplyQuery> implements IMpTransferApplyService {

}
