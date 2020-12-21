package com.ycandyz.master.service.coupon.impl;

import com.ycandyz.master.dao.coupon.CouponDetailDao;
import com.ycandyz.master.domain.query.coupon.CouponDetailQuery;
import com.ycandyz.master.entities.coupon.CouponDetail;
import com.ycandyz.master.service.coupon.ICouponDetailService;
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
public class CouponTicketInfoServiceImpl extends BaseService<CouponDetailDao,CouponDetail,CouponDetailQuery> implements ICouponDetailService {

}
