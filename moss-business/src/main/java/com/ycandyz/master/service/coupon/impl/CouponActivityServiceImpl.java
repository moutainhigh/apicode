package com.ycandyz.master.service.coupon.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.enums.coupon.CouponActivityEnum;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.model.coupon.CouponActivityPutModel;
import com.ycandyz.master.domain.query.coupon.CouponActivityCouponQuery;
import com.ycandyz.master.domain.query.coupon.CouponDetailUserQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityResp;
import com.ycandyz.master.domain.response.coupon.CouponActivityCouponResp;
import com.ycandyz.master.domain.response.coupon.CouponDetailUserResp;
import com.ycandyz.master.entities.coupon.CouponActivity;
import com.ycandyz.master.domain.query.coupon.CouponActivityQuery;
import com.ycandyz.master.dao.coupon.CouponActivityDao;
import com.ycandyz.master.entities.coupon.CouponActivityCoupon;
import com.ycandyz.master.service.coupon.ICouponActivityService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.AssertUtils;
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
    private CouponActivityCouponServiceImpl couponActivityCouponService;

    @Override
    public Page<CouponActivity> page(Page page, CouponActivityQuery query) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        query.setShopNo(getShopNo());
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .select(CouponActivity::getId,CouponActivity::getTitle,CouponActivity::getBeginTime,CouponActivity::getEndTime,CouponActivity::getUserType,
                        CouponActivity::getEnabled,CouponActivity::getInletName,CouponActivity::getActivityNum,CouponActivity::getCreateTime)
                .apply(null != query.getCreateTimeBegin(),
                        "date_format (create_time,'%Y-%m-%d') >= date_format('" + DateUtil.formatDate(query.getCreateTimeBegin()) + "','%Y-%m-%d')")
                .apply(null != query.getCreateTimeEnd(),
                        "date_format (create_time,'%Y-%m-%d') <= date_format('" + DateUtil.formatDate(query.getCreateTimeEnd())+ "','%Y-%m-%d')")
                .like(StrUtil.isNotEmpty(query.getTitle()),CouponActivity::getTitle,query.getTitle())
                .eq(CouponActivity::getShopNo, query.getShopNo())
                //.eq(CouponActivity::getStatus, SpecialEnum.EnabledEnum.DISABLE.getCode())
                .orderByDesc(CouponActivity::getCreateTime,CouponActivity::getCreateTime);
        Page<CouponActivity> p = (Page<CouponActivity>) baseMapper.selectPage(page, queryWrapper);
        p.getRecords().stream().forEach(f -> {
            if(CouponActivityEnum.Enabled.TYPE_1.getCode().equals(f.getEnabled())){
                if(new Date().after(f.getBeginTime())){
                    f.setStatusName(CouponActivityEnum.Status.TYPE_1.getText());
                }
                else if(new Date().before(f.getEndTime())){
                    f.setStatusName(CouponActivityEnum.Status.TYPE_3.getText());
                }else{
                    f.setStatusName(CouponActivityEnum.Status.TYPE_2.getText());
                }
            }else {
                f.setStatusName(CouponActivityEnum.Status.TYPE_4.getText());
            }
            f.setActivityRemainNum(f.getActivityNum());
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
        List<CouponActivityCouponResp> activityCouponList = couponActivityCouponService.list(entity.getId());
        vo.setActivityCouponList(activityCouponList);
        return vo;
    }

    @Override
    public boolean insert(CouponActivityModel entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        // TODO 校验时间折叠
        AssertUtils.isFalse(entity.getEndTime().before(new Date()),"截止时间不能小于当前时间");
        AssertUtils.isFalse(entity.getEndTime().before(entity.getBeginTime()),"截止时间不能大于开始时间");
        AssertUtils.isFalse(isEmpty(entity),"该时间区间存在已有活动,请更改时间区间");
        CouponActivity t = new CouponActivity();
        BeanUtils.copyProperties(entity,t);
        t.setCreateBy(getUserId());
        t.setUpdateBy(getUserId());
        t.setShopNo(getShopNo());
        boolean b = super.save(t);
        entity.getCouponIds().forEach(i -> {
            CouponActivityCoupon at = new CouponActivityCoupon();
            at.setActivityId(t.getId());
            at.setCouponId(i);
            couponActivityCouponService.save(at);
        });
        return b;
    }

    @Override
    public boolean update(CouponActivityModel entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        // TODO 校验时间折叠
        AssertUtils.isFalse(entity.getEndTime().before(entity.getBeginTime()),"截止时间不能大于开始时间");
        AssertUtils.isFalse(isEmpty(entity),"该时间区间存在已有活动,请更改时间区间");
        LambdaQueryWrapper<CouponActivityCoupon> queryWrapper = new LambdaQueryWrapper<CouponActivityCoupon>()
                .eq(CouponActivityCoupon::getActivityId, entity.getId());
        couponActivityCouponService.remove(queryWrapper);
        CouponActivity t = new CouponActivity();
        BeanUtils.copyProperties(entity,t);
        t.setUpdateBy(getUserId());
        t.setShopNo(getShopNo());
        entity.getCouponIds().forEach(i -> {
            CouponActivityCoupon at = new CouponActivityCoupon();
            at.setActivityId(t.getId());
            at.setCouponId(i);
            couponActivityCouponService.save(at);
        });
        return super.updateById(t);
    }

    private boolean isEmpty(CouponActivityModel model){
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .eq(CouponActivity::getShopNo, getShopNo())
                .and(obj -> obj.between(CouponActivity::getBeginTime, model.getBeginTime(),model.getEndTime())
                        .or(obj1 -> obj1.between(CouponActivity::getEndTime, model.getBeginTime(),model.getEndTime()))
                        .or(obj1 -> obj1.lt(CouponActivity::getBeginTime,model.getBeginTime()).gt(CouponActivity::getEndTime,model.getEndTime()))
                )
                .notIn(model.getId() != null ,CouponActivity::getId, model.getId());
        List<CouponActivity> list = baseMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(list)){
            return false;
        }
        return true;
    }

    @Override
    public boolean switchById(CouponActivityPutModel model) {
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .eq(CouponActivity::getShopNo, getShopNo())
                .eq(CouponActivity::getId, model.getId());
        CouponActivity entity = baseMapper.selectOne(queryWrapper);
        AssertUtils.notNull(entity, "未匹配到符合条件的记录");
        if(entity.getEnabled().equals(model.getEnabled())){
            AssertUtils.notNull(null, "操作失败，状态已更新");
        }
        return this.retBool(baseMapper.updateStatusById(model));
    }

    @Override
    public boolean removeCouponById(Serializable id) {
        return couponActivityCouponService.removeById(id);
    }

    @Override
    public Page<CouponActivityCouponResp> selectCouponPage(Page page, CouponActivityCouponQuery query) {
        CouponActivityEnum.Type type = CouponActivityEnum.Type.parseCode(query.getType());
        //AssertUtils.notNull(type, "类型不正确");
        AssertUtils.notNull(getShopNo(), "商店编号不存在");
        AssertUtils.notNull(query.getId(), "发卷宝ID不能为空");

        query.setShopNo(getShopNo());
        if(type.getCode().equals(CouponActivityEnum.Type.TYPE_0.getCode())){
            return couponActivityCouponService.selectCouponPage(page,query);
        }
        return couponActivityCouponService.selectActivityCouponPage(page,query);
    }

    @Override
    public Page<CouponDetailUserResp> selectUserActivityCouponPage(Page page, CouponDetailUserQuery query) {
        Page<CouponDetailUserResp> p = couponActivityCouponService.selectUserActivityCouponPage(page,query);
        if(p.getRecords() != null){
            p.getRecords().stream().forEach(f -> {
                if(f.getStatus() == 1){
                    f.setStatus(2);
                }else if(f.getEndTime().before(new Date())){
                    f.setStatus(1);
                }else{
                    f.setStatus(0);
                }
            });
        }
        return p;
    }

}
