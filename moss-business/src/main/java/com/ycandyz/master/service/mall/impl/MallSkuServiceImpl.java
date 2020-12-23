package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.entities.mall.MallSku;
import com.ycandyz.master.domain.model.mall.MallSkuModel;
import com.ycandyz.master.domain.query.mall.MallSkuQuery;
import com.ycandyz.master.dao.mall.MallSkuDao;
import com.ycandyz.master.service.mall.IMallSkuService;
import com.ycandyz.master.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * @Description sku表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-20
 * @version 2.0
 */
@Slf4j
@Service
public class MallSkuServiceImpl extends BaseService<MallSkuDao,MallSku,MallSkuQuery> implements IMallSkuService {

    @Override
    public boolean insert(MallSkuModel model) {
        MallSku t = new MallSku();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(MallSkuModel model) {
        MallSku t = new MallSku();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }


}
