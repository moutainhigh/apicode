package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.mall.MallSkuSpec;
import com.ycandyz.master.domain.model.mall.MallSkuSpecModel;

/**
 * <p>
 * @Description sku值表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-20
 * @version 2.0
 */
public interface IMallSkuSpecService extends IService<MallSkuSpec>{

    boolean insert(MallSkuSpecModel model);

    boolean update(MallSkuSpecModel model);
}
