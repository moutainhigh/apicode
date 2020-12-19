package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.model.coupon.CouponActivityPutModel;
import com.ycandyz.master.domain.model.mall.MallItemModel;
import com.ycandyz.master.domain.model.mall.MallItemShelfModel;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityResp;
import com.ycandyz.master.domain.response.mall.MallItemBaseResp;
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

    MallItemResp getByItemNo(String itemNo);

    boolean deleteByItemNo(String itemNo);

    Page<MallItemResp> getMallItemPage(Page<MallItem> page, MallItemQuery query);

    boolean insert(MallItemModel entity);

    boolean update(MallItemModel entity);

    boolean shelf(MallItemShelfModel model);
	
}
