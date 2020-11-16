package com.ycandyz.master.service.miniprogram.impl;

import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanPageQuery;
import com.ycandyz.master.dao.miniprogram.MpConfigPlanPageDao;
import com.ycandyz.master.service.miniprogram.IMpConfigPlanPageService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 小程序配置-页面配置 业务类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Slf4j
@Service
public class MpConfigPlanPageServiceImpl extends BaseService<MpConfigPlanPageDao,MpConfigPlanPage,MpConfigPlanPageQuery> implements IMpConfigPlanPageService {

    @Override
    public Boolean add(MpConfigPlanPageModel model) {
        return null;
    }
}
