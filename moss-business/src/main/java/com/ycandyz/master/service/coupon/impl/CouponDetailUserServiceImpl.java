package com.ycandyz.master.service.coupon.impl;

import com.ycandyz.master.entities.coupon.CouponDetailUser;
import com.ycandyz.master.domain.model.coupon.CouponDetailUserModel;
import com.ycandyz.master.domain.query.coupon.CouponDetailUserQuery;
import com.ycandyz.master.dao.coupon.CouponDetailUserDao;
import com.ycandyz.master.service.coupon.ICouponDetailUserService;
import com.ycandyz.master.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * @Description 用户领取优惠券记录表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-18
 * @version 2.0
 */
@Slf4j
@Service
public class CouponDetailUserServiceImpl extends BaseService<CouponDetailUserDao,CouponDetailUser,CouponDetailUserQuery> implements ICouponDetailUserService {

    @Override
    public boolean insert(CouponDetailUserModel model) {
        CouponDetailUser t = new CouponDetailUser();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(CouponDetailUserModel model) {
        CouponDetailUser t = new CouponDetailUser();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }


}
