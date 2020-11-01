package com.ycandyz.master.service.mall.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.config.MyApplication;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.dto.mall.*;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.*;
import com.ycandyz.master.service.mall.MallOrderService;
import com.ycandyz.master.utils.DateUtil;
import com.ycandyz.master.utils.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    @Value("${excel.sheet}")
    private int num;

    @Autowired
    private MyApplication myApplication;

    @Override
    public ReturnResponse<Page<MallOrderVO>> queryOrderList(RequestParams<MallOrderQuery> requestParams, UserVO userVO) {
        MallOrderQuery mallOrderQuery = (MallOrderQuery) requestParams.getT();  //请求入参
        //获取企业id
//        if (mallOrderQuery.getOrganizeId()==null) {
//            return ReturnResponse.failed("传入企业id参数为空");
//        }
//        Long organizeId = mallOrderQuery.getOrganizeId();
        mallOrderQuery.setShopNo(userVO.getShopNo());

        List<MallOrderVO> list = new ArrayList<>();
        Page<MallOrderVO> page1 = new Page<>();
        try {
            //获取总条数
            Integer count = mallOrderDao.getTrendMallOrderPageSize(mallOrderQuery);
            page1.setTotal(count);
            if (count!=null && count>0) {
                //分页
                List<MallOrderDTO> mallDTOList = mallOrderDao.getTrendMallOrderByPage((requestParams.getPage() - 1) * requestParams.getPage_size(), requestParams.getPage_size(), mallOrderQuery);
                //page = mallOrderDao.getTrendMallOrderPage(pageQuery, mallOrderQuery);
                MallOrderVO mallOrderVo = null;
                if (mallDTOList != null && mallDTOList.size() > 0) {
                    for (MallOrderDTO mallOrderDTO : mallDTOList) {
                        if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                            mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //如果母订单号为空，则填写子订单号为母订单号
                        }
                        mallOrderVo = new MallOrderVO();
                        BeanUtils.copyProperties(mallOrderDTO, mallOrderVo);
                        //订单列表显示商品名称数组
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
                        //订单列表显示商品货号数组
                        List<String> goodsNos = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getGoodsNo).filter(x -> x != null && !"".equals(x)).collect(Collectors.toList());
                        mallOrderVo.setGoodsNo(goodsNos);
                        List<Integer> skuQuantity = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getSkuQuantity).collect(Collectors.toList());
                        if (skuQuantity != null && skuQuantity.size() > 0) {
                            int quantity = skuQuantity.stream().reduce(Integer::sum).orElse(0);
                            mallOrderVo.setQuantity(quantity);
                        }

                        if (!mallOrderVo.getStatus().equals(50)) {   //已取消订单不展示分销人相关信息
                            //订单列表显示分销合伙人，分销金额统计
                            List<MallSocialShareFlowDTO> mallSocialShareFlowDTOS = mallSocialShareFlowDao.queryByOrderNo(mallOrderVo.getOrderNo());
                            if (mallSocialShareFlowDTOS != null && mallSocialShareFlowDTOS.size() > 0) {
                                List<String> sellerUserList = new ArrayList<>();
                                List<String> shareAmountList = new ArrayList<>();
                                mallSocialShareFlowDTOS.forEach(dto -> {
                                    String sellerUser = dto.getUserName() + " " + dto.getPhoneNo();
                                    sellerUserList.add(sellerUser); //分销合伙人

                                    String shareAmount = dto.getAmount().toString();
                                    shareAmountList.add(shareAmount);   //分销佣金
                                });
                                mallOrderVo.setShareAmount(shareAmountList);
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

                            //拼接是否分佣字段 isEnableShare
                            List<Integer> isEnableShareList = detailVOList.stream().map(MallOrderDetailVO::getIsEnableShare).collect(Collectors.toList());
                            if (isEnableShareList.contains(1)){ //有分佣
                                mallOrderVo.setIsEnableShare(1);
                            }else { //无分佣
                                mallOrderVo.setIsEnableShare(0);
                            }
                        }

                        //拼接售后字段
                        List<MallAfterSales> mallAfterSalesList = mallAfterSalesDao.selectList(new QueryWrapper<MallAfterSales>().eq("order_no", mallOrderVo.getOrderNo()));
                        if (mallAfterSalesList != null && mallAfterSalesList.size() > 0) {
                            List<Integer> subStatusList = mallAfterSalesList.stream().map(MallAfterSales::getSubStatus).collect(Collectors.toList());
                            if (subStatusList.contains(1010) || subStatusList.contains(1030) || subStatusList.contains(1050) || subStatusList.contains(1070) || subStatusList.contains(2010)) {
                                //退款进行中
                                mallOrderVo.setAfterSalesStatus(1);
                            } else {
                                mallOrderVo.setAfterSalesStatus(2);
                            }
                        } else {
                            mallOrderVo.setAfterSalesStatus(0);
                        }
                        if (mallOrderVo.getAfterSalesStatus() != null) {
                            //修改售后返回值给前端
                            if (mallOrderVo.getAfterSalesStatus() != 0) {
                                mallOrderVo.setAsStatus(111);  //111: 是：申请了售后就是，跟有效期无关
                            } else {
                                if (mallOrderVo.getAfterSalesEndAt() != null && mallOrderVo.getAfterSalesEndAt() > new Date().getTime() / 1000) {
                                    mallOrderVo.setAsStatus(100);  //100: 暂无：还在有效期内，目前还没有申请售后
                                } else {
                                    mallOrderVo.setAsStatus(110);  //110: 否：超过有效期，但是没有申请售后
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

    public String exportEXT(MallOrderQuery mallOrderQuery, UserVO userVO){
        mallOrderQuery.setShopNo(userVO.getShopNo());
        List<MallOrderDTO> list = mallOrderDao.getTrendMallOrder(mallOrderQuery);
        try {
            Calendar now = Calendar.getInstance();
            String today = DateUtil.formatDateForYMD(now.getTime());
            now.add(Calendar.DATE,-1);
            String yestoday = DateUtil.formatDateForYMD(now.getTime());
            String pathpPefix = "src/main/resources/static/";
            deleteFile(yestoday, pathpPefix);
            ExcelWriter writer = ExcelUtil.getWriter(pathpPefix + today + "/订单.xls","第1页");
            double size = list.size();
            log.info("总共{}条数据",(int)size);
            int ceil = (int)Math.ceil(size / num);
            log.info("需要{}个sheet，每个sheet{}条数据",ceil,num);
            log.info("正在导出第{}sheet",1);
            List<String> containsList = addHeader(writer);
            int end = num>(int)size?(int)size:num;
            List<MallOrderDTO> subList = list.subList(0, end);
            List<Map<String, Object>> result = MapUtil.beanToMap(subList,containsList);
            writer.write(result, true);
            log.info("第{}sheet导出完成",1);
            int beginIndex = num;
            int endIndex = (beginIndex + num)>(int)size?(int)size:(beginIndex + num);
            for (int i = 2; i <= ceil; i++) {
                log.info("正在导出第{}sheet",i);
                writer.setSheet("第"+i+"页");
                List<String> containsList2 = addHeader(writer);
                List<MallOrderDTO> subList2 = list.subList(beginIndex, endIndex);
                List<Map<String, Object>> result2 = MapUtil.beanToMap(subList2,containsList2);
                writer.write(result2, true);
                beginIndex = endIndex;
                endIndex = (beginIndex + num)>(int)size?(int)size:(beginIndex + num);
                log.info("第{}sheet导出完成",i);
            }
            log.info("全部导出完成");
            writer.close();
            String url = myApplication.getUrl()+"/";
            String path = url +  today + "/订单.xls";
            log.info("文件路径{}",path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> addHeader(ExcelWriter writer) {
        writer.addHeaderAlias("cartOrderSn", "母订单编号");
        writer.addHeaderAlias("orderNo", "子订单编号");
        writer.addHeaderAlias("organizeName", "所属企业");
        writer.addHeaderAlias("itemName", "商品名称");
        writer.addHeaderAlias("goodsNo", "货号");
        writer.addHeaderAlias("allMoney", "总计金额(¥)");
        writer.addHeaderAlias("payType", "支付方式");
        writer.addHeaderAlias("quantity", "购买数量");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("isEnableShare", "是否分销");
        writer.addHeaderAlias("sellerUserName", "分销合伙人");
        writer.addHeaderAlias("shareAmount", "分佣金额统计");
        writer.addHeaderAlias("asStatus", "售后");
        writer.addHeaderAlias("payuser", "购买用户");
        writer.addHeaderAlias("receiverName", "收货人");
        writer.addHeaderAlias("receiverAddress", "收货人地址");
        writer.addHeaderAlias("orderAtStr", "下单时间");
        writer.addHeaderAlias("payedAtStr", "支付时间");
        writer.addHeaderAlias("receiveAtStr", "收货时间");
        List<String> containsList = Arrays.asList("cartOrderSn","orderNo","organizeName","itemName","goodsNo","allMoney",
                "payType","quantity","status","isEnableShare","sellerUserName","shareAmount","asStatus",
                "payuser","receiverName","receiverAddress","orderAtStr","payedAtStr","receiveAtStr");
        return containsList;
    }

    private void deleteFile(String yestoday, String pathpPefix) {
        File file = new File(pathpPefix + yestoday);
        if (file.exists()){
            File[] filePaths = file.listFiles();
            if (filePaths != null && filePaths.length > 0){
                Arrays.stream(filePaths).filter(file1 -> file1.isFile()).forEach(f->f.delete());
            }
            file.delete();
        }
    }

    @Override
    public ReturnResponse<MallOrderVO> queryOrderDetail(String orderNo, UserVO userVO) {
        MallOrderVO mallOrderVO = null;
        MallOrderDTO mallOrderDTO = mallOrderDao.queryOrderDetail(orderNo,userVO.getShopNo());
        if (mallOrderDTO != null){
            mallOrderVO = new MallOrderVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);

            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                List<MallOrderDetailVO> detailVOList = new ArrayList<>();
                mallOrderDTO.getDetails().forEach(orderDetail->{
                    MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
                    BeanUtils.copyProperties(orderDetail,mallOrderDetailVO);

                    if (mallOrderDTO.getOrderType()!=null) {
                        if (mallOrderDTO.getOrderType() == 2) { //新订单，神州通的
                            //查询佣金流水表
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
                        }else if (mallOrderDTO.getOrderType() == 1){    //老订单，商城的
                            //查询佣金流水表
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

            //查看商店
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(userVO.getShopNo());
            if (mallShopDTO==null){
                return ReturnResponse.failed("店铺信息为空");
            }
            MallShopVO mallShopVO = new MallShopVO();
            BeanUtils.copyProperties(mallShopDTO,mallShopVO);
            mallOrderVO.setShopInfo(mallShopVO);

            //获取订单详情编号列表
            List<String> orderDetailNoList = null;
            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                orderDetailNoList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getOrderDetailNo).collect(Collectors.toList());
            }

            Integer orderType = mallOrderVO.getOrderType();
            //查看售后
            if (orderDetailNoList!=null) {
                List<MallAfterSalesDTO> mallAfterSalesDTOs = mallAfterSalesDao.querySalesByOrderDetailNoList(orderDetailNoList, orderType, orderNo);
                if (mallAfterSalesDTOs != null && mallAfterSalesDTOs.size() > 0) {
                    //获取售后编号列表
                    List<String> afterSalesNoList = new ArrayList<>();
                    List<MallAfterSalesVO> voList = new ArrayList<>();
                    mallAfterSalesDTOs.forEach(dto -> {
                        MallAfterSalesVO mallAfterSalesVO = new MallAfterSalesVO();
                        BeanUtils.copyProperties(dto, mallAfterSalesVO);
                        voList.add(mallAfterSalesVO);
                        afterSalesNoList.add(dto.getAfterSalesNo());
                    });
                    mallOrderVO.setAfterSales(voList);

                    //查看售后日志
                    List<MallAfterSalesLogDTO> mallAfterSalesLogDTOs = mallAfterSalesLogDao.querySalesLogByShopNoAndSalesNoList(afterSalesNoList, userVO.getShopNo());
                    if (mallAfterSalesLogDTOs != null && mallAfterSalesLogDTOs.size() > 0) {
                        List<MallAfterSalesLogVO> salesLogVOList = new ArrayList<>();
                        mallAfterSalesLogDTOs.forEach(dto -> {
                            MallAfterSalesLogVO mallAfterSalesLogVO = new MallAfterSalesLogVO();
                            BeanUtils.copyProperties(dto, mallAfterSalesLogVO);
                            salesLogVOList.add(mallAfterSalesLogVO);
                        });
                        mallOrderVO.setAfterSalesLog(salesLogVOList);
                    }

                    //查看买家寄出的快递表
                    List<MallBuyerShippingDTO> mallBuyerShippingDTOs = mallBuyerShippingDao.queryByAfterSalesNoList(afterSalesNoList);
                    if (mallBuyerShippingDTOs != null && mallBuyerShippingDTOs.size() > 0) {
                        //获取售后编号列表
                        List<String> buyerShippingNoList = new ArrayList<>();
                        List<MallBuyerShippingVO> buyerShippingVOList = new ArrayList<>();
                        mallBuyerShippingDTOs.forEach(dto -> {
                            MallBuyerShippingVO mallBuyerShippingVO = new MallBuyerShippingVO();
                            BeanUtils.copyProperties(dto, mallBuyerShippingVO);
                            buyerShippingVOList.add(mallBuyerShippingVO);
                            buyerShippingNoList.add(dto.getBuyerShippingNo());
                        });
                        mallOrderVO.setBuyerShipping(buyerShippingVOList);

                        //查看买家寄出的快递物流日志表
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
            //商家寄出的快递表
            MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(orderNo);
            if (mallShopShippingDTO!=null){
                MallShopShippingVO mallShopShippingVO = new MallShopShippingVO();
                BeanUtils.copyProperties(mallShopShippingDTO,mallShopShippingVO);
                mallOrderVO.setShopShipping(mallShopShippingVO);

                //商家寄出的快递物流日志表
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

            //查询订单详情规格值表
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
    public ReturnResponse<MallOrderVO> queryDetailByPickupNo(String pickupNo, String orderNo, UserVO userVO) {
        MallOrderDTO mallOrderDTO = mallOrderDao.queryDetailByPickupNo(pickupNo, userVO.getShopNo());
        if (mallOrderDTO!=null){
            //判断pickNo查询到订单是否是orderNo的订单
            if (StringUtils.isNotEmpty(orderNo)){
                //orderNo不为空，说明是订单详情中进行的订单校验
                if (!orderNo.equals(mallOrderDTO.getOrderNo())){
                    return ReturnResponse.failed("当前自提码与当前订单不一致，校验失败");
                }
            }
            MallOrderVO mallOrderVO = new MallOrderVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);
            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                List<String> orderDetailNoList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getOrderDetailNo).collect(Collectors.toList());
                List<MallOrderDetailSpecDTO> specList = mallOrderDetailSpecDao.queryListByOrderDetailNoList(orderDetailNoList);     //查询订单详情规格值表
                Map<String, List<MallOrderDetailSpecVO>> map = null;
                if (specList!=null){
                    List<MallOrderDetailSpecVO> specVoList = new ArrayList<>();
                    specList.forEach(spec->{
                        MallOrderDetailSpecVO mallOrderDetailSpecVO = new MallOrderDetailSpecVO();
                        BeanUtils.copyProperties(spec,mallOrderDetailSpecVO);
                        specVoList.add(mallOrderDetailSpecVO);
                    });
                    map = specVoList.stream().collect(Collectors.groupingBy(input -> input.getOrderDetailNo()));        //吧specVO集合转换为map类型，方便下面做判断和取值
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
        return ReturnResponse.failed("查询提货码未查询到订单");
    }

    @Override
    public ReturnResponse<String> verPickupNo(String pickupNo, String orderNo, UserVO userVO) {
//        MallOrderDTO mallOrderDTO = mallOrderDao.queryDetailByOrderNo(orderNo, userVO.getShopNo());
        MallOrderDTO mallOrderDTO = mallOrderDao.queryDetailByPickupNo(pickupNo, userVO.getShopNo());
        if (mallOrderDTO!=null){
            //判断pickNo查询到订单是否是orderNo的订单
            if (StringUtils.isNotEmpty(orderNo)){
                //orderNo不为空，说明是订单详情中进行的订单校验
                if (!orderNo.equals(mallOrderDTO.getOrderNo())){
                    return ReturnResponse.failed("当前自提码与当前订单不一致，校验失败");
                }
            }
            Long time = new Date().getTime()/1000;      //获取当前时间到秒
            mallOrderDTO.setReceiveAt(time.intValue());
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(userVO.getShopNo());
            if (mallOrderDTO==null){
                return ReturnResponse.failed("未查询到店铺");
            }
            //更新数据库
            MallOrder mallOrder = new MallOrder();
            mallOrder.setId(mallOrderDTO.getId());
            mallOrder.setReceiveAt(time.intValue());
            mallOrder.setAfterSalesEndAt(time.intValue()+mallShopDTO.getAsHoldDays()*24*60*60);
            mallOrder.setStatus(40);
            mallOrder.setSubStatus(4060);
            Long timeAt = new Date().getTime()/1000;
            mallOrder.setReceiveAt(timeAt.intValue());  //更新收货时间
            mallOrderDao.updateById(mallOrder);
            return ReturnResponse.success("操作成功");
        }
        return ReturnResponse.failed("未查询到待自提订单");
    }

}
