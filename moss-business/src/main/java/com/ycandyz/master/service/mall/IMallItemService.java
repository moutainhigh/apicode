package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.model.coupon.CouponActivityPutModel;
import com.ycandyz.master.domain.model.mall.*;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityResp;
import com.ycandyz.master.domain.response.mall.MallItemBaseResp;
import com.ycandyz.master.domain.response.mall.MallItemPageResp;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.domain.response.mall.MallItemShareResp;
import com.ycandyz.master.entities.mall.MallItem;

import java.util.List;

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

    CommonResult deleteByItemNo(String itemNo);

    Page<MallItemPageResp> getMallItemPage(Page<MallItem> page, MallItemQuery query);

    CommonResult insert(MallItemModel model);

    CommonResult update(MallItemModel model);

    CommonResult shelf(MallItemShelfModel model);

    boolean addOrganize(MallItemOrgModel model);

    MallItemShareResp getShareByItemNo(String itemNo);

    boolean updateShareByItemNo(MallItemShareModel model);

    MallItem getOneDetailByItemNo(String itemNo);

    List<MallItem> getListByItemNos(List<String> itemNos);

    boolean edit(MallItemDetailModel model);
	
}
