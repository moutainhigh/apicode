package com.ycandyz.master.service.miniprogram.impl;

import com.ycandyz.master.domain.model.miniprogram.ConfigPlanAndMenuModel;
import com.ycandyz.master.domain.response.miniprogram.MpConfigMenuResp;
import com.ycandyz.master.domain.response.miniprogram.MpConfigPlanMenuResp;
import com.ycandyz.master.entities.miniprogram.MpConfigPlan;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanQuery;
import com.ycandyz.master.dao.miniprogram.MpConfigPlanDao;
import com.ycandyz.master.service.miniprogram.IMpConfigPlanService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * @Description 小程序配置方案 业务类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
@Slf4j
@Service
public class MpConfigPlanServiceImpl extends BaseService<MpConfigPlanDao,MpConfigPlan,MpConfigPlanQuery> implements IMpConfigPlanService {

    @Override
    public List<MpConfigPlanMenuResp> add(ConfigPlanAndMenuModel model) {

        return null;
    }
}
