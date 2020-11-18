package com.ycandyz.master.service.coupon.impl;

import com.ycandyz.master.entities.coupon.CouponTicketInfoItem;
import com.ycandyz.master.domain.query.coupon.CouponTicketInfoItemQuery;
import com.ycandyz.master.dao.coupon.CouponTicketInfoItemDao;
import com.ycandyz.master.service.coupon.ICouponTicketInfoItemService;
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
public class CouponTicketInfoItemServiceImpl extends BaseService<CouponTicketInfoItemDao,CouponTicketInfoItem,CouponTicketInfoItemQuery> implements ICouponTicketInfoItemService {

}
