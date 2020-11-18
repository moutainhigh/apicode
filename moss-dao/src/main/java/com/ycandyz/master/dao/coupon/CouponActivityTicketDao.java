package com.ycandyz.master.dao.coupon;

import com.ycandyz.master.domain.response.coupon.CouponActivityTicketResp;
import com.ycandyz.master.entities.coupon.CouponActivityTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 发卷宝-优惠卷 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 */
public interface CouponActivityTicketDao extends BaseMapper<CouponActivityTicket> {

    List<CouponActivityTicketResp> list(String activityNo);
}
