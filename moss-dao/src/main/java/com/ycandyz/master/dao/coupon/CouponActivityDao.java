package com.ycandyz.master.dao.coupon;

import com.ycandyz.master.entities.coupon.CouponActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 发卷宝 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 */
public interface CouponActivityDao extends BaseMapper<CouponActivity> {

    int updateStatusById(CouponActivity entity);

}
