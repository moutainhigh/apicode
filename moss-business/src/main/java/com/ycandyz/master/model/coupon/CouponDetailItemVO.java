package com.ycandyz.master.model.coupon;

import com.ycandyz.master.entities.coupon.CouponDetailItem;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 优惠券详情关联商品表 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_detail_item")
public class CouponDetailItemVO extends CouponDetailItem {

}
