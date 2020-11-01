package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.entities.mall.MallItem;

/**
 * <p>
 * @Description 商品表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
public interface IMallItemService extends IService<MallItem>{

    Page<MallItemResp> selectMallItemPage(Page<MallItem> page, MallItemQuery query);
	
}
