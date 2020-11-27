package com.ycandyz.master.domain.response.coupon;

import com.ycandyz.master.entities.coupon.CouponTicketInfo;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 优惠券详情表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_ticket_info")
public class CouponTicketInfoResp extends CouponTicketInfo {

}
