package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.entities.mall.MallItemOrganize;
import com.ycandyz.master.domain.model.mall.MallItemOrganizeModel;
import com.ycandyz.master.domain.query.mall.MallItemOrganizeQuery;
import com.ycandyz.master.dao.mall.MallItemOrganizeDao;
import com.ycandyz.master.service.mall.IMallItemOrganizeService;
import com.ycandyz.master.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * @Description 商品-集团 业务类
 * </p>
 *
 * @author SanGang
 * @since 2021-01-05
 * @version 2.0
 */
@Slf4j
@Service
public class MallItemOrganizeServiceImpl extends BaseService<MallItemOrganizeDao,MallItemOrganize,MallItemOrganizeQuery> implements IMallItemOrganizeService {

    @Override
    public boolean insert(MallItemOrganizeModel model) {
        MallItemOrganize t = new MallItemOrganize();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(MallItemOrganizeModel model) {
        MallItemOrganize t = new MallItemOrganize();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }


}
