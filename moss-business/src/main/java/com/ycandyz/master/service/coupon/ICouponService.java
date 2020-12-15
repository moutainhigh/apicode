package com.ycandyz.master.service.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.query.coupon.CouponDetailQuery;
import com.ycandyz.master.domain.query.coupon.CouponQuery;
import com.ycandyz.master.entities.coupon.Coupon;
import com.ycandyz.master.model.coupon.CouponDetailVO;
import com.ycandyz.master.model.mall.MallCategoryVO;
import com.ycandyz.master.vo.MallItemVO;

import java.util.List;

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

    CommonResult<BasePageResult<CouponDetailVO>> selectPageList(PageModel pageModel, CouponQuery couponQuery);

    CommonResult<String> auditState(Long id, Integer state);

    CommonResult<CouponDetailVO> ticketDetail(Long id);

    CommonResult<String> insertTicket(CouponDetailQuery couponDetailQuery);

    CommonResult<String> updateTicket(CouponDetailQuery couponDetailQuery);

    CommonResult<List<MallCategoryVO>> getCategoryList();

    CommonResult<BasePageResult<MallItemVO>> itemList(Long id, Long page, Long pageSize, String type, String keyword, String category);
}