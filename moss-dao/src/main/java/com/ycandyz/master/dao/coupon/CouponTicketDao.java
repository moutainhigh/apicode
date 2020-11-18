package com.ycandyz.master.dao.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.coupon.CouponTicketQuery;
import com.ycandyz.master.dto.coupon.CouponTicketDTO;
import com.ycandyz.master.dto.coupon.CouponTicketInfoDTO;
import com.ycandyz.master.entities.coupon.CouponTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠卷 Mapper 接口
 * </p>
 *
 * @since 2020-11-17
 */
public interface CouponTicketDao extends BaseMapper<CouponTicket> {

    Page<CouponTicketInfoDTO> queryTicketPageList(Page page, @Param("p") CouponTicketQuery couponTicketQuery);

    CouponTicketInfoDTO queryTicketDetailByTicketNo(@Param("ticketNo") String ticketNo, @Param("shopNo") String shopNo);
}
