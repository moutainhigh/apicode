package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.entities.mall.MallSkuSpec;
import com.ycandyz.master.domain.model.mall.MallSkuSpecModel;
import com.ycandyz.master.domain.query.mall.MallSkuSpecQuery;
import com.ycandyz.master.dao.mall.MallSkuSpecDao;
import com.ycandyz.master.service.mall.IMallSkuSpecService;
import com.ycandyz.master.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * @Description sku值表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-20
 * @version 2.0
 */
@Slf4j
@Service
public class MallSkuSpecServiceImpl extends BaseService<MallSkuSpecDao,MallSkuSpec,MallSkuSpecQuery> implements IMallSkuSpecService {

    @Override
    public boolean insert(MallSkuSpecModel model) {
        MallSkuSpec t = new MallSkuSpec();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(MallSkuSpecModel model) {
        MallSkuSpec t = new MallSkuSpec();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }


}
