package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.mall.MallItemRecommendRelationModel;
import com.ycandyz.master.entities.mall.MallItemRecommendRelation;

/**
 * @Description 商品推荐关联表 业务接口类
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
public interface IMallItemRecommendRelationService extends IService<MallItemRecommendRelation>{

    boolean insert(MallItemRecommendRelationModel model);

    boolean update(MallItemRecommendRelationModel model);
}
