package com.ycandyz.master.domain.response.coupon;

import com.ycandyz.master.entities.coupon.CouponTicket;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 优惠卷 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_ticket")
public class CouponTicketResp extends CouponTicket {

}
