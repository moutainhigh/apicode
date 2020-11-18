package com.ycandyz.master.service.coupon;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.response.coupon.CouponActivityTicketResp;
import com.ycandyz.master.entities.coupon.CouponActivityTicket;

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
public interface ICouponActivityTicketService extends IService<CouponActivityTicket>{

    List<CouponActivityTicketResp> list(String activityNo);
}
