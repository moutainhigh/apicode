package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.mall.MallItemOrganize;
import com.ycandyz.master.domain.model.mall.MallItemOrganizeModel;

/**
 * <p>
 * @Description 商品-集团 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2021-01-05
 * @version 2.0
 */
public interface IMallItemOrganizeService extends IService<MallItemOrganize>{

    boolean insert(MallItemOrganizeModel model);

    boolean update(MallItemOrganizeModel model);
}
