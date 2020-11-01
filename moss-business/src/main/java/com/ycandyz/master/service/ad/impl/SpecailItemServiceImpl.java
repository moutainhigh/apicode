package com.ycandyz.master.service.ad.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.ad.SpecailItemDao;
import com.ycandyz.master.domain.query.ad.SpecailItemQuery;
import com.ycandyz.master.entities.ad.SpecailItem;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.service.ad.ISpecailItemService;
import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * @Description 专题-商品表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Slf4j
@Service
public class SpecailItemServiceImpl extends BaseService<SpecailItemDao,SpecailItem,SpecailItemQuery> implements ISpecailItemService {

    @Override
    public Page<MallItem> selectHomePage(Page page, SpecailItemQuery query) {
        AssertUtils.notNull(query.getShopNo(), "商店编号不能为空");
        AssertUtils.notNull(query.getSpecialNo(), "专题编号不能为空");
        return baseMapper.selectHomePage(page, query);
    }

    @Override
    public List<MallItem> selectList(SpecailItemQuery query) {
        AssertUtils.notNull(query.getShopNo(), "商店编号不能为空");
        AssertUtils.notNull(query.getSpecialNo(), "专题编号不能为空");
        return baseMapper.selectList(query);
    }

    @Override
    public List<String> selectItemNoList(SpecailItemQuery query) {
        AssertUtils.notNull(query.getShopNo(), "商店编号不能为空");
        AssertUtils.notNull(query.getSpecialNo(), "专题编号不能为空");
        return baseMapper.selectItemNoList(query);
    }
}
