package com.ycandyz.master.service.mall.impl;

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
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.dao.organize.OrganizeRelDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.domain.query.mall.MallOrderUAppQuery;
import com.ycandyz.master.domain.query.mall.uApp.MallPickupUAppQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.dto.mall.*;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.entities.organize.Organize;
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

    @Value("${excel.sheet}")
    private int num;

    @Override
    public ReturnResponse<Page<MallOrderVO>> queryOrderList(RequestParams<MallOrderQuery> requestParams) {
        UserVO userVO = getUser();  //获取当前登陆用户
        List<MallOrderVO> list = new ArrayList<>();
        Page<MallOrderVO> page1 = new Page<>();
        MallOrderQuery mallOrderQuery = (MallOrderQuery) requestParams.getT();  //请求入参
        List<Integer> organizeIds = new ArrayList<>();  //保存企业id，用于批量查询
        Map<String, Integer> shopNoAndOrganizeId = new HashMap<>(); //保存shopNo和organizeid    map<shopNo,organizeid>
        //获取企业id
        if (mallOrderQuery.getIsGroup().equals("0")){   //当前登陆为企业账户
            mallOrderQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
            organizeIds.add(userVO.getOrganizeId().intValue());
            shopNoAndOrganizeId.put(userVO.getShopNo(),userVO.getOrganizeId().intValue());
        }else if (mallOrderQuery.getIsGroup().equals("1")){ //集团
            if (mallOrderQuery.getChildOrganizeId()==null || "".equals(mallOrderQuery.getChildOrganizeId()) || mallOrderQuery.getChildOrganizeId().equals("0")){
                //查询集团所有数据
                Long groupOrganizeId = userVO.getOrganizeId();   //集团id
                if (groupOrganizeId!=null) {
                    List<OrganizeRel> organizeRels = organizeRelDao.selectList(new QueryWrapper<OrganizeRel>().eq("group_organize_id", groupOrganizeId.intValue()).eq("status",2));
                    if (organizeRels != null && organizeRels.size() > 0) {
                        List<Integer> oIds = organizeRels.stream().map(OrganizeRel::getOrganizeId).collect(Collectors.toList());
                        organizeIds.addAll(oIds);
                        organizeIds.add(groupOrganizeId.intValue());
                        List<MallShopDTO> mallShopDTOS = mallShopDao.queryByOrganizeIdList(organizeIds);
                        if (mallShopDTOS!=null && mallShopDTOS.size()>0){
                            List<String> shopNos = mallShopDTOS.stream().map(MallShopDTO::getShopNo).collect(Collectors.toList());
                            mallOrderQuery.setShopNo(shopNos);
                            Map<String, Integer> map = mallShopDTOS.stream().collect(Collectors.toMap(MallShopDTO::getShopNo, MallShopDTO::getOrganizeId));
                            shopNoAndOrganizeId.putAll(map);
                        }
                    }
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
            //获取总条数
            Integer count = mallOrderDao.getTrendMallOrderPageSize(mallOrderQuery);
            page1.setTotal(count);
            if (count!=null && count>0) {
                //分页
                List<MallOrderDTO> mallDTOList = mallOrderDao.getTrendMallOrderByPage((requestParams.getPage() - 1) * requestParams.getPage_size(), requestParams.getPage_size(), mallOrderQuery);
                //page = mallOrderDao.getTrendMallOrderPage(pageQuery, mallOrderQuery);
                MallOrderVO mallOrderVo = null;
                if (mallDTOList != null && mallDTOList.size() > 0) {

                    //查询企业名称，通过organizedIds
                    List<Organize> organizeList = organizeDao.selectBatchIds(organizeIds);
                    Map<Integer, String> organizeIdAndName = organizeList.stream().collect(Collectors.toMap(Organize::getId, Organize::getFullName));

                    for (MallOrderDTO mallOrderDTO : mallDTOList) {
                        if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                            mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //如果母订单号为空，则填写子订单号为母订单号
                        }
                        mallOrderVo = new MallOrderVO();
                        BeanUtils.copyProperties(mallOrderDTO, mallOrderVo);

                        //获取企业名称，拼接进入返回值中
                        String fullName = organizeIdAndName.get(shopNoAndOrganizeId.get(mallOrderVo.getShopNo()));
                        mallOrderVo.setOrganizeName(fullName);

                        //order_at;payed_at;receive_at时间转换为字符串
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

                        if (mallOrderVo.getStatus()!=50 && mallOrderVo.getStatus()!=10) {   //已取消订单不展示分销人相关信息
                            //订单列表显示分销合伙人，分销金额统计
                            List<MallSocialShareFlowDTO> mallSocialShareFlowDTOS = mallSocialShareFlowDao.queryAllShareByOrderNo(mallOrderVo.getOrderNo());
                            if (mallSocialShareFlowDTOS != null && mallSocialShareFlowDTOS.size() > 0) {
                                List<String> sellerUserList = new ArrayList<>();
                                BigDecimal shareAmount = new BigDecimal(0);
                                for (MallSocialShareFlowDTO dto : mallSocialShareFlowDTOS) {
                                    if (dto.getShareType()==0) {
                                        String sellerUser = dto.getUserName() + " " + dto.getPhoneNo();
                                        sellerUserList.add(sellerUser); //分销合伙人
                                    }
                                    shareAmount = shareAmount.add(dto.getAmount());   //分销佣金
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

                            //拼接是否分佣字段 isEnableShare
                            List<Integer> isEnableShareList = detailVOList.stream().map(MallOrderDetailVO::getIsEnableShare).collect(Collectors.toList());
                            if (isEnableShareList.contains(1) && mallOrderVo.getStatus()!=50 && mallOrderVo.getStatus()!=10){ //有分佣
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

    public MallOrderExportResp exportEXT(MallOrderQuery mallOrderQuery){
        UserVO userVO = getUser();  //获取当前登陆用户
        //获取企业id
        if (mallOrderQuery.getIsGroup().equals("0")){   //当前登陆为企业账户
            mallOrderQuery.setShopNo(Arrays.asList(userVO.getShopNo()));
        }else if (mallOrderQuery.getIsGroup().equals("1")){ //集团
            if (mallOrderQuery.getChildOrganizeId()==null || "".equals(mallOrderQuery.getChildOrganizeId()) || mallOrderQuery.getChildOrganizeId().equals("0")){
                //查询集团所有数据
                Long groupOrganizeId = userVO.getOrganizeId();   //集团id
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

                //order_at;payed_at;receive_at时间转换为字符串
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
                    mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //如果母订单号为空，则填写子订单号为母订单号
                }

                if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0) {
                    //订单列表显示商品名称数组
                    List<String> itemNames = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getItemName).collect(Collectors.toList());
                    mallOrderDTO.setItemName(itemNames);
                    //订单中的购买商品数量
                    List<Integer> skuQuantity = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getSkuQuantity).collect(Collectors.toList());
                    if (skuQuantity != null && skuQuantity.size() > 0) {
                        int quantity = skuQuantity.stream().reduce(Integer::sum).orElse(0);
                        mallOrderDTO.setQuantity(quantity);
                    }
                    //订单列表显示商品货号数组
                    List<String> goodsNos = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getGoodsNo).filter(x -> x != null && !"".equals(x)).collect(Collectors.toList());
                    mallOrderDTO.setGoodsNo(goodsNos);
                    //拼接是否分佣字段 isEnableShare
                    List<Integer> isEnableShareList = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getIsEnableShare).collect(Collectors.toList());
                    if (isEnableShareList.contains(1) && mallOrderDTO.getStatus() != 50 && mallOrderDTO.getStatus() != 10) { //有分佣
                        mallOrderDTO.setIsEnableShare(1);
                    } else { //无分佣
                        mallOrderDTO.setIsEnableShare(0);
                    }
                }
                if (mallOrderDTO.getStatus()!=50 && mallOrderDTO.getStatus()!=10) {   //已取消订单不展示分销人相关信息
                    //订单列表显示分销合伙人，分销金额统计
                    List<MallSocialShareFlowDTO> mallSocialShareFlowDTOS = mallSocialShareFlowDao.queryAllShareByOrderNo(mallOrderDTO.getOrderNo());
                    if (mallSocialShareFlowDTOS != null && mallSocialShareFlowDTOS.size() > 0) {
                        List<String> sellerUserList = new ArrayList<>();
                        BigDecimal shareAmount = new BigDecimal(0);
                        for (MallSocialShareFlowDTO dto : mallSocialShareFlowDTOS) {
                            if (dto.getShareType()==0) {
                                String sellerUser = dto.getUserName() + " " + dto.getPhoneNo();
                                sellerUserList.add(sellerUser); //分销合伙人
                            }
                            shareAmount = shareAmount.add(dto.getAmount());   //分销佣金
                        }
                        mallOrderDTO.setShareAmount(shareAmount);
                        mallOrderDTO.setSellerUserName(sellerUserList);
                    }
                }

                //拼接售后字段
                List<MallAfterSales> mallAfterSalesList = mallAfterSalesDao.selectList(new QueryWrapper<MallAfterSales>().eq("order_no", mallOrderDTO.getOrderNo()));
                if (mallAfterSalesList != null && mallAfterSalesList.size() > 0) {
                    List<Integer> subStatusList = mallAfterSalesList.stream().map(MallAfterSales::getSubStatus).collect(Collectors.toList());
                    if (subStatusList.contains(1010) || subStatusList.contains(1030) || subStatusList.contains(1050) || subStatusList.contains(1070) || subStatusList.contains(2010)) {
                        //退款进行中
                        mallOrderDTO.setAfterSalesStatus(1);
                    } else {
                        mallOrderDTO.setAfterSalesStatus(2);
                    }
                } else {
                    mallOrderDTO.setAfterSalesStatus(0);
                }
                if (mallOrderDTO.getAfterSalesStatus() != null) {
                    //修改售后返回值给前端
                    if (mallOrderDTO.getAfterSalesStatus() != 0) {
                        mallOrderDTO.setAsStatus(111);  //111: 是：申请了售后就是，跟有效期无关
                    } else {
                        if (mallOrderDTO.getAfterSalesEndAt() != null && mallOrderDTO.getAfterSalesEndAt() > new Date().getTime() / 1000) {
                            mallOrderDTO.setAsStatus(100);  //100: 暂无：还在有效期内，目前还没有申请售后
                        } else {
                            mallOrderDTO.setAsStatus(110);  //110: 否：超过有效期，但是没有申请售后
                        }
                    }
                }
            }
        }

        String status = null;
        if (mallOrderQuery != null && mallOrderQuery.getStatus() != null){
            status = EnumUtil.getByCode(StatusEnum.class,mallOrderQuery.getStatus()).getDesc();
        }else {
            status = EnumUtil.getByCode(StatusEnum.class,9).getDesc(); //全部包含已取消
        }
        try {
            Calendar now = Calendar.getInstance();
            String today = DateUtil.formatDateForYMD(now.getTime());
            now.add(Calendar.DATE,-1);
            String yestoday = DateUtil.formatDateForYMD(now.getTime());
            String randomnum = String.valueOf(IDGeneratorUtils.getLongId());
            String pathpPefix = System.getProperty("user.dir") + "/xls/";
            String fileName =today + status + "订单.xls";
            String suffix = today + CommonConstant.SLASH + randomnum + CommonConstant.SLASH + userVO.getOrganizeId() + CommonConstant.SLASH +fileName;
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
            List<MallOrderDTO> subList = list.subList(0, end);
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
                List<MallOrderDTO> subList2 = list.subList(beginIndex, endIndex);
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
        writer.addHeaderAlias("cartOrderSn", "母订单编号");
        writer.addHeaderAlias("orderNo", "子订单编号");
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
        writer.addHeaderAlias("deliverType", "发货方式");
        writer.addHeaderAlias("orderAtStr", "下单时间");
        writer.addHeaderAlias("payedAtStr", "支付时间");
        writer.addHeaderAlias("receiveAtStr", "收货时间");
        List<String> containsList = Arrays.asList("cartOrderSn","orderNo","itemName","goodsNo","allMoney",
                "payType","quantity","status","isEnableShare","sellerUserName","shareAmount","asStatus",
                "payuser","receiverName","receiverAddress","deliverType","orderAtStr","payedAtStr","receiveAtStr");
        return containsList;
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
        UserVO userVO = getUser();  //获取当前登陆用户
        MallOrderVO mallOrderVO = null;
        MallOrderDTO mallOrderDTO = mallOrderDao.queryOrderDetail(orderNo);
        if (mallOrderDTO != null){
            mallOrderVO = new MallOrderVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);

            //order_at;payed_at;receive_at时间转换为字符串
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
                mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //如果母订单号为空，则填写子订单号为母订单号
            }

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
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(mallOrderVO.getShopNo());
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
                        //更新createdTime时间展示
                        if (mallAfterSalesVO.getCreatedTime()!=null) {
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                            mallAfterSalesVO.setCreatedAtStr(orderAtStr);
                        }
                        voList.add(mallAfterSalesVO);
                        afterSalesNoList.add(dto.getAfterSalesNo());
                    });
                    mallOrderVO.setAfterSales(voList);

                    //查看售后日志
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
    public ReturnResponse<MallOrderVO> queryDetailByPickupNo(String pickupNo, String orderNo) {
        UserVO userVO = getUser();  //获取当前登陆用户
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

            //order_at;payed_at;receive_at时间转换为字符串
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
    public ReturnResponse<String> verPickupNo(String pickupNo, String orderNo) {
        UserVO userVO = getUser();  //获取当前登陆用户
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

    /**
     * 导出excel中的状态码转中文
     * @param list
     */
    private static void salesStateCodeToString(List<Map<String, Object>> list) {
        if (list != null && list.size() > 0) {
            list.forEach(map -> {
                //payType
                if (Objects.equals(map.get("payType"), 1)) {
                    map.put("payType", "支付宝");
                } else if (Objects.equals(map.get("payType"), 2)) {
                    map.put("payType", "微信");
                } else {
                    map.put("payType", "未知");
                }
                //status
                if (Objects.equals(map.get("status"), 10)) {
                    map.put("status", "待支付");
                } else if (Objects.equals(map.get("status"), 20)) {
                    map.put("status", "待发货");
                } else if (Objects.equals(map.get("status"), 30)) {
                    map.put("status", "待收货");
                } else if (Objects.equals(map.get("status"), 40)) {
                    map.put("status", "已收货");
                } else if (Objects.equals(map.get("status"), 50)) {
                    map.put("status", "已取消");
                } else if (Objects.equals(map.get("status"), 60)) {
                    map.put("status", "完成");
                }
                //isEnableShare
                if (Objects.equals(map.get("isEnableShare"), 0)) {
                    map.put("isEnableShare", "否");
                } else if (Objects.equals(map.get("isEnableShare"), 1)) {
                    map.put("isEnableShare", "是");
                }
                //asStatus
                if (Objects.equals(map.get("asStatus"), 100)) {
                    map.put("asStatus", "暂无");
                } else if (Objects.equals(map.get("asStatus"), 110)) {
                    map.put("asStatus", "否");
                } else if (Objects.equals(map.get("asStatus"), 111)) {
                    map.put("asStatus", "是");
                }
                //deliverType
                if (Objects.equals(map.get("deliverType"), 1)) {
                    map.put("deliverType", "配送");
                } else if (Objects.equals(map.get("deliverType"), 2)) {
                    map.put("deliverType", "自提");
                }
            });
        }
    }

    @Override
    public CommonResult<BasePageResult<MallOrderUAppVO>> queryMallOrderListByUApp(Long page, Long pageSize, String mallOrderQuery, Integer status, Integer deliverType, Long orderAtBegin, Long orderAtEnd) {
        UserVO userVO = getUser();  //获取当前登陆用户
        List<MallOrderUAppVO> list = new ArrayList<>();
        Integer count = null;   //总条数
        MallOrderUAppQuery mallOrderUAppQuery = new MallOrderUAppQuery();  //请求入参
        mallOrderUAppQuery.setStatus(status);
        mallOrderUAppQuery.setDeliverType(deliverType);
        mallOrderUAppQuery.setMallOrderQuery(mallOrderQuery);
        mallOrderUAppQuery.setShopNo(userVO.getShopNo());
        mallOrderUAppQuery.setOrderAtBegin(orderAtBegin);
        mallOrderUAppQuery.setOrderAtEnd(orderAtEnd);
        try {
            //获取总条数
            count = mallOrderDao.getTrendMallOrderByUAppPageSize(mallOrderUAppQuery);
            if (count!=null && count>0) {
                //分页
                List<MallOrderUAppDTO> mallDTOList = mallOrderDao.getTrendMallOrderByPageUApp((page - 1) * pageSize, pageSize, mallOrderUAppQuery);
                //page = mallOrderDao.getTrendMallOrderPage(pageQuery, mallOrderQuery);
                MallOrderUAppVO mallOrderVo = null;
                if (mallDTOList != null && mallDTOList.size() > 0) {

                    for (MallOrderUAppDTO mallOrderDTO : mallDTOList) {
                        if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                            mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //如果母订单号为空，则填写子订单号为母订单号
                        }
                        mallOrderVo = new MallOrderUAppVO();
                        BeanUtils.copyProperties(mallOrderDTO, mallOrderVo);

                        //order_at;payed_at;receive_at时间转换为字符串
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

                        //订单列表显示商品名称数组
                        List<String> itemNames = mallOrderDTO.getDetails().stream().map(MallOrderDetailDTO::getItemName).collect(Collectors.toList());

                        mallOrderVo.setItemName(itemNames);
                        //订单列表显示商品货号数组
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
        BasePageResult<MallOrderUAppVO> basePageResult = new BasePageResult<>();
        basePageResult.setPage(page);
        basePageResult.setPageSize(pageSize);
        basePageResult.setTotal(count);
        basePageResult.setResult(list);
        return CommonResult.success(basePageResult);
    }

    @Override
    public ReturnResponse<MallOrderUAppVO> queryOrderDetailByUApp(String orderNo) {
        UserVO userVO = getUser();  //获取当前登陆用户
        MallOrderUAppVO mallOrderVO = null;
        MallOrderUAppDTO mallOrderDTO = mallOrderDao.queryOrderDetailByUApp(orderNo);
        if (mallOrderDTO != null){
            mallOrderVO = new MallOrderUAppVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);

            //order_at;payed_at;receive_at时间转换为字符串
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
            if (mallOrderVO.getSendAt()!=null && mallOrderVO.getSendAt()>0) {
                long time = Long.valueOf(mallOrderVO.getSendAt())*1000;
                String sendAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setSendAtStr(sendAtStr);
            }
            if (mallOrderVO.getAfterSalesEndAt()!=null && mallOrderVO.getAfterSalesEndAt()>0) {
                long time = Long.valueOf(mallOrderVO.getAfterSalesEndAt())*1000;
                String sendAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                mallOrderVO.setAfterSalesEndAtStr(sendAtStr);   //售后截止时间,佣金预计到账时间
            }

            if (mallOrderDTO.getCartOrderSn() == null || "".equals(mallOrderDTO.getCartOrderSn())) {
                mallOrderDTO.setCartOrderSn(mallOrderDTO.getOrderNo());     //如果母订单号为空，则填写子订单号为母订单号
            }

            if (mallOrderDTO.getDetails()!=null && mallOrderDTO.getDetails().size()>0){
                List<MallOrderDetailUAppVO> detailVOList = new ArrayList<>();
                BigDecimal manageMoney = new BigDecimal(0);
                BigDecimal distributionMoney = new BigDecimal(0);
                for(MallOrderDetailDTO orderDetail : mallOrderDTO.getDetails()){
                    MallOrderDetailUAppVO mallOrderDetailVO = new MallOrderDetailUAppVO();
                    BeanUtils.copyProperties(orderDetail,mallOrderDetailVO);

                    if (mallOrderDTO.getOrderType()!=null) {
                        if (mallOrderDTO.getStatus() != 50 && mallOrderDTO.getStatus() != 10){  //已取消订单不展示分销人相关信息

                            //查询佣金流水表
                            List<MallSocialShareFlowDTO> mallSocialShareFlowDTOs = mallSocialShareFlowDao.queryAllShareByOrderNo(mallOrderDTO.getOrderNo());
                            if (mallSocialShareFlowDTOs != null && mallSocialShareFlowDTOs.size() > 0) {
                                List<MallSocialShareFlowUAppVO> flowList = new ArrayList<>();
                                for(MallSocialShareFlowDTO dto : mallSocialShareFlowDTOs) {
                                    MallSocialShareFlowUAppVO mallSocialShareFlowVO = new MallSocialShareFlowUAppVO();
                                    BeanUtils.copyProperties(dto, mallSocialShareFlowVO);
                                    flowList.add(mallSocialShareFlowVO);
                                    if (dto.getShareType()==0){ //分销佣金
                                        distributionMoney = distributionMoney.add(dto.getAmount());
                                    }else if (dto.getShareType()==1){   //管理佣金
                                        manageMoney = manageMoney.add(dto.getAmount());
                                    }
                                }
                                mallOrderDetailVO.setShareFlowInfo(flowList);
                            }
                        }
                        detailVOList.add(mallOrderDetailVO);
                    }
                }
                mallOrderVO.setDetails(detailVOList);
                mallOrderVO.setShareManageMoney(manageMoney);
                mallOrderVO.setShareDistributionMoney(distributionMoney);
            }

            //查看商店
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(mallOrderVO.getShopNo());
            if (mallShopDTO==null){
                return ReturnResponse.failed("店铺信息为空");
            }
            MallShopUAppVO mallShopVO = new MallShopUAppVO();
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
                    List<MallAfterSalesUAppVO> voList = new ArrayList<>();
                    mallAfterSalesDTOs.forEach(dto -> {
                        MallAfterSalesUAppVO mallAfterSalesVO = new MallAfterSalesUAppVO();
                        BeanUtils.copyProperties(dto, mallAfterSalesVO);
                        //更新createdTime时间展示
                        if (mallAfterSalesVO.getCreatedTime()!=null) {
                            String orderAtStr = cn.hutool.core.date.DateUtil.format(mallAfterSalesVO.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
                            mallAfterSalesVO.setCreatedAtStr(orderAtStr);
                        }
                        voList.add(mallAfterSalesVO);
                        afterSalesNoList.add(dto.getAfterSalesNo());
                    });
                    mallOrderVO.setAfterSales(voList);

                    //查看售后日志
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

                    //查看买家寄出的快递表
                    List<MallBuyerShippingDTO> mallBuyerShippingDTOs = mallBuyerShippingDao.queryByAfterSalesNoList(afterSalesNoList);
                    if (mallBuyerShippingDTOs != null && mallBuyerShippingDTOs.size() > 0) {
                        //获取售后编号列表
                        List<String> buyerShippingNoList = new ArrayList<>();
                        List<MallBuyerShippingUAppVO> buyerShippingVOList = new ArrayList<>();
                        mallBuyerShippingDTOs.forEach(dto -> {
                            MallBuyerShippingUAppVO mallBuyerShippingVO = new MallBuyerShippingUAppVO();
                            BeanUtils.copyProperties(dto, mallBuyerShippingVO);
                            buyerShippingVOList.add(mallBuyerShippingVO);
                            buyerShippingNoList.add(dto.getBuyerShippingNo());
                        });
                        mallOrderVO.setBuyerShipping(buyerShippingVOList);

                        //查看买家寄出的快递物流日志表
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
            //商家寄出的快递表
            MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(orderNo);
            if (mallShopShippingDTO!=null){
                MallShopShippingUAppVO mallShopShippingVO = new MallShopShippingUAppVO();
                BeanUtils.copyProperties(mallShopShippingDTO,mallShopShippingVO);
                mallOrderVO.setShopShipping(mallShopShippingVO);

                //商家寄出的快递物流日志表
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

            //查询订单详情规格值表
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

            //订单头部信息拼接


        }
        return ReturnResponse.success(mallOrderVO);
    }

    @Override
    public ReturnResponse<MallOrderUAppVO> queryDetailByPickupNoUApp(String pickupNo, String orderNo) {
        UserVO userVO = getUser();  //获取当前登陆用户
        MallOrderUAppDTO mallOrderDTO = mallOrderDao.queryDetailByPickupNoUApp(pickupNo, userVO.getShopNo());
        if (mallOrderDTO!=null){
            //判断pickNo查询到订单是否是orderNo的订单
            if (StringUtils.isNotEmpty(orderNo)){
                //orderNo不为空，说明是订单详情中进行的订单校验
                if (!orderNo.equals(mallOrderDTO.getOrderNo())){
                    return ReturnResponse.failed("当前自提码与当前订单不一致，校验失败");
                }
            }
            MallOrderUAppVO mallOrderVO = new MallOrderUAppVO();
            BeanUtils.copyProperties(mallOrderDTO,mallOrderVO);

            //order_at;payed_at;receive_at时间转换为字符串
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
                List<MallOrderDetailSpecDTO> specList = mallOrderDetailSpecDao.queryListByOrderDetailNoList(orderDetailNoList);     //查询订单详情规格值表
                Map<String, List<MallOrderDetailSpecUAppVO>> map = null;
                if (specList!=null){
                    List<MallOrderDetailSpecUAppVO> specVoList = new ArrayList<>();
                    specList.forEach(spec->{
                        MallOrderDetailSpecUAppVO mallOrderDetailSpecVO = new MallOrderDetailSpecUAppVO();
                        BeanUtils.copyProperties(spec,mallOrderDetailSpecVO);
                        specVoList.add(mallOrderDetailSpecVO);
                    });
                    map = specVoList.stream().collect(Collectors.groupingBy(input -> input.getOrderDetailNo()));        //吧specVO集合转换为map类型，方便下面做判断和取值
                }else {
                    map = new HashMap<>();
                }
                List<MallOrderDetailUAppVO> list = new ArrayList<>();
                for(MallOrderDetailDTO dto : mallOrderDTO.getDetails()){
                    MallOrderDetailUAppVO mallOrderDetailVO = new MallOrderDetailUAppVO();
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
    public ReturnResponse<MallOrderUAppVO> verPickupNoByUApp(MallPickupUAppQuery mallPickupUAppQuery) {
        UserVO userVO = getUser();
        MallOrderDTO mallOrderDTO = mallOrderDao.queryDetailByPickupNo(mallPickupUAppQuery.getPickupNo(), userVO.getShopNo());
        if (mallOrderDTO!=null){
            //判断pickNo查询到订单是否是orderNo的订单
            if (StringUtils.isNotEmpty(mallPickupUAppQuery.getOrderNo())){
                //orderNo不为空，说明是订单详情中进行的订单校验
                if (!mallPickupUAppQuery.getOrderNo().equals(mallOrderDTO.getOrderNo())){
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
            return queryOrderDetailByUApp(mallPickupUAppQuery.getOrderNo()); //更新成功，返回订单详情
        }
        return ReturnResponse.failed("未查询到待自提订单");
    }

    @Override
    public CommonResult<BasePageResult<MallOrderDetailUAppVO>> queryOrderDetailShareFlowListByNo(Long page, Long pageSize, String orderNo) {
        UserVO userVO = getUser();  //获取当前登陆用户
        BasePageResult<MallOrderDetailUAppVO> basePageResult = new BasePageResult();
        Page pageQuery = new Page(page, pageSize);
        List<MallOrderDetailUAppVO> list = new ArrayList<>();
        try {
            List<String> orderNoList = new ArrayList<>();
            MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no",orderNo));
            if (mallOrder!=null){
                //传入的为订单编号
                if (!mallOrder.getShopNo().equals(userVO.getShopNo())){
                    return CommonResult.failed("当前用户无法查看其他企业的订单");
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

            Page<MallOrderDetailDTO> mallOrderDetailDTOPage = mallOrderDetailDao.queryDetailListByOrderNos(pageQuery,orderNoList);
            if (mallOrderDetailDTOPage==null){
                mallOrderDetailDTOPage = new Page<>();
            }
            if (mallOrderDetailDTOPage.getRecords() != null && mallOrderDetailDTOPage.getRecords().size() > 0) {
                MallOrderDetailUAppVO mallOrderDetailUAppVO = null;
                for (MallOrderDetailDTO mallOrderDetailDTO : mallOrderDetailDTOPage.getRecords()){
                    mallOrderDetailUAppVO = new MallOrderDetailUAppVO();
                    BeanUtils.copyProperties(mallOrderDetailDTO,mallOrderDetailUAppVO);
                    //分销佣金保存
                    if (mallOrderDetailDTO.getShareFlowInfo() != null && mallOrderDetailDTO.getShareFlowInfo().size() > 0) {
                        List<MallSocialShareFlowUAppVO> flowList = new ArrayList<>();
                        mallOrderDetailDTO.getShareFlowInfo().forEach(dto -> {
                            MallSocialShareFlowUAppVO mallSocialShareFlowVO = new MallSocialShareFlowUAppVO();
                            BeanUtils.copyProperties(dto, mallSocialShareFlowVO);
                            flowList.add(mallSocialShareFlowVO);
                        });
                        mallOrderDetailUAppVO.setShareFlowInfo(flowList);
                    }
                    //产品规格保存
                    if (mallOrderDetailDTO.getSpecs()!=null && mallOrderDetailDTO.getSpecs().size()>0){
                        List<MallOrderDetailSpecUAppVO> specList = new ArrayList<>();
                        mallOrderDetailDTO.getSpecs().forEach(spec->{
                            MallOrderDetailSpecUAppVO mallOrderDetailSpecUAppVO = new MallOrderDetailSpecUAppVO();
                            BeanUtils.copyProperties(spec, mallOrderDetailSpecUAppVO);
                            specList.add(mallOrderDetailSpecUAppVO);
                        });
                        mallOrderDetailUAppVO.setSpecs(specList);
                    }
                    if (mallOrderDetailUAppVO.getMoAfterSalesEndAt()!=null && mallOrderDetailUAppVO.getMoAfterSalesEndAt()>0){
                        long time = mallOrderDetailUAppVO.getMoAfterSalesEndAt()*1000;
                        String orderAtStr = cn.hutool.core.date.DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
                        mallOrderDetailUAppVO.setMoAfterSalesEndAtStr(orderAtStr);
                    }
                }
                list.add(mallOrderDetailUAppVO);
                basePageResult.setPage(mallOrderDetailDTOPage.getPages());
                basePageResult.setPageSize(mallOrderDetailDTOPage.getSize());
                basePageResult.setTotal(mallOrderDetailDTOPage.getTotal());
                basePageResult.setResult(list);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return CommonResult.success(basePageResult);
    }
}
