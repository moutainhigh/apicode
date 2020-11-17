package com.ycandyz.master.service.coupon.impl;

import com.ycandyz.master.entities.coupon.CouponTicketInfo;
import com.ycandyz.master.domain.query.coupon.CouponTicketInfoQuery;
import com.ycandyz.master.dao.coupon.CouponTicketInfoDao;
import com.ycandyz.master.service.coupon.ICouponTicketInfoService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 优惠券详情表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Slf4j
@Service
public class CouponTicketInfoServiceImpl extends BaseService<CouponTicketInfoDao,CouponTicketInfo,CouponTicketInfoQuery> implements ICouponTicketInfoService {

}
