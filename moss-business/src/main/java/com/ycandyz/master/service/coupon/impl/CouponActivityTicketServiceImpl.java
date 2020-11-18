package com.ycandyz.master.service.coupon.impl;

import com.ycandyz.master.domain.response.coupon.CouponActivityTicketResp;
import com.ycandyz.master.entities.coupon.CouponActivityTicket;
import com.ycandyz.master.domain.query.coupon.CouponActivityTicketQuery;
import com.ycandyz.master.dao.coupon.CouponActivityTicketDao;
import com.ycandyz.master.service.coupon.ICouponActivityTicketService;
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
public class CouponActivityTicketServiceImpl extends BaseService<CouponActivityTicketDao,CouponActivityTicket,CouponActivityTicketQuery> implements ICouponActivityTicketService {

    @Override
    public List<CouponActivityTicketResp> list(String activityNo) {
        return baseMapper.list(activityNo);
    }
}
