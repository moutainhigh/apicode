package com.ycandyz.master.service.miniprogram.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.ycandyz.master.domain.model.miniprogram.MpTransferApplyModel;
import com.ycandyz.master.entities.miniprogram.MpTransferApply;
import com.ycandyz.master.domain.query.miniprogram.MpTransferApplyQuery;
import com.ycandyz.master.dao.miniprogram.MpTransferApplyDao;
import com.ycandyz.master.service.miniprogram.IMpTransferApplyService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.DateUtils;
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

    @Override
    public Boolean add(MpTransferApplyModel model) {

        MpTransferApply params = new MpTransferApply();
        BeanUtil.copyProperties(model,params);
        return this.save(params);
    }

    @Override
    public Boolean updateTransferApply(MpTransferApply entity) {

        if(entity.getButtedStatus() == 2){
            //对接状态已完成，添加交接完成时间
            entity.setButtedFinishTime(DateUtils.getZeroZoneTime());
        }
        return this.updateById(entity);
    }


}
