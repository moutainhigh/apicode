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
import com.ycandyz.master.dao.organize.OrganizeRelDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.enums.mall.MallAfterSalesEnum;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.dto.mall.*;
import com.ycandyz.master.entities.mall.*;
import com.ycandyz.master.entities.organize.Organize;
import com.ycandyz.master.entities.organize.OrganizeRel;
import com.ycandyz.master.enums.SalesEnum;
import com.ycandyz.master.model.mall.*;
import com.ycandyz.master.service.mall.MallAfterSalesService;
import com.ycandyz.master.service.user.IUserExportRecordService;
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

    @Value("${after-sales-days:7}")
    private Integer afterSalesDays;

    @Value("${excel.sheet}")
    private int num;

    @Override
    public ReturnResponse<Page<MallAfterSalesVO>> querySalesListPage(RequestParams<MallafterSalesQuery> requestParams, UserVO userVO) {
        Page<MallAfterSalesVO> mallPage = new Page<>();
        List<MallAfterSalesVO> list = new ArrayList<>();
        MallafterSalesQuery mallafterSalesQuery = requestParams.getT();
        if (mallafterSalesQuery==null){
            mallafterSalesQuery = new MallafterSalesQuery();
        }
        List<Integer> organizeIds = new ArrayList<>();  //保存企业id，用于批量查询
        Map<String, Integer> shopNoAndOrganizeId = new HashMap<>(); //保存shopNo和organizeid    map<shopNo,organizeid>
        if (mallafterSalesQuery.getIsGroup().equals("0")){   //当前登陆为企业账户
            mallafterSalesQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
            organizeIds.add(userVO.getOrganizeId().intValue());
            shopNoAndOrganizeId.put(userVO.getShopNo(),userVO.getOrganizeId().intValue());
        }else if (mallafterSalesQuery.getIsGroup().equals("1")){ //集团
            if (mallafterSalesQuery.getChildOrganizeId()==null || "".equals(mallafterSalesQuery.getChildOrganizeId()) || mallafterSalesQuery.getChildOrganizeId().equals("0")){
                //查询集团所有数据
                Long groupOrganizeId = userVO.getOrganizeId();   //集团id
                if (groupOrganizeId!=null) {
                    List<OrganizeRel> organizeRels = organizeRelDao.selectList(new QueryWrapper<OrganizeRel>().eq("group_organize_id", groupOrganizeId.intValue()));
                    if (organizeRels != null && organizeRels.size() > 0) {
                        List<Integer> oids = organizeRels.stream().map(OrganizeRel::getOrganizeId).collect(Collectors.toList());
                        organizeIds.addAll(oids);
                        List<MallShopDTO> mallShopDTOS = mallShopDao.queryByOrganizeIdList(organizeIds);
                        if (mallShopDTOS!=null && mallShopDTOS.size()>0){
                            List<String> shopNos = mallShopDTOS.stream().map(MallShopDTO::getShopNo).collect(Collectors.toList());
                            mallafterSalesQuery.setShopNo(shopNos);
                            Map<String, Integer> map = mallShopDTOS.stream().collect(Collectors.toMap(MallShopDTO::getShopNo, MallShopDTO::getOrganizeId));
                            shopNoAndOrganizeId.putAll(map);
                        }
                    }
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

                //查询企业名称，通过organizedIds
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
                        }else if (subStatus==1080 || subStatus==2020 || subStatus==2030){
                            state = 4;
                        }else if (subStatus==1020 || subStatus==1040 || subStatus==1060 || subStatus==2050){
                            state = 6;
                        }else {
                            state = 5;
                        }
                    }
                    mallAfterSalesDTO.setState(state);
                    mallAfterSalesVO = new MallAfterSalesVO();
                    BeanUtils.copyProperties(mallAfterSalesDTO, mallAfterSalesVO);

                    //获取企业名称，拼接进入返回值中
                    String fullName = organizeIdAndName.get(shopNoAndOrganizeId.get(mallAfterSalesVO.getShopNo()));
                    mallAfterSalesVO.setOrganizeName(fullName);

                    //更新createdTime时间展示
                    if (mallAfterSalesVO.getCreatedTime()!=null) {
                        String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                        mallAfterSalesVO.setCreatedAtStr(orderAtStr);
                    }

                    MallOrderByAfterSalesDTO mallOrderByAfterSalesDTO = mallAfterSalesDTO.getOrder();
                    if (mallOrderByAfterSalesDTO!=null){
                        //关联订单
                        MallOrderByAfterSalesVO mallOrderByAfterSalesVO = new MallOrderByAfterSalesVO();
                        BeanUtils.copyProperties(mallOrderByAfterSalesDTO, mallOrderByAfterSalesVO);
                        mallAfterSalesVO.setOrder(mallOrderByAfterSalesVO);

                        //支付方式拼接
                        mallAfterSalesVO.setPayType(mallOrderByAfterSalesVO.getPayType());

                        //总计金额拼接
                        mallAfterSalesVO.setAllMoney(mallOrderByAfterSalesVO.getAllMoney());
                    }
                    MallOrderDetailByAfterSalesDTO mallOrderDetailByAfterSalesDTO = mallAfterSalesDTO.getDetails();
                    if (mallOrderDetailByAfterSalesDTO!=null){
                        //关联订单详情
                        MallOrderDetailByAfterSalesVO mallOrderDetailByAfterSalesVO = new MallOrderDetailByAfterSalesVO();
                        BeanUtils.copyProperties(mallOrderDetailByAfterSalesDTO,mallOrderDetailByAfterSalesVO);
                        mallAfterSalesVO.setDetails(mallOrderDetailByAfterSalesVO);

                        //购买数量拼接
                        mallAfterSalesVO.setOrderQuantity(mallOrderDetailByAfterSalesVO.getQuantity());
                        //货号拼接
                        mallAfterSalesVO.setGoodsNo(mallOrderDetailByAfterSalesVO.getGoodsNo());
                        //商品名称拼接
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
    public ReturnResponse<MallAfterSalesVO> querySalesDetail(String afterSalesNo, UserVO userVO) {
        MallAfterSalesVO mallAfterSalesVO = null;
        MallAfterSalesDTO mallAfterSalesDTO = mallAfterSalesDao.querySalesDetail(afterSalesNo,userVO.getShopNo());
        if (mallAfterSalesDTO!=null){
            mallAfterSalesVO = new MallAfterSalesVO();
            BeanUtils.copyProperties(mallAfterSalesDTO,mallAfterSalesVO);

            //更新createdTime时间展示
            if (mallAfterSalesVO.getCreatedTime()!=null) {
                String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                mallAfterSalesVO.setCreatedAtStr(orderAtStr);
            }

            MallOrderByAfterSalesDTO mallOrderByAfterSalesDTO = mallAfterSalesDTO.getOrder();
            Integer orderType = null;   //订单的类型，用来区分是新老订单
            if (mallOrderByAfterSalesDTO!=null){
                //关联订单
                MallOrderByAfterSalesVO mallOrderByAfterSalesVO = new MallOrderByAfterSalesVO();
                BeanUtils.copyProperties(mallOrderByAfterSalesDTO, mallOrderByAfterSalesVO);
                mallAfterSalesVO.setOrder(mallOrderByAfterSalesVO);
                orderType = mallOrderByAfterSalesDTO.getOrderType();

                //总计金额拼接
                mallAfterSalesVO.setAllMoney(mallOrderByAfterSalesVO.getAllMoney());
            }

            //订单详情
            MallOrderDetailByAfterSalesDTO mallOrderDetailByAfterSalesDTO = mallAfterSalesDTO.getDetails();
            MallOrderDetailByAfterSalesVO mallOrderDetailByAfterSalesVO = null;
            if (mallOrderDetailByAfterSalesDTO==null){
                //售后订单的详情是空
                if (orderType!=null && orderType==1){   //老订单
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
                //MallOrderDetail中的item信息拼接
                MallItemByMallOrderDetailVO mallItemByMallOrderDetailVO = new MallItemByMallOrderDetailVO();
                mallItemByMallOrderDetailVO.setItemNo(mallOrderDetailByAfterSalesVO.getItemNo());
                mallItemByMallOrderDetailVO.setItemName(mallOrderDetailByAfterSalesVO.getItemName());
                mallItemByMallOrderDetailVO.setGoodsNo(mallOrderDetailByAfterSalesVO.getGoodsNo());
                mallItemByMallOrderDetailVO.setItemCover(mallOrderDetailByAfterSalesVO.getItemCover());
                mallItemByMallOrderDetailVO.setPrice(mallOrderDetailByAfterSalesVO.getPrice());
                mallItemByMallOrderDetailVO.setQuantity(mallOrderDetailByAfterSalesVO.getQuantity());

                //MallOrderDetailSpec中的specValue商品规格拼接
                List<MallOrderDetailSpecDTO> mallOrderDetailSpecDTOs = mallItemSpecByMallOrderDetailSpecVO.queryListByOrderDetailNo(mallOrderDetailByAfterSalesVO.getOrderDetailNo());
                if (mallOrderDetailSpecDTOs!=null && mallOrderDetailSpecDTOs.size()>0){
                    List<MallOrderDetailSpecVO> mallOrderDetailSpecVOS = new ArrayList<>();
                    List<MallItemSpecByMallOrderDetailSpecVO> mallItemSpecByMallOrderDetailSpecVOS = new ArrayList<>();
                    mallOrderDetailSpecDTOs.forEach(dto->{
                        //拼接数据到item中
                        MallItemSpecByMallOrderDetailSpecVO mallItemSpecByMallOrderDetailSpecVO = new MallItemSpecByMallOrderDetailSpecVO();
                        mallItemSpecByMallOrderDetailSpecVO.setSpecValue(dto.getSpecValue());
                        mallItemSpecByMallOrderDetailSpecVOS.add(mallItemSpecByMallOrderDetailSpecVO);

                        //拼接数据到details中
                        MallOrderDetailSpecVO mallOrderDetailSpecVO = new MallOrderDetailSpecVO();
                        BeanUtils.copyProperties(dto,mallOrderDetailSpecVO);
                        mallOrderDetailSpecVOS.add(mallOrderDetailSpecVO);
                    });
                    mallItemByMallOrderDetailVO.setSpec(mallItemSpecByMallOrderDetailSpecVOS);
                    mallAfterSalesVO.getDetails().setSpecs(mallOrderDetailSpecVOS);
                }
                mallAfterSalesVO.setItemInfo(mallItemByMallOrderDetailVO);
            }

            //MallAfterSalesLog表
            List<MallAfterSalesLogDTO> mallAfterSalesLogDTOs = mallAfterSalesLogDao.querySalesLogByShopNoAndSalesNo(mallAfterSalesDTO.getAfterSalesNo(),userVO.getShopNo());
            if (mallAfterSalesLogDTOs!=null && mallAfterSalesLogDTOs.size()>0){
                List<MallAfterSalesLogVO> mallAfterSalesLogVOS = new ArrayList<>();
                mallAfterSalesLogDTOs.forEach(dto->{
                    MallAfterSalesLogVO mallAfterSalesLogVO = new MallAfterSalesLogVO();
                    BeanUtils.copyProperties(dto,mallAfterSalesLogVO);
                    mallAfterSalesLogVOS.add(mallAfterSalesLogVO);
                });
                mallAfterSalesVO.setAsLog(mallAfterSalesLogVOS);
            }

            //MallShopShpping表
            MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(mallAfterSalesDTO.getOrderNo());
            if (mallShopShippingDTO!=null){
                MallShopShippingVO mallShopShippingVO = new MallShopShippingVO();
                BeanUtils.copyProperties(mallShopShippingDTO,mallShopShippingVO);
                mallAfterSalesVO.setShippingInfo(mallShopShippingVO);

                //MallShopShippingLog表
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

            //MallBuyerShipping表
            MallBuyerShippingDTO mallBuyerShippingDTO = mallBuyerShippingDao.queryByAfterSalesNo(mallAfterSalesVO.getAfterSalesNo());
            if (mallBuyerShippingDTO!=null){
                MallBuyerShippingVO mallBuyerShippingVO = new MallBuyerShippingVO();
                BeanUtils.copyProperties(mallBuyerShippingDTO,mallBuyerShippingVO);
                mallAfterSalesVO.setBuyerShipping(mallBuyerShippingVO);

                //MallBuyerShippingLog表
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

            //MallShop信息拼接
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(userVO.getShopNo());
            if (mallShopDTO!=null){
                MallShopVO mallShopVO = new MallShopVO();
                BeanUtils.copyProperties(mallShopDTO,mallShopVO);
                mallAfterSalesVO.getOrder().setShop(mallShopVO);
            }

            //转换退款原因reason为reasonStr（文字描述）
            if (mallAfterSalesVO.getReason()!=null){
                if (mallAfterSalesVO.getReason()==10402010){
                    mallAfterSalesVO.setReasonStr("多拍/拍错/不想要");
                }else if (mallAfterSalesVO.getReason()==10402020){
                    mallAfterSalesVO.setReasonStr("不喜欢/效果不好");
                }else if (mallAfterSalesVO.getReason()==10402030){
                    mallAfterSalesVO.setReasonStr("做工/质量问题");
                }else if (mallAfterSalesVO.getReason()==10402040){
                    mallAfterSalesVO.setReasonStr("商家发错货");
                }else if (mallAfterSalesVO.getReason()==10402050){
                    mallAfterSalesVO.setReasonStr("未按约定时间发货");
                }else if (mallAfterSalesVO.getReason()==10402060){
                    mallAfterSalesVO.setReasonStr("与商品描述严重不符");
                }else if (mallAfterSalesVO.getReason()==10402070){
                    mallAfterSalesVO.setReasonStr("收到商品少件/破损/污渍");
                }else if (mallAfterSalesVO.getReason()==10402099){
                    mallAfterSalesVO.setReasonStr("其他 仅退款原因 未收到货");
                }else if (mallAfterSalesVO.getReason()==20401010){
                    mallAfterSalesVO.setReasonStr("空包裹");
                }else if (mallAfterSalesVO.getReason()==20401020){
                    mallAfterSalesVO.setReasonStr("快递/物流一直未到");
                }else if (mallAfterSalesVO.getReason()==20401030){
                    mallAfterSalesVO.setReasonStr("快递/物流无跟踪记录");
                }else if (mallAfterSalesVO.getReason()==20401040){
                    mallAfterSalesVO.setReasonStr("货物破损/变质 仅退款原因 已收到货");
                }else if (mallAfterSalesVO.getReason()==20402010){
                    mallAfterSalesVO.setReasonStr("不喜欢/效果不好");
                }else if (mallAfterSalesVO.getReason()==20402020){
                    mallAfterSalesVO.setReasonStr("做工/质量问题");
                }else if (mallAfterSalesVO.getReason()==20402030){
                    mallAfterSalesVO.setReasonStr("商家发错货未按约定时间发货");
                }else if (mallAfterSalesVO.getReason()==20402040){
                    mallAfterSalesVO.setReasonStr("与商品描述严重不符");
                }else if (mallAfterSalesVO.getReason()==20402050){
                    mallAfterSalesVO.setReasonStr("收到商品少件/破损/污渍");
                }else if (mallAfterSalesVO.getReason()==20402099){
                    mallAfterSalesVO.setReasonStr("其他");
                }
            }

            //转换photos，jsonarray为List数组
            if (StringUtils.isNotEmpty(mallAfterSalesVO.getPhotos())){
                String demosub = mallAfterSalesVO.getPhotos().substring(1,mallAfterSalesVO.getPhotos().length()-1).replaceAll("\"","");
                List<String> photosArray = Arrays.asList(demosub.split(","));
                mallAfterSalesVO.setPhotosArray(photosArray);
            }

            //拼接state字段
            Integer subStatus = mallAfterSalesDTO.getSubStatus();
            Integer state = null;
            if (subStatus!=null){
                if (subStatus==1010){
                    state = 1;
                }else if (subStatus==1030){
                    state = 2;
                }else if (subStatus==1050 || subStatus==2010){
                    state = 3;
                }else if (subStatus==1080 || subStatus==2020 || subStatus==2030){
                    state = 4;
                }else if (subStatus==1020 || subStatus==1040 || subStatus==1060 || subStatus==2050){
                    state = 6;
                }else {
                    state = 5;
                }
            }
            mallAfterSalesVO.setState(state);
            //计算本次退款记录的退款金额
            mallAfterSalesVO.setRefundMoney(mallAfterSalesDTO.getSkuPrice().multiply(new BigDecimal(mallAfterSalesDTO.getSkuQuantity())));
        }
        return ReturnResponse.success(mallAfterSalesVO);
    }

    @Transactional
    @Override
    public boolean refundAuditFirst(MallafterSalesQuery mallafterSalesQuery, UserVO userVO) {
        boolean flag = false;
        MallAfterSales mallAfterSales = mallAfterSalesDao.selectById(mallafterSalesQuery.getId());
        if (mallAfterSales!=null){
            if (StringUtils.isEmpty(mallAfterSales.getShopNo()) || userVO.getShopNo().equals(mallAfterSales.getShopNo())){  //鉴权用户
                if (mallAfterSales.getStatus()==10 && Objects.equals(mallAfterSales.getSubStatus(),1010)){
                    mallAfterSales.setSubStatus(mallafterSalesQuery.getSubStatus());
                    mallAfterSales.setAuditFirstAt(new Date().getTime());
                    mallAfterSalesDao.updateById(mallAfterSales);
                    if (Objects.equals(mallafterSalesQuery.getSubStatus(),1030)){
                        //审批通过记录日志
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
    public MallOrderExportResp exportEXT(MallafterSalesQuery mallafterSalesQuery, UserVO userVO) {
        if (mallafterSalesQuery.getIsGroup().equals("0")){   //当前登陆为企业账户
            mallafterSalesQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
        }else if (mallafterSalesQuery.getIsGroup().equals("1")){ //集团
            if (mallafterSalesQuery.getChildOrganizeId()==null || "".equals(mallafterSalesQuery.getChildOrganizeId()) || mallafterSalesQuery.getChildOrganizeId().equals("0")){
                //查询集团所有数据
                Long groupOrganizeId = userVO.getOrganizeId();   //集团id
                if (groupOrganizeId!=null) {
                    List<OrganizeRel> organizeRels = organizeRelDao.selectList(new QueryWrapper<OrganizeRel>().eq("group_organize_id", groupOrganizeId.intValue()));
                    if (organizeRels != null && organizeRels.size() > 0) {
                        List<Integer> organizeIds = organizeRels.stream().map(OrganizeRel::getOrganizeId).collect(Collectors.toList());
                        List<MallShopDTO> mallShopDTOS = mallShopDao.queryByOrganizeIdList(organizeIds);
                        if (mallShopDTOS!=null && mallShopDTOS.size()>0){
                            List<String> shopNos = mallShopDTOS.stream().map(MallShopDTO::getShopNo).collect(Collectors.toList());
                            mallafterSalesQuery.setShopNo(shopNos);
                        }
                    }
                }
            }else {
                mallafterSalesQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
                MallShop mallShop = mallShopDao.selectOne(new QueryWrapper<MallShop>().eq("organize_id", mallafterSalesQuery.getChildOrganizeId()));
                if (mallShop!=null){
                    mallafterSalesQuery.setShopNo(Arrays.asList(mallShop.getShopNo()));
                }
            }
        }
        List<MallAfterSalesDTO> list = mallAfterSalesDao.getTrendMallAfterSalesList(mallafterSalesQuery);

        list.forEach(dto->{

            //更新createdTime时间展示
            if (dto.getCreatedTime()!=null) {
                String orderAtStr = cn.hutool.core.date.DateUtil.format(dto.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                dto.setCreatedAtStr(orderAtStr);
            }

            //拼接state字段
            Integer subStatus = dto.getSubStatus();
            Integer state = null;
            if (subStatus!=null){
                if (subStatus==1010){
                    state = 1;
                }else if (subStatus==1030){
                    state = 2;
                }else if (subStatus==1050 || subStatus==2010){
                    state = 3;
                }else if (subStatus==1080 || subStatus==2020 || subStatus==2030){
                    state = 4;
                }else if (subStatus==1020 || subStatus==1040 || subStatus==1060 || subStatus==2050){
                    state = 6;
                }else {
                    state = 5;
                }
            }
            dto.setState(state);

            if (dto.getOrder()!=null) {
                //支付方式拼接
                dto.setPayType(dto.getOrder().getPayType());
                //总计金额拼接
                dto.setAllMoney(dto.getOrder().getAllMoney());
            }
            if (dto.getDetails()!=null) {
                //购买数量拼接
                dto.setOrderQuantity(dto.getDetails().getQuantity());
                //货号拼接
                dto.setGoodsNo(dto.getDetails().getGoodsNo());
                //商品名称拼接
                dto.setItemName(dto.getDetails().getItemName());
            }
        });

        // 通过工具类创建writer，默认创建xls格式
        try {
//            String basePath = ResourceUtils.getURL("classpath:").getPath();
            Calendar now = Calendar.getInstance();
            String today = DateUtil.formatDateForYMD(now.getTime());
            now.add(Calendar.DATE,-1);
            String yestoday = DateUtil.formatDateForYMD(now.getTime());
            String randomnum = String.valueOf(IDGeneratorUtils.getLongId());
            String pathpPefix = System.getProperty("user.dir") + "/xls/";
            String fileName =today + "售后订单.xls";
            String suffix = today + CommonConstant.SLASH + randomnum + CommonConstant.SLASH + userVO.getShopNo() + CommonConstant.SLASH +fileName;
            String path = pathpPefix + suffix;
            deleteFile(yestoday,pathpPefix);
            ExcelWriter writer = ExcelUtil.getWriter(path,"第1页");

            //设置单元格格式
            writer.getStyleSet().setAlign(HorizontalAlignment.LEFT, VerticalAlignment.CENTER); //水平左对齐，垂直中间对齐
            writer.setColumnWidth(0, 40); //第1列40px宽

            double size = list.size();
            log.info("总共{}条数据",(int)size);
            int ceil = (int)Math.ceil(size / num);
            log.info("需要{}个sheet，每个sheet{}条数据",ceil,num);
            log.info("正在导出第{}sheet",1);
            List<String> containsList = addHeader(writer);
            int end = num>(int)size?(int)size:num;
            List<MallAfterSalesDTO> subList = list.subList(0, end);
            MapUtil mapUtil = new MapUtil();
            List<Map<String, Object>> result = mapUtil.beanToMap(subList,containsList);
            salesStateCodeToString(result);

            writer.write(result, true);
            log.info("第{}sheet导出完成",1);
            int beginIndex = num;
            int endIndex = (beginIndex + num)>(int)size?(int)size:(beginIndex + num);

            for (int i = 2; i <= ceil; i++) {
                log.info("正在导出第{}sheet",i);
                writer.setSheet("第"+i+"页");
                List<String> containsList2 = addHeader(writer);
                List<MallAfterSalesDTO> subList2 = list.subList(beginIndex, endIndex);
                List<Map<String, Object>> result2 = mapUtil.beanToMap(subList2,containsList2);
                salesStateCodeToString(result2);
                writer.write(result2, true);
                beginIndex = endIndex;
                endIndex = (beginIndex + num)>(int)size?(int)size:(beginIndex + num);
                log.info("第{}sheet导出完成",i);
            }
            log.info("全部导出完成");

            writer.close();
            log.info("文件本地存储路径:{}",path);
            File file = new File(path);
            String url = s3UploadFile.uploadFile(file, suffix);
            MallOrderExportResp mallOrderExportResp = new MallOrderExportResp();
            mallOrderExportResp.setFileName(fileName);
            mallOrderExportResp.setFielUrl(url);
            log.info("导出excel响应:{}",mallOrderExportResp);
            //导出记录表
            iUserExportRecordService.insertExportRecord(mallOrderExportResp,userVO);

            return mallOrderExportResp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> addHeader(ExcelWriter writer) {
        //自定义标题别名
        writer.addHeaderAlias("afterSalesNo", "售后编号");
        writer.addHeaderAlias("orderNo", "订单编号");
        writer.addHeaderAlias("itemName", "商品名称");
        writer.addHeaderAlias("goodsNo", "货号");
        writer.addHeaderAlias("status", "售后类型");
        writer.addHeaderAlias("state", "售后状态");
        writer.addHeaderAlias("skuQuantity", "退款数量");
        writer.addHeaderAlias("money", "退款金额（元）");
        writer.addHeaderAlias("allMoney", "总计金额（元）");
        writer.addHeaderAlias("payType", "支付方式");
        writer.addHeaderAlias("orderQuantity", "购买数量");
        writer.addHeaderAlias("userName", "购买用户");
        writer.addHeaderAlias("createdAtStr", "售后申请时间");
        List<String> containList = Arrays.asList("afterSalesNo","orderNo","itemName","goodsNo",
                "status","state","skuQuantity","money","allMoney","payType","orderQuantity",
                "userName","createdAtStr");
        return containList;
    }

    //删除昨天的全部文件和今天同一shopNo下的文件
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
    public ReturnResponse<String> refundDetail(String orderNo, UserVO userVO) {
        MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no",orderNo).eq("shop_no",userVO.getShopNo()));
        if (mallOrder!=null){
            BigDecimal shippingMoney = mallOrder.getAllMoney().subtract(mallOrder.getRealMoney());  //运费
            BigDecimal salesMoney = new BigDecimal(0);      //已经退掉的运费
            List<MallAfterSales> mallAfterSalesList = mallAfterSalesDao.selectList(new QueryWrapper<MallAfterSales>().eq("order_no",orderNo).eq("shop_no",userVO.getShopNo()));
            if (mallAfterSalesList!=null) {
                for (MallAfterSales sales : mallAfterSalesList){
                    if (sales.getSubStatus()==1020 || sales.getSubStatus()==1040 || sales.getSubStatus()==1060 || sales.getSubStatus()==1090 || sales.getSubStatus()==2040 || sales.getSubStatus()==2050){
                        continue;
                    }
                    BigDecimal salesShippingMoney = sales.getMoney().subtract(sales.getPrice().multiply(new BigDecimal(sales.getQuantity())));
                    salesMoney = salesMoney.add(salesShippingMoney);
                }
            }
            return ReturnResponse.success(shippingMoney.subtract(salesMoney).toString());
        }
        return ReturnResponse.failed("当前订单不存在");
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
            //如果截止时间在当前时间之前,返回true
            boolean bl =endDate.before(new Date());
            if(bl){
                //更新售后状态
                MallAfterSales mallAfterSales = new MallAfterSales();
                mallAfterSales.setSubStatus(MallAfterSalesEnum.SUB_STATUS.SUB_STATUS_1040.getCode());
                mallAfterSales.setAfterSalesNo(entity.getAfterSalesNo());
                mallAfterSales.setCloseAt(getCurrentSeconds());
                baseMapper.updateCloseAtByAfterSalesNo(mallAfterSales);
                //添加超时售后记录
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
                log.info("已超出退货时间 currentDateStr:{} endDateStr:{} MallAfterSales:{}",currentDateStr,endDateStr,mallAfterSales);
            }
        });

    }

    /**
     * 导出excel中的状态码转中文
     * @param list
     */
    private static void salesStateCodeToString(List<Map<String, Object>> list){
        if (list!=null && list.size()>0){
            list.forEach(map->{
                //status
                if (Objects.equals(map.get("status"),10)){
                    map.put("status","退货退款");
                }else if (Objects.equals(map.get("status"),20)){
                    map.put("status","仅退款");
                }
                //state
                if (Objects.equals(map.get("state"),1)){
                    map.put("state","待审核");
                }else if (Objects.equals(map.get("state"),2)){
                    map.put("state","待买家退货");
                }else if (Objects.equals(map.get("state"),3)){
                    map.put("state","待确认退款");
                }else if (Objects.equals(map.get("state"),4)){
                    map.put("state","退款成功");
                }else if (Objects.equals(map.get("state"),5)){
                    map.put("state","退款失败");
                }else if (Objects.equals(map.get("state"),6)){
                    map.put("state","退款关闭");
                }
                //payType
                if (Objects.equals(map.get("payType"),1)){
                    map.put("payType","支付宝");
                }else if (Objects.equals(map.get("payType"),2)){
                    map.put("payType","微信");
                }else {
                    map.put("payType","-");
                }
            });
        }
    }
}
