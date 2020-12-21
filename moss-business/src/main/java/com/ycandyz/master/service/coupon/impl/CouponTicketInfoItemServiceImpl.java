package com.ycandyz.master.service.coupon.impl;

import com.ycandyz.master.dao.coupon.CouponDetailItemDao;
import com.ycandyz.master.domain.query.coupon.CouponDetailItemQuery;
import com.ycandyz.master.entities.coupon.CouponDetailItem;
import com.ycandyz.master.service.coupon.ICouponDetailItemService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 优惠券详情关联商品表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Slf4j
@Service
public class CouponTicketInfoItemServiceImpl extends BaseService<CouponDetailItemDao,CouponDetailItem,CouponDetailItemQuery> implements ICouponDetailItemService {

}
