package com.ycandyz.master.service.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.model.coupon.CouponActivityPutModel;
import com.ycandyz.master.domain.query.coupon.CouponActivityCouponQuery;
import com.ycandyz.master.domain.query.coupon.CouponDetailUserQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityResp;
import com.ycandyz.master.domain.response.coupon.CouponActivityCouponResp;
import com.ycandyz.master.domain.response.coupon.CouponDetailUserResp;
import com.ycandyz.master.entities.coupon.CouponActivity;

import java.io.Serializable;

/**
 * <p>
 * @Description 发卷宝 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
public interface ICouponActivityService extends IService<CouponActivity>{

    CouponActivityResp selectById(Long id);

    boolean insert(CouponActivityModel entity);

    boolean update(CouponActivityModel entity);

    boolean switchById(CouponActivityPutModel model);

    boolean removeCouponById(Serializable id);

    Page<CouponActivityCouponResp> selectCouponPage(Page page, CouponActivityCouponQuery query);

    Page<CouponDetailUserResp> selectUserActivityCouponPage(Page page, CouponDetailUserQuery query);
}
