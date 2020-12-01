package com.ycandyz.master.dao.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.coupon.CouponActivityTicketQuery;
import com.ycandyz.master.domain.query.coupon.CouponUserActivityTicketQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityTicketResp;
import com.ycandyz.master.domain.response.coupon.CouponUserTicketResp;
import com.ycandyz.master.entities.coupon.CouponActivityTicket;
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
public interface CouponActivityTicketDao extends BaseMapper<CouponActivityTicket> {

    List<CouponActivityTicketResp> list(String activityNo);

    Page<CouponActivityTicketResp> selectTicketPage(Page page, @Param("q") CouponActivityTicketQuery query);

    Page<CouponUserTicketResp> selectUserActivityTicketPage(Page page, @Param("q") CouponUserActivityTicketQuery query);

    Page<CouponActivityTicketResp> selectActivityTicketPage(Page page, @Param("q") CouponActivityTicketQuery query);

}
