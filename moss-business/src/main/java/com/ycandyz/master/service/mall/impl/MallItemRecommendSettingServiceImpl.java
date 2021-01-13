package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.entities.mall.MallItemRecommendSetting;
import com.ycandyz.master.domain.model.mall.MallItemRecommendSettingModel;
import com.ycandyz.master.domain.query.mall.MallItemRecommendSettingQuery;
import com.ycandyz.master.dao.mall.MallItemRecommendSettingDao;
import com.ycandyz.master.service.mall.IMallItemRecommendSettingService;
import com.ycandyz.master.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * @Description 商品推荐设置表 业务类
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
@Slf4j
@Service
public class MallItemRecommendSettingServiceImpl extends BaseService<MallItemRecommendSettingDao,MallItemRecommendSetting,MallItemRecommendSettingQuery> implements IMallItemRecommendSettingService {

    @Override
    public boolean insert(MallItemRecommendSettingModel model) {
        MallItemRecommendSetting t = new MallItemRecommendSetting();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(MallItemRecommendSettingModel model) {
        MallItemRecommendSetting t = new MallItemRecommendSetting();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }


}
