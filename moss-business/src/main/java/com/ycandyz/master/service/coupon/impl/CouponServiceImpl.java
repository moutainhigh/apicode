package com.ycandyz.master.service.coupon.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.dao.coupon.CouponDao;
import com.ycandyz.master.dao.coupon.CouponDetailDao;
import com.ycandyz.master.dao.coupon.CouponDetailItemDao;
import com.ycandyz.master.dao.coupon.CouponDetailUserDao;
import com.ycandyz.master.dao.mall.MallItemHomeDao;
import com.ycandyz.master.dao.mall.goodsManage.GoodsMallCategoryDao;
import com.ycandyz.master.dao.mall.MallOrderDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.coupon.CouponBaseQuery;
import com.ycandyz.master.domain.query.coupon.CouponDetailQuery;
import com.ycandyz.master.domain.query.coupon.CouponQuery;
import com.ycandyz.master.domain.query.coupon.CouponStateQuery;
import com.ycandyz.master.domain.query.coupon.CouponUseUserQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.dto.coupon.CouponDetailDTO;
import com.ycandyz.master.dto.coupon.CouponUseUserDTO;
import com.ycandyz.master.dto.coupon.CouponUserAndCartOrderDTO;
import com.ycandyz.master.dto.mall.MallItemDTO;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.response.mall.MallItemBaseResp;
import com.ycandyz.master.entities.coupon.Coupon;
import com.ycandyz.master.entities.coupon.CouponDetail;
import com.ycandyz.master.entities.coupon.CouponDetailItem;
import com.ycandyz.master.entities.coupon.CouponDetailUser;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.entities.mall.goodsManage.MallCategory;
import com.ycandyz.master.model.coupon.CouponDetailVO;
import com.ycandyz.master.model.coupon.CouponUseUserVO;
import com.ycandyz.master.model.mall.MallCategoryVO;
import com.ycandyz.master.service.coupon.ICouponService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.vo.MallItemVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * @Description ????????? ?????????
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

    @Autowired
    private GoodsMallCategoryDao mallCategoryDao;

    @Autowired
    private MallItemHomeDao mallHomeItemDao;

    @Autowired
    private CouponDetailUserDao couponDetailUserDao;

    @Autowired
    private MallOrderDao mallOrderDao;

    @Override
    public CommonResult<BasePageResult<CouponDetailVO>> selectPageList(PageModel pageModel, CouponQuery couponQuery) {
        UserVO userVO = getUser();  //??????????????????
        if (userVO==null || userVO.getShopNo()==null || "".equals(userVO.getShopNo())){
            return CommonResult.failed("???????????????");
        }
        BasePageResult<CouponDetailVO> infoVOPage = new BasePageResult<>();
        List<CouponDetailVO> list = new ArrayList<>();
        couponQuery.setShopNo(userVO.getShopNo());
        couponQuery.setName(couponQuery.getName()!=null?couponQuery.getName().trim():null);
        Page pageQuery = new Page(pageModel.getPage(), pageModel.getPageSize());
        Page<CouponDetailDTO> page = null;
        try {
            page = couponDao.queryTicketPageList(pageQuery, couponQuery);
            CouponDetailVO couponDetailVO = null;
            if (page.getRecords() != null && page.getRecords().size() > 0) {
                for (CouponDetailDTO couponDetailDTO : page.getRecords()){
                    couponDetailVO = new CouponDetailVO();
                    BeanUtils.copyProperties(couponDetailDTO, couponDetailVO);

                    if (couponDetailDTO.getObtain()!=null && !"".equals(couponDetailDTO.getObtain())){
                        couponDetailVO.setObtainList(Arrays.asList(couponDetailDTO.getObtain().split(",")));
                    }else {
                        couponDetailVO.setObtainList(new ArrayList<>());
                    }
                    couponDetailVO.setObtain(null);

                    //??????????????????
                    List<CouponUserAndCartOrderDTO> couponUserAndCartOrderDTOS = couponDetailUserDao.queryAmountByCouponId(couponDetailDTO.getCouponId(),userVO.getShopNo());
                    BigDecimal bigDecimal = new BigDecimal("0");
                    if (couponUserAndCartOrderDTOS!=null && couponUserAndCartOrderDTOS.size()>0){
                        for (CouponUserAndCartOrderDTO orderDTO : couponUserAndCartOrderDTOS){
                            bigDecimal = bigDecimal.add(orderDTO.getOrderTotalMoney());
                        }
                    }
                    couponDetailVO.setDealAmount(bigDecimal);

                    int status = 0;
                    if (couponDetailDTO.getCouponStatus()==1){
                        if (couponDetailDTO.getValidityType()==0){
                            if (couponDetailDTO.getBeginTime().after(new Date())){  //??????????????????????????????
                                status = 0;
                            }
                            if (couponDetailDTO.getBeginTime().before(new Date()) && couponDetailDTO.getEndTime().after(new Date())){
                                status = 1;
                            }
                            if (couponDetailDTO.getEndTime().before(new Date())){
                                status = 2;
                            }
                        }else if (couponDetailDTO.getValidityType()==1 || couponDetailDTO.getValidityType()==2){
                            if (couponDetailDTO.getCouponSum()<=couponDetailDTO.getObtainNum()){
                                status = 2;
                            }else {
                                status = 1;
                            }
                        }
                    }else if (couponDetailDTO.getCouponStatus()==0){
                        status = 3;
                    }
                    couponDetailVO.setStatus(status);
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
    public CommonResult<String> auditState(Long id, CouponStateQuery couponStateQuery) {
        UserVO userVO = getUser();  //??????????????????
        Coupon coupon = couponDao.selectById(id);
        if (coupon==null){
            return CommonResult.failed("??????id????????????????????????");
        }
        if (!userVO.getShopNo().equals(coupon.getShopNo())){
            return CommonResult.failed("?????????????????????????????????????????????");
        }
        if (coupon.getStatus()==couponStateQuery.getState()){
            //?????????????????????????????????
            return CommonResult.failed("??????????????????");
        }
        coupon.setStatus(couponStateQuery.getState());
        coupon.setUpdateTime(new Date());
        int flag = couponDao.updateById(coupon);
        if (flag>0){
            return CommonResult.success("??????");
        }
        return CommonResult.failed("????????????");
    }

    @Override
    public CommonResult<CouponDetailVO> ticketDetail(Long id) {
        UserVO userVO = getUser();  //??????????????????
        CouponDetailDTO couponTicketInfoDTO = couponDao.queryTicketDetailByTicketNo(id,userVO.getShopNo());
        CouponDetailVO couponTicketInfoVO = null;
        if (couponTicketInfoDTO!=null){
            couponTicketInfoVO = new CouponDetailVO();
            BeanUtils.copyProperties(couponTicketInfoDTO,couponTicketInfoVO);
            //??????????????????????????????
            if (couponTicketInfoDTO.getObtain()!=null && !"".equals(couponTicketInfoDTO.getObtain())){
                couponTicketInfoVO.setObtainList(Arrays.asList(couponTicketInfoDTO.getObtain().split(",")));
            }else {
                couponTicketInfoVO.setObtainList(new ArrayList<>());
            }
            couponTicketInfoVO.setObtain(null);

            //??????????????????????????????????????????
            List<MallItemBaseResp> couponDetailItemDTOS = couponDetailItemDao.queryByCouponDetailId(couponTicketInfoDTO.getId());
            if (couponDetailItemDTOS!=null && couponDetailItemDTOS.size()>0){
                couponTicketInfoVO.setMallItemResps(couponDetailItemDTOS);
                List<String> itemNoList = couponDetailItemDTOS.stream().map(MallItemBaseResp::getItemNo).collect(Collectors.toList());
                couponTicketInfoVO.setItemNoList(itemNoList);
            }else{
                couponTicketInfoVO.setMallItemResps(new ArrayList<>());
                couponTicketInfoVO.setItemNoList(new ArrayList<>());
            }
        }
        return CommonResult.success(couponTicketInfoVO);
    }

    @Transactional
    @Override
    public CommonResult<String> updateTicket(Long id, CouponDetailQuery couponDetailQuery){
        UserVO userVO = getUser();  //??????????????????
        //??????ticket???
        Coupon coupon = couponDao.selectById(id);
        if (coupon!=null){
            if (!coupon.getShopNo().equals(userVO.getShopNo())){
                return CommonResult.failed("???????????????????????????????????????");
            }
            if (coupon.getObtainNum()>couponDetailQuery.getCouponSum()){
                return CommonResult.failed("????????????????????????????????????????????????");
            }
            //?????????????????????
            CouponDetail couponDetail = couponDetailDao.selectOne(new QueryWrapper<CouponDetail>().eq("coupon_id", id).eq("status",1));
            if (couponDetail!=null){
                String ticketInfoNo = couponDetail.getCouponDetailNo();   //?????????????????????
                //?????????????????????????????????
                List<CouponDetailItem> couponDetailItems = couponDetailItemDao.selectList(new QueryWrapper<CouponDetailItem>().eq("coupon_detail_id",couponDetail.getId()));
                List<String> infoItemNoList = null;
                if (couponDetailItems!=null && couponDetailItems.size()>0){
                    infoItemNoList = couponDetailItems.stream().map(CouponDetailItem::getItemNo).collect(Collectors.toList());
                }
                List<String> list1 = Arrays.asList(couponDetail.getObtain()!=null ? couponDetail.getObtain().split(",") : new String[]{""});
                List<String> list2 = couponDetailQuery.getObtainList()==null ? new ArrayList<>() : couponDetailQuery.getObtainList();
                List<String> differenceList = null;
                if (list1.size()>=list2.size()) {
                    differenceList = list1.stream().filter(obtain -> !list2.contains(obtain)).collect(Collectors.toList());    //?????????
                }else if (list2.size()>list1.size()){
                    differenceList = list2.stream().filter(obtain -> !list1.contains(obtain)).collect(Collectors.toList());    //?????????
                }


                if (!Objects.equals(couponDetail.getBeginTime(),couponDetailQuery.getBeginTime()) ||
                        !Objects.equals(couponDetail.getEndTime(),couponDetailQuery.getEndTime()) ||
                        !Objects.equals(couponDetail.getShopType(),couponDetailQuery.getShopType()) ||
                        !Objects.equals(couponDetail.getUseType(),couponDetailQuery.getUserType()) ||
                        couponDetail.getFullMoney().compareTo(couponDetailQuery.getFullMoney())!=0 ||
                        couponDetail.getDiscountMoney().compareTo(couponDetailQuery.getDiscountMoney())!=0 ||
                        !Objects.equals(couponDetail.getValidityType(),couponDetailQuery.getValidityType()) ||
                        !Objects.equals(couponDetail.getDays(),couponDetailQuery.getDays()==null?0:couponDetailQuery.getDays()) ||
                        !Objects.equals(couponDetail.getUserType(),couponDetailQuery.getUserType()) ||
                        !Objects.equals(couponDetail.getTakeNum(),couponDetailQuery.getTakeNum()) ||
                        !Objects.equals(couponDetail.getSuperposition(),couponDetailQuery.getSuperposition()) ||
                        differenceList.size()!=0 ||
                        !Objects.equals(couponDetail.getRemark(),couponDetailQuery.getRemark()) ||
                        !Objects.equals(infoItemNoList,couponDetailQuery.getItemNoList())){
                    //?????????????????????????????????
                    CouponDetail oldInfo = couponDetailDao.selectOne(new QueryWrapper<CouponDetail>().eq("coupon_id",id).eq("status",1));
                    if (oldInfo!=null){
                        oldInfo.setStatus(0);
                        couponDetailDao.updateById(oldInfo);
                    }
                    //?????????????????????????????????????????????????????????
                    CouponDetail ticketInfo = new CouponDetail();
                    if (couponDetailQuery.getBeginTime()!=null) {
                        ticketInfo.setBeginTime(DateUtil.offsetHour(couponDetailQuery.getBeginTime(), -8));
                    }
                    if (couponDetailQuery.getEndTime()!=null) {
                        ticketInfo.setEndTime(DateUtil.offsetHour(couponDetailQuery.getEndTime(), -8));
                    }
                    ticketInfo.setCreateTime(new Date());
                    ticketInfo.setDays(couponDetailQuery.getDays());
                    ticketInfo.setDiscountMoney(couponDetailQuery.getDiscountMoney());
                    ticketInfo.setFullMoney(couponDetailQuery.getFullMoney());
                    ticketInfo.setName(couponDetailQuery.getName());
                    String stringBuffer = "";
                    if (couponDetailQuery.getObtainList()!=null && couponDetailQuery.getObtainList().size()>0){
                        for (String str : couponDetailQuery.getObtainList()){
                            stringBuffer += str+",";
                        }
                        stringBuffer = stringBuffer.substring(0,stringBuffer.length()-1);
                    }
                    ticketInfo.setObtain(stringBuffer);
                    ticketInfo.setRemark(couponDetailQuery.getRemark());
                    ticketInfo.setShopType(couponDetailQuery.getShopType());
                    ticketInfo.setValidityType(couponDetailQuery.getValidityType());
                    ticketInfo.setSuperposition(couponDetailQuery.getSuperposition());
                    ticketInfo.setTakeNum(couponDetailQuery.getTakeNum());
                    ticketInfo.setCouponDetailNo(String.valueOf(IDGeneratorUtils.getLongId()));
                    ticketInfo.setUpdateTime(new Date());
                    ticketInfo.setUserType(couponDetailQuery.getUserType());
                    ticketInfo.setUseType(couponDetailQuery.getUseType());
                    ticketInfo.setCouponId(couponDetail.getCouponId());
                    ticketInfo.setStatus(1);
                    couponDetailDao.insert(ticketInfo);
                    //?????????????????????????????????
                    CouponDetailItem couponDetailItem = new CouponDetailItem();
                    for (String itemId : couponDetailQuery.getItemNoList()) {
                        couponDetailItem.setCouponDetailId(ticketInfo.getId());
                        couponDetailItem.setItemNo(itemId);
                        couponDetailItemDao.insert(couponDetailItem);
                    }
                }

                //????????????????????????
                coupon.setName(couponDetailQuery.getName());
                coupon.setCouponSum(couponDetailQuery.getCouponSum());
                couponDao.updateById(coupon);
            }
            return CommonResult.success("????????????!");
        }
        return CommonResult.failed("????????????");
    }

    @Transactional
    @Override
    public CommonResult<String> insertTicket(CouponDetailQuery couponDetailQuery) {
        UserVO userVO = getUser();  //??????????????????
        String ticketInfoNo = String.valueOf(IDGeneratorUtils.getLongId());
        String ticketNo = String.valueOf(IDGeneratorUtils.getLongId());

        //???????????????
        Coupon couponTicket = new Coupon();
        couponTicket.setName(couponDetailQuery.getName());
        couponTicket.setCouponSum(couponDetailQuery.getCouponSum());
        couponTicket.setShopNo(userVO.getShopNo());
        couponTicket.setCouponNo(ticketNo);
        couponTicket.setStatus(1);
        couponDao.insert(couponTicket);

        //?????????????????????
        CouponDetail ticketInfo = new CouponDetail();
        if (couponDetailQuery.getBeginTime()!=null) {
            ticketInfo.setBeginTime(DateUtil.offsetHour(couponDetailQuery.getBeginTime(), -8));
        }
        if (couponDetailQuery.getEndTime()!=null) {
            ticketInfo.setEndTime(DateUtil.offsetHour(couponDetailQuery.getEndTime(), -8));
        }
        ticketInfo.setDays(couponDetailQuery.getDays());
        ticketInfo.setDiscountMoney(couponDetailQuery.getDiscountMoney());
        ticketInfo.setFullMoney(couponDetailQuery.getFullMoney());
        ticketInfo.setName(couponDetailQuery.getName());
        String stringBuffer = "";
        if (couponDetailQuery.getObtainList()!=null && couponDetailQuery.getObtainList().size()>0){
            for (String str : couponDetailQuery.getObtainList()){
                stringBuffer += str+",";
            }
            stringBuffer = stringBuffer.substring(0,stringBuffer.length()-1);
        }
        ticketInfo.setObtain(stringBuffer);
        ticketInfo.setRemark(couponDetailQuery.getRemark());
        ticketInfo.setShopType(couponDetailQuery.getShopType());
        ticketInfo.setValidityType(couponDetailQuery.getValidityType());
        ticketInfo.setSuperposition(couponDetailQuery.getSuperposition());
        ticketInfo.setTakeNum(couponDetailQuery.getTakeNum());
        ticketInfo.setCouponDetailNo(ticketInfoNo);
        ticketInfo.setUserType(couponDetailQuery.getUserType());
        ticketInfo.setUseType(couponDetailQuery.getUseType());
        ticketInfo.setCouponId(couponTicket.getId());
        int flag = couponDetailDao.insert(ticketInfo);
        //?????????????????????????????????
        CouponDetailItem couponDetailItem = new CouponDetailItem();
        for (String itemId : couponDetailQuery.getItemNoList()) {
            couponDetailItem.setCouponDetailId(ticketInfo.getId());
            couponDetailItem.setItemNo(itemId);
            couponDetailItemDao.insert(couponDetailItem);
        }
        if (flag>0){
            return CommonResult.success("????????????");
        }
        return CommonResult.failed("????????????");
    }

    @Override
    public CommonResult<List<MallCategoryVO>> getCategoryList() {
        UserVO userVO = getUser();
        List<MallCategory> list = mallCategoryDao.selectAllByShopNo(userVO.getShopNo());
        if (list!=null && list.size()>0){
            List<MallCategoryVO> vos = categoryTree(list,"");
            return CommonResult.success(vos);
        }
        return CommonResult.success(null);
    }

    private List<MallCategoryVO> categoryTree(List<MallCategory> dtoList, String parentNo){
        List<MallCategoryVO> list = new ArrayList<>();
        MallCategoryVO mallCategoryVO = null;
        for (MallCategory mallCategoryDTO : dtoList){
            if (mallCategoryDTO.getParentCategoryNo().equals(parentNo)){
                mallCategoryVO = new MallCategoryVO();
                mallCategoryVO.setCategoryImg(mallCategoryDTO.getCategoryImg());
                mallCategoryVO.setCategoryName(mallCategoryDTO.getCategoryName());
                mallCategoryVO.setCategoryNo(mallCategoryDTO.getCategoryNo());
                mallCategoryVO.setCreatedTime(mallCategoryDTO.getCreatedTime());
                mallCategoryVO.setId(mallCategoryDTO.getId());
                mallCategoryVO.setLayer(mallCategoryDTO.getLayer());
                mallCategoryVO.setParentCategoryNo(mallCategoryDTO.getParentCategoryNo());
                mallCategoryVO.setShopNo(mallCategoryDTO.getShopNo());
                mallCategoryVO.setSortValue(mallCategoryDTO.getSortValue());
                mallCategoryVO.setStatus(mallCategoryDTO.getStatus());
                mallCategoryVO.setUpdatedTime(mallCategoryDTO.getUpdatedTime());
                mallCategoryVO.setChaildList(categoryTree(dtoList, mallCategoryVO.getCategoryNo()));
                list.add(mallCategoryVO);
            }
        }
        return list;
    }

    @Override
    public CommonResult<BasePageResult<MallItemVO>> itemList(Page page, CouponBaseQuery query) {
        BasePageResult<MallItemVO> basePageResult = new BasePageResult<>();
        basePageResult.setPage(page.getCurrent());
        basePageResult.setPageSize(page.getSize());
        UserVO userVO = getUser();
        Page page1 = new Page();
        page1.setCurrent(page.getCurrent());
        page1.setSize(page.getSize());
        MallItemBaseQuery mallItemQuery = new MallItemBaseQuery();
        mallItemQuery.setShopNo(userVO.getShopNo());
        mallItemQuery.setCategoryNo(query.getCategoryNo()!=null?query.getCategoryNo().trim():null);
        mallItemQuery.setItemName(query.getKeyword()!=null?query.getKeyword().trim():null);
        Page<MallItemBaseResp> mallItemRespPage = null;
        if (query.getType().equals("all")){
            mallItemRespPage = mallHomeItemDao.selectMallItemPage(page1,mallItemQuery);
        }else if (query.getType().equals("choose")){
            mallItemQuery.setCouponId(query.getId());
            mallItemRespPage = mallHomeItemDao.selectMallItemPageByCouponId(page1,mallItemQuery);
        }
        List<MallItemVO> list = new ArrayList<>();
        if (mallItemRespPage!=null && mallItemRespPage.getRecords()!=null && mallItemRespPage.getRecords().size()>0){
            //????????????????????????
            List<MallCategory> mallCategoryList = mallCategoryDao.selectList(new QueryWrapper<MallCategory>().eq("shop_no",userVO.getShopNo()).eq("status",1));
            MallItemVO mallItemVO = null;
            for (MallItemBaseResp mallItemResp : mallItemRespPage.getRecords()){
                mallItemVO = mallItemRespToVO(mallItemResp);
                mallItemVO.setCategoryName(getTreeCategoryName(mallCategoryList,mallItemVO.getCategoryNo()));
                mallItemVO.setPrice(mallItemResp.getHighestSalePrice().compareTo(mallItemResp.getLowestSalePrice())>0 ? mallItemResp.getLowestSalePrice() : mallItemResp.getHighestSalePrice());
                list.add(mallItemVO);
            }
            basePageResult.setTotal(mallItemRespPage.getTotal());
            basePageResult.setResult(list);
        }
        return CommonResult.success(basePageResult);
    }

    @Override
    public CommonResult<BasePageResult<CouponUseUserVO>> getCouponUseList(Long id, Page page, CouponUseUserQuery couponUseUserQuery) {
        UserVO userVO = getUser();
        BasePageResult<CouponUseUserVO> basePageResult = new BasePageResult<>();
        basePageResult.setPage(page.getCurrent());
        basePageResult.setPageSize(page.getSize());
        List<CouponUseUserVO> list = new ArrayList<>();
        Page<CouponUseUserDTO> dtoPage = null;
        couponUseUserQuery.setShopNo(userVO.getShopNo());
        couponUseUserQuery.setCouponId(id);
        couponUseUserQuery.setUserMsg(couponUseUserQuery.getUserMsg()!=null && !"".equals(couponUseUserQuery.getUserMsg()) ? couponUseUserQuery.getUserMsg().trim() : null);
        couponUseUserQuery.setBeginOrderTime(couponUseUserQuery.getBeginOrderTime()!=null ? DateUtil.offsetHour(couponUseUserQuery.getBeginOrderTime(),-8) : null);
        couponUseUserQuery.setEndOrderTime(couponUseUserQuery.getEndOrderTime()!=null ? DateUtil.offsetHour(couponUseUserQuery.getEndOrderTime(),-8) : null);
        couponUseUserQuery.setBeginTakeTime(couponUseUserQuery.getBeginTakeTime()!=null ? DateUtil.offsetHour(couponUseUserQuery.getBeginTakeTime(),-8) : null);
        couponUseUserQuery.setEndTakeTime(couponUseUserQuery.getEndTakeTime()!=null ? DateUtil.offsetHour(couponUseUserQuery.getEndTakeTime(),-8) : null);
        if (couponUseUserQuery.getPageType()==0){ //?????????
            dtoPage = couponDetailUserDao.selectTakeUserCouponList(page,couponUseUserQuery);
        }else if (couponUseUserQuery.getPageType()==1){   //?????????
            dtoPage = couponDetailUserDao.selectUseUserCouponList(page,couponUseUserQuery);
        }
        if (dtoPage!=null && dtoPage.getRecords().size()>0){
            CouponUseUserVO vo = null;
            for (CouponUseUserDTO dto : dtoPage.getRecords()){
                vo = new CouponUseUserVO();
                vo.setCouponId(dto.getCouponId());
                vo.setCouponDetailId(dto.getCouponDetailId());
                vo.setCouponName(dto.getCouponName());
                if (dto.getUseType()==0) {
                    vo.setCouponTypeMsg("???"+dto.getDiscountMoney());
                }else if (dto.getUseType()==1){
                    vo.setCouponTypeMsg("???"+dto.getFullMoney()+"???"+dto.getDiscountMoney());
                }
                if (dto.getStatus()==0) {
                    if (dto.getCouponUserEndTime().after(new Date())){  //?????????????????????????????????????????????
                        vo.setStatus(2);  //?????????
                    }else {
                        vo.setStatus(3);  //?????????
                    }
                }else {
                    vo.setStatus(dto.getStatus());  //?????????
                }
                vo.setOrderAtStr(dto.getOrderAtStr());
                vo.setOrderSn(dto.getOrderSn());
                vo.setOrderStatus(dto.getOrderStatus());
                vo.setPayAmount(dto.getPayAmount());
                vo.setSource(dto.getSource());
                vo.setTakeTime(dto.getTakeTime());
                vo.setUserId(dto.getUserId());
                vo.setUserName(dto.getUserName());
                vo.setValidityType(dto.getValidityType());
                if (dto.getValidityType()==0){
                    vo.setValidityTypeMsg(DateUtil.format(dto.getCouponUserBeginTime(),"yyyy-MM-dd HH:mm:ss")+" ~ "+DateUtil.format(dto.getCouponUserEndTime(),"yyyy-MM-dd HH:mm:ss"));
                }else if (dto.getValidityType()==1){
                    vo.setValidityTypeMsg("???????????????"+dto.getDays()+"????????????");
                }else if (dto.getValidityType()==2){
                    vo.setValidityTypeMsg("???????????????"+dto.getDays()+"????????????");
                }
                if (dto.getOrderSn()!=null && !"".equals(dto.getOrderSn())){
                    List<MallItemDTO> itemDTOS = mallHomeItemDao.selectMallItemByCartOrderSn(dto.getOrderSn());
                    if (itemDTOS!=null && itemDTOS.size()>0){
                        List<String> itemNameList = itemDTOS.stream().map(MallItemDTO :: getItemName).collect(Collectors.toList());
                        vo.setItemNameList(itemNameList);
                    }
                    List<MallOrder> mallOrders = mallOrderDao.selectList(new QueryWrapper<MallOrder>().eq("cart_order_sn",dto.getOrderSn()));
                    List<String> orderNoList = new ArrayList<>();
                    List<String> childOrderStatus = new ArrayList<>();
                    if (mallOrders!=null && mallOrders.size()>0){
                        for (MallOrder mallOrder : mallOrders) {
                            orderNoList.add(mallOrder.getOrderNo());
                            if (mallOrder.getStatus()==10){
                                childOrderStatus.add("?????????");
                            }else if (mallOrder.getStatus()==20){
                                childOrderStatus.add("?????????");
                            }else if (mallOrder.getStatus()==30){
                                childOrderStatus.add("?????????");
                            }else if (mallOrder.getStatus()==40){
                                childOrderStatus.add("?????????");
                            }else if (mallOrder.getStatus()==50){
                                childOrderStatus.add("?????????");
                            }
                        }
                    }
                    vo.setOrderNoList(orderNoList);
                    vo.setChildOrderStatusList(childOrderStatus);
                }
                list.add(vo);
            }
            basePageResult.setTotal(dtoPage.getTotal());
            basePageResult.setResult(list);
        }
        return CommonResult.success(basePageResult);
    }

    /**
     * ???????????????????????????????????????
     * @param mallItemResp
     * @return
     */
    private MallItemVO mallItemRespToVO(MallItemBaseResp mallItemResp){
        MallItemVO mallItemVO = new MallItemVO();
        mallItemVO.setItemName(mallItemResp.getItemName());
        mallItemVO.setItemNo(mallItemResp.getItemNo());
        mallItemVO.setCategoryNo(mallItemResp.getCategoryNo());
        mallItemVO.setItemCover(mallItemResp.getItemCover());
        mallItemVO.setItemText(mallItemResp.getItemText());
        mallItemVO.setPrice(mallItemResp.getPrice());
        mallItemVO.setStock(mallItemResp.getStock());
        mallItemVO.setGoodsNo(mallItemResp.getGoodsNo());
        return mallItemVO;
    }

    /**
     * ????????????????????????????????????????????????????????????
     * @param list
     * @param categoryNo
     * @return
     */
    private String getTreeCategoryName(List<MallCategory> list, String categoryNo){
        if (list!=null && list.size()>0){
            Map<String, MallCategory> map = list.stream().collect(Collectors.toMap(MallCategory::getCategoryNo, a -> a,(k1,k2)->k1));
            if (map.get(categoryNo)!=null){
                MallCategory nowCategory = map.get(categoryNo);
                String name = recursionNo(map,nowCategory.getParentCategoryNo());
                return name+">"+nowCategory.getCategoryName();
            }
        }
        return null;
    }

    /**
     * ????????????????????????????????????????????????????????????
     * @param map
     * @param parentNo
     * @return
     */
    private String recursionNo(Map<String, MallCategory> map, String parentNo){
        String name = "";
        if (parentNo!=null){
            MallCategory mallCategory = map.get(parentNo);
            if (mallCategory!=null) {
                name = recursionNo(map,mallCategory.getParentCategoryNo())+">"+mallCategory.getCategoryName();
            }
        }
        return name;
    }

    public static void main(String[] args) {
        String obtain = "0";
        System.out.println(obtain.contains("1"));
    }
}
