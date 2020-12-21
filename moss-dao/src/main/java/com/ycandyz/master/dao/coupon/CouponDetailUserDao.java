package com.ycandyz.master.dao.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.coupon.CouponUseUserQuery;
import com.ycandyz.master.dto.coupon.CouponUseUserDTO;
import com.ycandyz.master.entities.coupon.CouponDetailUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户领取优惠券记录表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-12-18
 */
public interface CouponDetailUserDao extends BaseMapper<CouponDetailUser> {

    Page<CouponUseUserDTO> selectTakeUserCouponList(Page page, @Param("q") CouponUseUserQuery couponUseUserQuery);

    Page<CouponUseUserDTO> selectUseUserCouponList(Page page, @Param("q") CouponUseUserQuery couponUseUserQuery);
}
