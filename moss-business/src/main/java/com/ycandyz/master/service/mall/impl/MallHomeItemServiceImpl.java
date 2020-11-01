package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.MallHomeItemDao;
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
public class MallHomeItemServiceImpl extends BaseService<MallHomeItemDao, MallItem,MallItemQuery> implements IMallItemService {

    @Override
    public Page<MallItemResp> selectMallItemPage(Page<MallItem> page, MallItemQuery query) {
        if(StrUtil.isNotEmpty(query.getItemName()) && StrUtil.isNotEmpty(query.getItemName().trim())){
            query.setItemName(query.getItemName().trim());
        }else{
            query.setItemName(null);
        }
        Page<MallItemResp> p =baseMapper.selectMallItemPage(page,query);
        return p;
    }
}
