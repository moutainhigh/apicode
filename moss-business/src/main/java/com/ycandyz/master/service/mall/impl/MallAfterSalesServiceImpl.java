package com.ycandyz.master.service.mall.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.enums.mall.MallAfterSalesEnum;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.dto.mall.*;
import com.ycandyz.master.entities.mall.*;
import com.ycandyz.master.enums.SalesEnum;
import com.ycandyz.master.model.mall.*;
import com.ycandyz.master.service.mall.MallAfterSalesService;
import com.ycandyz.master.utils.DateUtils;
import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.utils.MapUtil;
import com.ycandyz.master.utils.Parser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Value("${after-sales-days:7}")
    private Integer afterSalesDays;

    @Override
    public ReturnResponse<Page<MallAfterSalesVO>> querySalesListPage(RequestParams<MallafterSalesQuery> requestParams, UserVO userVO) {
        Page<MallAfterSalesVO> mallPage = new Page<>();
        MallafterSalesQuery mallafterSalesQuery = requestParams.getT();
        if (mallafterSalesQuery==null){
            mallafterSalesQuery = new MallafterSalesQuery();
        }
        mallafterSalesQuery.setShopNo(userVO.getShopNo());
        Page pageQuery = new Page(requestParams.getPage(), requestParams.getPage_size());
        List<MallAfterSalesVO> list = new ArrayList<>();
        Page<MallAfterSalesDTO> page = null;
        try {
            page = mallAfterSalesDao.getTrendMallAfterSalesPage(pageQuery, mallafterSalesQuery);
            if (page.getRecords() != null && page.getRecords().size() > 0) {
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
    public void exportEXT(MallafterSalesQuery mallafterSalesQuery, UserVO userVO) {
        mallafterSalesQuery.setShopNo(userVO.getShopNo());
        List<MallAfterSalesDTO> list = mallAfterSalesDao.getTrendMallAfterSalesList(mallafterSalesQuery);

        list.forEach(dto->{
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
        });

        // 通过工具类创建writer，默认创建xls格式
        try {
//            String basePath = ResourceUtils.getURL("classpath:").getPath();
            ExcelWriter writer = writer = ExcelUtil.getWriter("src/main/resources/static/writeTest1.xls");
//        writer.merge(list1.size() - 1, "测试标题");
            //自定义标题别名
            writer.addHeaderAlias("afterSalesNo", "售后编号");
            writer.addHeaderAlias("orderNo", "订单编号");
            writer.addHeaderAlias("itemName", "商品名称");
            writer.addHeaderAlias("goodsNo", "货号");
            writer.addHeaderAlias("payType", "售后类型");
            writer.addHeaderAlias("status", "售后状态");
            writer.addHeaderAlias("afterSalesStatus", "退款数量");
            writer.addHeaderAlias("money", "退款金额（元）");
            writer.addHeaderAlias("allMoney", "总计金额（元）");
            writer.addHeaderAlias("receiverAddress", "支付方式");
            writer.addHeaderAlias("orderAt", "购买数量");
            writer.addHeaderAlias("userName", "购买用户");
            writer.addHeaderAlias("receiveAt", "售后申请时间");
            List<String> containList = Arrays.asList("afterSalesNo","orderNo","itemName","goodsNo",
                    "payType","status","afterSalesStatus","payuser","receiverName","receiverAddress","orderAt",
                    "userName","receiveAt");
            List<Map<String, Object>> result = MapUtil.beanToMap(list,containList);
            // 一次性写出内容，使用默认样式，强制输出标题
            writer.write(result, true);
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
}
