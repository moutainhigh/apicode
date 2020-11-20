package com.ycandyz.master.service.coupon.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ycandyz.master.domain.enums.coupon.CouponActivityEnum;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.response.coupon.CouponActivityResp;
import com.ycandyz.master.entities.coupon.CouponActivity;
import com.ycandyz.master.domain.query.coupon.CouponActivityQuery;
import com.ycandyz.master.dao.coupon.CouponActivityDao;
import com.ycandyz.master.entities.coupon.CouponActivityTicket;
import com.ycandyz.master.service.coupon.ICouponActivityService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.IDGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * @Description 发卷宝 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Slf4j
@Service
public class CouponActivityServiceImpl extends BaseService<CouponActivityDao,CouponActivity,CouponActivityQuery> implements ICouponActivityService {

    @Autowired
    private CouponActivityTicketServiceImpl couponActivityTicketService;


    @Override
    public CouponActivityResp selectById(Long id) {
        CouponActivity entity = baseMapper.selectById(id);
        if(null == entity){
            return null;
        }
        CouponActivityResp vo = new CouponActivityResp();
        BeanUtils.copyProperties(entity,vo);
        vo.setActivityTicketList(couponActivityTicketService.list(entity.getActivityNo()));
        return vo;
    }

    @Override
    public boolean insert(CouponActivityModel entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        AssertUtils.notNull(entity.getTicketList(), "优惠卷不能为空");
        // TODO 校验时间折叠
        AssertUtils.isFalse(isEmpty(entity.getBeginAt(),entity.getEndAt(),null),"该时间区间存在已有活动,请更改时间区间");
        CouponActivity t = new CouponActivity();
        BeanUtils.copyProperties(entity,t);
        t.setActivityNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
        entity.getTicketList().forEach(i -> {
            CouponActivityTicket at = new CouponActivityTicket();
            at.setActivityNo(t.getActivityNo());
            at.setTicketNo(i);
            couponActivityTicketService.save(at);
        });
        return super.save(t);
    }

    @Override
    public boolean update(CouponActivityModel entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        AssertUtils.notNull(entity.getTicketList(), "优惠卷不能为空");
        // TODO 校验时间折叠
        AssertUtils.isFalse(isEmpty(entity.getBeginAt(),entity.getEndAt(),entity.getActivityNo()),"该时间区间存在已有活动,请更改时间区间");
        LambdaQueryWrapper<CouponActivityTicket> queryWrapper = new LambdaQueryWrapper<CouponActivityTicket>()
                .eq(CouponActivityTicket::getActivityNo, entity.getActivityNo());
        couponActivityTicketService.remove(queryWrapper);
        CouponActivity t = new CouponActivity();
        BeanUtils.copyProperties(entity,t);
        entity.getTicketList().forEach(i -> {
            CouponActivityTicket at = new CouponActivityTicket();
            at.setActivityNo(t.getActivityNo());
            at.setTicketNo(i);
            couponActivityTicketService.save(at);
        });
        return super.updateById(t);
    }

    private boolean isEmpty(Long beginAt,Long endAt,String activityNo){
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .eq(CouponActivity::getShopNo, getShopNo())
                .ge(CouponActivity::getBeginAt, beginAt)
                .gt(CouponActivity::getEndAt, endAt)
                .notIn(StrUtil.isNotEmpty(activityNo),CouponActivity::getActivityNo, activityNo);
        CouponActivity couponActivity = baseMapper.selectOne(queryWrapper);
        if(null == couponActivity){
            return false;
        }
        return true;
    }

    @Override
    public boolean stopById(Long id) {
        return this.retBool(baseMapper.updateStatusById(id, CouponActivityEnum.Status.TYPE_0.getCode()));
    }

    @Override
    public boolean startById(Long id) {
        return this.retBool(baseMapper.updateStatusById(id, CouponActivityEnum.Status.TYPE_4.getCode()));
    }

    @Override
    public boolean removeTicketById(Serializable id) {
        return couponActivityTicketService.removeById(id);
    }
}
