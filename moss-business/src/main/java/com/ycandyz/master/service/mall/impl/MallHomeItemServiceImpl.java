package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.MallHomeItemDao;
import com.ycandyz.master.domain.model.mall.MallItemModel;
import com.ycandyz.master.domain.model.mall.MallItemShelfModel;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.service.mall.IMallItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 商品表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Service
public class MallHomeItemServiceImpl extends BaseService<MallHomeItemDao, MallItem, MallItemBaseQuery> implements IMallItemService {

    @Override
    public MallItemResp getByItemNo(String itemNo) {
        return null;
    }

    @Override
    public boolean deleteByItemNo(String itemNo) {
        return false;
    }

    @Override
    public Page<MallItemResp> getMallItemPage(Page<MallItem> page, MallItemQuery query) {
        if(StrUtil.isNotEmpty(query.getItemName()) && StrUtil.isNotEmpty(query.getItemName().trim())){
            if(query.getItemName().contains("%")){
                String itemName = query.getItemName().replace("%","\\%");
                query.setItemName(itemName);
            }else{
                query.setItemName(query.getItemName().trim());
            }
        }else{
            query.setItemName(null);
        }
        Page<MallItemResp> p =baseMapper.selectMallItemPage(page,query);
        return p;
    }

    @Override
    public boolean insert(MallItemModel entity) {
        return false;
    }

    @Override
    public boolean update(MallItemModel entity) {
        return false;
    }

    @Override
    public boolean shelf(MallItemShelfModel model) {
        return false;
    }
}
