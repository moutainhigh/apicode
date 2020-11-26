package com.ycandyz.master.service.coupon.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.response.coupon.CouponActivityTicketResp;
import com.ycandyz.master.entities.coupon.CouponActivityTicket;
import com.ycandyz.master.domain.query.coupon.CouponActivityTicketQuery;
import com.ycandyz.master.dao.coupon.CouponActivityTicketDao;
import com.ycandyz.master.service.coupon.ICouponActivityTicketService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * @Description 发卷宝-优惠卷 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Slf4j
@Service
public class CouponActivityTicketServiceImpl extends BaseService<CouponActivityTicketDao,CouponActivityTicket,CouponActivityTicketQuery> implements ICouponActivityTicketService {

    @Override
    public List<CouponActivityTicketResp> list(String activityNo) {
        return baseMapper.list(activityNo);
    }

    @Override
    public Page<CouponActivityTicketResp> selectTicketPage(Page page, CouponActivityTicketQuery query) {
        Page<CouponActivityTicketResp> p = baseMapper.selectTicketPage(page,query);
        p.getRecords().stream().forEach(f -> {
            if (f.getBeginAt() != null && f.getBeginAt() != 0){
                f.setBeginTime(DateUtils.getCurrentDate(f.getBeginAt()));
            }
            if (f.getEndAt() != null && f.getEndAt() != 0){
                f.setEndTime(DateUtils.getCurrentDate(f.getEndAt()));
            }
        });
        return p;
    }

    @Override
    public Page<CouponActivityTicketResp> selectActivityTicketPage(Page page, CouponActivityTicketQuery query) {
        Page<CouponActivityTicketResp> p = baseMapper.selectActivityTicketPage(page,query);
        p.getRecords().stream().forEach(f -> {
            if (f.getBeginAt() != null && f.getBeginAt() != 0){
                f.setBeginTime(DateUtils.getCurrentDate(f.getBeginAt()));
            }
            if (f.getEndAt() != null && f.getEndAt() != 0){
                f.setEndTime(DateUtils.getCurrentDate(f.getEndAt()));
            }
        });
        return p;
    }
}
