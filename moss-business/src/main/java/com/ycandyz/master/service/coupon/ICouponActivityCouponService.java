package com.ycandyz.master.service.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.query.coupon.CouponActivityCouponQuery;
import com.ycandyz.master.domain.query.coupon.CouponDetailUserQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityCouponResp;
import com.ycandyz.master.domain.response.coupon.CouponDetailUserResp;
import com.ycandyz.master.entities.coupon.CouponActivityCoupon;

import java.util.List;

/**
 * <p>
 * @Description 发卷宝-优惠卷 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
public interface ICouponActivityCouponService extends IService<CouponActivityCoupon>{

    List<CouponActivityCouponResp> list(Long activityId);

    Page<CouponActivityCouponResp> selectCouponPage(Page page, CouponActivityCouponQuery query);

    Page<CouponActivityCouponResp> selectActivityCouponPage(Page page, CouponActivityCouponQuery query);

    Page<CouponDetailUserResp> selectUserActivityCouponPage(Page page, CouponDetailUserQuery query);
}
