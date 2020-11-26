package com.ycandyz.master.service.coupon.impl;

import com.ycandyz.master.entities.coupon.CouponActivity;
import com.ycandyz.master.domain.query.coupon.CouponActivityQuery;
import com.ycandyz.master.dao.coupon.CouponActivityDao;
import com.ycandyz.master.service.coupon.ICouponActivityService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 发卷宝 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Slf4j
@Service
public class CouponActivityServiceImpl extends BaseService<CouponActivityDao,CouponActivity,CouponActivityQuery> implements ICouponActivityService {

}
