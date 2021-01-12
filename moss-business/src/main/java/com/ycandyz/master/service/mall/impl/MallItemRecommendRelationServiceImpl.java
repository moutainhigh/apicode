package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.entities.mall.MallItemRecommendRelation;
import com.ycandyz.master.domain.model.mall.MallItemRecommendRelationModel;
import com.ycandyz.master.domain.query.mall.MallItemRecommendRelationQuery;
import com.ycandyz.master.dao.mall.MallItemRecommendRelationDao;
import com.ycandyz.master.service.mall.IMallItemRecommendRelationService;
import com.ycandyz.master.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * @Description 商品推荐关联表 业务类
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
@Slf4j
@Service
public class MallItemRecommendRelationServiceImpl extends BaseService<MallItemRecommendRelationDao,MallItemRecommendRelation,MallItemRecommendRelationQuery> implements IMallItemRecommendRelationService {

    @Override
    public boolean insert(MallItemRecommendRelationModel model) {
        MallItemRecommendRelation t = new MallItemRecommendRelation();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(MallItemRecommendRelationModel model) {
        MallItemRecommendRelation t = new MallItemRecommendRelation();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }


}
