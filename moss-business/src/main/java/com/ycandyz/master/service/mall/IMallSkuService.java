package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.mall.MallSku;
import com.ycandyz.master.domain.model.mall.MallSkuModel;

/**
 * <p>
 * @Description sku表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-20
 * @version 2.0
 */
public interface IMallSkuService extends IService<MallSku>{

    boolean insert(MallSkuModel model);

    boolean update(MallSkuModel model);
}
