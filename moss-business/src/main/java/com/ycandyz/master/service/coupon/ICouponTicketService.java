package com.ycandyz.master.service.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.coupon.CouponTicketInfoQuery;
import com.ycandyz.master.domain.query.coupon.CouponTicketQuery;
import com.ycandyz.master.entities.coupon.CouponTicket;
import com.ycandyz.master.model.coupon.CouponTicketInfoVO;
import com.ycandyz.master.model.coupon.CouponTicketVO;

/**
 * <p>
 * @Description 优惠卷 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
public interface ICouponTicketService extends IService<CouponTicket>{

    ReturnResponse<Page<CouponTicketInfoVO>> selectPageList(RequestParams<CouponTicketQuery> requestParams, UserVO userVO);

    ReturnResponse<String> auditState(Long id, Integer state, UserVO userVO);

    ReturnResponse<CouponTicketInfoVO> ticketDetail(String ticketNo, UserVO userVO);

    ReturnResponse<String> updateTicket(CouponTicketInfoQuery couponTicketInfoQuery);
}
