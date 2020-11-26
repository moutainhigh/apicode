package com.ycandyz.master.dto.coupon;

import com.ycandyz.master.entities.coupon.CouponActivityTicket;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 发卷宝-优惠卷 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_activity_ticket")
public class CouponActivityTicketDTO extends CouponActivityTicket {

}
