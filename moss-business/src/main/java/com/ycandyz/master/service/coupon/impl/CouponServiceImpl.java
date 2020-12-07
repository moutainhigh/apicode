package com.ycandyz.master.service.coupon.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.dao.coupon.CouponDao;
import com.ycandyz.master.dao.coupon.CouponDetailDao;
import com.ycandyz.master.dao.coupon.CouponDetailItemDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.coupon.CouponDetailQuery;
import com.ycandyz.master.domain.query.coupon.CouponQuery;
import com.ycandyz.master.dto.coupon.CouponDetailDTO;
import com.ycandyz.master.entities.coupon.Coupon;
import com.ycandyz.master.entities.coupon.CouponDetail;
import com.ycandyz.master.entities.coupon.CouponDetailItem;
import com.ycandyz.master.model.coupon.CouponDetailVO;
import com.ycandyz.master.service.coupon.ICouponService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.IDGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * @Description 优惠卷 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Slf4j
@Service
public class CouponServiceImpl extends BaseService<CouponDao,Coupon,CouponQuery> implements ICouponService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponDetailDao couponDetailDao;

    @Autowired
    private CouponDetailItemDao couponDetailItemDao;

    @Override
    public CommonResult<BasePageResult<CouponDetailVO>> selectPageList(RequestParams<CouponQuery> requestParams) {
        UserVO userVO = getUser();  //当前登陆用户
        if (userVO==null || userVO.getShopNo()==null || "".equals(userVO.getShopNo())){
            return CommonResult.failed("无门店信息");
        }
        BasePageResult<CouponDetailVO> infoVOPage = new BasePageResult<>();
        List<CouponDetailVO> list = new ArrayList<>();
        CouponQuery couponQuery = requestParams.getT();
        couponQuery.setShopNo(userVO.getShopNo());
        Page pageQuery = new Page(requestParams.getPage(), requestParams.getPage_size());
        Page<CouponDetailDTO> page = null;
        try {
            page = couponDao.queryTicketPageList(pageQuery, couponQuery);
            CouponDetailVO couponDetailVO = null;
            if (page.getRecords() != null && page.getRecords().size() > 0) {
                for (CouponDetailDTO couponDetailDTO : page.getRecords()){
                    couponDetailVO = new CouponDetailVO();
                    BeanUtils.copyProperties(couponDetailDTO, couponDetailVO);
                    /*if (couponTicketInfoVO.getTicketCreatedTime()!=null && couponTicketInfoVO.getTicketCreatedTime()!=0){
                        couponTicketInfoVO.setTicketCreatedAtStr(DateUtil.formTime(DateUtil.date(couponTicketInfoVO.getTicketCreatedTime()*1000),"yyyy-MM-dd HH:mm:ss"));
                    }
                    if (couponTicketInfoVO.getBeginTime()!=null && couponTicketInfoVO.getBeginTime()!=0){
                        couponTicketInfoVO.setBeginAtStr(DateUtil.formTime(DateUtil.date(couponTicketInfoVO.getBeginTime()*1000),"yyyy-MM-dd HH:mm:ss"));
                    }
                    if (couponTicketInfoVO.getEndTime()!=null && couponTicketInfoVO.getEndTime()!=0){
                        couponTicketInfoVO.setEndAtStr(DateUtil.formTime(DateUtil.date(couponTicketInfoVO.getEndTime()*1000),"yyyy-MM-dd HH:mm:ss"));
                    }*/
                    list.add(couponDetailVO);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        infoVOPage.setTotal(page.getTotal());
        infoVOPage.setPageSize(page.getSize());
        infoVOPage.setResult(list);
        infoVOPage.setPage(page.getCurrent());
        return CommonResult.success(infoVOPage);
    }

    @Override
    public CommonResult<String> auditState(Long id, Integer state) {
        UserVO userVO = getUser();  //当前登陆用户
        Coupon coupon = couponDao.selectById(id);
        if (coupon==null){
            return CommonResult.failed("传入id无法查询到优惠券");
        }
        if (!userVO.getShopNo().equals(coupon.getShopNo())){
            return CommonResult.failed("当前用户登陆门店无权进行此操作");
        }
        coupon.setState(state);
        coupon.setUpdateTime(new Date());
        int flag = couponDao.updateById(coupon);
        if (flag>0){
            return CommonResult.success("成功");
        }
        return CommonResult.failed("更新失败");
    }

    @Override
    public CommonResult<CouponDetailVO> ticketDetail(String ticketNo) {
        UserVO userVO = getUser();  //当前登陆用户
        CouponDetailDTO couponTicketInfoDTO = couponDao.queryTicketDetailByTicketNo(ticketNo,userVO.getShopNo());
        CouponDetailVO couponTicketInfoVO = null;
        if (couponTicketInfoDTO!=null){
            couponTicketInfoVO = new CouponDetailVO();
            BeanUtils.copyProperties(couponTicketInfoDTO,couponTicketInfoVO);
            /*if (couponTicketInfoVO.getTicketCreatedTime()!=null && couponTicketInfoVO.getTicketCreatedTime()!=0){
                couponTicketInfoVO.setTicketCreatedAtStr(DateUtil.formTime(DateUtil.date(couponTicketInfoVO.getTicketCreatedTime()*1000),"yyyy-MM-dd HH:mm:ss"));
            }
            if (couponTicketInfoVO.getBeginTime()!=null && couponTicketInfoVO.getBeginTime()!=0){
                couponTicketInfoVO.setBeginAtStr(DateUtil.formTime(DateUtil.date(couponTicketInfoVO.getBeginTime()*1000),"yyyy-MM-dd HH:mm:ss"));
            }
            if (couponTicketInfoVO.getEndTime()!=null && couponTicketInfoVO.getEndTime()!=0){
                couponTicketInfoVO.setEndAtStr(DateUtil.formTime(DateUtil.date(couponTicketInfoVO.getEndTime()*1000),"yyyy-MM-dd HH:mm:ss"));
            }*/
        }
        return CommonResult.success(couponTicketInfoVO);
    }

    @Override
    public CommonResult<String> saveTicket(CouponDetailQuery couponDetailQuery) {
        if (couponDetailQuery.getCouponId()!=null && couponDetailQuery.getCouponId()>0){
            return updateTicket(couponDetailQuery);
        }else {
            return insertTicket(couponDetailQuery);
        }
    }

    @Transactional
    public CommonResult<String> updateTicket(CouponDetailQuery couponDetailQuery){
        UserVO userVO = getUser();  //当前登陆用户
        //更新ticket表
        Coupon coupon = couponDao.selectOne(new QueryWrapper<Coupon>().eq("coupon_id",couponDetailQuery.getCouponId()));
        if (coupon!=null){

            if (!coupon.getShopNo().equals(userVO.getShopNo())){
                return CommonResult.failed("当前所在门店无权进行此操作");
            }

            //获取优惠卷详情
            CouponDetail couponDetail = couponDetailDao.selectOne(new QueryWrapper<CouponDetail>().eq("ticketInfoNo", couponDetailQuery.getLastTicketInfoNo()));
            if (couponDetail!=null){

                String ticketInfoNo = couponDetail.getCouponDetailNo();   //优惠券详情编号

                //获取优惠券关联商品列表
                List<CouponDetailItem> couponDetailItems = couponDetailItemDao.selectList(new QueryWrapper<CouponDetailItem>().eq("ticket_info_no",couponDetail.getId()));
                List<String> infoItemNoList = null;
                if (couponDetailItems!=null && couponDetailItems.size()>0){
                    infoItemNoList = couponDetailItems.stream().map(CouponDetailItem::getItemNo).collect(Collectors.toList());
                }
                if (!Objects.equals(couponDetail.getBeginTime(),couponDetailQuery.getBeginTime()) ||
                        !Objects.equals(couponDetail.getEndTime(),couponDetailQuery.getEndTime()) ||
                        !Objects.equals(couponDetail.getShopType(),couponDetailQuery.getShopType()) ||
                        !Objects.equals(couponDetail.getUseType(),couponDetailQuery.getUserType()) ||
                        !Objects.equals(couponDetail.getFullMoney(),couponDetailQuery.getFullMoney()) ||
                        !Objects.equals(couponDetail.getDiscountMoney(),couponDetailQuery.getDiscountMoney()) ||
                        !Objects.equals(couponDetail.getStatus(),couponDetailQuery.getStatus()) ||
                        !Objects.equals(couponDetail.getDays(),couponDetailQuery.getDays()) ||
                        !Objects.equals(couponDetail.getUserType(),couponDetailQuery.getUserType()) ||
                        !Objects.equals(couponDetail.getTakeNum(),couponDetailQuery.getTakeNum()) ||
                        !Objects.equals(couponDetail.getSuperposition(),couponDetailQuery.getSuperposition()) ||
                        !Objects.equals(couponDetail.getObtain(),couponDetailQuery.getObtain()) ||
                        !Objects.equals(couponDetail.getRemark(),couponDetailQuery.getRemark()) ||
                        !Objects.equals(infoItemNoList,couponDetailQuery.getItemNoList())){
                    //有不满足的进入到优惠券详情修改相关代码
                    CouponDetail ticketInfo = new CouponDetail();
                    ticketInfo.setBeginTime(couponDetailQuery.getBeginTime());
                    ticketInfo.setEndTime(couponDetailQuery.getEndTime());
                    ticketInfo.setCreateTime(new Date());
                    ticketInfo.setDays(couponDetailQuery.getDays());
                    ticketInfo.setDiscountMoney(couponDetailQuery.getDiscountMoney());
                    ticketInfo.setFullMoney(couponDetailQuery.getFullMoney());
                    ticketInfo.setName(couponDetail.getName());
                    ticketInfo.setObtain(couponDetailQuery.getObtain());
                    ticketInfo.setRemark(couponDetailQuery.getRemark());
                    ticketInfo.setShopType(couponDetailQuery.getShopType());
                    ticketInfo.setStatus(couponDetailQuery.getStatus());
                    ticketInfo.setSuperposition(couponDetailQuery.getSuperposition());
                    ticketInfo.setTakeNum(couponDetailQuery.getTakeNum());
                    ticketInfo.setCouponDetailNo(String.valueOf(IDGeneratorUtils.getLongId()));
                    ticketInfo.setUpdateTime(new Date());
                    ticketInfo.setUserType(couponDetailQuery.getUserType());
                    ticketInfo.setUseType(couponDetailQuery.getUseType());
                    ticketInfo.setCouponId(couponDetail.getCouponId());
                    couponDetailDao.insert(ticketInfo);

                    ticketInfoNo = ticketInfo.getCouponDetailNo();
                }

                //最后更新优惠券表
                coupon.setUpdateTime(new Date());
                coupon.setLastTicketInfoNo(ticketInfoNo);
                coupon.setName(couponDetailQuery.getName());
                coupon.setTicketSum(couponDetailQuery.getTicketSum());
                couponDao.updateById(coupon);
            }
            return CommonResult.success("更新成功!");
        }
        return CommonResult.failed("更新失败");
    }

    @Transactional
    public CommonResult<String> insertTicket(CouponDetailQuery couponDetailQuery) {
        UserVO userVO = getUser();  //当前登陆用户
        Date current = new Date();
        String ticketInfoNo = String.valueOf(IDGeneratorUtils.getLongId());
        String ticketNo = String.valueOf(IDGeneratorUtils.getLongId());

        //新增优惠券
        Coupon couponTicket = new Coupon();
        couponTicket.setUpdateTime(current);
        couponTicket.setLastTicketInfoNo(ticketInfoNo);
        couponTicket.setName(couponDetailQuery.getName());
        couponTicket.setTicketSum(couponDetailQuery.getTicketSum());
        couponTicket.setShopNo(userVO.getShopNo());
        couponTicket.setTicketNo(ticketNo);
        if (current.before(couponDetailQuery.getBeginTime())) {
            couponTicket.setState(0);
        }else if (current.after(couponDetailQuery.getBeginTime()) && current.before(couponDetailQuery.getEndTime())){
            couponTicket.setState(1);
        }else {
            couponTicket.setState(2);
        }
        couponDao.insert(couponTicket);

        //新增优惠券详情
        CouponDetail ticketInfo = new CouponDetail();
        ticketInfo.setBeginTime(couponDetailQuery.getBeginTime());
        ticketInfo.setEndTime(couponDetailQuery.getEndTime());
        ticketInfo.setCreateTime(new Date());
        ticketInfo.setDays(couponDetailQuery.getDays());
        ticketInfo.setDiscountMoney(couponDetailQuery.getDiscountMoney());
        ticketInfo.setFullMoney(couponDetailQuery.getFullMoney());
        ticketInfo.setName(couponDetailQuery.getName());
        ticketInfo.setObtain(couponDetailQuery.getObtain());
        ticketInfo.setRemark(couponDetailQuery.getRemark());
        ticketInfo.setShopType(couponDetailQuery.getShopType());
        ticketInfo.setStatus(couponDetailQuery.getStatus());
        ticketInfo.setSuperposition(couponDetailQuery.getSuperposition());
        ticketInfo.setTakeNum(couponDetailQuery.getTakeNum());
        ticketInfo.setCouponDetailNo(ticketInfoNo);
        ticketInfo.setUpdateTime(current);
        ticketInfo.setUserType(couponDetailQuery.getUserType());
        ticketInfo.setUseType(couponDetailQuery.getUseType());
        ticketInfo.setCouponId(couponTicket.getId());
        int flag = couponDetailDao.insert(ticketInfo);
        if (flag>0){
            return CommonResult.success("新增成功");
        }
        return CommonResult.failed("新增失败");
    }
}
