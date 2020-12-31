package com.ycandyz.master.service.coupon.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.enums.coupon.CouponActivityEnum;
import com.ycandyz.master.domain.enums.coupon.CouponActivityUserEnum;
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
import com.ycandyz.master.entities.coupon.CouponDetailUser;
import com.ycandyz.master.service.coupon.ICouponActivityService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private CouponDetailUserServiceImpl couponDetailUserService;

    @Override
    public Page<CouponActivity> page(Page page, CouponActivityQuery query) {
        query.setCreateTimeBegin(DateUtils.toZeroZoneTime(query.getCreateTimeBegin()));
        query.setCreateTimeEnd(DateUtils.toZeroZoneTime(query.getCreateTimeEnd()));

        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        query.setShopNo(getShopNo());
        if(StrUtil.isNotEmpty(query.getTitle()) && StrUtil.isNotEmpty(query.getTitle().trim())){
            if(query.getTitle().contains("%")){
                String itemName = query.getTitle().replace("%","\\%");
                query.setTitle(itemName.trim());
            }else{
                query.setTitle(query.getTitle().trim());
            }
        }else{
            query.setTitle(null);
        }
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .select(CouponActivity::getId,CouponActivity::getTitle,CouponActivity::getBeginTime,CouponActivity::getEndTime,CouponActivity::getUserType,
                        CouponActivity::getEnabled,CouponActivity::getInletName,CouponActivity::getMaxLimit,CouponActivity::getCreateTime)
                .apply(null != query.getCreateTimeBegin(),
                        "date_format (create_time,'%Y-%m-%d') >= date_format('" + DateUtil.formatDate(query.getCreateTimeBegin()) + "','%Y-%m-%d')")
                .apply(null != query.getCreateTimeEnd(),
                        "date_format (create_time,'%Y-%m-%d') <= date_format('" + DateUtil.formatDate(query.getCreateTimeEnd())+ "','%Y-%m-%d')")
                .like(StrUtil.isNotEmpty(query.getTitle()),CouponActivity::getTitle,query.getTitle())
                .eq(CouponActivity::getShopNo, query.getShopNo())
                //.eq(CouponActivity::getStatus, SpecialEnum.EnabledEnum.DISABLE.getCode())
                .orderByDesc(CouponActivity::getCreateTime);
        Page<CouponActivity> p = (Page<CouponActivity>) baseMapper.selectPage(page, queryWrapper);
        p.getRecords().stream().forEach(f -> {
            if(CouponActivityEnum.Enabled.TYPE_1.getCode().equals(f.getEnabled())){
                if(new Date().before(f.getBeginTime())){
                    f.setStatus(CouponActivityEnum.Status.TYPE_1.getCode());
                    f.setStatusName(CouponActivityEnum.Status.TYPE_1.getText());
                }
                else if(new Date().after(f.getEndTime())){
                    f.setStatus(CouponActivityEnum.Status.TYPE_3.getCode());
                    f.setStatusName(CouponActivityEnum.Status.TYPE_3.getText());
                }else{
                    f.setStatus(CouponActivityEnum.Status.TYPE_2.getCode());
                    f.setStatusName(CouponActivityEnum.Status.TYPE_2.getText());
                }
            }else {
                f.setStatus(CouponActivityEnum.Status.TYPE_4.getCode());
                f.setStatusName(CouponActivityEnum.Status.TYPE_4.getText());
            }
            //获取活动参与人数
            LambdaQueryWrapper<CouponDetailUser> countWrapper = new LambdaQueryWrapper<CouponDetailUser>()
                    .eq(CouponDetailUser::getActivityId, f.getId());
            Integer c = couponDetailUserService.count(countWrapper);
            f.setActivityNum(c);
            f.setActivityRemainNum(f.getMaxLimit()-c);
            CouponActivityEnum.UserType userType = CouponActivityEnum.UserType.parseCode(f.getUserType());
            f.setUserTypeName(userType.getText());
        });
        p.setRecords(p.getRecords().stream().sorted(Comparator.comparing(CouponActivity::getStatus)).collect(Collectors.toList()));
        return p;
    }

    @Override
    public CouponActivityResp selectById(Long id) {
        CouponActivity entity = baseMapper.selectById(id);
        if(null == entity || !entity.getShopNo().equals(getShopNo())){
            return null;
        }
        CouponActivityResp vo = new CouponActivityResp();
        BeanUtils.copyProperties(entity,vo);
        List<CouponActivityCouponResp> activityCouponList = baseMapper.list(entity.getId());
        vo.setActivityCouponList(activityCouponList);
        List<Long> couponIds = activityCouponList.stream().map(CouponActivityCouponResp::getId).collect(Collectors.toList());
        vo.setCouponIds(couponIds);
        return vo;
    }

    @Override
    public boolean insert(CouponActivityModel entity) {
//        entity.setBeginTime(DateUtils.toZeroZoneTime(entity.getBeginTime()));
//        entity.setEndTime(DateUtils.toZeroZoneTime(entity.getEndTime()));
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        // TODO 校验时间折叠
        AssertUtils.isFalse(entity.getEndTime().before(new Date()),"截止时间不能小于当前时间");
        AssertUtils.isFalse(entity.getEndTime().before(entity.getBeginTime()),"截止时间不能大于开始时间");
        CouponActivity couponActivity = isEmpty(entity);
        if(couponActivity != null){
            AssertUtils.isFalse(true,"该活动时间与"+couponActivity.getTitle()+"活动时间重叠，请重新选择时间");
        }
        CouponActivity t = new CouponActivity();
        BeanUtils.copyProperties(entity,t);
        t.setCreateBy(getUserId());
        t.setUpdateBy(getUserId());
        t.setShopNo(getShopNo());
        t.setBeginTime(DateUtils.toZeroZoneTime(t.getBeginTime()));
        t.setEndTime(DateUtils.toZeroZoneTime(t.getEndTime()));
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
        entity.setBeginTime(DateUtils.toZeroZoneTime(entity.getBeginTime()));
        entity.setEndTime(DateUtils.toZeroZoneTime(entity.getEndTime()));
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        CouponActivity ca = baseMapper.selectById(entity.getId());
        if(null == ca || !ca.getShopNo().equals(getShopNo())){
            return false;
        }
        // TODO 校验时间折叠
        AssertUtils.isFalse(entity.getEndTime().before(entity.getBeginTime()),"截止时间不能大于开始时间");
        CouponActivity couponActivity = isEmpty(entity);
        if(couponActivity != null){
            AssertUtils.isFalse(true,"该活动时间与"+couponActivity.getTitle()+"活动时间重叠，请重新选择时间");
        }
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

    private CouponActivity isEmpty(CouponActivityModel model){
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .eq(CouponActivity::getShopNo, getShopNo())
                .eq(CouponActivity::getEnabled, CouponActivityEnum.Enabled.TYPE_1.getCode())
                .ge(CouponActivity::getEndTime,DateUtils.toZeroZoneTime(new Date()))
                .and(obj -> obj.between(CouponActivity::getBeginTime, model.getBeginTime(),model.getEndTime())
                        .or(obj1 -> obj1.between(CouponActivity::getEndTime, model.getBeginTime(),model.getEndTime()))
                        .or(obj1 -> obj1.lt(CouponActivity::getBeginTime,model.getBeginTime()).gt(CouponActivity::getEndTime,model.getEndTime()))
                )
                .notIn(model.getId() != null ,CouponActivity::getId, model.getId());
        List<CouponActivity> list = baseMapper.selectList(queryWrapper);
        List<CouponActivity> newlist = list.stream().filter(f -> {
            if(new Date().before(f.getBeginTime())){
                f.setStatus(CouponActivityEnum.Status.TYPE_1.getCode());
                return true;
            }else if(new Date().after(f.getEndTime())){
                f.setStatus(CouponActivityEnum.Status.TYPE_3.getCode());
                return false;
            }else{
                f.setStatus(CouponActivityEnum.Status.TYPE_2.getCode());
                return true;
            }
        }).collect(Collectors.toList());;
        if(CollectionUtil.isNotEmpty(newlist)){
            return newlist.get(0);
        }
        return null;
    }

    @Override
    public boolean switchById(CouponActivityPutModel model) {
        LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<CouponActivity>()
                .eq(CouponActivity::getShopNo, getShopNo())
                .eq(CouponActivity::getId, model.getId());
        CouponActivity entity = baseMapper.selectOne(queryWrapper);
        AssertUtils.notNull(entity, "未匹配到符合条件的记录");
        if(entity.getEnabled().equals(model.getStatus())){
            AssertUtils.notNull(null, "操作失败，状态已更新");
        }
        if(CouponActivityEnum.Enabled.TYPE_1.getCode().equals(model.getStatus())){
            CouponActivityModel checkModel = new CouponActivityModel();
            checkModel.setId(entity.getId());
            checkModel.setBeginTime(DateUtils.toZeroZoneTime(entity.getBeginTime()));
            checkModel.setEndTime(DateUtils.toZeroZoneTime(entity.getEndTime()));
            CouponActivity couponActivity = isEmpty(checkModel);
            if(couponActivity != null){
                AssertUtils.isFalse(true,"该活动时间与"+couponActivity.getTitle()+"活动时间重叠，无法启用");
            }
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
            return baseMapper.selectCouponPage(page,query);
        }
        return baseMapper.selectActivityCouponPage(page,query);
    }

    @Override
    public Page<CouponDetailUserResp> selectUserActivityCouponPage(Page page, CouponDetailUserQuery query) {
        query.setCreateBeginTime(DateUtils.toZeroZoneTime(query.getCreateBeginTime()));
        query.setCreateEndTime(DateUtils.toZeroZoneTime(query.getCreateEndTime()));
        Page<CouponDetailUserResp> p = baseMapper.selectUserActivityCouponPage(page,query);
        if(p.getRecords() != null){
            p.getRecords().stream().forEach(f -> {
                if(f.getStatus() == 1){
                    f.setStatus(2);
                    f.setStatusName(CouponActivityUserEnum.Status.parseCode(f.getStatus()).getText());
                }else if(f.getEndTime().before(new Date())){
                    f.setStatus(1);
                    f.setStatusName(CouponActivityUserEnum.Status.parseCode(f.getStatus()).getText());
                }else{
                    f.setStatus(0);
                    f.setStatusName(CouponActivityUserEnum.Status.parseCode(f.getStatus()).getText());
                }
            });
        }
        return p;
    }

}
