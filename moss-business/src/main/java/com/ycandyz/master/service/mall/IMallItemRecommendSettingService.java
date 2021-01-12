package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.mall.MallItemRecommendSetting;
import com.ycandyz.master.domain.model.mall.MallItemRecommendSettingModel;

/**
 * @Description 商品推荐设置表 业务接口类
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
public interface IMallItemRecommendSettingService extends IService<MallItemRecommendSetting>{

    boolean insert(MallItemRecommendSettingModel model);

    boolean update(MallItemRecommendSettingModel model);
}
