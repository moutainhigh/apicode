package com.ycandyz.master.dto.coupon;

import com.ycandyz.master.entities.coupon.CouponTicketInfoItem;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 优惠券详情关联商品表 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_ticket_info_item")
public class CouponTicketInfoItemDTO extends CouponTicketInfoItem {

}
