package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.dto.mall.*;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.*;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.MallOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
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

    @Override
    public ReturnResponse<Page<MallOrderVO>> queryOrderList(RequestParams<MallOrderQuery> requestParams, UserVO userVO) {
        MallOrderQuery mallOrderQuery = (MallOrderQuery) requestParams.getT();  //请求入参
        //获取企业id
//        if (mallOrderQuery.getOrganizeId()==null) {
//            return ReturnResponse.failed("传入企业id参数为空");
//        }
//        Long organizeId = mallOrderQuery.getOrganizeId();
        mallOrderQuery.setShopNo(userVO.getShopNo());
        Page pageQuery = new Page(requestParams.getPage(),requestParams.getPage_size());
        List<MallOrderVO> list = new ArrayList<>();
        Page<MallOrderVO> page1 = new Page<>();
        Page<MallOrderDTO> page = null;
        try {
            page = mallOrderDao.getTrendMallOrderPage(pageQuery, mallOrderQuery);
            MallOrderVO mallOrderVo = null;
            if (page.getRecords()!=null && page.getRecords().size()>0) {
                for (MallOrderDTO mallOrderDTO : page.getRecords()) {
                    if (mallOrderDTO.getCartOrderSn()==null || "".equals(mallOrderDTO.getCartOrderSn())){
                        mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());
                    }
                    if (mallOrderDTO.getAfterSalesStatus()!=null){
                        //修改售后返回值给前端
                        if (mallOrderDTO.getAfterSalesStatus()==0){
                            mallOrderDTO.setAsStatus(111);  //111: 是：申请了售后就是，跟有效期无关
                        }else {
                            if (mallOrderDTO.getAfterSalesEndAt()!=null && mallOrderDTO.getAfterSalesEndAt()>new Date().getTime()/1000){
                                mallOrderDTO.setAsStatus(100);  //100: 暂无：还在有效期内，目前还没有申请售后
                            }else {
                                mallOrderDTO.setAsStatus(110);  //110: 否：超过有效期，但是没有申请售后
                            }
                        }
                    }
                    mallOrderVo = new MallOrderVO();
                    BeanUtils.copyProperties(mallOrderDTO, mallOrderVo);
                    //订单列表显示商品名称数组
                    List<String> itemNames = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getItemName).collect(Collectors.toList());
                    mallOrderVo.setItemName(itemNames);
                    //订单列表显示商品货号数组
                    List<String> goodsNos = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getGoodsNo).collect(Collectors.toList());
                    mallOrderVo.setGoodsNo(goodsNos);
                    List<Integer> skuQuantity = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getSkuQuantity).collect(Collectors.toList());
                    if (skuQuantity!=null && skuQuantity.size()>0){
                        int quantity = skuQuantity.stream().reduce(Integer::sum).orElse(0);
                        mallOrderVo.setQuantity(quantity);
                    }
                    list.add(mallOrderVo);
                }
            }
        }catch (Exception e){
            page = new Page<>(0,10,0);
        }
        page1.setPages(page.getPages());
        page1.setCurrent(page.getCurrent());
        page1.setOrders(page.getOrders());
        page1.setRecords(list);
        page1.setSize(page.getSize());
        page1.setTotal(page.getTotal());
        return ReturnResponse.success(page1);
    }

    public void exportEXT(MallOrderQuery mallOrderQuery, UserVO userVO, HttpServletResponse response){
        mallOrderQuery.setShopNo(userVO.getShopNo());
        List<MallOrderDTO> list = mallOrderDao.getTrendMallOrder(mallOrderQuery);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("cartOrderSn", "母订单编号");
        writer.addHeaderAlias("orderNo", "订单编号");
        writer.addHeaderAlias("itemName", "商品名称");
        writer.addHeaderAlias("allMoney", "总计金额");
        writer.addHeaderAlias("payType", "支付方式");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("afterSalesStatus", "售后");
        writer.addHeaderAlias("payuser", "购买用户");
        writer.addHeaderAlias("receiverName", "收货人");
        writer.addHeaderAlias("receiverAddress", "收货地址");
        writer.addHeaderAlias("orderAt", "下单时间");
        writer.addHeaderAlias("paymentTime", "支付时间");
        writer.addHeaderAlias("receiveAt", "收货时间");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String name = "Order-list";
        response.setHeader("Content-Disposition","attachment;filename="+name+".xls");
        ServletOutputStream out= null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        // 关闭writer，释放内存ß
            writer.close();
        }
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    @Override
    public ReturnResponse<MallOrderVO> queryOrderDetail(String orderNo, UserVO userVO) {
        MallOrderVO mallOrderVO = null;
        MallOrderDTO mallOrderDTO = mallOrderDao.queryOrderDetail(orderNo);
        if (mallOrderDTO != null){
            mallOrderVO = new MallOrderVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);

            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                List<MallOrderDetailVO> detailVOList = new ArrayList<>();
                mallOrderDTO.getDetails().forEach(orderDetail->{
                    MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
                    BeanUtils.copyProperties(orderDetail,mallOrderDetailVO);
                    detailVOList.add(mallOrderDetailVO);
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

            //查看售后
            if (orderDetailNoList!=null) {
                List<MallAfterSalesDTO> mallAfterSalesDTOs = mallAfterSalesDao.querySalesByOrderDetailNoList(orderDetailNoList);
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

            //查询佣金流水表
            List<MallSocialShareFlowDTO> mallSocialShareFlowDTOs = mallSocialShareFlowDao.queryByOrderNo(mallOrderVO.getOrderNo());
            if (mallSocialShareFlowDTOs!=null && mallSocialShareFlowDTOs.size()>0){
                List<MallSocialShareFlowVO> flowList = new ArrayList<>();
                mallSocialShareFlowDTOs.forEach(dto->{
                    MallSocialShareFlowVO mallSocialShareFlowVO = new MallSocialShareFlowVO();
                    BeanUtils.copyProperties(dto,mallSocialShareFlowVO);
                    flowList.add(mallSocialShareFlowVO);
                });
                mallOrderVO.setShareInfo(flowList);
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
            mallOrderDao.updateById(mallOrder);
            return ReturnResponse.success("操作成功");
        }
        return ReturnResponse.failed("未查询到待自提订单");
    }

}
