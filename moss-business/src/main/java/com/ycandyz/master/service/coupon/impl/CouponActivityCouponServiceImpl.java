package com.ycandyz.master.service.coupon.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.coupon.CouponUserActivityCouponQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityCouponResp;
import com.ycandyz.master.domain.response.coupon.CouponDetailUserResp;
import com.ycandyz.master.entities.coupon.CouponActivityCoupon;
import com.ycandyz.master.domain.query.coupon.CouponActivityCouponQuery;
import com.ycandyz.master.dao.coupon.CouponActivityCouponDao;
import com.ycandyz.master.service.coupon.ICouponActivityCouponService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * @Description 发卷宝-优惠卷 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Slf4j
@Service
public class CouponActivityCouponServiceImpl extends BaseService<CouponActivityCouponDao, CouponActivityCoupon, CouponActivityCouponQuery> implements ICouponActivityCouponService {

    @Override
    public List<CouponActivityCouponResp> list(Long activityId) {
        return baseMapper.list(activityId);
    }

    @Override
    public Page<CouponActivityCouponResp> selectCouponPage(Page page, CouponActivityCouponQuery query) {
        return baseMapper.selectCouponPage(page,query);
    }

    @Override
    public Page<CouponActivityCouponResp> selectActivityCouponPage(Page page, CouponActivityCouponQuery query) {
        return baseMapper.selectActivityCouponPage(page,query);
    }

    @Override
    public Page<CouponDetailUserResp> selectUserActivityCouponPage(Page page, CouponUserActivityCouponQuery query) {
        return baseMapper.selectUserActivityCouponPage(page,query);
    }
}
