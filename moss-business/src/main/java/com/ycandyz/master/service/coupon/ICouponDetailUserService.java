package com.ycandyz.master.service.coupon;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.coupon.CouponDetailUser;
import com.ycandyz.master.domain.model.coupon.CouponDetailUserModel;

/**
 * <p>
 * @Description 用户领取优惠券记录表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-18
 * @version 2.0
 */
public interface ICouponDetailUserService extends IService<CouponDetailUser>{

    boolean insert(CouponDetailUserModel model);

    boolean update(CouponDetailUserModel model);
}
