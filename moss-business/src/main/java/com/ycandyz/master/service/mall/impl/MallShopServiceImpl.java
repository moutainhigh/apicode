package com.ycandyz.master.service.mall.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.domain.query.mall.MallShopQuery;
import com.ycandyz.master.dao.mall.MallShopDao;
import com.ycandyz.master.service.mall.IMallShopService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 商城 - 店铺表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-05
 * @version 2.0
 */
@Slf4j
@Service
public class MallShopServiceImpl extends BaseService<MallShopDao,MallShop,MallShopQuery> implements IMallShopService {

    @Override
    public Page<MallShop> getByOrganizeId(Page<MallShop> page, Long organizeId) {
        return baseMapper.getByOrganizeId(page,organizeId);
    }

    @Override
    public Page<MallShop> getByItemNo(Page<MallShop> page, String itemNo) {
        return baseMapper.getByItemNo(page,itemNo);
    }
}
