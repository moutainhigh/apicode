package com.ycandyz.master.dao.coupon;

import com.ycandyz.master.domain.response.mall.MallItemBaseResp;
import com.ycandyz.master.entities.coupon.CouponDetailItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券详情关联商品表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 */
public interface CouponDetailItemDao extends BaseMapper<CouponDetailItem> {

    List<MallItemBaseResp> queryByCouponDetailId(@Param("couponDetailId") Long couponDetailId);
}
