package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.domain.query.mall.MallShopQuery;
import com.ycandyz.master.dao.mall.MallShopDao;
import com.ycandyz.master.service.mall.IMallShopService;
import com.ycandyz.master.controller.base.BaseService;

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

}
