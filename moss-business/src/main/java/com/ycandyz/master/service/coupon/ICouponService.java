package com.ycandyz.master.service.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.coupon.CouponDetailQuery;
import com.ycandyz.master.domain.query.coupon.CouponQuery;
import com.ycandyz.master.entities.coupon.Coupon;
import com.ycandyz.master.model.coupon.CouponDetailVO;

/**
 * <p>
 * @Description 优惠卷 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
public interface ICouponService extends IService<Coupon>{

    CommonResult<BasePageResult<CouponDetailVO>> selectPageList(RequestParams<CouponQuery> requestParams);

    CommonResult<String> auditState(Long id, Integer state);

    CommonResult<CouponDetailVO> ticketDetail(String ticketNo);

    CommonResult<String> saveTicket(CouponDetailQuery couponDetailQuery);
}
