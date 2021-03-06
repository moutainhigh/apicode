package com.ycandyz.master.service.mall.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.constant.CommonConstant;
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.dao.organize.OrganizeGroupDao;
import com.ycandyz.master.dao.organize.OrganizeRelDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.enums.mall.MallAfterSalesEnum;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.dto.mall.*;
import com.ycandyz.master.entities.mall.*;
import com.ycandyz.master.entities.organize.Organize;
import com.ycandyz.master.entities.organize.OrganizeGroup;
import com.ycandyz.master.entities.organize.OrganizeRel;
import com.ycandyz.master.enums.SalesEnum;
import com.ycandyz.master.model.mall.*;
import com.ycandyz.master.service.mall.MallAfterSalesService;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MallAfterSalesServiceImpl extends BaseService<MallAfterSalesDao, MallAfterSales, MallafterSalesQuery> implements MallAfterSalesService {

    @Autowired
    private MallAfterSalesDao mallAfterSalesDao;

    @Autowired
    private MallAfterSalesLogDao mallAfterSalesLogDao;

    @Autowired
    private MallShopShippingDao mallShopShippingDao;

    @Autowired
    private MallShopShippingLogDao mallShopShippingLogDao;

    @Autowired
    private MallOrderDetailSpecDao mallItemSpecByMallOrderDetailSpecVO;

    @Autowired
    private MallShopDao mallShopDao;

    @Autowired
    private MallOrderDetailDao mallOrderDetailDao;

    @Autowired
    private MallBuyerShippingDao mallBuyerShippingDao;

    @Autowired
    private MallBuyerShippingLogDao mallBuyerShippingLogDao;

    @Autowired
    private MallOrderDao mallOrderDao;

    @Autowired
    private S3UploadFile s3UploadFile;

    @Autowired
    private IUserExportRecordService iUserExportRecordService;

    @Autowired
    private OrganizeRelDao organizeRelDao;

    @Autowired
    private OrganizeDao organizeDao;

    @Autowired
    private OrganizeGroupDao organizeGroupDao;

    @Value("${after-sales-days:7}")
    private Integer afterSalesDays;

    @Value("${excel.sheet}")
    private int num;

    @Override
    public ReturnResponse<Page<MallAfterSalesVO>> querySalesListPage(RequestParams<MallafterSalesQuery> requestParams) {
        UserVO userVO = getUser();  //????????????????????????
        Page<MallAfterSalesVO> mallPage = new Page<>();
        List<MallAfterSalesVO> list = new ArrayList<>();
        MallafterSalesQuery mallafterSalesQuery = requestParams.getT();
        if (mallafterSalesQuery==null){
            mallafterSalesQuery = new MallafterSalesQuery();
        }
        List<Integer> organizeIds = new ArrayList<>();  //????????????id?????????????????????
        Map<String, Integer> shopNoAndOrganizeId = new HashMap<>(); //??????shopNo???organizeid    map<shopNo,organizeid>
        if (mallafterSalesQuery.getIsGroup().equals("0")){   //???????????????????????????
            mallafterSalesQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
            organizeIds.add(userVO.getOrganizeId().intValue());
            shopNoAndOrganizeId.put(userVO.getShopNo(),userVO.getOrganizeId().intValue());
        }else if (mallafterSalesQuery.getIsGroup().equals("1")){ //??????
            if (mallafterSalesQuery.getChildOrganizeId()==null || "".equals(mallafterSalesQuery.getChildOrganizeId()) || mallafterSalesQuery.getChildOrganizeId().equals("0")){
                //????????????????????????
                Long groupOrganizeId = userVO.getOrganizeId();   //??????id
                if (groupOrganizeId!=null) {
                    List<OrganizeRel> organizeRels = organizeRelDao.selectList(new QueryWrapper<OrganizeRel>().eq("group_organize_id", groupOrganizeId.intValue()).eq("status",2));
                    if (organizeRels != null && organizeRels.size() > 0) {
                        List<Integer> oids = organizeRels.stream().map(OrganizeRel::getOrganizeId).collect(Collectors.toList());
                        organizeIds.addAll(oids);
//                        organizeIds.add(groupOrganizeId.intValue());
                        List<MallShopDTO> mallShopDTOS = mallShopDao.queryByOrganizeIdList(organizeIds);
                        if (mallShopDTOS!=null && mallShopDTOS.size()>0){
                            List<String> shopNos = mallShopDTOS.stream().map(MallShopDTO::getShopNo).collect(Collectors.toList());
                            mallafterSalesQuery.setShopNo(shopNos);
                            Map<String, Integer> map = mallShopDTOS.stream().collect(Collectors.toMap(MallShopDTO::getShopNo, MallShopDTO::getOrganizeId));
                            shopNoAndOrganizeId.putAll(map);
                        }
                    }
                    //??????????????????????????????????????????
                    organizeIds.add(groupOrganizeId.intValue());
                    if (mallafterSalesQuery.getShopNo()!=null && mallafterSalesQuery.getShopNo().size()>0) {
                        mallafterSalesQuery.getShopNo().add(userVO.getShopNo());
                    }else {
                        mallafterSalesQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
                    }
                    shopNoAndOrganizeId.put(userVO.getShopNo(),groupOrganizeId.intValue());
                }
            }else {
                mallafterSalesQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
                organizeIds.add(Integer.valueOf(mallafterSalesQuery.getChildOrganizeId()));
                MallShop mallShop = mallShopDao.selectOne(new QueryWrapper<MallShop>().eq("organize_id", mallafterSalesQuery.getChildOrganizeId()));
                if (mallShop!=null){
                    mallafterSalesQuery.setShopNo(Arrays.asList(mallShop.getShopNo()));
                    shopNoAndOrganizeId.put(mallShop.getShopNo(), Integer.valueOf(mallafterSalesQuery.getChildOrganizeId()));
                }else {
                    mallPage.setSize(requestParams.getPage_size());
                    mallPage.setRecords(list);
                    mallPage.setCurrent(requestParams.getPage());
                    mallPage.setPages(requestParams.getPage());
                    return ReturnResponse.success(mallPage);
                }
            }
        }
        Page pageQuery = new Page(requestParams.getPage(), requestParams.getPage_size());

        Page<MallAfterSalesDTO> page = null;
        try {
            page = mallAfterSalesDao.getTrendMallAfterSalesPage(pageQuery, mallafterSalesQuery);
            if (page.getRecords() != null && page.getRecords().size() > 0) {

                boolean isOpenMaintainable = false;
                if (mallafterSalesQuery.getIsGroup().equals("0")) {
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

                //???????????????????????????organizedIds
                List<Organize> organizeList = organizeDao.selectBatchIds(organizeIds);
                Map<Integer, String> organizeIdAndName = organizeList.stream().collect(Collectors.toMap(Organize::getId, Organize::getFullName));


                MallAfterSalesVO mallAfterSalesVO = null;
                for (MallAfterSalesDTO mallAfterSalesDTO : page.getRecords()) {
                    Integer subStatus = mallAfterSalesDTO.getSubStatus();
                    Integer state = null;
                    if (subStatus!=null){
                        if (subStatus==1010){
                            state = 1;
                        }else if (subStatus==1030){
                            state = 2;
                        }else if (subStatus==1050 || subStatus==2010){
                            state = 3;
                        }else if (subStatus==1080 || subStatus==2020 || subStatus==2030 || (subStatus==1070 && mallAfterSalesDTO.getAuditSecondAt()>= cn.hutool.core.date.DateUtil.beginOfDay(new Date()).getTime()/1000)){
                            state = 4;
                        }else if (subStatus==1020 || subStatus==1040 || subStatus==1060 || subStatus==2050 || (subStatus==1070 && mallAfterSalesDTO.getAuditSecondAt()>= cn.hutool.core.date.DateUtil.beginOfDay(new Date()).getTime()/1000)){
                            state = 6;
                        }else {
                            state = 5;
                        }
                    }
                    mallAfterSalesDTO.setState(state);
                    mallAfterSalesVO = new MallAfterSalesVO();
                    BeanUtils.copyProperties(mallAfterSalesDTO, mallAfterSalesVO);

                    //?????????????????????????????????????????????
                    String fullName = organizeIdAndName.get(shopNoAndOrganizeId.get(mallAfterSalesVO.getShopNo()));
                    mallAfterSalesVO.setOrganizeName(fullName);

                    //??????createdTime????????????
                    if (mallAfterSalesVO.getCreatedTime()!=null) {
                        String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                        mallAfterSalesVO.setCreatedAtStr(orderAtStr);
                    }

                    MallOrderByAfterSalesDTO mallOrderByAfterSalesDTO = mallAfterSalesDTO.getOrder();
                    if (mallOrderByAfterSalesDTO!=null){
                        //????????????
                        MallOrderByAfterSalesVO mallOrderByAfterSalesVO = new MallOrderByAfterSalesVO();
                        BeanUtils.copyProperties(mallOrderByAfterSalesDTO, mallOrderByAfterSalesVO);
                        mallAfterSalesVO.setOrder(mallOrderByAfterSalesVO);

                        //?????????????????????
                        if (isOpenMaintainable){
                            mallAfterSalesVO.getOrder().setAllowOperating(1);
                        }else {
                            mallAfterSalesVO.getOrder().setAllowOperating(0);
                        }

                        //??????????????????
                        mallAfterSalesVO.setPayType(mallOrderByAfterSalesVO.getPayType());

                        BigDecimal shippingMoney = mallOrderByAfterSalesVO.getAllMoney().subtract(mallOrderByAfterSalesVO.getRealMoney());

                        //???????????????????????????+??????
                        mallAfterSalesVO.setAllMoney(mallOrderByAfterSalesVO.getTotalMoney().add(shippingMoney));

                    }
                    MallOrderDetailByAfterSalesDTO mallOrderDetailByAfterSalesDTO = mallAfterSalesDTO.getDetails();
                    if (mallOrderDetailByAfterSalesDTO!=null){
                        //??????????????????
                        MallOrderDetailByAfterSalesVO mallOrderDetailByAfterSalesVO = new MallOrderDetailByAfterSalesVO();
                        BeanUtils.copyProperties(mallOrderDetailByAfterSalesDTO,mallOrderDetailByAfterSalesVO);
                        mallAfterSalesVO.setDetails(mallOrderDetailByAfterSalesVO);

                        //??????????????????
                        mallAfterSalesVO.setOrderQuantity(mallOrderDetailByAfterSalesVO.getQuantity());
                        //????????????
                        mallAfterSalesVO.setGoodsNo(mallOrderDetailByAfterSalesVO.getGoodsNo());
                        //??????????????????
                        mallAfterSalesVO.setItemName(mallOrderDetailByAfterSalesVO.getItemName());
                    }
                    list.add(mallAfterSalesVO);
                }
            }
        }catch (Exception e){
            page = new Page<>(0,10,0);
            log.error(e.getMessage(),e);
        }
        mallPage.setTotal(page.getTotal());
        mallPage.setSize(page.getSize());
        mallPage.setRecords(list);
        mallPage.setCurrent(page.getCurrent());
        mallPage.setPages(page.getPages());
        return ReturnResponse.success(mallPage);
    }

    @Override
    public ReturnResponse<MallAfterSalesVO> querySalesDetail(String afterSalesNo) {
        UserVO userVO = getUser();  //????????????????????????
        MallAfterSalesVO mallAfterSalesVO = null;
        MallAfterSalesDTO mallAfterSalesDTO = mallAfterSalesDao.querySalesDetail(afterSalesNo);
        if (mallAfterSalesDTO!=null){
            mallAfterSalesVO = new MallAfterSalesVO();
            BeanUtils.copyProperties(mallAfterSalesDTO,mallAfterSalesVO);

            //??????createdTime????????????
            if (mallAfterSalesVO.getCreatedTime()!=null) {
                String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                mallAfterSalesVO.setCreatedAtStr(orderAtStr);
            }

            //??????????????????
            mallAfterSalesVO.setSaleAllMoney(mallAfterSalesDTO.getMoney());


            //???????????????????????????????????????
//                mallAfterSalesVO.setRefundMoney(mallAfterSalesDTO.getSkuPrice().multiply(new BigDecimal(mallAfterSalesDTO.getSkuQuantity())));
            mallAfterSalesVO.setRefundMoney(mallAfterSalesDTO.getMoney());

            MallOrderByAfterSalesDTO mallOrderByAfterSalesDTO = mallAfterSalesDTO.getOrder();
            Integer orderType = null;   //?????????????????????????????????????????????
            if (mallOrderByAfterSalesDTO!=null){
                //????????????
                MallOrderByAfterSalesVO mallOrderByAfterSalesVO = new MallOrderByAfterSalesVO();
                BeanUtils.copyProperties(mallOrderByAfterSalesDTO, mallOrderByAfterSalesVO);

                //??????
                mallOrderByAfterSalesVO.setShippingMoney((mallOrderByAfterSalesVO.getAllMoney().subtract(mallOrderByAfterSalesVO.getRealMoney())).toString());

                mallAfterSalesVO.setOrder(mallOrderByAfterSalesVO);
                orderType = mallOrderByAfterSalesDTO.getOrderType();

                Organize organize = organizeDao.selectById(userVO.getOrganizeId());
                if (organize!=null){
                    if (organize.getIsGroup()==1){  //??????
                        mallAfterSalesVO.getOrder().setAllowOperating(1);   //???????????????
                    }else if (organize.getIsGroup()==0){    //??????
                        if (mallAfterSalesDTO.getOrder().getIsGroupSupply()==0){    //???????????????
                            mallAfterSalesVO.getOrder().setAllowOperating(1);   //???????????????
                        }else { //????????????
                            OrganizeRel organizeRel = organizeRelDao.selectOne(new QueryWrapper<OrganizeRel>().eq("organize_id", userVO.getOrganizeId()).eq("status", 2));
                            if (organizeRel != null) {
                                OrganizeGroup organizeGroup = organizeGroupDao.selectOne(new QueryWrapper<OrganizeGroup>().eq("organize_id", organizeRel.getGroupOrganizeId()));
                                if (organizeGroup != null && organizeGroup.getIsOpenMaintainable()) {
                                    mallAfterSalesVO.getOrder().setAllowOperating(1);   //???????????????
                                } else {
                                    mallAfterSalesVO.getOrder().setAllowOperating(0);   //???????????????
                                }
                            }
                        }
                    }
                }

            }

            //????????????
            MallOrderDetailByAfterSalesDTO mallOrderDetailByAfterSalesDTO = mallAfterSalesDTO.getDetails();
            MallOrderDetailByAfterSalesVO mallOrderDetailByAfterSalesVO = null;
            if (mallOrderDetailByAfterSalesDTO==null){
                //???????????????????????????
                if (orderType!=null && orderType==1){   //?????????
                    MallOrderDetail mallOrderDetail = mallOrderDetailDao.selectOne(new QueryWrapper<MallOrderDetail>().eq("order_no",mallOrderByAfterSalesDTO.getOrderNo()));
                    if (mallOrderDetail!=null){
                        mallOrderDetailByAfterSalesVO = new MallOrderDetailByAfterSalesVO();
                        mallOrderDetailByAfterSalesVO.setGoodsNo(mallOrderDetail.getGoodsNo());
                        mallOrderDetailByAfterSalesVO.setItemCover(mallOrderDetail.getItemCover());
                        mallOrderDetailByAfterSalesVO.setItemName(mallOrderDetail.getItemName());
                        mallOrderDetailByAfterSalesVO.setItemNo(mallOrderDetail.getItemNo());
                        mallOrderDetailByAfterSalesVO.setOrderDetailNo(mallOrderDetail.getOrderDetailNo());
                        mallOrderDetailByAfterSalesVO.setOrderNo(mallOrderDetail.getOrderNo());
                        mallOrderDetailByAfterSalesVO.setPrice(mallOrderDetail.getPrice());
                        mallOrderDetailByAfterSalesVO.setQuantity(mallOrderDetail.getQuantity());
                        mallOrderDetailByAfterSalesVO.setSkuNo(mallOrderDetail.getSkuNo());
                        mallAfterSalesVO.setDetails(mallOrderDetailByAfterSalesVO);
                    }
                }
            }else {
                mallOrderDetailByAfterSalesVO = new MallOrderDetailByAfterSalesVO();
                BeanUtils.copyProperties(mallOrderDetailByAfterSalesDTO,mallOrderDetailByAfterSalesVO);
                mallAfterSalesVO.setDetails(mallOrderDetailByAfterSalesVO);
            }

            if (mallOrderDetailByAfterSalesVO!=null){
                //MallOrderDetail??????item????????????
                MallItemByMallOrderDetailVO mallItemByMallOrderDetailVO = new MallItemByMallOrderDetailVO();
                mallItemByMallOrderDetailVO.setItemNo(mallOrderDetailByAfterSalesVO.getItemNo());
                mallItemByMallOrderDetailVO.setItemName(mallOrderDetailByAfterSalesVO.getItemName());
                mallItemByMallOrderDetailVO.setGoodsNo(mallOrderDetailByAfterSalesVO.getGoodsNo());
                mallItemByMallOrderDetailVO.setItemCover(mallOrderDetailByAfterSalesVO.getItemCover());
                mallItemByMallOrderDetailVO.setPrice(mallOrderDetailByAfterSalesVO.getPrice());
                mallItemByMallOrderDetailVO.setQuantity(mallOrderDetailByAfterSalesVO.getQuantity());

                //MallOrderDetailSpec??????specValue??????????????????
                List<MallOrderDetailSpecDTO> mallOrderDetailSpecDTOs = mallItemSpecByMallOrderDetailSpecVO.queryListByOrderDetailNo(mallOrderDetailByAfterSalesVO.getOrderDetailNo());
                if (mallOrderDetailSpecDTOs!=null && mallOrderDetailSpecDTOs.size()>0){
                    List<MallOrderDetailSpecVO> mallOrderDetailSpecVOS = new ArrayList<>();
                    List<MallItemSpecByMallOrderDetailSpecVO> mallItemSpecByMallOrderDetailSpecVOS = new ArrayList<>();
                    mallOrderDetailSpecDTOs.forEach(dto->{
                        //???????????????item???
                        MallItemSpecByMallOrderDetailSpecVO mallItemSpecByMallOrderDetailSpecVO = new MallItemSpecByMallOrderDetailSpecVO();
                        mallItemSpecByMallOrderDetailSpecVO.setSpecValue(dto.getSpecValue());
                        mallItemSpecByMallOrderDetailSpecVOS.add(mallItemSpecByMallOrderDetailSpecVO);

                        //???????????????details???
                        MallOrderDetailSpecVO mallOrderDetailSpecVO = new MallOrderDetailSpecVO();
                        BeanUtils.copyProperties(dto,mallOrderDetailSpecVO);
                        mallOrderDetailSpecVOS.add(mallOrderDetailSpecVO);
                    });
                    mallItemByMallOrderDetailVO.setSpec(mallItemSpecByMallOrderDetailSpecVOS);
                    mallAfterSalesVO.getDetails().setSpecs(mallOrderDetailSpecVOS);
                }
                mallAfterSalesVO.setItemInfo(mallItemByMallOrderDetailVO);
            }

            //MallAfterSalesLog???
            List<MallAfterSalesLogDTO> mallAfterSalesLogDTOs = mallAfterSalesLogDao.querySalesLogByShopNoAndSalesNo(mallAfterSalesDTO.getAfterSalesNo());
            if (mallAfterSalesLogDTOs!=null && mallAfterSalesLogDTOs.size()>0){
                List<MallAfterSalesLogVO> mallAfterSalesLogVOS = new ArrayList<>();
                mallAfterSalesLogDTOs.forEach(dto->{
                    MallAfterSalesLogVO mallAfterSalesLogVO = new MallAfterSalesLogVO();
                    BeanUtils.copyProperties(dto,mallAfterSalesLogVO);
                    mallAfterSalesLogVOS.add(mallAfterSalesLogVO);
                });
                mallAfterSalesVO.setAsLog(mallAfterSalesLogVOS);
            }

            //MallShopShpping???
            MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(mallAfterSalesDTO.getOrderNo());
            if (mallShopShippingDTO!=null){
                MallShopShippingVO mallShopShippingVO = new MallShopShippingVO();
                BeanUtils.copyProperties(mallShopShippingDTO,mallShopShippingVO);
                mallAfterSalesVO.setShippingInfo(mallShopShippingVO);

                //MallShopShippingLog???
                List<MallShopShippingLogDTO> mallShopShippingLogDTOS = mallShopShippingLogDao.selectListByShopShippingNo(mallShopShippingVO.getShopShippingNo());
                if (mallShopShippingLogDTOS!=null && mallShopShippingLogDTOS.size()>0){
                    List<MallShopShippingLogVO> mallShopShippingLogVOS = new ArrayList<>();
                    mallShopShippingLogDTOS.forEach(dto->{
                        MallShopShippingLogVO mallShopShippingLogVO = new MallShopShippingLogVO();
                        BeanUtils.copyProperties(dto,mallShopShippingLogVO);
                        mallShopShippingLogVOS.add(mallShopShippingLogVO);
                    });
                    mallAfterSalesVO.setShippinglog(mallShopShippingLogVOS);
                }
            }

            //MallBuyerShipping???
            MallBuyerShippingDTO mallBuyerShippingDTO = mallBuyerShippingDao.queryByAfterSalesNo(mallAfterSalesVO.getAfterSalesNo());
            if (mallBuyerShippingDTO!=null){
                MallBuyerShippingVO mallBuyerShippingVO = new MallBuyerShippingVO();
                BeanUtils.copyProperties(mallBuyerShippingDTO,mallBuyerShippingVO);
                mallAfterSalesVO.setBuyerShipping(mallBuyerShippingVO);

                //MallBuyerShippingLog???
                List<MallBuyerShippingLogDTO> mallBuyerShippingLogDTOS = mallBuyerShippingLogDao.queryListByBuyerShippingNo(mallBuyerShippingVO.getBuyerShippingNo());
                if (mallBuyerShippingLogDTOS!=null && mallBuyerShippingLogDTOS.size()>0){
                    List<MallBuyerShippingLogVO> mallBuyerShippingLogVOS = new ArrayList<>();
                    mallBuyerShippingLogDTOS.forEach(dto->{
                        MallBuyerShippingLogVO mallBuyerShippingLogVO = new MallBuyerShippingLogVO();
                        BeanUtils.copyProperties(dto,mallBuyerShippingLogVO);
                        mallBuyerShippingLogVOS.add(mallBuyerShippingLogVO);
                    });
                    mallAfterSalesVO.setBuyerShippingLog(mallBuyerShippingLogVOS);
                }
            }

            //MallShop????????????
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(userVO.getShopNo());
            if (mallShopDTO!=null){
                MallShopVO mallShopVO = new MallShopVO();
                BeanUtils.copyProperties(mallShopDTO,mallShopVO);
                mallAfterSalesVO.getOrder().setShop(mallShopVO);
            }

            //????????????
            Organize organ = organizeDao.selectById(mallShopDTO.getOrganizeId());
            if (organ!=null){
                mallAfterSalesVO.setOrganizeName(organ.getFullName());
            }

            //??????????????????reason???reasonStr??????????????????
            if (mallAfterSalesVO.getReason()!=null){
                if (mallAfterSalesVO.getReason()==10402010){
                    mallAfterSalesVO.setReasonStr("??????/??????/?????????");
                }else if (mallAfterSalesVO.getReason()==10402020){
                    mallAfterSalesVO.setReasonStr("?????????/????????????");
                }else if (mallAfterSalesVO.getReason()==10402030){
                    mallAfterSalesVO.setReasonStr("??????/????????????");
                }else if (mallAfterSalesVO.getReason()==10402040){
                    mallAfterSalesVO.setReasonStr("???????????????");
                }else if (mallAfterSalesVO.getReason()==10402050){
                    mallAfterSalesVO.setReasonStr("????????????????????????");
                }else if (mallAfterSalesVO.getReason()==10402060){
                    mallAfterSalesVO.setReasonStr("???????????????????????????");
                }else if (mallAfterSalesVO.getReason()==10402070){
                    mallAfterSalesVO.setReasonStr("??????????????????/??????/??????");
                }else if (mallAfterSalesVO.getReason()==10402099){
                    mallAfterSalesVO.setReasonStr("?????? ??????????????? ????????????");
                }else if (mallAfterSalesVO.getReason()==20401010){
                    mallAfterSalesVO.setReasonStr("?????????");
                }else if (mallAfterSalesVO.getReason()==20401020){
                    mallAfterSalesVO.setReasonStr("??????/??????????????????");
                }else if (mallAfterSalesVO.getReason()==20401030){
                    mallAfterSalesVO.setReasonStr("??????/?????????????????????");
                }else if (mallAfterSalesVO.getReason()==20401040){
                    mallAfterSalesVO.setReasonStr("????????????/?????? ??????????????? ????????????");
                }else if (mallAfterSalesVO.getReason()==20402010){
                    mallAfterSalesVO.setReasonStr("?????????/????????????");
                }else if (mallAfterSalesVO.getReason()==20402020){
                    mallAfterSalesVO.setReasonStr("??????/????????????");
                }else if (mallAfterSalesVO.getReason()==20402030){
                    mallAfterSalesVO.setReasonStr("???????????????????????????????????????");
                }else if (mallAfterSalesVO.getReason()==20402040){
                    mallAfterSalesVO.setReasonStr("???????????????????????????");
                }else if (mallAfterSalesVO.getReason()==20402050){
                    mallAfterSalesVO.setReasonStr("??????????????????/??????/??????");
                }else if (mallAfterSalesVO.getReason()==20402099){
                    mallAfterSalesVO.setReasonStr("??????");
                }
            }

            //??????photos???jsonarray???List??????
            if (StringUtils.isNotEmpty(mallAfterSalesVO.getPhotos())){
                String demosub = mallAfterSalesVO.getPhotos().substring(1,mallAfterSalesVO.getPhotos().length()-1).replaceAll("\"","");
                List<String> photosArray = Arrays.asList(demosub.split(","));
                mallAfterSalesVO.setPhotosArray(photosArray);
            }

            //??????state??????
            Integer subStatus = mallAfterSalesDTO.getSubStatus();
            Integer state = null;
            if (subStatus!=null){
                if (subStatus==1010){
                    state = 1;
                }else if (subStatus==1030){
                    state = 2;
                }else if (subStatus==1050 || subStatus==2010){
                    state = 3;
                }else if (subStatus==1080 || subStatus==2020 || subStatus==2030 || (subStatus==1070 && mallAfterSalesDTO.getAuditSecondAt()>= cn.hutool.core.date.DateUtil.beginOfDay(new Date()).getTime()/1000)){
                    state = 4;
                }else if (subStatus==1020 || subStatus==1040 || subStatus==1060 || subStatus==2050 || (subStatus==1070 && mallAfterSalesDTO.getAuditSecondAt()>= cn.hutool.core.date.DateUtil.beginOfDay(new Date()).getTime()/1000)){
                    state = 6;
                }else {
                    state = 5;
                }
            }
            mallAfterSalesVO.setState(state);
        }
        return ReturnResponse.success(mallAfterSalesVO);
    }

    @Transactional
    @Override
    public boolean refundAuditFirst(MallafterSalesQuery mallafterSalesQuery) {
        UserVO userVO = getUser();  //????????????????????????
        boolean flag = false;
        MallAfterSales mallAfterSales = mallAfterSalesDao.selectById(mallafterSalesQuery.getId());
        if (mallAfterSales!=null){
            if (StringUtils.isEmpty(mallAfterSales.getShopNo()) || userVO.getShopNo().equals(mallAfterSales.getShopNo())){  //????????????
                if (mallAfterSales.getStatus()==10 && Objects.equals(mallAfterSales.getSubStatus(),1010)){
                    mallAfterSales.setSubStatus(mallafterSalesQuery.getSubStatus());
                    mallAfterSales.setAuditFirstAt(new Date().getTime());
                    mallAfterSalesDao.updateById(mallAfterSales);
                    if (Objects.equals(mallafterSalesQuery.getSubStatus(),1030)){
                        //????????????????????????
                        MallAfterSalesLog mallAfterSalesLog = new MallAfterSalesLog();
                        mallAfterSalesLog.setAfterSalesNo(mallAfterSales.getAfterSalesNo());
                        mallAfterSalesLog.setShopNo(userVO.getShopNo());
                        mallAfterSalesLog.setUserId(mallAfterSales.getUserId());
                        mallAfterSalesLog.setLogNo(IDGeneratorUtils.getLongId()+"");
                        mallAfterSalesLog.setContextShop(SalesEnum.ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_AUDIT_FIRST_PASS.description().replace("ADDRESS",mallafterSalesQuery.getReceiver()+
                                ","+mallafterSalesQuery.getReceiverPhone()+","+mallafterSalesQuery.getReceiverAddress()).replace("MONEY",mallAfterSales.getMoney().toString()));
                        mallAfterSalesLog.setContextBuyer(SalesEnum.ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_AUDIT_FIRST_PASS.description().replace("ADDRESS",mallafterSalesQuery.getReceiver()+
                                ","+mallafterSalesQuery.getReceiverPhone()+","+mallafterSalesQuery.getReceiverAddress()).replace("MONEY",mallAfterSales.getMoney().toString()));
                        mallAfterSalesLogDao.insert(mallAfterSalesLog);
                    }
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public MallOrderExportResp exportEXT(MallafterSalesQuery mallafterSalesQuery) {
        UserVO userVO = getUser();  //????????????????????????
        if (mallafterSalesQuery.getIsGroup().equals("0")){   //???????????????????????????
            mallafterSalesQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
        }else if (mallafterSalesQuery.getIsGroup().equals("1")){ //??????
            if (mallafterSalesQuery.getChildOrganizeId()==null || "".equals(mallafterSalesQuery.getChildOrganizeId()) || mallafterSalesQuery.getChildOrganizeId().equals("0")){
                //????????????????????????
                Long groupOrganizeId = userVO.getOrganizeId();   //??????id
                if (groupOrganizeId!=null) {
                    List<OrganizeRel> organizeRels = organizeRelDao.selectList(new QueryWrapper<OrganizeRel>().eq("group_organize_id", groupOrganizeId.intValue()));
                    if (organizeRels != null && organizeRels.size() > 0) {
                        List<Integer> organizeIds = organizeRels.stream().map(OrganizeRel::getOrganizeId).collect(Collectors.toList());
                        organizeIds.add(groupOrganizeId.intValue());
                        List<MallShopDTO> mallShopDTOS = mallShopDao.queryByOrganizeIdList(organizeIds);
                        if (mallShopDTOS!=null && mallShopDTOS.size()>0){
                            List<String> shopNos = mallShopDTOS.stream().map(MallShopDTO::getShopNo).collect(Collectors.toList());
                            mallafterSalesQuery.setShopNo(shopNos);
                        }
                    }
                }
            }else {
                mallafterSalesQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
                MallShop mallShop = mallShopDao.selectOne(new QueryWrapper<MallShop>().eq("organize_id", mallafterSalesQuery.getChildOrganizeId()).eq("status",2));
                if (mallShop!=null){
                    mallafterSalesQuery.setShopNo(Arrays.asList(mallShop.getShopNo()));
                }
            }
        }
        List<MallAfterSalesDTO> list = mallAfterSalesDao.getTrendMallAfterSalesList(mallafterSalesQuery);

        list.forEach(dto->{

            //??????createdTime????????????
            if (dto.getCreatedTime()!=null) {
                String orderAtStr = cn.hutool.core.date.DateUtil.format(dto.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                dto.setCreatedAtStr(orderAtStr);
            }

            //??????state??????
            Integer subStatus = dto.getSubStatus();
            Integer state = null;
            if (subStatus!=null){
                if (subStatus==1010){
                    state = 1;
                }else if (subStatus==1030){
                    state = 2;
                }else if (subStatus==1050 || subStatus==2010){
                    state = 3;
                }else if (subStatus==1080 || subStatus==2020 || subStatus==2030 || (subStatus==1070 && dto.getAuditSecondAt()>= cn.hutool.core.date.DateUtil.beginOfDay(new Date()).getTime()/1000)){
                    state = 4;
                }else if (subStatus==1020 || subStatus==1040 || subStatus==1060 || subStatus==2050 || (subStatus==1070 && dto.getAuditSecondAt()>= cn.hutool.core.date.DateUtil.beginOfDay(new Date()).getTime()/1000)){
                    state = 6;
                }else {
                    state = 5;
                }
            }
            dto.setState(state);

            if (dto.getOrder()!=null) {
                //??????????????????
                dto.setPayType(dto.getOrder().getPayType());
                //??????????????????
                dto.setAllMoney(dto.getOrder().getAllMoney());
            }
            if (dto.getDetails()!=null) {
                //??????????????????
                dto.setOrderQuantity(dto.getDetails().getQuantity());
                //????????????
                dto.setGoodsNo(dto.getDetails().getGoodsNo());
                //??????????????????
                dto.setItemName(dto.getDetails().getItemName());
            }
        });

        // ?????????????????????writer???????????????xls??????
        try {
//            String basePath = ResourceUtils.getURL("classpath:").getPath();
            Calendar now = Calendar.getInstance();
            String today = DateUtil.formatDateForYMD(now.getTime());
            now.add(Calendar.DATE,-1);
            String yestoday = DateUtil.formatDateForYMD(now.getTime());
            String randomnum = String.valueOf(IDGeneratorUtils.getLongId());
            String pathpPefix = System.getProperty("user.dir") + "/xls/";
            String fileName =today + "????????????.xls";
            String suffix = today + CommonConstant.SLASH + randomnum + CommonConstant.SLASH + userVO.getShopNo() + CommonConstant.SLASH +fileName;
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
            List<MallAfterSalesDTO> subList = list.subList(0, end);
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
                List<MallAfterSalesDTO> subList2 = list.subList(beginIndex, endIndex);
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
        //?????????????????????
        writer.addHeaderAlias("afterSalesNo", "????????????");
        writer.addHeaderAlias("orderNo", "????????????");
        writer.addHeaderAlias("itemName", "????????????");
        writer.addHeaderAlias("goodsNo", "??????");
        writer.addHeaderAlias("status", "????????????");
        writer.addHeaderAlias("state", "????????????");
        writer.addHeaderAlias("skuQuantity", "????????????");
        writer.addHeaderAlias("money", "?????????????????????");
        writer.addHeaderAlias("allMoney", "?????????????????????");
        writer.addHeaderAlias("payType", "????????????");
        writer.addHeaderAlias("orderQuantity", "????????????");
        writer.addHeaderAlias("userName", "????????????");
        writer.addHeaderAlias("createdAtStr", "??????????????????");
        writer.addHeaderAlias("isGroupSupply", "????????????");
        List<String> containList = Arrays.asList("afterSalesNo","orderNo","itemName","goodsNo",
                "status","state","skuQuantity","money","allMoney","payType","orderQuantity",
                "userName","createdAtStr","isGroupSupply");
        return containList;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReturnResponse<String> refundDetail(String orderNo) {
        UserVO userVO = getUser();  //????????????????????????
        MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no",orderNo).eq("shop_no",userVO.getShopNo()));
        if (mallOrder!=null){
            BigDecimal shippingMoney = mallOrder.getAllMoney().subtract(mallOrder.getRealMoney());  //?????????allmoney-realmoney???
            BigDecimal salesMoney = new BigDecimal(0);      //?????????????????????
            List<MallAfterSales> mallAfterSalesList = mallAfterSalesDao.selectList(new QueryWrapper<MallAfterSales>().eq("order_no",orderNo).eq("shop_no",userVO.getShopNo()));
            if (mallAfterSalesList!=null) {
                for (MallAfterSales sales : mallAfterSalesList){
                    if (sales.getSubStatus()==1020 || sales.getSubStatus()==1040 || sales.getSubStatus()==1060 || sales.getSubStatus()==1090 || sales.getSubStatus()==2040 || sales.getSubStatus()==2050){
                        continue;
                    }
                    BigDecimal itemAmount = sales.getPrice().multiply(new BigDecimal(sales.getQuantity()));
                    BigDecimal realmoney = itemAmount.compareTo(sales.getCouponDeducted())<=0 ? BigDecimal.ZERO : itemAmount.subtract(sales.getCouponDeducted());
                    BigDecimal salesShippingMoney = sales.getMoney().subtract(realmoney);
//                    BigDecimal salesShippingMoney = sales.getMoney().subtract(sales.getPrice().multiply(new BigDecimal(sales.getQuantity())));
                    salesMoney = salesMoney.add(salesShippingMoney);
                }
            }
            return ReturnResponse.success(shippingMoney.subtract(salesMoney).toString());
        }
        return ReturnResponse.failed("?????????????????????");
    }

    @Override
    public void processSubStatus() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LambdaQueryWrapper<MallAfterSales> advertisingWrapper = new LambdaQueryWrapper<MallAfterSales>()
                .eq(MallAfterSales::getSubStatus, MallAfterSalesEnum.SUB_STATUS.SUB_STATUS_1030.getCode());
        List<MallAfterSales> list = baseMapper.selectList(advertisingWrapper);
        list.forEach(entity -> {
            Date date = getCurrentDate(entity.getAuditFirstAt());
            Date endDate= DateUtils.getAddDay(date,afterSalesDays);
            //???????????????????????????????????????,??????true
            boolean bl =endDate.before(new Date());
            if(bl){
                //??????????????????
                MallAfterSales mallAfterSales = new MallAfterSales();
                mallAfterSales.setSubStatus(MallAfterSalesEnum.SUB_STATUS.SUB_STATUS_1040.getCode());
                mallAfterSales.setAfterSalesNo(entity.getAfterSalesNo());
                mallAfterSales.setCloseAt(getCurrentSeconds());
                baseMapper.updateCloseAtByAfterSalesNo(mallAfterSales);
                //????????????????????????
                MallAfterSalesLog mallAfterSalesLog = new MallAfterSalesLog();
                mallAfterSalesLog.setAfterSalesNo(entity.getAfterSalesNo());
                mallAfterSalesLog.setShopNo(entity.getShopNo());
                mallAfterSalesLog.setUserId(entity.getUserId());
                mallAfterSalesLog.setLogNo(IDGeneratorUtils.getLongId()+"");
                mallAfterSalesLog.setContextBuyer(Parser.parse(SalesEnum.ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_SYS_CLOSE.description()));
                mallAfterSalesLog.setContextShop(Parser.parse(SalesEnum.ORDER_AFTER_SALES_LOG_THTK_SHOP_ANGLE_SYS_CLOSE.description()));
                mallAfterSalesLogDao.insert(mallAfterSalesLog);
                String endDateStr = sdf.format(endDate);
                String currentDateStr = sdf.format(new Date());
                log.info(currentDateStr+" | "+endDateStr);
                log.info("????????????????????? currentDateStr:{} endDateStr:{} MallAfterSales:{}",currentDateStr,endDateStr,mallAfterSales);
            }
        });

    }

    /**
     * ??????excel????????????????????????
     * @param list
     */
    private static void salesStateCodeToString(List<Map<String, Object>> list){
        if (list!=null && list.size()>0){
            list.forEach(map->{
                //status
                if (Objects.equals(map.get("status"),10)){
                    map.put("status","????????????");
                }else if (Objects.equals(map.get("status"),20)){
                    map.put("status","?????????");
                }
                //state
                if (Objects.equals(map.get("state"),1)){
                    map.put("state","?????????");
                }else if (Objects.equals(map.get("state"),2)){
                    map.put("state","???????????????");
                }else if (Objects.equals(map.get("state"),3)){
                    map.put("state","???????????????");
                }else if (Objects.equals(map.get("state"),4)){
                    map.put("state","????????????");
                }else if (Objects.equals(map.get("state"),5)){
                    map.put("state","????????????");
                }else if (Objects.equals(map.get("state"),6)){
                    map.put("state","????????????");
                }
                //payType
                if (Objects.equals(map.get("payType"),1)){
                    map.put("payType","?????????");
                }else if (Objects.equals(map.get("payType"),2)){
                    map.put("payType","??????");
                }else {
                    map.put("payType","-");
                }
            });
        }
    }
}
