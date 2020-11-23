package com.ycandyz.master.service.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.query.coupon.CouponActivityTicketQuery;
import com.ycandyz.master.domain.query.coupon.CouponTicketQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityResp;
import com.ycandyz.master.domain.response.coupon.CouponActivityTicketResp;
import com.ycandyz.master.domain.response.coupon.CouponTicketResp;
import com.ycandyz.master.entities.coupon.CouponActivity;
import com.ycandyz.master.model.ad.SpecialModel;
import com.ycandyz.master.utils.QueryUtil;

import java.io.Serializable;

/**
 * <p>
 * @Description 发卷宝 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
public interface ICouponActivityService extends IService<CouponActivity>{

    CouponActivityResp selectById(Long id);

    boolean insert(CouponActivityModel entity);

    boolean update(CouponActivityModel entity);

    boolean stopById(Long id);

    boolean startById(Long id);

    boolean removeTicketById(Serializable id);

    Page<CouponActivityTicketResp> selectTicketPage(Page page, CouponActivityTicketQuery query);

    Page<CouponActivityTicketResp> selectActivityTicketPage(Page page, CouponActivityTicketQuery query);
}
