package com.ycandyz.master.dao.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.coupon.CouponActivityCouponQuery;
import com.ycandyz.master.domain.query.coupon.CouponDetailUserQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityCouponResp;
import com.ycandyz.master.domain.response.coupon.CouponDetailUserResp;
import com.ycandyz.master.entities.coupon.CouponActivityCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 发卷宝-优惠卷 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 */
public interface CouponActivityCouponDao extends BaseMapper<CouponActivityCoupon> {

    List<CouponActivityCouponResp> list(Long activityId);

    Page<CouponActivityCouponResp> selectCouponPage(Page page, @Param("q") CouponActivityCouponQuery query);

    Page<CouponDetailUserResp> selectUserActivityCouponPage(Page page, @Param("q") CouponDetailUserQuery query);

    Page<CouponActivityCouponResp> selectActivityCouponPage(Page page, @Param("q") CouponActivityCouponQuery query);

}
