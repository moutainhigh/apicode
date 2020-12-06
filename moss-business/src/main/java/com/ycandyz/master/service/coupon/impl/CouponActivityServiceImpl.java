package com.ycandyz.master.service.coupon.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.enums.coupon.CouponActivityEnum;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.model.coupon.CouponActivityPutModel;
import com.ycandyz.master.domain.query.coupon.CouponActivityTicketQuery;
import com.ycandyz.master.domain.query.coupon.CouponUserActivityTicketQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityResp;
import com.ycandyz.master.domain.response.coupon.CouponActivityTicketResp;
import com.ycandyz.master.domain.response.coupon.CouponUserTicketResp;
import com.ycandyz.master.entities.coupon.CouponActivity;
import com.ycandyz.master.domain.query.coupon.CouponActivityQuery;
import com.ycandyz.master.dao.coupon.CouponActivityDao;
import com.ycandyz.master.entities.coupon.CouponActivityTicket;
import com.ycandyz.master.service.coupon.ICouponActivityService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.DateUtils;
import com.ycandyz.master.utils.IDGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    public Page<CouponActivity> page(Page page, CouponActivityQuery query) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        query.setShopNo(getShopNo());
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .select(CouponActivity::getId,CouponActivity::getActivityNo,CouponActivity::getName,CouponActivity::getBeginTime,CouponActivity::getEndTime,
                        CouponActivity::getStatus,CouponActivity::getJoinType,CouponActivity::getActivityNum,CouponActivity::getCreateTime)
                .apply(null != query.getCreateTimeS(),
                        "date_format (created_time,'%Y-%m-%d') >= date_format('" + DateUtil.formatDate(query.getCreateTimeS()) + "','%Y-%m-%d')")
                .apply(null != query.getCreateTimeE(),
                        "date_format (created_time,'%Y-%m-%d') <= date_format('" + DateUtil.formatDate(query.getCreateTimeE())+ "','%Y-%m-%d')")
                .like(StrUtil.isNotEmpty(query.getName()),CouponActivity::getName,query.getName())
                .eq(CouponActivity::getShopNo, query.getShopNo())
                //.eq(CouponActivity::getStatus, SpecialEnum.EnabledEnum.DISABLE.getCode())
                .orderByDesc(CouponActivity::getCreateTime,CouponActivity::getCreateTime);
        Page<CouponActivity> p = (Page<CouponActivity>) baseMapper.selectPage(page, queryWrapper);
        p.getRecords().stream().forEach(f -> {
            if(CouponActivityEnum.Status.TYPE_0.getCode().equals(f.getStatus())){
                if(new Date().after(f.getBeginTime())){
                    f.setStatus(CouponActivityEnum.Status.TYPE_1.getCode());
                }
                else if(new Date().before(f.getEndTime())){
                    f.setStatus(CouponActivityEnum.Status.TYPE_3.getCode());
                }else{
                    f.setStatus(CouponActivityEnum.Status.TYPE_2.getCode());
                }
            }
        });
        return p;
    }

    @Override
    public CouponActivityResp selectById(Long id) {
        CouponActivity entity = baseMapper.selectById(id);
        if(null == entity){
            return null;
        }
        CouponActivityResp vo = new CouponActivityResp();
        BeanUtils.copyProperties(entity,vo);
        List<CouponActivityTicketResp> activityTicketList = couponActivityTicketService.list(entity.getActivityNo());
        vo.setActivityTicketList(activityTicketList);
        return vo;
    }

    @Override
    public boolean insert(CouponActivityModel entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        AssertUtils.notNull(entity.getTicketList(), "优惠卷不能为空");
        // TODO 校验时间折叠
        AssertUtils.isFalse(entity.getEndTime().before(new Date()),"截止时间不能小于当前时间");
        AssertUtils.isFalse(entity.getEndTime().before(entity.getBeginTime()),"截止时间不能大于开始时间");
        AssertUtils.isFalse(isEmpty(entity),"该时间区间存在已有活动,请更改时间区间");
        CouponActivity t = new CouponActivity();
        BeanUtils.copyProperties(entity,t);
        t.setCreateBy(getUserId());
        t.setUpdateBy(getUserId());
        t.setShopNo(getShopNo());
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
        AssertUtils.isFalse(entity.getEndTime().before(entity.getBeginTime()),"截止时间不能大于开始时间");
        AssertUtils.isFalse(isEmpty(entity),"该时间区间存在已有活动,请更改时间区间");
        LambdaQueryWrapper<CouponActivityTicket> queryWrapper = new LambdaQueryWrapper<CouponActivityTicket>()
                .eq(CouponActivityTicket::getActivityNo, entity.getActivityNo());
        couponActivityTicketService.remove(queryWrapper);
        CouponActivity t = new CouponActivity();
        BeanUtils.copyProperties(entity,t);
        t.setUpdateBy(getUserId());
        t.setShopNo(getShopNo());
        entity.getTicketList().forEach(i -> {
            CouponActivityTicket at = new CouponActivityTicket();
            at.setActivityNo(t.getActivityNo());
            at.setTicketNo(i);
            couponActivityTicketService.save(at);
        });
        return super.updateById(t);
    }

    private boolean isEmpty(CouponActivityModel model){
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .eq(CouponActivity::getShopNo, getShopNo())
                .and(obj -> obj.between(CouponActivity::getBeginTime, model.getBeginTime(),model.getEndTime())
                        .or(obj1 -> obj1.between(CouponActivity::getEndTime, model.getBeginTime(),model.getEndTime()))
                )
                .notIn(StrUtil.isNotEmpty(model.getActivityNo()),CouponActivity::getActivityNo, model.getActivityNo());
        List<CouponActivity> list = baseMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(list)){
            return false;
        }
        return true;
    }

    @Override
    public boolean switchById(Long id) {
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .eq(CouponActivity::getShopNo, getShopNo())
                .eq(CouponActivity::getId, id);
        CouponActivity entity = baseMapper.selectOne(queryWrapper);
        AssertUtils.notNull(entity, "未匹配到符合条件的记录");
        if(CouponActivityEnum.Status.TYPE_4.getCode().equals(entity.getStatus())){
            entity.setStatus(CouponActivityEnum.Status.TYPE_0.getCode());
        }else if (CouponActivityEnum.Status.TYPE_0.getCode().equals(entity.getStatus())){
            entity.setStatus(CouponActivityEnum.Status.TYPE_4.getCode());
        }else{
            AssertUtils.notNull(null, "操作失败，状态不正确");
        }
        return this.retBool(baseMapper.updateStatusById(entity));
    }

    @Override
    public boolean removeTicketById(Serializable id) {
        return couponActivityTicketService.removeById(id);
    }

    @Override
    public Page<CouponActivityTicketResp> selectTicketPage(Page page, CouponActivityTicketQuery query) {
        CouponActivityEnum.Type type = CouponActivityEnum.Type.parseCode(query.getType());
        AssertUtils.notNull(getShopNo(), "商店编号不存在");
        AssertUtils.notNull(query.getActivityNo(), "活动编号不能为空");
        AssertUtils.notNull(type, "类型不正确");
        query.setShopNo(getShopNo());
        if(type.getCode().equals(CouponActivityEnum.Type.TYPE_0.getCode())){
            return couponActivityTicketService.selectTicketPage(page,query);
        }
        return couponActivityTicketService.selectActivityTicketPage(page,query);
    }

    @Override
    public Page<CouponUserTicketResp> selectUserActivityTicketPage(Page page, CouponUserActivityTicketQuery query) {
        Page<CouponUserTicketResp> p = couponActivityTicketService.selectUserActivityTicketPage(page,query);
        p.getRecords().stream().forEach(f -> {
            if(f.getStatus() == 1){
                f.setStatusType(2);
            }else if(f.getEndTime().before(new Date())){
                f.setStatusType(1);
            }else{
                f.setStatusType(0);
            }
        });
        return p;
    }

}
