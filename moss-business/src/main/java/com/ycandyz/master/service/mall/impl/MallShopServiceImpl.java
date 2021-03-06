package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.domain.enums.mall.MallItemOriganizeEnum;
import com.ycandyz.master.domain.response.mall.MallShopResp;
import com.ycandyz.master.entities.mall.MallItemOrganize;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.domain.query.mall.MallShopQuery;
import com.ycandyz.master.dao.mall.MallShopDao;
import com.ycandyz.master.entities.mall.MallSku;
import com.ycandyz.master.entities.organize.Organize;
import com.ycandyz.master.entities.organize.OrganizeRel;
import com.ycandyz.master.service.mall.IMallShopService;

import com.ycandyz.master.service.organize.impl.OrganizeRelServiceImpl;
import com.ycandyz.master.service.organize.impl.OrganizeServiceImpl;
import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MallItemOrganizeServiceImpl mallItemOrganizeService;

    @Autowired
    private OrganizeRelServiceImpl organizeRelService;
    @Autowired
    private OrganizeServiceImpl organizeService;

    @Override
    public Page<MallShopResp> getByOrganizeId(Page<MallShop> page) {
        Organize o = organizeService.getById(getOrganizeId());
        if(o == null || o.getIsGroup() == 0){
            AssertUtils.isTrue(false, "该账号不是集团账号，不能执行此操作");
        }
        return baseMapper.getByOrganizeId(page,getOrganizeId());
    }

    @Override
    public Page<MallShopResp> getByItemNo(Page<MallShop> page, String itemNo) {
        if(StrUtil.isEmpty(itemNo)){
            return getByOrganizeId(page);
        }
        //校验集团供货商品，切换商品编号
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(itemNo);
        //AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "该商品是集团供货商品，不可执行此操作");
        itemNo = mio.getOrganizeItemNo();
        return baseMapper.getByItemNo(page,itemNo);
    }
}
