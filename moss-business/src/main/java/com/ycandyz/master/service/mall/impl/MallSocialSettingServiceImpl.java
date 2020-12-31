package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.entities.mall.MallSocialSetting;
import com.ycandyz.master.domain.model.mall.MallSocialSettingModel;
import com.ycandyz.master.domain.query.mall.MallSocialSettingQuery;
import com.ycandyz.master.dao.mall.MallSocialSettingDao;
import com.ycandyz.master.service.mall.IMallSocialSettingService;
import com.ycandyz.master.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * @Description 分销设置表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-29
 * @version 2.0
 */
@Slf4j
@Service
public class MallSocialSettingServiceImpl extends BaseService<MallSocialSettingDao,MallSocialSetting,MallSocialSettingQuery> implements IMallSocialSettingService {

    @Override
    public boolean insert(MallSocialSettingModel model) {
        MallSocialSetting t = new MallSocialSetting();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(MallSocialSettingModel model) {
        MallSocialSetting t = new MallSocialSetting();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }


}
