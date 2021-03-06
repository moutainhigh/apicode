package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.constant.CommonConstant;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.coupon.CouponDetailUserDao;
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.dao.organize.OrganizeGroupDao;
import com.ycandyz.master.dao.organize.OrganizeRelDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.enums.mall.MallOrderEnum;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.domain.query.mall.MallOrderUAppQuery;
import com.ycandyz.master.domain.query.mall.uApp.MallPickupUAppQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.dto.mall.*;
import com.ycandyz.master.entities.coupon.CouponDetailUser;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.entities.organize.Organize;
import com.ycandyz.master.entities.organize.OrganizeGroup;
import com.ycandyz.master.entities.organize.OrganizeRel;
import com.ycandyz.master.enums.StatusEnum;
import com.ycandyz.master.model.mall.*;
import com.ycandyz.master.model.mall.uApp.*;
import com.ycandyz.master.service.mall.MallOrderService;
import com.ycandyz.master.service.userExportRecord.IUserExportRecordService;
import com.ycandyz.master.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MaillOrderServiceImpl extends BaseService<MallOrderDao, MallOrder, MallOrderQuery> implements MallOrderService {

    @Autowired
    private MallOrderDao mallOrderDao;

    @Autowired
    private MallShopDao mallShopDao;

    @Autowired
    private MallAfterSalesDao mallAfterSalesDao;

    @Autowired
    private MallAfterSalesLogDao mallAfterSalesLogDao;

    @Autowired
    private MallShopShippingDao mallShopShippingDao;

    @Autowired
    private MallShopShippingLogDao mallShopShippingLogDao;

    @Autowired
    private MallBuyerShippingDao mallBuyerShippingDao;

    @Autowired
    private MallOrderDetailSpecDao mallOrderDetailSpecDao;

    @Autowired
    private MallBuyerShippingLogDao mallBuyerShippingLogDao;

    @Autowired
    private MallSocialShareFlowDao mallSocialShareFlowDao;

    @Autowired
    private S3UploadFile s3UploadFile;

    @Autowired
    private IUserExportRecordService iUserExportRecordService;

    @Autowired
    private OrganizeRelDao organizeRelDao;

    @Autowired
    private OrganizeDao organizeDao;

    @Autowired
    private MallOrderDetailDao mallOrderDetailDao;

    @Autowired
    private CouponDetailUserDao couponDetailUserDao;

    @Autowired
    private OrganizeGroupDao organizeGroupDao;

    @Value("${excel.sheet}")
    private int num;

    @Override
    public ReturnResponse<Page<MallOrderVO>> queryOrderList(RequestParams<MallOrderQuery> requestParams) {
        UserVO userVO = getUser();  //????????????????????????
        List<MallOrderVO> list = new ArrayList<>();
        Page<MallOrderVO> page1 = new Page<>();
        MallOrderQuery mallOrderQuery = (MallOrderQuery) requestParams.getT();  //????????????
        List<Integer> organizeIds = new ArrayList<>();  //????????????id?????????????????????
        Map<String, Integer> shopNoAndOrganizeId = new HashMap<>(); //??????shopNo???organizeid    map<shopNo,organizeid>
        //????????????id
        if (mallOrderQuery.getIsGroup().equals("0")){   //???????????????????????????
            mallOrderQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
            organizeIds.add(userVO.getOrganizeId().intValue());
            shopNoAndOrganizeId.put(userVO.getShopNo(),userVO.getOrganizeId().intValue());
        }else if (mallOrderQuery.getIsGroup().equals("1")){ //??????
            if (mallOrderQuery.getChildOrganizeId()==null || "".equals(mallOrderQuery.getChildOrganizeId()) || mallOrderQuery.getChildOrganizeId().equals("0")){
                //????????????????????????
                Long groupOrganizeId = userVO.getOrganizeId();   //??????id
                if (groupOrganizeId!=null) {
                    List<OrganizeRel> organizeRels = organizeRelDao.selectList(new QueryWrapper<OrganizeRel>().eq("group_organize_id", groupOrganizeId.intValue()).eq("status",2));
                    if (organizeRels != null && organizeRels.size() > 0) {
                        List<Integer> oIds = organizeRels.stream().map(OrganizeRel::getOrganizeId).collect(Collectors.toList());
                        organizeIds.addAll(oIds);
                        List<MallShopDTO> mallShopDTOS = mallShopDao.queryByOrganizeIdList(organizeIds);
                        if (mallShopDTOS!=null && mallShopDTOS.size()>0){
                            List<String> shopNos = mallShopDTOS.stream().map(MallShopDTO::getShopNo).collect(Collectors.toList());
                            mallOrderQuery.setShopNo(shopNos);
                            Map<String, Integer> map = mallShopDTOS.stream().collect(Collectors.toMap(MallShopDTO::getShopNo, MallShopDTO::getOrganizeId));
                            shopNoAndOrganizeId.putAll(map);
                        }
                    }
                    //??????????????????????????????????????????
                    organizeIds.add(groupOrganizeId.intValue());
                    if (mallOrderQuery.getShopNo()!=null && mallOrderQuery.getShopNo().size()>0) {
                        mallOrderQuery.getShopNo().add(userVO.getShopNo());
                    }else {
                        mallOrderQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
                    }
                    shopNoAndOrganizeId.put(userVO.getShopNo(),groupOrganizeId.intValue());
                }
            }else {
                mallOrderQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
                MallShop mallShop = mallShopDao.selectOne(new QueryWrapper<MallShop>().eq("organize_id", mallOrderQuery.getChildOrganizeId()));
                organizeIds.add(Integer.valueOf(mallOrderQuery.getChildOrganizeId()));
                if (mallShop!=null){
                    mallOrderQuery.setShopNo(Arrays.asList(mallShop.getShopNo()));
                    shopNoAndOrganizeId.put(mallShop.getShopNo(), Integer.valueOf(mallOrderQuery.getChildOrganizeId()));
                }else {
                    page1.setPages(requestParams.getPage());
                    page1.setCurrent(requestParams.getPage());
                    page1.setRecords(list);
                    page1.setSize(requestParams.getPage_size());
                    return ReturnResponse.success(page1);
                }
            }
        }

        try {
            //???????????????
            Integer count = mallOrderDao.getTrendMallOrderPageSize(mallOrderQuery);
            page1.setTotal(count);
            if (count!=null && count>0) {
                boolean isOpenMaintainable = false;
                if (mallOrderQuery.getIsGroup().equals("0")) {
                    OrganizeRel organizeRel = organizeRelDao.selectOne(new QueryWrapper<OrganizeRel>().eq("organize_id",userVO.getOrganizeId()).eq("status",2));
                    if (organizeRel!=null){
                        OrganizeGroup organizeGroup = organizeGroupDao.selectOne(new QueryWrapper<OrganizeGroup>().eq("organize_id", organizeRel.getGroupOrganizeId()));
                        if (organizeGroup.getIsOpenMaintainable() != null && organizeGroup.getIsOpenMaintainable()) {
                            //?????????????????????
                            isOpenMaintainable = true;
                        }
                    }
                }else {
                    isOpenMaintainable = true;
                }

                //??????
                List<MallOrderDTO> mallDTOList = mallOrderDao.getTrendMallOrderByPage((requestParams.getPage() - 1) * requestParams.getPage_size(), requestParams.getPage_size(), mallOrderQuery);
                //page = mallOrderDao.getTrendMallOrderPage(pageQuery, mallOrderQuery);
                MallOrderVO mallOrderVo = null;
                if (mallDTOList != null && mallDTOList.size() > 0) {

                    //???????????????????????????organizedIds
                    List<Organize> organizeList = organizeDao.selectBatchIds(organizeIds);
                    Map<Integer, String> organizeIdAndName = organizeList.stream().collect(Collectors.toMap(Organize::getId, Organize::getFullName));

                    for (MallOrderDTO mallOrderDTO : mallDTOList) {
                        if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                            mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //???????????????????????????????????????????????????????????????
                        }
                        mallOrderVo = new MallOrderVO();
                        BeanUtils.copyProperties(mallOrderDTO, mallOrderVo);

                        //????????????????????????????????????   ????????????+??????
                        mallOrderVo.setTotalMoney(mallOrderDTO.getTotalMoney().add(mallOrderDTO.getAllMoney().subtract(mallOrderDTO.getRealMoney())));

                        //?????????????????????
                        if (mallOrderDTO.getIsCoupon()==null){
                            mallOrderVo.setIsCoupon(0);
                        }

                        //?????????????????????
                        if (isOpenMaintainable){
                            mallOrderVo.setAllowOperating(1);
                        }else {
                            mallOrderVo.setAllowOperating(0);
                        }

                        //?????????????????????????????????????????????
                        String fullName = organizeIdAndName.get(shopNoAndOrganizeId.get(mallOrderVo.getShopNo()));
                        mallOrderVo.setOrganizeName(fullName);

                        //order_at;payed_at;receive_at????????????????????????
                        if (mallOrderVo.getOrderAt()!=null && mallOrderVo.getOrderAt()>0) {
                            long time = Long.valueOf(mallOrderVo.getOrderAt())*1000;
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                            mallOrderVo.setOrderAtStr(orderAtStr);
                        }
                        if (mallOrderVo.getPayedAt()!=null && mallOrderVo.getPayedAt()>0) {
                            long time = Long.valueOf(mallOrderVo.getPayedAt())*1000;
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                            mallOrderVo.setPayedAtStr(orderAtStr);
                        }
                        if (mallOrderVo.getReceiveAt()!=null && mallOrderVo.getReceiveAt()>0) {
                            long time = Long.valueOf(mallOrderVo.getReceiveAt())*1000;
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                            mallOrderVo.setReceiveAtStr(orderAtStr);
                        }


                        //????????????????????????????????????
                        List<String> itemNames = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getItemName).collect(Collectors.toList());

                        Map<String, String> itemMap = mallOrderDTO.getDetails().stream().collect(Collectors.toMap(k -> k.getItemNo() + "#" + k.getSkuNo(), MallOrderDetailDTO::getItemName));
                        if (itemMap != null) {
                            List<MallItemByMallOrderVO> mallItemByMallOrderVOS = new ArrayList<>();
                            MallItemByMallOrderVO mallItemByMallOrderVO = null;
                            for (Map.Entry entrySet : itemMap.entrySet()) {
                                mallItemByMallOrderVO = new MallItemByMallOrderVO();
                                String key = (String) entrySet.getKey();
                                String value = (String) entrySet.getValue();
                                mallItemByMallOrderVO.setItemNo(key.split("#")[0]);
                                mallItemByMallOrderVO.setSkuNo(key.split("#")[1]);
                                mallItemByMallOrderVO.setItemName(value);
                                mallItemByMallOrderVOS.add(mallItemByMallOrderVO);
                            }
                            mallOrderVo.setItemInfoList(mallItemByMallOrderVOS);
                        }

                        mallOrderVo.setItemName(itemNames);
                        //????????????????????????????????????
                        List<String> goodsNos = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getGoodsNo).filter(x -> x != null && !"".equals(x)).collect(Collectors.toList());
                        mallOrderVo.setGoodsNo(goodsNos);
                        List<Integer> skuQuantity = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getSkuQuantity).collect(Collectors.toList());
                        if (skuQuantity != null && skuQuantity.size() > 0) {
                            int quantity = skuQuantity.stream().reduce(Integer::sum).orElse(0);
                            mallOrderVo.setQuantity(quantity);
                        }

                        if (mallOrderVo.getStatus()!=50 && mallOrderVo.getStatus()!=10) {   //?????????????????????????????????????????????
                            //??????????????????????????????????????????????????????
                            List<MallSocialShareFlowDTO> mallSocialShareFlowDTOS = mallSocialShareFlowDao.queryAllShareByOrderNo(mallOrderVo.getOrderNo());
                            if (mallSocialShareFlowDTOS != null && mallSocialShareFlowDTOS.size() > 0) {
                                List<String> sellerUserList = new ArrayList<>();
                                BigDecimal shareAmount = new BigDecimal(0);
                                for (MallSocialShareFlowDTO dto : mallSocialShareFlowDTOS) {
                                    if (dto.getShareType()==0) {
                                        String sellerUser = dto.getUserName() + " " + dto.getPhoneNo();
                                        sellerUserList.add(sellerUser); //???????????????
                                    }
                                    shareAmount = shareAmount.add(dto.getAmount());   //????????????
                                }
                                mallOrderVo.setShareAmount(shareAmount);
                                mallOrderVo.setSellerUserName(sellerUserList);
                            }
                        }

                        if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                            List<MallOrderDetailVO> detailVOList = new ArrayList<>();
                            mallOrderDTO.getDetails().forEach(detailDto->{
                                MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
                                BeanUtils.copyProperties(detailDto,mallOrderDetailVO);
                                detailVOList.add(mallOrderDetailVO);
                            });
                            mallOrderVo.setDetails(detailVOList);

                            //???????????????????????? isEnableShare
                            List<Integer> isEnableShareList = detailVOList.stream().map(MallOrderDetailVO::getIsEnableShare).collect(Collectors.toList());
                            if (isEnableShareList.contains(1) && mallOrderVo.getStatus()!=50 && mallOrderVo.getStatus()!=10){ //?????????
                                mallOrderVo.setIsEnableShare(1);
                            }else { //?????????
                                mallOrderVo.setIsEnableShare(0);
                            }
                        }

                        //??????????????????
                        List<MallAfterSales> mallAfterSalesList = mallAfterSalesDao.selectList(new QueryWrapper<MallAfterSales>().eq("order_no", mallOrderVo.getOrderNo()));
                        if (mallAfterSalesList != null && mallAfterSalesList.size() > 0) {
                            List<Integer> subStatusList = mallAfterSalesList.stream().map(MallAfterSales::getSubStatus).collect(Collectors.toList());
                            if (subStatusList.contains(1010) || subStatusList.contains(1030) || subStatusList.contains(1050) || subStatusList.contains(1070) || subStatusList.contains(2010)) {
                                //???????????????
                                mallOrderVo.setAfterSalesStatus(1);
                            } else {
                                mallOrderVo.setAfterSalesStatus(2);
                            }
                        } else {
                            mallOrderVo.setAfterSalesStatus(0);
                        }
                        if (mallOrderVo.getAfterSalesStatus() != null) {
                            //??????????????????????????????
                            if (mallOrderVo.getAfterSalesStatus() != 0) {
                                mallOrderVo.setAsStatus(111);  //111: ????????????????????????????????????????????????
                            } else {
                                if (mallOrderVo.getAfterSalesEndAt() != null && mallOrderVo.getAfterSalesEndAt() > new Date().getTime() / 1000) {
                                    mallOrderVo.setAsStatus(100);  //100: ?????????????????????????????????????????????????????????
                                } else {
                                    mallOrderVo.setAsStatus(110);  //110: ????????????????????????????????????????????????
                                }
                            }
                        }

                        list.add(mallOrderVo);
                    }
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        page1.setPages(requestParams.getPage());
        page1.setCurrent(requestParams.getPage());
        page1.setRecords(list);
        page1.setSize(requestParams.getPage_size());
        return ReturnResponse.success(page1);
    }

    public MallOrderExportResp exportEXT(MallOrderQuery mallOrderQuery){
        UserVO userVO = getUser();  //????????????????????????
        //????????????id
        if (mallOrderQuery.getIsGroup().equals("0")){   //???????????????????????????
            mallOrderQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
        }else if (mallOrderQuery.getIsGroup().equals("1")){ //??????
            if (mallOrderQuery.getChildOrganizeId()==null || "".equals(mallOrderQuery.getChildOrganizeId()) || mallOrderQuery.getChildOrganizeId().equals("0")){
                //????????????????????????
                Long groupOrganizeId = userVO.getOrganizeId();   //??????id
                if (groupOrganizeId!=null) {
                    List<OrganizeRel> organizeRels = organizeRelDao.selectList(new QueryWrapper<OrganizeRel>().eq("group_organize_id", groupOrganizeId.intValue()).eq("status",2));
                    if (organizeRels != null && organizeRels.size() > 0) {
                        List<Integer> organizeIds = organizeRels.stream().map(OrganizeRel::getOrganizeId).collect(Collectors.toList());
                        organizeIds.add(groupOrganizeId.intValue());
                        List<MallShopDTO> mallShopDTOS = mallShopDao.queryByOrganizeIdList(organizeIds);
                        if (mallShopDTOS!=null && mallShopDTOS.size()>0){
                            List<String> shopNos = mallShopDTOS.stream().map(MallShopDTO::getShopNo).collect(Collectors.toList());
                            mallOrderQuery.setShopNo(shopNos);
                        }
                    }
                }
            }else {
                mallOrderQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
                MallShop mallShop = mallShopDao.selectOne(new QueryWrapper<MallShop>().eq("organize_id", mallOrderQuery.getChildOrganizeId()));
                if (mallShop!=null){
                    mallOrderQuery.setShopNo(Arrays.asList(mallShop.getShopNo()));
                }else {
                    return null;
                }
            }
        }
        List<MallOrderDTO> list = mallOrderDao.getTrendMallOrder(mallOrderQuery);


        if (list!=null && list.size()>0) {
            for (MallOrderDTO mallOrderDTO : list) {

                //?????????????????????
                if (mallOrderDTO.getIsCoupon()==null){
                    mallOrderDTO.setIsCoupon(0);
                }

                //order_at;payed_at;receive_at????????????????????????
                if (mallOrderDTO.getOrderAt()!=null && mallOrderDTO.getOrderAt()>0) {
                    long time = Long.valueOf(mallOrderDTO.getOrderAt())*1000;
                    String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                    mallOrderDTO.setOrderAtStr(orderAtStr);
                }
                if (mallOrderDTO.getPayedAt()!=null && mallOrderDTO.getPayedAt()>0) {
                    long time = Long.valueOf(mallOrderDTO.getPayedAt())*1000;
                    String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                    mallOrderDTO.setPayedAtStr(orderAtStr);
                }
                if (mallOrderDTO.getReceiveAt()!=null && mallOrderDTO.getReceiveAt()>0) {
                    long time = Long.valueOf(mallOrderDTO.getReceiveAt())*1000;
                    String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                    mallOrderDTO.setReceiveAtStr(orderAtStr);
                }

                if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                    mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //???????????????????????????????????????????????????????????????
                }

                if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0) {
                    //????????????????????????????????????
                    List<String> itemNames = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getItemName).collect(Collectors.toList());
                    mallOrderDTO.setItemName(itemNames);
                    //??????????????????????????????
                    List<Integer> skuQuantity = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getSkuQuantity).collect(Collectors.toList());
                    if (skuQuantity != null && skuQuantity.size() > 0) {
                        int quantity = skuQuantity.stream().reduce(Integer::sum).orElse(0);
                        mallOrderDTO.setQuantity(quantity);
                    }
                    //????????????????????????????????????
                    List<String> goodsNos = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getGoodsNo).filter(x -> x != null && !"".equals(x)).collect(Collectors.toList());
                    mallOrderDTO.setGoodsNo(goodsNos);
                    //???????????????????????? isEnableShare
                    List<Integer> isEnableShareList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getIsEnableShare).collect(Collectors.toList());
                    if (isEnableShareList.contains(1) && mallOrderDTO.getStatus() != 50 && mallOrderDTO.getStatus() != 10) { //?????????
                        mallOrderDTO.setIsEnableShare(1);
                    } else { //?????????
                        mallOrderDTO.setIsEnableShare(0);
                    }
                }
                if (mallOrderDTO.getStatus()!=50 && mallOrderDTO.getStatus()!=10) {   //?????????????????????????????????????????????
                    //??????????????????????????????????????????????????????
                    List<MallSocialShareFlowDTO> mallSocialShareFlowDTOS = mallSocialShareFlowDao.queryAllShareByOrderNo(mallOrderDTO.getOrderNo());
                    if (mallSocialShareFlowDTOS != null && mallSocialShareFlowDTOS.size() > 0) {
                        List<String> sellerUserList = new ArrayList<>();
                        BigDecimal shareAmount = new BigDecimal(0);
                        for (MallSocialShareFlowDTO dto : mallSocialShareFlowDTOS) {
                            if (dto.getShareType()==0) {
                                String sellerUser = dto.getUserName() + " " + dto.getPhoneNo();
                                sellerUserList.add(sellerUser); //???????????????
                            }
                            shareAmount = shareAmount.add(dto.getAmount());   //????????????
                        }
                        mallOrderDTO.setShareAmount(shareAmount);
                        mallOrderDTO.setSellerUserName(sellerUserList);
                    }
                }

                //??????????????????
                List<MallAfterSales> mallAfterSalesList = mallAfterSalesDao.selectList(new QueryWrapper<MallAfterSales>().eq("order_no", mallOrderDTO.getOrderNo()));
                if (mallAfterSalesList != null && mallAfterSalesList.size() > 0) {
                    List<Integer> subStatusList = mallAfterSalesList.stream().map(MallAfterSales::getSubStatus).collect(Collectors.toList());
                    if (subStatusList.contains(1010) || subStatusList.contains(1030) || subStatusList.contains(1050) || subStatusList.contains(1070) || subStatusList.contains(2010)) {
                        //???????????????
                        mallOrderDTO.setAfterSalesStatus(1);
                    } else {
                        mallOrderDTO.setAfterSalesStatus(2);
                    }
                } else {
                    mallOrderDTO.setAfterSalesStatus(0);
                }
                if (mallOrderDTO.getAfterSalesStatus() != null) {
                    //??????????????????????????????
                    if (mallOrderDTO.getAfterSalesStatus() != 0) {
                        mallOrderDTO.setAsStatus(111);  //111: ????????????????????????????????????????????????
                    } else {
                        if (mallOrderDTO.getAfterSalesEndAt() != null && mallOrderDTO.getAfterSalesEndAt() > new Date().getTime() / 1000) {
                            mallOrderDTO.setAsStatus(100);  //100: ?????????????????????????????????????????????????????????
                        } else {
                            mallOrderDTO.setAsStatus(110);  //110: ????????????????????????????????????????????????
                        }
                    }
                }
            }
        }

        String status = null;
        if (mallOrderQuery != null && mallOrderQuery.getStatus() != null){
            status = EnumUtil.getByCode(StatusEnum.class,mallOrderQuery.getStatus()).getDesc();
        }else {
            status = EnumUtil.getByCode(StatusEnum.class,9).getDesc(); //?????????????????????
        }
        try {
            Calendar now = Calendar.getInstance();
            String today = DateUtil.formatDateForYMD(now.getTime());
            now.add(Calendar.DATE,-1);
            String yestoday = DateUtil.formatDateForYMD(now.getTime());
            String randomnum = String.valueOf(IDGeneratorUtils.getLongId());
            String pathpPefix = System.getProperty("user.dir") + "/xls/";
            String fileName =today + status + "??????.xls";
            String suffix = today + CommonConstant.SLASH + randomnum + CommonConstant.SLASH + userVO.getOrganizeId() + CommonConstant.SLASH +fileName;
            String path = pathpPefix + suffix;
            deleteFile(yestoday,pathpPefix);
            ExcelWriter writer = ExcelUtil.getWriter(path,"???1???");

            //?????????????????????
            writer.getStyleSet().setAlign(HorizontalAlignment.LEFT, VerticalAlignment.CENTER); //????????????????????????????????????
            writer.setColumnWidth(0, 40); //???1???40px???

            double size = list.size();
            log.info("??????{}?????????",(int)size);
            int ceil = (int)Math.ceil(size / num);
            log.info("??????{}???sheet?????????sheet{}?????????",ceil,num);
            log.info("???????????????{}sheet",1);
            List<String> containsList = addHeader(writer);
            int end = num>(int)size?(int)size:num;
            List<MallOrderDTO> subList = list.subList(0, end);
            MapUtil mapUtil = new MapUtil();
            List<Map<String, Object>> result = mapUtil.beanToMap(subList,containsList);
            salesStateCodeToString(result);
            writer.write(result, true);
            log.info("???{}sheet????????????",1);
            int beginIndex = num;
            int endIndex = (beginIndex + num)>(int)size?(int)size:(beginIndex + num);
            for (int i = 2; i <= ceil; i++) {
                log.info("???????????????{}sheet",i);
                writer.setSheet("???"+i+"???");
                List<String> containsList2 = addHeader(writer);
                List<MallOrderDTO> subList2 = list.subList(beginIndex, endIndex);
                List<Map<String, Object>> result2 = mapUtil.beanToMap(subList2,containsList2);
                salesStateCodeToString(result2);
                writer.write(result2, true);
                beginIndex = endIndex;
                endIndex = (beginIndex + num)>(int)size?(int)size:(beginIndex + num);
                log.info("???{}sheet????????????",i);
            }
            log.info("??????????????????");
            writer.close();
            log.info("????????????????????????:{}",path);
            File file = new File(path);
            String url = s3UploadFile.uploadFile(file, suffix);
            MallOrderExportResp mallOrderExportResp = new MallOrderExportResp();
            mallOrderExportResp.setFileName(fileName);
            mallOrderExportResp.setFielUrl(url);
            log.info("??????excel??????:{}",mallOrderExportResp);
            //???????????????
            iUserExportRecordService.insertExportRecord(mallOrderExportResp,userVO);

            return mallOrderExportResp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> addHeader(ExcelWriter writer) {
        writer.addHeaderAlias("cartOrderSn", "???????????????");
        writer.addHeaderAlias("orderNo", "???????????????");
        writer.addHeaderAlias("itemName", "????????????");
        writer.addHeaderAlias("goodsNo", "??????");
        writer.addHeaderAlias("allMoney", "????????????(??)");
        writer.addHeaderAlias("payType", "????????????");
        writer.addHeaderAlias("quantity", "????????????");
        writer.addHeaderAlias("status", "??????");
        writer.addHeaderAlias("isEnableShare", "????????????");
        writer.addHeaderAlias("sellerUserName", "???????????????");
        writer.addHeaderAlias("shareAmount", "??????????????????");
        writer.addHeaderAlias("asStatus", "??????");
        writer.addHeaderAlias("payuser", "????????????");
        writer.addHeaderAlias("isGroupSupply", "????????????");
        writer.addHeaderAlias("receiverName", "?????????");
        writer.addHeaderAlias("receiverAddress", "???????????????");
        writer.addHeaderAlias("deliverType", "????????????");
        writer.addHeaderAlias("orderAtStr", "????????????");
        writer.addHeaderAlias("payedAtStr", "????????????");
        writer.addHeaderAlias("receiveAtStr", "????????????");
        List<String> containsList = Arrays.asList("cartOrderSn","orderNo","itemName","goodsNo","allMoney",
                "payType","quantity","status","isEnableShare","sellerUserName","shareAmount","asStatus",
                "payuser","isGroupSupply","receiverName","receiverAddress","deliverType","orderAtStr","payedAtStr","receiveAtStr");
        return containsList;
    }

    //??????????????????????????????????????????shopNo????????????
    private void deleteFile(String yestoday,String pathpPefix) {
        try {
            File yestodayfile = new File(pathpPefix + yestoday);
            if (yestodayfile.exists()){
                File[] filePaths = yestodayfile.listFiles();
                if (filePaths != null && filePaths.length > 0){
                    Arrays.stream(filePaths).filter(file1 -> file1.isFile()).forEach(f->f.delete());
                }
                yestodayfile.delete();
            }
//            File todayfile = new File(pathpPefix + today + "/" + shopNo);
//            if (todayfile.exists()){
//                File[] filePaths = todayfile.listFiles();
//                if (filePaths != null && filePaths.length > 0){
//                    Arrays.stream(filePaths).filter(file1 -> file1.isFile()).forEach(f->f.delete());
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReturnResponse<MallOrderVO> queryOrderDetail(String orderNo) {
        UserVO userVO = getUser();  //????????????????????????
        MallOrderVO mallOrderVO = null;
        MallOrderDTO mallOrderDTO = mallOrderDao.queryOrderDetail(orderNo);
        if (mallOrderDTO != null){
            mallOrderVO = new MallOrderVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);

            Organize organize = organizeDao.selectById(userVO.getOrganizeId());
            if (organize!=null){
                if (organize.getIsGroup()==1){  //??????
                    mallOrderVO.setAllowOperating(1);   //???????????????
                }else if (organize.getIsGroup()==0){    //??????
                    if (mallOrderDTO.getIsGroupSupply()==0){    //???????????????
                        mallOrderVO.setAllowOperating(1);   //???????????????
                    }else { //????????????
                        OrganizeRel organizeRel = organizeRelDao.selectOne(new QueryWrapper<OrganizeRel>().eq("organize_id", userVO.getOrganizeId()).eq("status", 2));
                        if (organizeRel != null) {
                            OrganizeGroup organizeGroup = organizeGroupDao.selectOne(new QueryWrapper<OrganizeGroup>().eq("organize_id", organizeRel.getGroupOrganizeId()));
                            if (organizeGroup != null && organizeGroup.getIsOpenMaintainable()) {
                                mallOrderVO.setAllowOperating(1);   //???????????????
                            } else {
                                mallOrderVO.setAllowOperating(0);   //???????????????
                            }
                        }
                    }
                }
            }

            //??????
            mallOrderVO.setShippingMoney(mallOrderDTO.getAllMoney().subtract(mallOrderDTO.getRealMoney()));

            //????????????
            mallOrderVO.setDiscountMoney(mallOrderDTO.getTotalMoney().subtract(mallOrderDTO.getRealMoney()));

            //???????????????????????????
            List<CouponDetailUser> list = couponDetailUserDao.selectList(new QueryWrapper<CouponDetailUser>().eq("order_sn",mallOrderVO.getCartOrderSn()));
            if (list!=null && list.size()>0){
                mallOrderVO.setIsCoupon(1);
            }else {
                mallOrderVO.setIsCoupon(0);
            }

            //order_at;payed_at;receive_at????????????????????????
            if (mallOrderVO.getOrderAt()!=null && mallOrderVO.getOrderAt()>0) {
                long time = Long.valueOf(mallOrderVO.getOrderAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setOrderAtStr(orderAtStr);
            }
            if (mallOrderVO.getPayedAt()!=null && mallOrderVO.getPayedAt()>0) {
                long time = Long.valueOf(mallOrderVO.getPayedAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setPayedAtStr(orderAtStr);
            }
            if (mallOrderVO.getReceiveAt()!=null && mallOrderVO.getReceiveAt()>0) {
                long time = Long.valueOf(mallOrderVO.getReceiveAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setReceiveAtStr(orderAtStr);
            }

            if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //???????????????????????????????????????????????????????????????
            }

            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                List<MallOrderDetailVO> detailVOList = new ArrayList<>();
                mallOrderDTO.getDetails().forEach(orderDetail->{
                    MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
                    BeanUtils.copyProperties(orderDetail,mallOrderDetailVO);

                    if (mallOrderDTO.getOrderType()!=null) {
                        if (mallOrderDTO.getOrderType() == 2) { //????????????????????????
                            //?????????????????????
                            List<MallSocialShareFlowDTO> mallSocialShareFlowDTOs = mallSocialShareFlowDao.queryByOrderDetailNo(mallOrderDetailVO.getOrderDetailNo());
                            if (mallSocialShareFlowDTOs != null && mallSocialShareFlowDTOs.size() > 0) {
                                List<MallSocialShareFlowVO> flowList = new ArrayList<>();
                                mallSocialShareFlowDTOs.forEach(dto -> {
                                    MallSocialShareFlowVO mallSocialShareFlowVO = new MallSocialShareFlowVO();
                                    BeanUtils.copyProperties(dto, mallSocialShareFlowVO);
                                    flowList.add(mallSocialShareFlowVO);
                                });
                                mallOrderDetailVO.setShareFlowInfo(flowList);
                            }
                        }else if (mallOrderDTO.getOrderType() == 1){    //?????????????????????
                            //?????????????????????
                            List<MallSocialShareFlowDTO> mallSocialShareFlowDTOs = mallSocialShareFlowDao.queryAllShareByOrderNo(mallOrderDTO.getOrderNo());
                            if (mallSocialShareFlowDTOs != null && mallSocialShareFlowDTOs.size() > 0) {
                                List<MallSocialShareFlowVO> flowList = new ArrayList<>();
                                mallSocialShareFlowDTOs.forEach(dto -> {
                                    MallSocialShareFlowVO mallSocialShareFlowVO = new MallSocialShareFlowVO();
                                    BeanUtils.copyProperties(dto, mallSocialShareFlowVO);
                                    flowList.add(mallSocialShareFlowVO);
                                });
                                mallOrderDetailVO.setShareFlowInfo(flowList);
                            }
                        }
                        detailVOList.add(mallOrderDetailVO);
                    }
                });
                mallOrderVO.setDetails(detailVOList);
            }

            //????????????
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(mallOrderVO.getShopNo());
            if (mallShopDTO==null){
                return ReturnResponse.failed("??????????????????");
            }
            MallShopVO mallShopVO = new MallShopVO();
            BeanUtils.copyProperties(mallShopDTO,mallShopVO);
            mallOrderVO.setShopInfo(mallShopVO);

            //????????????
            Organize organ = organizeDao.selectById(mallShopDTO.getOrganizeId());
            if (organ!=null){
                mallOrderVO.setOrganizeName(organ.getFullName());
            }

            //??????????????????????????????
            List<String> orderDetailNoList = null;
            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                orderDetailNoList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getOrderDetailNo).collect(Collectors.toList());
            }

            Integer orderType = mallOrderVO.getOrderType();
            //????????????
            if (orderDetailNoList!=null) {
                List<MallAfterSalesDTO> mallAfterSalesDTOs = mallAfterSalesDao.querySalesByOrderDetailNoList(orderDetailNoList, orderType, orderNo);
                if (mallAfterSalesDTOs != null && mallAfterSalesDTOs.size() > 0) {
                    //????????????????????????
                    List<String> afterSalesNoList = new ArrayList<>();
                    List<MallAfterSalesVO> voList = new ArrayList<>();
                    mallAfterSalesDTOs.forEach(dto -> {
                        MallAfterSalesVO mallAfterSalesVO = new MallAfterSalesVO();
                        BeanUtils.copyProperties(dto, mallAfterSalesVO);
                        //??????createdTime????????????
                        if (mallAfterSalesVO.getCreatedTime()!=null) {
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                            mallAfterSalesVO.setCreatedAtStr(orderAtStr);
                        }
                        voList.add(mallAfterSalesVO);
                        afterSalesNoList.add(dto.getAfterSalesNo());
                    });
                    mallOrderVO.setAfterSales(voList);

                    //??????????????????
                    List<MallAfterSalesLogDTO> mallAfterSalesLogDTOs = mallAfterSalesLogDao.querySalesLogByShopNoAndSalesNoList(afterSalesNoList);
                    if (mallAfterSalesLogDTOs != null && mallAfterSalesLogDTOs.size() > 0) {
                        List<MallAfterSalesLogVO> salesLogVOList = new ArrayList<>();
                        mallAfterSalesLogDTOs.forEach(dto -> {
                            MallAfterSalesLogVO mallAfterSalesLogVO = new MallAfterSalesLogVO();
                            BeanUtils.copyProperties(dto, mallAfterSalesLogVO);
                            salesLogVOList.add(mallAfterSalesLogVO);
                        });
                        mallOrderVO.setAfterSalesLog(salesLogVOList);
                    }

                    //??????????????????????????????
                    List<MallBuyerShippingDTO> mallBuyerShippingDTOs = mallBuyerShippingDao.queryByAfterSalesNoList(afterSalesNoList);
                    if (mallBuyerShippingDTOs != null && mallBuyerShippingDTOs.size() > 0) {
                        //????????????????????????
                        List<String> buyerShippingNoList = new ArrayList<>();
                        List<MallBuyerShippingVO> buyerShippingVOList = new ArrayList<>();
                        mallBuyerShippingDTOs.forEach(dto -> {
                            MallBuyerShippingVO mallBuyerShippingVO = new MallBuyerShippingVO();
                            BeanUtils.copyProperties(dto, mallBuyerShippingVO);
                            buyerShippingVOList.add(mallBuyerShippingVO);
                            buyerShippingNoList.add(dto.getBuyerShippingNo());
                        });
                        mallOrderVO.setBuyerShipping(buyerShippingVOList);

                        //??????????????????????????????????????????
                        List<MallBuyerShippingLogDTO> mallBuyerShippingLogDTOs = mallBuyerShippingLogDao.queryListByBuyerShippingNoList(buyerShippingNoList);
                        if (mallBuyerShippingLogDTOs != null && mallBuyerShippingLogDTOs.size() > 0) {
                            List<MallBuyerShippingLogVO> buyerShippingLogVOList = new ArrayList<>();
                            mallBuyerShippingLogDTOs.forEach(dto -> {
                                MallBuyerShippingLogVO mallBuyerShippingLogVO = new MallBuyerShippingLogVO();
                                BeanUtils.copyProperties(dto, mallBuyerShippingLogVO);
                                buyerShippingLogVOList.add(mallBuyerShippingLogVO);
                            });
                            mallOrderVO.setBuyerShippingLog(buyerShippingLogVOList);
                        }
                    }
                }
            }
            //????????????????????????
            MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(orderNo);
            if (mallShopShippingDTO!=null){
                MallShopShippingVO mallShopShippingVO = new MallShopShippingVO();
                BeanUtils.copyProperties(mallShopShippingDTO,mallShopShippingVO);
                mallOrderVO.setShopShipping(mallShopShippingVO);

                //????????????????????????????????????
                List<MallShopShippingLogDTO> mallShopShippingLogDTOs = mallShopShippingLogDao.selectListByShopShippingNo(mallShopShippingVO.getShopShippingNo());
                if (mallShopShippingLogDTOs!=null && mallShopShippingLogDTOs.size()>0) {
                    List<MallShopShippingLogVO> voList = new ArrayList<>();
                    mallShopShippingLogDTOs.forEach(dto->{
                        MallShopShippingLogVO mallShopShippingLogVO = new MallShopShippingLogVO();
                        BeanUtils.copyProperties(dto,mallShopShippingLogVO);
                        voList.add(mallShopShippingLogVO);
                    });

                    mallOrderVO.setShopShippingLog(voList);
                }
            }

            //??????????????????????????????
            if (orderDetailNoList!=null) {
                List<MallOrderDetailSpecDTO> mallOrderDetailSpecDTOs = mallOrderDetailSpecDao.queryListByOrderDetailNoList(orderDetailNoList);
                if (mallOrderDetailSpecDTOs != null && mallOrderDetailSpecDTOs.size() > 0) {
                    List<MallOrderDetailSpecVO> voList = new ArrayList<>();
                    mallOrderDetailSpecDTOs.forEach(dto -> {
                        MallOrderDetailSpecVO mallOrderDetailSpecVO = new MallOrderDetailSpecVO();
                        BeanUtils.copyProperties(dto, mallOrderDetailSpecVO);
                        voList.add(mallOrderDetailSpecVO);
                    });
                    Map<String, List<MallOrderDetailSpecVO>> detailNoMap = voList.stream().collect(Collectors.groupingBy(input -> input.getOrderDetailNo()));
                    mallOrderVO.getDetails().forEach(detail -> {
                        if (detailNoMap.containsKey(detail.getOrderDetailNo())) {
                            detail.setSpecs(detailNoMap.get(detail.getOrderDetailNo()));
                        }
                    });
                }
            }

        }
        return ReturnResponse.success(mallOrderVO);
    }

    @Override
    public ReturnResponse<MallOrderVO> queryDetailByPickupNo(String pickupNo, String orderNo) {
        UserVO userVO = getUser();  //????????????????????????
        MallOrderDTO mallOrderDTO = mallOrderDao.queryDetailByPickupNo(pickupNo, userVO.getShopNo());
        if (mallOrderDTO!=null){

            if (mallOrderDTO.getIsGroupSupply()==1) {   //????????????
                //????????????????????????????????????????????????
                boolean checkd = checkOrder(userVO.getOrganizeId());
                if (!checkd) {
                    return ReturnResponse.failed("??????????????????????????????");
                }
            }

            //??????pickNo????????????????????????orderNo?????????
            if (StringUtils.isNotEmpty(orderNo)){
                //orderNo?????????????????????????????????????????????????????????
                if (!orderNo.equals(mallOrderDTO.getOrderNo())){
                    return ReturnResponse.failed("??????????????????????????????????????????????????????");
                }
            }
            MallOrderVO mallOrderVO = new MallOrderVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);

            //order_at;payed_at;receive_at????????????????????????
            if (mallOrderVO.getOrderAt()!=null && mallOrderVO.getOrderAt()>0) {
                long time = Long.valueOf(mallOrderVO.getOrderAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setOrderAtStr(orderAtStr);
            }
            if (mallOrderVO.getPayedAt()!=null && mallOrderVO.getPayedAt()>0) {
                long time = Long.valueOf(mallOrderVO.getPayedAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setPayedAtStr(orderAtStr);
            }
            if (mallOrderVO.getReceiveAt()!=null && mallOrderVO.getReceiveAt()>0) {
                long time = Long.valueOf(mallOrderVO.getReceiveAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setReceiveAtStr(orderAtStr);
            }

            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                List<String> orderDetailNoList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getOrderDetailNo).collect(Collectors.toList());
                List<MallOrderDetailSpecDTO> specList = mallOrderDetailSpecDao.queryListByOrderDetailNoList(orderDetailNoList);     //??????????????????????????????
                Map<String, List<MallOrderDetailSpecVO>> map = null;
                if (specList!=null){
                    List<MallOrderDetailSpecVO> specVoList = new ArrayList<>();
                    specList.forEach(spec->{
                        MallOrderDetailSpecVO mallOrderDetailSpecVO = new MallOrderDetailSpecVO();
                        BeanUtils.copyProperties(spec,mallOrderDetailSpecVO);
                        specVoList.add(mallOrderDetailSpecVO);
                    });
                    map = specVoList.stream().collect(Collectors.groupingBy(input -> input.getOrderDetailNo()));        //???specVO???????????????map???????????????????????????????????????
                }else {
                    map = new HashMap<>();
                }
                List<MallOrderDetailVO> list = new ArrayList<>();
                for(MallOrderDetailDTO dto : mallOrderDTO.getDetails()){
                    MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
                    BeanUtils.copyProperties(dto,mallOrderDetailVO);
                    if (map.containsKey(mallOrderDetailVO.getOrderDetailNo())){
                        mallOrderDetailVO.setSpecs(map.get(mallOrderDetailVO.getOrderDetailNo()));
                    }
                    list.add(mallOrderDetailVO);
                }
                mallOrderVO.setDetails(list);
            }
            return ReturnResponse.success(mallOrderVO);
        }
        return ReturnResponse.failed("?????????????????????????????????");
    }

    @Override
    public ReturnResponse<String> verPickupNo(String pickupNo, String orderNo) {
        UserVO userVO = getUser();  //????????????????????????
//        MallOrderDTO mallOrderDTO = mallOrderDao.queryDetailByOrderNo(orderNo, userVO.getShopNo());
        MallOrderDTO mallOrderDTO = mallOrderDao.queryDetailByPickupNo(pickupNo, userVO.getShopNo());
        if (mallOrderDTO!=null){

            if (mallOrderDTO.getIsGroupSupply()==1) {   //????????????
                //????????????????????????????????????????????????
                boolean checkd = checkOrder(userVO.getOrganizeId());
                if (!checkd) {
                    return ReturnResponse.failed("??????????????????????????????");
                }
            }

            //??????pickNo????????????????????????orderNo?????????
            if (StringUtils.isNotEmpty(orderNo)){
                //orderNo?????????????????????????????????????????????????????????
                if (!orderNo.equals(mallOrderDTO.getOrderNo())){
                    return ReturnResponse.failed("??????????????????????????????????????????????????????");
                }
            }
            Long time = new Date().getTime()/1000;      //????????????????????????
            mallOrderDTO.setReceiveAt(time.intValue());
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(userVO.getShopNo());
            if (mallOrderDTO==null){
                return ReturnResponse.failed("??????????????????");
            }
            //???????????????
            MallOrder mallOrder = new MallOrder();
            mallOrder.setId(mallOrderDTO.getId());
            mallOrder.setReceiveAt(time.intValue());
            mallOrder.setAfterSalesEndAt(time.intValue()+mallShopDTO.getAsHoldDays()*24*60*60);
            mallOrder.setStatus(40);
            mallOrder.setSubStatus(4060);
            Long timeAt = new Date().getTime()/1000;
            mallOrder.setReceiveAt(timeAt.intValue());  //??????????????????
            mallOrderDao.updateById(mallOrder);
            return ReturnResponse.success("????????????");
        }
        return ReturnResponse.failed("???????????????????????????");
    }

    /**
     * ??????excel????????????????????????
     * @param list
     */
    private static void salesStateCodeToString(List<Map<String, Object>> list) {
        if (list != null && list.size() > 0) {
            list.forEach(map -> {
                //payType
                if (Objects.equals(map.get("payType"), 1)) {
                    map.put("payType", "?????????");
                } else if (Objects.equals(map.get("payType"), 2)) {
                    map.put("payType", "??????");
                } else {
                    map.put("payType", "??????");
                }
                //status
                if (Objects.equals(map.get("status"), 10)) {
                    map.put("status", "?????????");
                } else if (Objects.equals(map.get("status"), 20)) {
                    map.put("status", "?????????");
                } else if (Objects.equals(map.get("status"), 30)) {
                    map.put("status", "?????????");
                } else if (Objects.equals(map.get("status"), 40)) {
                    map.put("status", "?????????");
                } else if (Objects.equals(map.get("status"), 50)) {
                    map.put("status", "?????????");
                } else if (Objects.equals(map.get("status"), 60)) {
                    map.put("status", "??????");
                }
                //isEnableShare
                if (Objects.equals(map.get("isEnableShare"), 0)) {
                    map.put("isEnableShare", "???");
                } else if (Objects.equals(map.get("isEnableShare"), 1)) {
                    map.put("isEnableShare", "???");
                }
                //asStatus
                if (Objects.equals(map.get("asStatus"), 100)) {
                    map.put("asStatus", "??????");
                } else if (Objects.equals(map.get("asStatus"), 110)) {
                    map.put("asStatus", "???");
                } else if (Objects.equals(map.get("asStatus"), 111)) {
                    map.put("asStatus", "???");
                }
                //deliverType
                if (Objects.equals(map.get("deliverType"), 1)) {
                    map.put("deliverType", "??????");
                } else if (Objects.equals(map.get("deliverType"), 2)) {
                    map.put("deliverType", "??????");
                }
            });
        }
    }

    @Override
    public CommonResult<BasePageResult<MallOrderUAppVO>> queryMallOrderListByUApp(Long page, Long pageSize, String mallOrderQuery, Integer status, Integer deliverType, Long orderAtBegin, Long orderAtEnd, String nextNo) {
        UserVO userVO = getUser();  //????????????????????????
        List<MallOrderUAppVO> list = new ArrayList<>();
        Integer count = null;   //?????????
        MallOrderUAppQuery mallOrderUAppQuery = new MallOrderUAppQuery();  //????????????
        mallOrderUAppQuery.setStatus(status);
        mallOrderUAppQuery.setDeliverType(deliverType);
        mallOrderUAppQuery.setMallOrderQuery(mallOrderQuery);
        mallOrderUAppQuery.setShopNo(userVO.getShopNo());
        mallOrderUAppQuery.setOrderAtBegin(orderAtBegin);
        mallOrderUAppQuery.setOrderAtEnd(orderAtEnd);
        try {
            //???????????????
            count = mallOrderDao.getTrendMallOrderByUAppPageSize(mallOrderUAppQuery);
            if (count!=null && count>0) {
                Long startNum = 0l;
//                page = (page - 1) * pageSize;
                List<MallOrderUAppDTO> mallDTOList = null;
                if (nextNo!=null && !"".equals(nextNo)) {
                    List<String> orderNoList = mallOrderDao.getOrderListByOrderNoUApp(mallOrderUAppQuery, 0, pageSize * page);
                    int row = orderNoList.indexOf(nextNo);
                    startNum = Long.valueOf(row + 1);
                    //??????
                    mallDTOList = mallOrderDao.getTrendMallOrderByPageUApp(startNum, pageSize, mallOrderUAppQuery);
                }else {
                    startNum = page - 1;
                    //??????
                    mallDTOList = mallOrderDao.getTrendMallOrderByPageUApp(startNum*pageSize, pageSize, mallOrderUAppQuery);
                }
                //page = mallOrderDao.getTrendMallOrderPage(pageQuery, mallOrderQuery);
                MallOrderUAppVO mallOrderVo = null;
                if (mallDTOList != null && mallDTOList.size() > 0) {

                    for (MallOrderUAppDTO mallOrderDTO : mallDTOList) {
                        if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                            mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //???????????????????????????????????????????????????????????????
                        }
                        mallOrderVo = new MallOrderUAppVO();
                        BeanUtils.copyProperties(mallOrderDTO, mallOrderVo);

                        //???????????? ????????????+??????
                        mallOrderVo.setTotalMoney(mallOrderDTO.getTotalMoney().add(mallOrderDTO.getAllMoney().subtract(mallOrderDTO.getRealMoney())));

                        //order_at;payed_at;receive_at????????????????????????
                        if (mallOrderVo.getOrderAt()!=null && mallOrderVo.getOrderAt()>0) {
                            long time = Long.valueOf(mallOrderVo.getOrderAt())*1000;
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                            mallOrderVo.setOrderAtStr(orderAtStr);
                        }
                        if (mallOrderVo.getPayedAt()!=null && mallOrderVo.getPayedAt()>0) {
                            long time = Long.valueOf(mallOrderVo.getPayedAt())*1000;
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                            mallOrderVo.setPayedAtStr(orderAtStr);
                        }

                        //????????????????????????????????????
                        List<String> itemNames = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getItemName).collect(Collectors.toList());

                        mallOrderVo.setItemName(itemNames);
                        //????????????????????????????????????
                        List<String> goodsNos = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getGoodsNo).filter(x -> x != null && !"".equals(x)).collect(Collectors.toList());
                        mallOrderVo.setGoodsNo(goodsNos);
                        List<Integer> skuQuantity = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getSkuQuantity).collect(Collectors.toList());
                        if (skuQuantity != null && skuQuantity.size() > 0) {
                            int quantity = skuQuantity.stream().reduce(Integer::sum).orElse(0);
                            mallOrderVo.setQuantity(quantity);
                        }

                        if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                            List<MallOrderDetailUAppVO> detailVOList = new ArrayList<>();
                            mallOrderDTO.getDetails().forEach(detailDto->{
                                MallOrderDetailUAppVO mallOrderDetailVO = new MallOrderDetailUAppVO();
                                BeanUtils.copyProperties(detailDto,mallOrderDetailVO);
                                detailVOList.add(mallOrderDetailVO);
                            });
                            mallOrderVo.setDetails(detailVOList);

                        }

                        //??????????????????
                        List<MallAfterSales> mallAfterSalesList = mallAfterSalesDao.selectList(new QueryWrapper<MallAfterSales>().eq("order_no", mallOrderVo.getOrderNo()));
                        if (mallAfterSalesList != null && mallAfterSalesList.size() > 0) {
                            List<Integer> subStatusList = mallAfterSalesList.stream().map(MallAfterSales::getSubStatus).collect(Collectors.toList());
                            if (subStatusList.contains(1010) || subStatusList.contains(1030) || subStatusList.contains(1050) || subStatusList.contains(1070) || subStatusList.contains(2010)) {
                                //???????????????
                                mallOrderVo.setAfterSalesStatus(1);
                            } else {
                                mallOrderVo.setAfterSalesStatus(2);
                            }
                        } else {
                            mallOrderVo.setAfterSalesStatus(0);
                        }
                        if (mallOrderVo.getAfterSalesStatus() != null) {
                            //??????????????????????????????
                            if (mallOrderVo.getAfterSalesStatus() != 0) {
                                mallOrderVo.setAsStatus(111);  //111: ????????????????????????????????????????????????
                            } else {
                                if (mallOrderVo.getAfterSalesEndAt() != null && mallOrderVo.getAfterSalesEndAt() > new Date().getTime() / 1000) {
                                    mallOrderVo.setAsStatus(100);  //100: ?????????????????????????????????????????????????????????
                                } else {
                                    mallOrderVo.setAsStatus(110);  //110: ????????????????????????????????????????????????
                                }
                            }
                        }
                        MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(mallOrderVo.getShopNo());
                        mallOrderVo.setAllowOperating(0);
                        if (null != mallShopDTO) {
                            Organize organize = organizeDao.selectById(mallShopDTO.getOrganizeId());
                            if (1 != organize.getIsGroup()) {
                                List<OrganizeGroup> organizeGroups = organizeGroupDao.selectList(new QueryWrapper<OrganizeGroup>().eq("organize_id", mallShopDTO.getOrganizeId()));
                                if (CollectionUtil.isNotEmpty(organizeGroups)) {
                                    mallOrderVo.setAllowOperating(organizeGroups.get(0).getIsOpenMaintainable()?1:0);
                                }
                            }
                        }
                        list.add(mallOrderVo);
                    }
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        BasePageResult<MallOrderUAppVO> basePageResult = new BasePageResult<>();
        basePageResult.setPage(page);
        basePageResult.setPageSize(pageSize);
        basePageResult.setTotal(count);
        basePageResult.setResult(list);
        return CommonResult.success(basePageResult);
    }

    @Override
    public ReturnResponse<MallOrderUAppVO> queryOrderDetailByUApp(String orderNo) {
        UserVO userVO = getUser();  //????????????????????????
        MallOrderUAppVO mallOrderVO = null;
        MallOrderUAppDTO mallOrderDTO = mallOrderDao.queryOrderDetailByUApp(orderNo);
        if (mallOrderDTO != null){
            mallOrderVO = new MallOrderUAppVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);

            String orderAtStrMin = "";
            String payedAtStrMin = "";
            String receiveAtStrMin = "";
            String sendAtStrMin = "";
            String afterSalesEndAtStrMin = "";
            String cancelAtStrMin = "";
            //order_at;payed_at;receive_at????????????????????????
            if (mallOrderVO.getOrderAt()!=null && mallOrderVO.getOrderAt()>0) {
                long time = Long.valueOf(mallOrderVO.getOrderAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setOrderAtStr(orderAtStr);
                orderAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm");
            }
            if (mallOrderVO.getPayedAt()!=null && mallOrderVO.getPayedAt()>0) {
                long time = Long.valueOf(mallOrderVO.getPayedAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setPayedAtStr(orderAtStr);
                payedAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm");
            }
            if (mallOrderVO.getReceiveAt()!=null && mallOrderVO.getReceiveAt()>0) {
                long time = Long.valueOf(mallOrderVO.getReceiveAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setReceiveAtStr(orderAtStr);
                receiveAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm");
            }
            if (mallOrderVO.getSendAt()!=null && mallOrderVO.getSendAt()>0) {
                long time = Long.valueOf(mallOrderVO.getSendAt())*1000;
                String sendAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setSendAtStr(sendAtStr);
                sendAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm");
            }
            if (mallOrderVO.getAfterSalesEndAt()!=null && mallOrderVO.getAfterSalesEndAt()>0) {
                long time = Long.valueOf(mallOrderVO.getAfterSalesEndAt())*1000;
                String sendAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setAfterSalesEndAtStr(sendAtStr);   //??????????????????,????????????????????????
                afterSalesEndAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm");
            }
            if (mallOrderVO.getCancelAt()!=null && mallOrderVO.getCancelAt()>0) {
                long time = Long.valueOf(mallOrderVO.getCancelAt())*1000;
                String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setCancelAtStr(orderAtStr);
                cancelAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm");
            }

            if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //???????????????????????????????????????????????????????????????
            }

            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                List<MallOrderDetailUAppVO> detailVOList = new ArrayList<>();

                for(MallOrderDetailDTO orderDetail : mallOrderDTO.getDetails()){
                    MallOrderDetailUAppVO mallOrderDetailVO = new MallOrderDetailUAppVO();
                    BeanUtils.copyProperties(orderDetail,mallOrderDetailVO);
                    detailVOList.add(mallOrderDetailVO);
                }
                mallOrderVO.setDetails(detailVOList);

            }

            if (mallOrderDTO.getOrderType()!=null) {
                if (mallOrderDTO.getStatus() != 50 && mallOrderDTO.getStatus() != 10){  //?????????????????????????????????????????????
                    BigDecimal manageMoney = new BigDecimal(0);
                    BigDecimal distributionMoney = new BigDecimal(0);
                    //?????????????????????
                    List<MallSocialShareFlowDTO> mallSocialShareFlowDTOs = mallSocialShareFlowDao.queryAllShareByOrderNo(mallOrderDTO.getOrderNo());
                    if (mallSocialShareFlowDTOs != null && mallSocialShareFlowDTOs.size() > 0) {
                        List<MallSocialShareFlowUAppVO> flowList = new ArrayList<>();
                        for(MallSocialShareFlowDTO dto : mallSocialShareFlowDTOs) {
                            MallSocialShareFlowUAppVO mallSocialShareFlowVO = new MallSocialShareFlowUAppVO();
                            BeanUtils.copyProperties(dto, mallSocialShareFlowVO);
                            flowList.add(mallSocialShareFlowVO);
                            if (dto.getShareType()==0){ //????????????
                                distributionMoney = distributionMoney.add(dto.getAmount());
                            }else if (dto.getShareType()==1){   //????????????
                                manageMoney = manageMoney.add(dto.getAmount());
                            }
                        }
                    }
                    mallOrderVO.setShareManageMoney(manageMoney);
                    mallOrderVO.setShareDistributionMoney(distributionMoney);
                }
            }

            //????????????
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(mallOrderVO.getShopNo());
            if (mallShopDTO==null){
                return ReturnResponse.failed("??????????????????");
            }
            MallShopUAppVO mallShopVO = new MallShopUAppVO();
            BeanUtils.copyProperties(mallShopDTO,mallShopVO);
            mallOrderVO.setShopInfo(mallShopVO);

            //??????????????????????????????
            List<String> orderDetailNoList = null;
            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                orderDetailNoList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getOrderDetailNo).collect(Collectors.toList());
                //?????????????????????
                int quantity = mallOrderDTO.getDetails().stream().collect(Collectors.summingInt(MallOrderDetailDTO::getSkuQuantity));
                mallOrderVO.setQuantity(quantity);
            }

            Integer orderType = mallOrderVO.getOrderType();
            //????????????
            if (orderDetailNoList!=null) {
                List<MallAfterSalesDTO> mallAfterSalesDTOs = mallAfterSalesDao.querySalesByOrderDetailNoList(orderDetailNoList, orderType, orderNo);
                if (mallAfterSalesDTOs != null && mallAfterSalesDTOs.size() > 0) {
                    //????????????????????????
                    List<String> afterSalesNoList = new ArrayList<>();
                    List<MallAfterSalesUAppVO> voList = new ArrayList<>();
                    mallAfterSalesDTOs.forEach(dto -> {
                        MallAfterSalesUAppVO mallAfterSalesVO = new MallAfterSalesUAppVO();
                        BeanUtils.copyProperties(dto, mallAfterSalesVO);
                        //??????createdTime????????????
                        if (mallAfterSalesVO.getCreatedTime()!=null) {
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                            mallAfterSalesVO.setCreatedAtStr(orderAtStr);
                        }
                        voList.add(mallAfterSalesVO);
                        afterSalesNoList.add(dto.getAfterSalesNo());
                    });
                    mallOrderVO.setAfterSales(voList);

                    //??????????????????
                    List<MallAfterSalesLogDTO> mallAfterSalesLogDTOs = mallAfterSalesLogDao.querySalesLogByShopNoAndSalesNoList(afterSalesNoList);
                    if (mallAfterSalesLogDTOs != null && mallAfterSalesLogDTOs.size() > 0) {
                        List<MallAfterSalesLogUAppVO> salesLogVOList = new ArrayList<>();
                        mallAfterSalesLogDTOs.forEach(dto -> {
                            MallAfterSalesLogUAppVO mallAfterSalesLogVO = new MallAfterSalesLogUAppVO();
                            BeanUtils.copyProperties(dto, mallAfterSalesLogVO);
                            salesLogVOList.add(mallAfterSalesLogVO);
                        });
                        mallOrderVO.setAfterSalesLog(salesLogVOList);
                    }

                    //??????????????????????????????
                    List<MallBuyerShippingDTO> mallBuyerShippingDTOs = mallBuyerShippingDao.queryByAfterSalesNoList(afterSalesNoList);
                    if (mallBuyerShippingDTOs != null && mallBuyerShippingDTOs.size() > 0) {
                        //????????????????????????
                        List<String> buyerShippingNoList = new ArrayList<>();
                        List<MallBuyerShippingUAppVO> buyerShippingVOList = new ArrayList<>();
                        mallBuyerShippingDTOs.forEach(dto -> {
                            MallBuyerShippingUAppVO mallBuyerShippingVO = new MallBuyerShippingUAppVO();
                            BeanUtils.copyProperties(dto, mallBuyerShippingVO);
                            buyerShippingVOList.add(mallBuyerShippingVO);
                            buyerShippingNoList.add(dto.getBuyerShippingNo());
                        });
                        mallOrderVO.setBuyerShipping(buyerShippingVOList);

                        //??????????????????????????????????????????
                        List<MallBuyerShippingLogDTO> mallBuyerShippingLogDTOs = mallBuyerShippingLogDao.queryListByBuyerShippingNoList(buyerShippingNoList);
                        if (mallBuyerShippingLogDTOs != null && mallBuyerShippingLogDTOs.size() > 0) {
                            List<MallBuyerShippingLogUAppVO> buyerShippingLogVOList = new ArrayList<>();
                            mallBuyerShippingLogDTOs.forEach(dto -> {
                                MallBuyerShippingLogUAppVO mallBuyerShippingLogVO = new MallBuyerShippingLogUAppVO();
                                BeanUtils.copyProperties(dto, mallBuyerShippingLogVO);
                                buyerShippingLogVOList.add(mallBuyerShippingLogVO);
                            });
                            mallOrderVO.setBuyerShippingLog(buyerShippingLogVOList);
                        }
                    }
                }
            }
            //????????????????????????
            MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(orderNo);
            if (mallShopShippingDTO!=null){
                MallShopShippingUAppVO mallShopShippingVO = new MallShopShippingUAppVO();
                BeanUtils.copyProperties(mallShopShippingDTO,mallShopShippingVO);
                mallOrderVO.setShopShipping(mallShopShippingVO);

                //????????????????????????????????????
                List<MallShopShippingLogDTO> mallShopShippingLogDTOs = mallShopShippingLogDao.selectListByShopShippingNo(mallShopShippingVO.getShopShippingNo());
                if (mallShopShippingLogDTOs!=null && mallShopShippingLogDTOs.size()>0) {
                    List<MallShopShippingLogUAppVO> voList = new ArrayList<>();
                    mallShopShippingLogDTOs.forEach(dto->{
                        MallShopShippingLogUAppVO mallShopShippingLogVO = new MallShopShippingLogUAppVO();
                        BeanUtils.copyProperties(dto,mallShopShippingLogVO);
                        voList.add(mallShopShippingLogVO);
                    });

                    mallOrderVO.setShopShippingLog(voList);
                }
            }

            //??????????????????????????????
            if (orderDetailNoList!=null) {
                List<MallOrderDetailSpecDTO> mallOrderDetailSpecDTOs = mallOrderDetailSpecDao.queryListByOrderDetailNoList(orderDetailNoList);
                if (mallOrderDetailSpecDTOs != null && mallOrderDetailSpecDTOs.size() > 0) {
                    List<MallOrderDetailSpecUAppVO> voList = new ArrayList<>();
                    mallOrderDetailSpecDTOs.forEach(dto -> {
                        MallOrderDetailSpecUAppVO mallOrderDetailSpecVO = new MallOrderDetailSpecUAppVO();
                        BeanUtils.copyProperties(dto, mallOrderDetailSpecVO);
                        voList.add(mallOrderDetailSpecVO);
                    });
                    Map<String, List<MallOrderDetailSpecUAppVO>> detailNoMap = voList.stream().collect(Collectors.groupingBy(input -> input.getOrderDetailNo()));
                    mallOrderVO.getDetails().forEach(detail -> {
                        if (detailNoMap.containsKey(detail.getOrderDetailNo())) {
                            detail.setSpecs(detailNoMap.get(detail.getOrderDetailNo()));
                        }
                    });
                }
            }

            //????????????????????????
            MallOrderEnum.CancelReason cancelReason = MallOrderEnum.CancelReason.parseCode(mallOrderVO.getCancelReason());
            if(null != cancelReason){
                mallOrderVO.setCancelReasonName(cancelReason.getText());
            }

            //????????????????????????
            if (mallOrderVO.getStatus()==10) {  //?????????
                mallOrderVO.setHeadField(orderAtStrMin+" ???????????????????????????");
            }
            if (mallOrderVO.getStatus()==20) {  //?????????
                mallOrderVO.setHeadField(payedAtStrMin+" ?????????????????????????????????");
            }
            if (mallOrderVO.getStatus()==30) {  //?????????
                if (mallOrderVO.getDeliverType()==2){  //??????
                    mallOrderVO.setHeadField(payedAtStrMin+" ??????????????????????????????????????????");
                }else { //???????????????
                    if (mallOrderVO.getDeliverMethod()==10) {    //??????
                        String n =  mallShopShippingDTO!=null?mallShopShippingDTO.getNumber():"";
                        String shipCompany =  mallShopShippingDTO!=null?mallShopShippingDTO.getCompany():"";
                        mallOrderVO.setHeadField("?????????"+sendAtStrMin+"????????????????????????"+n+" "+shipCompany);
                    }else { //????????????
                        mallOrderVO.setHeadField("?????????"+sendAtStrMin+"??????????????????????????????");
                    }
                }
            }
            if (mallOrderVO.getStatus()==40){   //?????????
                if (mallOrderVO.getDeliverType()==2) {  //??????
                    mallOrderVO.setHeadField(receiveAtStrMin+" ????????????????????????");
                }else {
                    if (mallOrderVO.getSubStatus()==4010){  //4010-??????????????????
                        mallOrderVO.setHeadField(receiveAtStrMin+" ???????????????????????????");
                    }else {
                        mallOrderVO.setHeadField(receiveAtStrMin + " ???????????????");
                    }
                }
            }
            if (mallOrderVO.getStatus()==50){   //?????????
                if (mallOrderVO.getSubStatus()==5020) { //5020-??????????????????(?????????)
                    mallOrderVO.setHeadField("???????????????????????????????????????"+cancelAtStrMin+"?????????????????????");
                }
                if (mallOrderVO.getSubStatus()==5010 || mallOrderVO.getSubStatus()==5030){ //5010-??????????????????(?????????)  5030-??????????????????(?????????)
                    mallOrderVO.setHeadField("?????????"+cancelAtStrMin+"???????????????");
                }
                if (mallOrderVO.getSubStatus()==1030 || mallOrderVO.getSubStatus()==5060){  //1030-????????????- ?????????  5060-????????????????????????-???????????????
                    mallOrderVO.setHeadField("????????????????????????"+cancelAtStrMin+"?????????????????????");
                }
                if (mallOrderVO.getSubStatus()==5040 || mallOrderVO.getSubStatus()==5050){  //5040-????????????(?????????)  5050-????????????????????????-???????????????
                    mallOrderVO.setHeadField(cancelAtStrMin+"?????????????????????\r\n????????????"+mallOrderVO.getCancelReasonName()+"??????????????????????????????");
                }
            }

        }
        MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(mallOrderVO.getShopNo());
        if (mallShopDTO != null) {
            Organize organize = organizeDao.selectById(mallShopDTO.getOrganizeId());
            mallOrderVO.setOrganizeName(organize.getFullName());
            if (1 == organize.getIsGroup()) {
                mallOrderVO.setAllowOperating(0);
            } else {
                List<OrganizeGroup> organizeGroups = organizeGroupDao.selectList(new QueryWrapper<OrganizeGroup>().eq("organize_id", mallShopDTO.getOrganizeId()));
                if (CollectionUtil.isNotEmpty(organizeGroups) && null != organizeGroups.get(0).getIsOpenMaintainable()) {
                    mallOrderVO.setAllowOperating(organizeGroups.get(0).getIsOpenMaintainable()?1:0);
                } else {
                    mallOrderVO.setAllowOperating(0);
                }
            }
        }
        return ReturnResponse.success(mallOrderVO);
    }

    @Override
    public ReturnResponse<MallOrderUAppVO> queryDetailByPickupNoUApp(String pickupNo, String orderNo) {
        UserVO userVO = getUser();  //????????????????????????
        MallOrderUAppDTO mallOrderDTO = mallOrderDao.queryDetailByPickupNoUApp(pickupNo, userVO.getShopNo());
        if (mallOrderDTO!=null) {

            if (mallOrderDTO.getIsGroupSupply()==1) {
                //????????????????????????????????????????????????
                boolean checkd = checkOrder(userVO.getOrganizeId());
                if (!checkd) {
                    return ReturnResponse.failed("??????????????????????????????");
                }
            }

            if (mallOrderDTO.getStatus() == 30 && mallOrderDTO.getSubStatus() == 3010) {
                //??????pickNo????????????????????????orderNo?????????
                if (StringUtils.isNotEmpty(orderNo)) {
                    //orderNo?????????????????????????????????????????????????????????
                    if (!orderNo.equals(mallOrderDTO.getOrderNo())) {
                        return ReturnResponse.failed("??????????????????????????????????????????????????????");
                    }
                }
                MallOrderUAppVO mallOrderVO = new MallOrderUAppVO();
                BeanUtils.copyProperties(mallOrderDTO, mallOrderVO);

                String orderAtStrMin = "";
                String payedAtStrMin = "";
                String receiveAtStrMin = "";
                String sendAtStrMin = "";
                String afterSalesEndAtStrMin = "";
                String cancelAtStrMin = "";
                //order_at;payed_at;receive_at????????????????????????
                if (mallOrderVO.getOrderAt() != null && mallOrderVO.getOrderAt() > 0) {
                    long time = Long.valueOf(mallOrderVO.getOrderAt()) * 1000;
                    String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
                    mallOrderVO.setOrderAtStr(orderAtStr);
                    orderAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm");
                }
                if (mallOrderVO.getPayedAt() != null && mallOrderVO.getPayedAt() > 0) {
                    long time = Long.valueOf(mallOrderVO.getPayedAt()) * 1000;
                    String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
                    mallOrderVO.setPayedAtStr(orderAtStr);
                    payedAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm");
                }
                if (mallOrderVO.getReceiveAt() != null && mallOrderVO.getReceiveAt() > 0) {
                    long time = Long.valueOf(mallOrderVO.getReceiveAt()) * 1000;
                    String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
                    mallOrderVO.setReceiveAtStr(orderAtStr);
                    receiveAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm");
                }
                if (mallOrderVO.getSendAt() != null && mallOrderVO.getSendAt() > 0) {
                    long time = Long.valueOf(mallOrderVO.getSendAt()) * 1000;
                    String sendAtStr = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
                    mallOrderVO.setSendAtStr(sendAtStr);
                    sendAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm");
                }
                if (mallOrderVO.getAfterSalesEndAt() != null && mallOrderVO.getAfterSalesEndAt() > 0) {
                    long time = Long.valueOf(mallOrderVO.getAfterSalesEndAt()) * 1000;
                    String sendAtStr = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
                    mallOrderVO.setAfterSalesEndAtStr(sendAtStr);   //??????????????????,????????????????????????
                    afterSalesEndAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm");
                }
                if (mallOrderVO.getCancelAt() != null && mallOrderVO.getCancelAt() > 0) {
                    long time = Long.valueOf(mallOrderVO.getCancelAt()) * 1000;
                    String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
                    mallOrderVO.setCancelAtStr(orderAtStr);
                    cancelAtStrMin = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm");
                }

                if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                    mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //???????????????????????????????????????????????????????????????
                }

                if (mallOrderDTO.getDetails() != null && mallOrderDTO.getDetails().size() > 0) {
                    List<String> orderDetailNoList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getOrderDetailNo).collect(Collectors.toList());
                    List<MallOrderDetailSpecDTO> specList = mallOrderDetailSpecDao.queryListByOrderDetailNoList(orderDetailNoList);     //??????????????????????????????
                    Map<String, List<MallOrderDetailSpecUAppVO>> map = null;
                    if (specList != null) {
                        List<MallOrderDetailSpecUAppVO> specVoList = new ArrayList<>();
                        specList.forEach(spec -> {
                            MallOrderDetailSpecUAppVO mallOrderDetailSpecVO = new MallOrderDetailSpecUAppVO();
                            BeanUtils.copyProperties(spec, mallOrderDetailSpecVO);
                            specVoList.add(mallOrderDetailSpecVO);
                        });
                        map = specVoList.stream().collect(Collectors.groupingBy(input -> input.getOrderDetailNo()));        //???specVO???????????????map???????????????????????????????????????
                    } else {
                        map = new HashMap<>();
                    }
                    List<MallOrderDetailUAppVO> list = new ArrayList<>();
                    BigDecimal manageMoney = new BigDecimal(0);
                    BigDecimal distributionMoney = new BigDecimal(0);
                    for (MallOrderDetailDTO dto : mallOrderDTO.getDetails()) {
                        MallOrderDetailUAppVO mallOrderDetailVO = new MallOrderDetailUAppVO();
                        BeanUtils.copyProperties(dto, mallOrderDetailVO);
                        if (map.containsKey(mallOrderDetailVO.getOrderDetailNo())) {
                            mallOrderDetailVO.setSpecs(map.get(mallOrderDetailVO.getOrderDetailNo()));
                        }
                        list.add(mallOrderDetailVO);
                    }
                    mallOrderVO.setDetails(list);
                    mallOrderVO.setShareManageMoney(manageMoney);
                    mallOrderVO.setShareDistributionMoney(distributionMoney);
                }

                if (mallOrderDTO.getOrderType() != null) {
                    if (mallOrderDTO.getStatus() != 50 && mallOrderDTO.getStatus() != 10) {  //?????????????????????????????????????????????
                        BigDecimal manageMoney = new BigDecimal(0);
                        BigDecimal distributionMoney = new BigDecimal(0);
                        //?????????????????????
                        List<MallSocialShareFlowDTO> mallSocialShareFlowDTOs = mallSocialShareFlowDao.queryAllShareByOrderNo(mallOrderDTO.getOrderNo());
                        if (mallSocialShareFlowDTOs != null && mallSocialShareFlowDTOs.size() > 0) {
                            List<MallSocialShareFlowUAppVO> flowList = new ArrayList<>();
                            for (MallSocialShareFlowDTO dto : mallSocialShareFlowDTOs) {
                                MallSocialShareFlowUAppVO mallSocialShareFlowVO = new MallSocialShareFlowUAppVO();
                                BeanUtils.copyProperties(dto, mallSocialShareFlowVO);
                                flowList.add(mallSocialShareFlowVO);
                                if (dto.getShareType() == 0) { //????????????
                                    distributionMoney = distributionMoney.add(dto.getAmount());
                                } else if (dto.getShareType() == 1) {   //????????????
                                    manageMoney = manageMoney.add(dto.getAmount());
                                }
                            }
                        }
                        mallOrderVO.setShareManageMoney(manageMoney);
                        mallOrderVO.setShareDistributionMoney(distributionMoney);
                    }
                }

                //??????????????????????????????
                List<String> orderDetailNoList = null;
                if (mallOrderDTO.getDetails() != null && mallOrderDTO.getDetails().size() > 0) {
                    orderDetailNoList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getOrderDetailNo).collect(Collectors.toList());
                    //?????????????????????
                    int quantity = mallOrderDTO.getDetails().stream().collect(Collectors.summingInt(MallOrderDetailDTO::getSkuQuantity));
                    mallOrderVO.setQuantity(quantity);
                }

                Integer orderType = mallOrderVO.getOrderType();
                //????????????
                if (orderDetailNoList != null) {
                    List<MallAfterSalesDTO> mallAfterSalesDTOs = mallAfterSalesDao.querySalesByOrderDetailNoList(orderDetailNoList, orderType, orderNo);
                    if (mallAfterSalesDTOs != null && mallAfterSalesDTOs.size() > 0) {
                        //????????????????????????
                        List<String> afterSalesNoList = new ArrayList<>();
                        List<MallAfterSalesUAppVO> voList = new ArrayList<>();
                        mallAfterSalesDTOs.forEach(dto -> {
                            MallAfterSalesUAppVO mallAfterSalesVO = new MallAfterSalesUAppVO();
                            BeanUtils.copyProperties(dto, mallAfterSalesVO);
                            //??????createdTime????????????
                            if (mallAfterSalesVO.getCreatedTime() != null) {
                                String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(), "yyyy-MM-dd HH:mm:ss");
                                mallAfterSalesVO.setCreatedAtStr(orderAtStr);
                            }
                            voList.add(mallAfterSalesVO);
                            afterSalesNoList.add(dto.getAfterSalesNo());
                        });
                        mallOrderVO.setAfterSales(voList);

                        //??????????????????
                        List<MallAfterSalesLogDTO> mallAfterSalesLogDTOs = mallAfterSalesLogDao.querySalesLogByShopNoAndSalesNoList(afterSalesNoList);
                        if (mallAfterSalesLogDTOs != null && mallAfterSalesLogDTOs.size() > 0) {
                            List<MallAfterSalesLogUAppVO> salesLogVOList = new ArrayList<>();
                            mallAfterSalesLogDTOs.forEach(dto -> {
                                MallAfterSalesLogUAppVO mallAfterSalesLogVO = new MallAfterSalesLogUAppVO();
                                BeanUtils.copyProperties(dto, mallAfterSalesLogVO);
                                salesLogVOList.add(mallAfterSalesLogVO);
                            });
                            mallOrderVO.setAfterSalesLog(salesLogVOList);
                        }

                        //??????????????????????????????
                        List<MallBuyerShippingDTO> mallBuyerShippingDTOs = mallBuyerShippingDao.queryByAfterSalesNoList(afterSalesNoList);
                        if (mallBuyerShippingDTOs != null && mallBuyerShippingDTOs.size() > 0) {
                            //????????????????????????
                            List<String> buyerShippingNoList = new ArrayList<>();
                            List<MallBuyerShippingUAppVO> buyerShippingVOList = new ArrayList<>();
                            mallBuyerShippingDTOs.forEach(dto -> {
                                MallBuyerShippingUAppVO mallBuyerShippingVO = new MallBuyerShippingUAppVO();
                                BeanUtils.copyProperties(dto, mallBuyerShippingVO);
                                buyerShippingVOList.add(mallBuyerShippingVO);
                                buyerShippingNoList.add(dto.getBuyerShippingNo());
                            });
                            mallOrderVO.setBuyerShipping(buyerShippingVOList);

                            //??????????????????????????????????????????
                            List<MallBuyerShippingLogDTO> mallBuyerShippingLogDTOs = mallBuyerShippingLogDao.queryListByBuyerShippingNoList(buyerShippingNoList);
                            if (mallBuyerShippingLogDTOs != null && mallBuyerShippingLogDTOs.size() > 0) {
                                List<MallBuyerShippingLogUAppVO> buyerShippingLogVOList = new ArrayList<>();
                                mallBuyerShippingLogDTOs.forEach(dto -> {
                                    MallBuyerShippingLogUAppVO mallBuyerShippingLogVO = new MallBuyerShippingLogUAppVO();
                                    BeanUtils.copyProperties(dto, mallBuyerShippingLogVO);
                                    buyerShippingLogVOList.add(mallBuyerShippingLogVO);
                                });
                                mallOrderVO.setBuyerShippingLog(buyerShippingLogVOList);
                            }
                        }
                    }
                }
                //????????????????????????
                MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(orderNo);
                if (mallShopShippingDTO != null) {
                    MallShopShippingUAppVO mallShopShippingVO = new MallShopShippingUAppVO();
                    BeanUtils.copyProperties(mallShopShippingDTO, mallShopShippingVO);
                    mallOrderVO.setShopShipping(mallShopShippingVO);

                    //????????????????????????????????????
                    List<MallShopShippingLogDTO> mallShopShippingLogDTOs = mallShopShippingLogDao.selectListByShopShippingNo(mallShopShippingVO.getShopShippingNo());
                    if (mallShopShippingLogDTOs != null && mallShopShippingLogDTOs.size() > 0) {
                        List<MallShopShippingLogUAppVO> voList = new ArrayList<>();
                        mallShopShippingLogDTOs.forEach(dto -> {
                            MallShopShippingLogUAppVO mallShopShippingLogVO = new MallShopShippingLogUAppVO();
                            BeanUtils.copyProperties(dto, mallShopShippingLogVO);
                            voList.add(mallShopShippingLogVO);
                        });

                        mallOrderVO.setShopShippingLog(voList);
                    }
                }

                //??????????????????????????????
                if (orderDetailNoList != null) {
                    List<MallOrderDetailSpecDTO> mallOrderDetailSpecDTOs = mallOrderDetailSpecDao.queryListByOrderDetailNoList(orderDetailNoList);
                    if (mallOrderDetailSpecDTOs != null && mallOrderDetailSpecDTOs.size() > 0) {
                        List<MallOrderDetailSpecUAppVO> voList = new ArrayList<>();
                        mallOrderDetailSpecDTOs.forEach(dto -> {
                            MallOrderDetailSpecUAppVO mallOrderDetailSpecVO = new MallOrderDetailSpecUAppVO();
                            BeanUtils.copyProperties(dto, mallOrderDetailSpecVO);
                            voList.add(mallOrderDetailSpecVO);
                        });
                        Map<String, List<MallOrderDetailSpecUAppVO>> detailNoMap = voList.stream().collect(Collectors.groupingBy(input -> input.getOrderDetailNo()));
                        mallOrderVO.getDetails().forEach(detail -> {
                            if (detailNoMap.containsKey(detail.getOrderDetailNo())) {
                                detail.setSpecs(detailNoMap.get(detail.getOrderDetailNo()));
                            }
                        });
                    }
                }

                //????????????????????????
                MallOrderEnum.CancelReason cancelReason = MallOrderEnum.CancelReason.parseCode(mallOrderVO.getCancelReason());
                if (null != cancelReason) {
                    mallOrderVO.setCancelReasonName(cancelReason.getText());
                }

                //????????????????????????
                if (mallOrderVO.getStatus() == 10) {  //?????????
                    mallOrderVO.setHeadField(orderAtStrMin + " ???????????????????????????");
                }
                if (mallOrderVO.getStatus() == 20) {  //?????????
                    mallOrderVO.setHeadField(payedAtStrMin + " ?????????????????????????????????");
                }
                if (mallOrderVO.getStatus() == 30) {  //?????????
                    if (mallOrderVO.getDeliverType() == 2) {  //??????
                        mallOrderVO.setHeadField(payedAtStrMin + " ??????????????????????????????????????????");
                    } else { //???????????????
                        if (mallOrderVO.getDeliverMethod() == 10) {    //??????
                            String n = mallShopShippingDTO != null ? mallShopShippingDTO.getNumber() : "";
                            String shipCompany = mallShopShippingDTO != null ? mallShopShippingDTO.getCompany() : "";
                            mallOrderVO.setHeadField("?????????" + sendAtStrMin + "????????????????????????" + n + " " + shipCompany);
                        } else { //????????????
                            mallOrderVO.setHeadField("?????????" + sendAtStrMin + "??????????????????????????????");
                        }
                    }
                }
                if (mallOrderVO.getStatus() == 40) {   //?????????
                    if (mallOrderVO.getDeliverType() == 2) {  //??????
                        mallOrderVO.setHeadField(receiveAtStrMin + " ????????????????????????");
                    } else {
                        if (mallOrderVO.getSubStatus() == 4010) {  //4010-??????????????????
                            mallOrderVO.setHeadField(receiveAtStrMin + " ???????????????????????????");
                        } else {
                            mallOrderVO.setHeadField(receiveAtStrMin + " ???????????????");
                        }
                    }
                }
                if (mallOrderVO.getStatus() == 50) {   //?????????
                    if (mallOrderVO.getSubStatus() == 5020) { //5020-??????????????????(?????????)
                        mallOrderVO.setHeadField("???????????????????????????????????????" + cancelAtStrMin + "?????????????????????");
                    }
                    if (mallOrderVO.getSubStatus() == 5010 || mallOrderVO.getSubStatus() == 5030) { //5010-??????????????????(?????????)  5030-??????????????????(?????????)
                        mallOrderVO.setHeadField("?????????" + cancelAtStrMin + "???????????????");
                    }
                    if (mallOrderVO.getSubStatus() == 1030 || mallOrderVO.getSubStatus() == 5060) {  //1030-????????????- ?????????  5060-????????????????????????-???????????????
                        mallOrderVO.setHeadField("????????????????????????" + cancelAtStrMin + "?????????????????????");
                    }
                    if (mallOrderVO.getSubStatus() == 5040 || mallOrderVO.getSubStatus() == 5050) {  //5040-????????????(?????????)  5050-????????????????????????-???????????????
                        mallOrderVO.setHeadField(cancelAtStrMin + "?????????????????????\r\n????????????" + mallOrderVO.getCancelReasonName() + "??????????????????????????????");
                    }
                }


                return ReturnResponse.success(mallOrderVO);
            }else if (mallOrderDTO.getStatus()==40){
                return ReturnResponse.failed("??????????????????????????????");
            }else if (mallOrderDTO.getStatus()==50){
                if (mallOrderDTO.getSubStatus()==5050){ //5050-????????????????????????-???????????????
                    return ReturnResponse.failed("????????????????????????????????????");
                }else if (mallOrderDTO.getSubStatus()==5060){   //5060-????????????????????????-???????????????
                    return ReturnResponse.failed("????????????????????????????????????");
                }else {
                    return ReturnResponse.failed("?????????????????????");
                }
            }
        }
        return ReturnResponse.failed("????????????????????????");
    }

    @Override
    public ReturnResponse<MallOrderUAppVO> verPickupNoByUApp(MallPickupUAppQuery mallPickupUAppQuery) {
        UserVO userVO = getUser();
        MallOrderUAppDTO mallOrderDTO = mallOrderDao.queryDetailByPickupNoUApp(mallPickupUAppQuery.getPickupNo(), userVO.getShopNo());
        if (mallOrderDTO!=null){

            if (mallOrderDTO.getIsGroupSupply()==1) {   //????????????
                //????????????????????????????????????????????????
                boolean checkd = checkOrder(userVO.getOrganizeId());
                if (!checkd) {
                    return ReturnResponse.failed("??????????????????????????????");
                }
            }

            //??????pickNo????????????????????????orderNo?????????
            if (StringUtils.isNotEmpty(mallPickupUAppQuery.getOrderNo())){
                //orderNo?????????????????????????????????????????????????????????
                if (!mallPickupUAppQuery.getOrderNo().equals(mallOrderDTO.getOrderNo())){
                    return ReturnResponse.failed("??????????????????????????????????????????????????????");
                }
            }
            if (mallOrderDTO.getStatus() == 30 && mallOrderDTO.getSubStatus() == 3010) {
                Long time = new Date().getTime() / 1000;      //????????????????????????
                mallOrderDTO.setReceiveAt(time.intValue());
                MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(userVO.getShopNo());
                if (mallOrderDTO == null) {
                    return ReturnResponse.failed("??????????????????");
                }
                //???????????????
                MallOrder mallOrder = new MallOrder();
                mallOrder.setId(mallOrderDTO.getId());
                mallOrder.setReceiveAt(time.intValue());
                mallOrder.setAfterSalesEndAt(time.intValue() + mallShopDTO.getAsHoldDays() * 24 * 60 * 60);
                mallOrder.setStatus(40);
                mallOrder.setSubStatus(4060);
                Long timeAt = new Date().getTime() / 1000;
                mallOrder.setReceiveAt(timeAt.intValue());  //??????????????????
                mallOrderDao.updateById(mallOrder);
                return queryOrderDetailByUApp(mallPickupUAppQuery.getOrderNo()); //?????????????????????????????????
            }else if (mallOrderDTO.getStatus()==40){
                return ReturnResponse.failed("??????????????????????????????");
            }else if (mallOrderDTO.getStatus()==50){
                if (mallOrderDTO.getSubStatus()==5050){ //5050-????????????????????????-???????????????
                    return ReturnResponse.failed("????????????????????????????????????");
                }else if (mallOrderDTO.getSubStatus()==5060){   //5060-????????????????????????-???????????????
                    return ReturnResponse.failed("????????????????????????????????????");
                }else {
                    return ReturnResponse.failed("?????????????????????");
                }
            }
        }
        return ReturnResponse.failed("????????????????????????");
    }

    @Override
    public CommonResult<BasePageResult<MallOrderDetailUAppVO>> queryOrderDetailShareFlowListByNo(Long page, Long pageSize, String orderNo) {
        UserVO userVO = getUser();  //????????????????????????
        BasePageResult<MallOrderDetailUAppVO> basePageResult = new BasePageResult();
        List<MallOrderDetailUAppVO> list = new ArrayList<>();
        try {
            List<String> orderNoList = new ArrayList<>();
            MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no",orderNo));
            if (mallOrder!=null){
                //????????????????????????
                if (!mallOrder.getShopNo().equals(userVO.getShopNo())){
                    return CommonResult.failed("?????????????????????????????????????????????");
                }
                orderNoList.add(orderNo);
            }else {
                List<MallOrder> mallOrderList = mallOrderDao.selectList(new QueryWrapper<MallOrder>().eq("cart_order_sn",orderNo));
                if (mallOrderList!=null && mallOrderList.size()>0){
                    mallOrderList.forEach(order->{
                        orderNoList.add(order.getOrderNo());
                    });
                }
            }

            Integer count = mallOrderDetailDao.queryDetailListByOrderNosCount(orderNoList);
            if (count!=null && count>0) {
                List<MallOrderDetailDTO> mallOrderDetailDTOList = mallOrderDetailDao.queryDetailListByOrderNosPage((page-1)*pageSize,pageSize, orderNoList);
                if (mallOrderDetailDTOList != null && mallOrderDetailDTOList.size() > 0) {
                    MallOrderDetailUAppVO mallOrderDetailUAppVO = null;
                    for (MallOrderDetailDTO mallOrderDetailDTO : mallOrderDetailDTOList) {
                        mallOrderDetailUAppVO = new MallOrderDetailUAppVO();
                        BeanUtils.copyProperties(mallOrderDetailDTO, mallOrderDetailUAppVO);
                        //??????????????????
                        if (mallOrderDetailDTO.getShareFlowInfo() != null && mallOrderDetailDTO.getShareFlowInfo().size() > 0) {
                            List<MallSocialShareFlowUAppVO> flowList = new ArrayList<>();
                            mallOrderDetailDTO.getShareFlowInfo().forEach(dto -> {
                                MallSocialShareFlowUAppVO mallSocialShareFlowVO = new MallSocialShareFlowUAppVO();
                                BeanUtils.copyProperties(dto, mallSocialShareFlowVO);
                                flowList.add(mallSocialShareFlowVO);
                            });
                            mallOrderDetailUAppVO.setShareFlowInfo(flowList);
                        }
                        //??????????????????
                        if (mallOrderDetailDTO.getSpecs() != null && mallOrderDetailDTO.getSpecs().size() > 0) {
                            List<MallOrderDetailSpecUAppVO> specList = new ArrayList<>();
                            mallOrderDetailDTO.getSpecs().forEach(spec -> {
                                MallOrderDetailSpecUAppVO mallOrderDetailSpecUAppVO = new MallOrderDetailSpecUAppVO();
                                BeanUtils.copyProperties(spec, mallOrderDetailSpecUAppVO);
                                specList.add(mallOrderDetailSpecUAppVO);
                            });
                            mallOrderDetailUAppVO.setSpecs(specList);
                        }
                        if (mallOrderDetailUAppVO.getMoAfterSalesEndAt() != null && mallOrderDetailUAppVO.getMoAfterSalesEndAt() > 0) {
                            long time = mallOrderDetailUAppVO.getMoAfterSalesEndAt() * 1000;
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
                            mallOrderDetailUAppVO.setMoAfterSalesEndAtStr(orderAtStr);
                        }
                        list.add(mallOrderDetailUAppVO);
                    }
                    basePageResult.setPage(page);
                    basePageResult.setPageSize(pageSize);
                    basePageResult.setTotal(count);
                    basePageResult.setResult(list);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return CommonResult.success(basePageResult);
    }

    /**
     * ??????????????????????????????????????????
     * @param organizeId
     * @return
     */
    private boolean checkOrder(Long organizeId) {
        Organize organize = organizeDao.selectById(organizeId);
        if (organize!=null){
            if (organize.getIsGroup()==1){
                return true;
            }
            OrganizeRel organizeRel = organizeRelDao.selectOne(new QueryWrapper<OrganizeRel>().eq("organize_id",organizeId).eq("status",2));
            if (organizeRel!=null){
                OrganizeGroup organizeGroup = organizeGroupDao.selectOne(new QueryWrapper<OrganizeGroup>().eq("organize_id",organizeRel.getGroupOrganizeId()));
                if (organizeGroup!=null){
                    if (organizeGroup.getIsOpenMaintainable()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
