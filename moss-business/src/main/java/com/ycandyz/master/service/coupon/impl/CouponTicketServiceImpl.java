package com.ycandyz.master.service.coupon.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.coupon.CouponTicketInfoDao;
import com.ycandyz.master.dao.coupon.CouponTicketInfoItemDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.coupon.CouponTicketInfoQuery;
import com.ycandyz.master.dto.coupon.CouponTicketDTO;
import com.ycandyz.master.dto.coupon.CouponTicketInfoDTO;
import com.ycandyz.master.entities.coupon.CouponTicket;
import com.ycandyz.master.domain.query.coupon.CouponTicketQuery;
import com.ycandyz.master.dao.coupon.CouponTicketDao;
import com.ycandyz.master.entities.coupon.CouponTicketInfo;
import com.ycandyz.master.entities.coupon.CouponTicketInfoItem;
import com.ycandyz.master.model.coupon.CouponTicketInfoVO;
import com.ycandyz.master.model.coupon.CouponTicketVO;
import com.ycandyz.master.model.mall.MallAfterSalesVO;
import com.ycandyz.master.service.coupon.ICouponTicketService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.IDGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class CouponTicketServiceImpl extends BaseService<CouponTicketDao,CouponTicket,CouponTicketQuery> implements ICouponTicketService {

    @Autowired
    private CouponTicketDao couponTicketDao;

    @Autowired
    private CouponTicketInfoDao couponTicketInfoDao;

    @Autowired
    private CouponTicketInfoItemDao couponTicketInfoItemDao;

    @Override
    public ReturnResponse<Page<CouponTicketInfoVO>> selectPageList(RequestParams<CouponTicketQuery> requestParams, UserVO userVO) {
        if (userVO==null || userVO.getShopNo()==null || "".equals(userVO.getShopNo())){
            return ReturnResponse.failed("无门店信息");
        }
        Page<CouponTicketInfoVO> infoVOPage = new Page<>();
        List<CouponTicketInfoVO> list = new ArrayList<>();
        CouponTicketQuery couponTicketQuery = requestParams.getT();
        couponTicketQuery.setShopNo(userVO.getShopNo());
        Page pageQuery = new Page(requestParams.getPage(), requestParams.getPage_size());
        Page<CouponTicketInfoDTO> page = null;
        try {
            page = couponTicketDao.queryTicketPageList(pageQuery, couponTicketQuery);
            CouponTicketInfoVO couponTicketInfoVO = null;
            if (page.getRecords() != null && page.getRecords().size() > 0) {
                for (CouponTicketInfoDTO couponTicketInfoDTO : page.getRecords()){
                    couponTicketInfoVO = new CouponTicketInfoVO();
                    BeanUtils.copyProperties(couponTicketInfoDTO, couponTicketInfoVO);
                    if (couponTicketInfoVO.getTicketCreatedAt()!=null && couponTicketInfoVO.getTicketCreatedAt()!=0){
                        couponTicketInfoVO.setTicketCreatedAtStr(DateUtil.format(DateUtil.date(couponTicketInfoVO.getTicketCreatedAt()*1000),"yyyy-MM-dd HH:mm:ss"));
                    }
                    list.add(couponTicketInfoVO);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        infoVOPage.setTotal(page.getTotal());
        infoVOPage.setSize(page.getSize());
        infoVOPage.setRecords(list);
        infoVOPage.setCurrent(page.getCurrent());
        infoVOPage.setPages(page.getPages());
        return ReturnResponse.success(infoVOPage);
    }

    @Override
    public ReturnResponse<String> auditState(Long id, Integer state, UserVO userVO) {
        CouponTicket couponTicket = couponTicketDao.selectById(id);
        if (couponTicket==null){
            return ReturnResponse.failed("传入id无法查询到优惠券");
        }
        if (!userVO.getShopNo().equals(couponTicket.getShopNo())){
            return ReturnResponse.failed("当前用户登陆门店无权进行此操作");
        }
        couponTicket.setState(state);
        couponTicket.setUpdateAt(System.currentTimeMillis()/1000);
        int flag = couponTicketDao.updateById(couponTicket);
        if (flag>0){
            return ReturnResponse.success("成功");
        }
        return ReturnResponse.failed("更新失败");
    }

    @Override
    public ReturnResponse<CouponTicketInfoVO> ticketDetail(String ticketNo, UserVO userVO) {
        CouponTicketInfoDTO couponTicketInfoDTO = couponTicketDao.queryTicketDetailByTicketNo(ticketNo,userVO.getShopNo());
        CouponTicketInfoVO couponTicketInfoVO = null;
        if (couponTicketInfoDTO!=null){
            couponTicketInfoVO = new CouponTicketInfoVO();
            BeanUtils.copyProperties(couponTicketInfoDTO,couponTicketInfoVO);
        }
        return ReturnResponse.success(couponTicketInfoVO);
    }

    @Override
    public ReturnResponse<String> updateTicket(CouponTicketInfoQuery couponTicketInfoQuery) {
        //获取优惠卷详情
        CouponTicketInfo couponTicketInfo = couponTicketInfoDao.selectOne(new QueryWrapper<CouponTicketInfo>().eq("ticketInfoNo", couponTicketInfoQuery.getLastTicketInfoNo()));
        if (couponTicketInfo!=null){
            //获取优惠券关联商品列表
            List<CouponTicketInfoItem> couponTicketInfoItems = couponTicketInfoItemDao.selectList(new QueryWrapper<CouponTicketInfoItem>().eq("ticket_info_no",couponTicketInfoQuery.getLastTicketInfoNo()));
            List<String> infoItemNoList = null;
            if (couponTicketInfoItems!=null && couponTicketInfoItems.size()>0){
                infoItemNoList = couponTicketInfoItems.stream().map(CouponTicketInfoItem::getItemNo).collect(Collectors.toList());
            }
            if (!Objects.equals(couponTicketInfo.getBeginAt(),couponTicketInfoQuery.getBeginAt()) ||
                    !Objects.equals(couponTicketInfo.getEndAt(),couponTicketInfoQuery.getEndAt()) ||
                    !Objects.equals(couponTicketInfo.getShopType(),couponTicketInfoQuery.getShopType()) ||
                    !Objects.equals(couponTicketInfo.getUseType(),couponTicketInfoQuery.getUserType()) ||
                    !Objects.equals(couponTicketInfo.getFullMoney(),couponTicketInfoQuery.getFullMoney()) ||
                    !Objects.equals(couponTicketInfo.getDiscountMoney(),couponTicketInfoQuery.getDiscountMoney()) ||
                    !Objects.equals(couponTicketInfo.getStatus(),couponTicketInfoQuery.getStatus()) ||
                    !Objects.equals(couponTicketInfo.getDays(),couponTicketInfoQuery.getDays()) ||
                    !Objects.equals(couponTicketInfo.getUserType(),couponTicketInfoQuery.getUserType()) ||
                    !Objects.equals(couponTicketInfo.getTakeNum(),couponTicketInfoQuery.getTakeNum()) ||
                    !Objects.equals(couponTicketInfo.getSuperposition(),couponTicketInfoQuery.getSuperposition()) ||
                    !Objects.equals(couponTicketInfo.getObtain(),couponTicketInfoQuery.getObtain()) ||
                    !Objects.equals(couponTicketInfo.getRemark(),couponTicketInfoQuery.getRemark()) ||
                    !Objects.equals(infoItemNoList,couponTicketInfoQuery.getItemNoList())){
                //有不满足的进入到优惠券详情修改相关代码
                CouponTicketInfo ticketInfo = new CouponTicketInfo();
                ticketInfo.setBeginAt(couponTicketInfoQuery.getBeginAt());
                ticketInfo.setEndAt(couponTicketInfoQuery.getEndAt());
                ticketInfo.setCreatedAt(System.currentTimeMillis()/1000);
                ticketInfo.setDays(couponTicketInfoQuery.getDays());
                ticketInfo.setDiscountMoney(couponTicketInfoQuery.getDiscountMoney());
                ticketInfo.setFullMoney(couponTicketInfoQuery.getFullMoney());
                ticketInfo.setName(couponTicketInfo.getName());
                ticketInfo.setObtain(couponTicketInfoQuery.getObtain());
                ticketInfo.setRemark(couponTicketInfoQuery.getRemark());
                ticketInfo.setShopType(couponTicketInfoQuery.getShopType());
                ticketInfo.setStatus(couponTicketInfoQuery.getStatus());
                ticketInfo.setSuperposition(couponTicketInfoQuery.getSuperposition());
                ticketInfo.setTakeNum(couponTicketInfoQuery.getTakeNum());
                ticketInfo.setTicketInfoNo(String.valueOf(IDGeneratorUtils.getLongId()));
                ticketInfo.setUpdateAt(System.currentTimeMillis()/1000);
                ticketInfo.setUserType(couponTicketInfoQuery.getUserType());
                ticketInfo.setUseType(couponTicketInfoQuery.getUseType());
                couponTicketInfoDao.insert(ticketInfo);
            }
        }
        return null;
    }

}
