package com.ycandyz.master.service.mall.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.mall.MallShopShippingQuery;
import com.ycandyz.master.domain.query.mall.uApp.MallShopShippingUAppQuery;
import com.ycandyz.master.domain.shipment.query.*;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.dto.mall.MallShopShippingDTO;
import com.ycandyz.master.dto.mall.MallShopShippingLogDTO;
import com.ycandyz.master.entities.mall.*;
import com.ycandyz.master.enums.ExpressEnum;
import com.ycandyz.master.model.mall.MallOrderUAppVO;
import com.ycandyz.master.model.mall.MallShopShippingVO;
import com.ycandyz.master.model.mall.uApp.MallShopShippingLogUAppVO;
import com.ycandyz.master.model.mall.uApp.MallShopShippingUAppVO;
import com.ycandyz.master.service.mall.MallOrderService;
import com.ycandyz.master.service.mall.MallShopShippingService;
import com.ycandyz.master.utils.IDGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty;

@Slf4j
@Service
public class MallShopShippingServiceImpl extends BaseService<MallShopShippingDao, MallShopShipping, MallShopShippingQuery> implements MallShopShippingService {

    @Autowired
    private MallShopShippingDao mallShopShippingDao;

    @Autowired
    private MallShopShippingLogDao mallShopShippingLogDao;

    @Autowired
    private MallShopShippingPollLogDao mallShopShippingPollLogDao;

    @Autowired
    private MallOrderDao mallOrderDao;

    @Autowired
    private MallTempOrderWaitReceiveDao mallTempOrderWaitReceiveDao;

    @Autowired
    private MallTempShippingDao mallTempShippingDao;

    @Autowired
    private MallOrderService mallOrderService;

    @Value("${kuaidi.autonumber.url}")
    private String autonumberUrl;

    @Value("${kuaidi.poll.url}")
    private String kuaidiPollUrl;

    @Value("${kuaidi.key}")
    private String kuaidiKey;

    @Value("${kuaidi.customer}")
    private String kuaidiCustomer;

    @Value("${kuaidi.poll.callback}")
    private String kuaidiCallbackUrl;

    @Override
    public ReturnResponse<MallShopShippingVO> verShipmentNo(String shipNumber) {
        String result = HttpUtil.get(autonumberUrl.replace("NUM",shipNumber).replace("KEY",kuaidiKey));
        JSONObject jsonObject = JSONUtil.parseArray(result).getJSONObject(0);
        if (jsonObject!=null) {
            if (jsonObject.getStr("comCode") != null && !"".equals(jsonObject.getStr("comCode"))) {
                String value = ExpressEnum.getValue(jsonObject.getStr("comCode"));
                MallShopShippingVO mallShopShippingVO = new MallShopShippingVO();
                mallShopShippingVO.setCompany(value);
                mallShopShippingVO.setCompanyCode(jsonObject.getStr("comCode"));
                mallShopShippingVO.setNumber(shipNumber);
                return ReturnResponse.success(mallShopShippingVO);
            }
        }
        return ReturnResponse.failed("为查询到快递记录。");
    }

    @Transactional
    @Override
    public ReturnResponse<String> enterShipping(MallShopShippingQuery mallShopShippingQuery) {
        MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(mallShopShippingQuery.getOrderNo());
        if (mallShopShippingDTO==null){
            return ReturnResponse.failed("未查询到当前记录");
        }
        MallShopShipping mallShopShipping = new MallShopShipping();
        mallShopShipping.setId(mallShopShippingDTO.getId());
        mallShopShipping.setCompany(mallShopShippingQuery.getCompany());
        mallShopShipping.setCompanyCode(mallShopShippingQuery.getCompanyCode());
        mallShopShipping.setNumber(mallShopShippingQuery.getNumber());
        mallShopShipping.setPollState(mallShopShippingDTO.getPollState());
        mallShopShipping.setShopShippingNo(mallShopShippingDTO.getShopShippingNo());
        mallShopShipping.setOrderNo(mallShopShippingDTO.getOrderNo());
        mallShopShipping.setReceiver(mallShopShippingDTO.getReceiver());
        mallShopShipping.setReceiverAddress(mallShopShippingDTO.getReceiverAddress());
        mallShopShipping.setReceiverHeadimg(mallShopShippingDTO.getReceiverHeadimg());
        mallShopShipping.setReceiverPhone(mallShopShippingDTO.getReceiverPhone());
        mallShopShipping.setShippingMoney(mallShopShippingDTO.getShippingMoney());
        mallShopShipping.setType(mallShopShippingDTO.getType());

        if (mallShopShippingDTO.getType()==10) {        //快递
            //调用快递订阅功能
            PollShipmentParamQuery pollShipmentParamQuery = new PollShipmentParamQuery();
            pollShipmentParamQuery.setCompany(mallShopShippingQuery.getCompanyCode());
            pollShipmentParamQuery.setNumber(mallShopShippingQuery.getNumber());
            pollShipmentParamQuery.setKey(kuaidiKey);
            PollShipmentParametersQuery pollShipmentParametersQuery = new PollShipmentParametersQuery();
            pollShipmentParametersQuery.setCallbackurl(kuaidiCallbackUrl);
            pollShipmentParametersQuery.setAutoCom(0);
            pollShipmentParametersQuery.setInterCom(0);
            pollShipmentParametersQuery.setResultv2(1);
            pollShipmentParamQuery.setParameters(pollShipmentParametersQuery);
            Map<String, Object> params = new HashMap<>();
            params.put("schema", "json");
            params.put("param", JSONUtil.parseObj(pollShipmentParamQuery).toString());
            PollShipmentRequest pollShipmentRequest = new PollShipmentRequest();
            pollShipmentRequest.setSchema("json");
            pollShipmentRequest.setParam(pollShipmentParamQuery);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            String str = com.alibaba.fastjson.JSONObject.toJSONString(pollShipmentParamQuery, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue);
            String result = HttpUtil.createPost(kuaidiPollUrl).addHeaders(headers).form(params).execute().body();
            log.info("快递订阅接口请求response返回" + result);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject resultObject = JSONUtil.parseObj(result);
                if (!resultObject.getBool("result")) {
                    if (resultObject.getStr("returnCode").equals("501")){
                        //重复订阅，需要额外处理
                        List<MallShopShippingLogDTO> mallShopShippingLogs = mallShopShippingLogDao.selectListByShopShippingNo(mallShopShipping.getShopShippingNo());
                        if (mallShopShippingLogs!=null && mallShopShippingLogs.size()>0){
                            //如果存在物流信息，无法重复订阅，需要认为的主动把该物流订单同步到新的订单物流中
                            mallShopShippingLogs.forEach(shippinglog->{
                                shippinglog.setId(null);
                                shippinglog.setShippingMoney(mallShopShipping.getShippingMoney());
                                shippinglog.setShopShippingNo(mallShopShipping.getShopShippingNo());
                            });
                            mallShopShippingLogDao.insertBatch(mallShopShippingLogs);
                        }
                    }

                    //订阅失败
                    //更新快递推送表poll_state字段
                    mallShopShipping.setPollState(2);
                    //记录日志表,后续做kafka队列任务处理相关信息
                    MallShopShippingPollLog mallShopShippingPollLog = new MallShopShippingPollLog();
                    mallShopShippingPollLog.setOrderNo(mallShopShippingDTO.getOrderNo());
                    mallShopShippingPollLog.setNumber(mallShopShippingQuery.getNumber());
                    mallShopShippingPollLog.setCode(0); //神州通项目code码传入0
                    mallShopShippingPollLog.setShopShippingNo(mallShopShippingDTO.getShopShippingNo());
                    mallShopShippingPollLog.setLog(resultObject.getStr("message"));
                    mallShopShippingPollLog.setCreatedTime(new Date());
                    mallShopShippingPollLogDao.insert(mallShopShippingPollLog);
                    mallShopShipping.setPollState(2);
                } else {
                    //订阅成功，更新poll_state字段
                    mallShopShipping.setPollState(1);
                }
            }
            mallShopShippingDao.updateById(mallShopShipping);

            //更新待收货临时表
            MallTempOrderWaitReceive mallTempOrderWaitReceive = new MallTempOrderWaitReceive();
            Long time = new Date().getTime() / 1000;
            mallTempOrderWaitReceive.setCloseAt(time + 7 * 24 * 60 * 60);
            mallTempOrderWaitReceive.setOrderNo(mallShopShippingQuery.getOrderNo());
            mallTempOrderWaitReceive.setShopNo(mallShopShippingDTO.getShopShippingNo());
            mallTempOrderWaitReceive.setTempOrderNo(String.valueOf(IDGeneratorUtils.getLongId()));
            mallTempOrderWaitReceiveDao.insert(mallTempOrderWaitReceive);

            //未签收的临时物流表
            MallTempShipping mallTempShipping = mallTempShippingDao.selectOne(new QueryWrapper<MallTempShipping>().eq("com_code", mallShopShippingQuery.getCompanyCode()).eq("number", mallShopShippingQuery.getNumber()));
            if (mallTempShipping == null) {
                mallTempShipping = new MallTempShipping();
                mallTempShipping.setComCode(mallShopShippingQuery.getCompanyCode());
                mallTempShipping.setCompany(mallShopShippingQuery.getCompany());
                mallTempShipping.setCreatedAt(time);
                mallTempShipping.setNumber(mallShopShippingQuery.getNumber());
                mallTempShippingDao.insert(mallTempShipping);
            }
        }

        //更新订单表
        MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no", mallShopShippingQuery.getOrderNo()));
        if (mallOrder != null) {
            mallOrder.setStatus(30);
            mallOrder.setSubStatus(3010);
            Long timeAt = new Date().getTime() / 1000;
            mallOrder.setSendAt(timeAt.intValue()); //更新商家发货时间
            mallOrderDao.updateById(mallOrder);
        }

        return ReturnResponse.success("发货成功");
    }

    @Transactional
    @Override
    public ShipmentResponseDataVO shipmentCallBack(String param) {
        ShipmentParamQuery shipmentParamQuery = JSONUtil.toBean(param,ShipmentParamQuery.class);
        if (shipmentParamQuery.getStatus().equals("abort")){
            //当message为“3天查询无记录”或“60天无变化时”status= abort
            log.info("-----当前快递存在异常，请业务人员进行处理关注。");
            return ShipmentResponseDataVO.success("成功");
        }else {
            ShipmentParamLastResultQuery shipmentParamLastResultQuery = shipmentParamQuery.getLastResult();
            List<MallShopShippingDTO> mallShopShippingList = mallShopShippingDao.queryByCodeAndNum(shipmentParamLastResultQuery.getCom(),shipmentParamLastResultQuery.getNu());
            if (mallShopShippingList!=null && mallShopShippingList.size()>0){
                List<ShipmentParamLastResultDataQuery> list = shipmentParamLastResultQuery.getData();
                Collections.reverse(list);
                for (MallShopShippingDTO mallShopShippingDTO : mallShopShippingList){
                    List<MallShopShippingLogDTO> mallShopShippingLogDTOS = mallShopShippingLogDao.selectListByShopShippingNo(mallShopShippingDTO.getShopShippingNo());
                    if (mallShopShippingLogDTOS!=null){
                        List<Integer> statusList = mallShopShippingLogDTOS.stream().map(MallShopShippingLogDTO::getStatus).collect(Collectors.toList());
                        if (statusList.contains(3)) {
                            return ShipmentResponseDataVO.success("当前状态已经签收，无需重复签收");
                        }else {
                            //先删除
                            Map<String, Object> map = new HashMap<>();
                            map.put("company_code", shipmentParamLastResultQuery.getCom());
                            map.put("number", shipmentParamLastResultQuery.getNu());
                            map.put("shop_shipping_no",mallShopShippingDTO.getShopShippingNo());
                            mallShopShippingLogDao.deleteByMap(map);
                        }

                        //后新增
                        //更新数据到MallShopShippingLog表
                        MallShopShippingLog mallShopShippingLog = null;
                        int i = 0;
                        for(ShipmentParamLastResultDataQuery data : list) {
                            mallShopShippingLog = new MallShopShippingLog();
                            mallShopShippingLog.setShopShippingNo(mallShopShippingDTO.getShopShippingNo());
                            mallShopShippingLog.setCompanyCode(shipmentParamLastResultQuery.getCom());
                            mallShopShippingLog.setCompany(ExpressEnum.getValue(shipmentParamLastResultQuery.getCom()));
                            mallShopShippingLog.setNumber(shipmentParamLastResultQuery.getNu());
                            mallShopShippingLog.setContext(data.getContext());
                            mallShopShippingLog.setShippingMoney(mallShopShippingDTO.getShippingMoney());
                            if (i == list.size()-1) {
                                mallShopShippingLog.setStatus(Integer.parseInt(shipmentParamLastResultQuery.getState()));
                            }else {
                                mallShopShippingLog.setStatus(0);
                            }
                            mallShopShippingLog.setLogAt(data.getFtime());
                            if (shipmentParamLastResultQuery.getState().equals("3")){
                                //已经签收，需要修改is_check字段状态
                                mallShopShippingLog.setIsCheck(1);
                            }else {
                                mallShopShippingLog.setIsCheck(0);
                            }
//                            dataList.add(mallShopShippingLog);
                            mallShopShippingLogDao.insert(mallShopShippingLog);
                            i++;
                        }
                    }
                }
//                mallShopShippingLogDao.insertBatch(dataList);   //更新卖家物流表
            }
        }
        return ShipmentResponseDataVO.success("更新成功");
    }

    @Transactional
    @Override
    public ReturnResponse<MallOrderUAppVO> enterShippingUApp(MallShopShippingUAppQuery mallShopShippingUAppQuery) {

        MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no",mallShopShippingUAppQuery.getOrderNo()));
        if (mallOrder != null) {
            if (mallOrder.getDeliverMethod()==10) {     //快递发货
                UserVO userVO = getUser();  //获取当前登陆用户
                MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(mallShopShippingUAppQuery.getOrderNo());
                if (mallShopShippingDTO == null) {
                    return ReturnResponse.failed("未查询到当前记录");
                }
                MallShopShipping mallShopShipping = new MallShopShipping();
                mallShopShipping.setId(mallShopShippingDTO.getId());
                mallShopShipping.setCompany(mallShopShippingUAppQuery.getCompany());
                mallShopShipping.setCompanyCode(mallShopShippingUAppQuery.getCompanyCode());
                mallShopShipping.setNumber(mallShopShippingUAppQuery.getNumber());
                mallShopShipping.setPollState(mallShopShippingDTO.getPollState());
                mallShopShipping.setShopShippingNo(mallShopShippingDTO.getShopShippingNo());
                mallShopShipping.setOrderNo(mallShopShippingDTO.getOrderNo());
                mallShopShipping.setReceiver(mallShopShippingDTO.getReceiver());
                mallShopShipping.setReceiverAddress(mallShopShippingDTO.getReceiverAddress());
                mallShopShipping.setReceiverHeadimg(mallShopShippingDTO.getReceiverHeadimg());
                mallShopShipping.setReceiverPhone(mallShopShippingDTO.getReceiverPhone());
                mallShopShipping.setShippingMoney(mallShopShippingDTO.getShippingMoney());
                mallShopShipping.setType(mallShopShippingDTO.getType());

                if (mallShopShippingDTO.getType() == 10) {        //快递
                    //调用快递订阅功能
                    PollShipmentParamQuery pollShipmentParamQuery = new PollShipmentParamQuery();
                    pollShipmentParamQuery.setCompany(mallShopShippingUAppQuery.getCompanyCode());
                    pollShipmentParamQuery.setNumber(mallShopShippingUAppQuery.getNumber());
                    pollShipmentParamQuery.setKey(kuaidiKey);
                    PollShipmentParametersQuery pollShipmentParametersQuery = new PollShipmentParametersQuery();
                    pollShipmentParametersQuery.setCallbackurl(kuaidiCallbackUrl);
                    pollShipmentParametersQuery.setAutoCom(0);
                    pollShipmentParametersQuery.setInterCom(0);
                    pollShipmentParametersQuery.setResultv2(1);
                    pollShipmentParamQuery.setParameters(pollShipmentParametersQuery);
                    Map<String, Object> params = new HashMap<>();
                    params.put("schema", "json");
                    params.put("param", JSONUtil.parseObj(pollShipmentParamQuery).toString());
                    PollShipmentRequest pollShipmentRequest = new PollShipmentRequest();
                    pollShipmentRequest.setSchema("json");
                    pollShipmentRequest.setParam(pollShipmentParamQuery);
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    String str = com.alibaba.fastjson.JSONObject.toJSONString(pollShipmentParamQuery, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue);
                    String result = HttpUtil.createPost(kuaidiPollUrl).addHeaders(headers).form(params).execute().body();
                    log.info("快递订阅接口请求response返回" + result);
                    if (StringUtils.isNotEmpty(result)) {
                        JSONObject resultObject = JSONUtil.parseObj(result);
                        if (!resultObject.getBool("result")) {
                            if (resultObject.getStr("returnCode").equals("501")) {
                                //重复订阅，需要额外处理
                                List<MallShopShippingLogDTO> mallShopShippingLogs = mallShopShippingLogDao.selectListByShopShippingNo(mallShopShipping.getShopShippingNo());
                                if (mallShopShippingLogs != null && mallShopShippingLogs.size() > 0) {
                                    //如果存在物流信息，无法重复订阅，需要认为的主动把该物流订单同步到新的订单物流中
                                    mallShopShippingLogs.forEach(shippinglog -> {
                                        shippinglog.setId(null);
                                        shippinglog.setShippingMoney(mallShopShipping.getShippingMoney());
                                        shippinglog.setShopShippingNo(mallShopShipping.getShopShippingNo());
                                    });
                                    mallShopShippingLogDao.insertBatch(mallShopShippingLogs);
                                }
                            }

                            //订阅失败
                            //更新快递推送表poll_state字段
                            mallShopShipping.setPollState(2);
                            //记录日志表,后续做kafka队列任务处理相关信息
                            MallShopShippingPollLog mallShopShippingPollLog = new MallShopShippingPollLog();
                            mallShopShippingPollLog.setOrderNo(mallShopShippingDTO.getOrderNo());
                            mallShopShippingPollLog.setNumber(mallShopShippingUAppQuery.getNumber());
                            mallShopShippingPollLog.setCode(0); //神州通项目code码传入0
                            mallShopShippingPollLog.setShopShippingNo(mallShopShippingDTO.getShopShippingNo());
                            mallShopShippingPollLog.setLog(resultObject.getStr("message"));
                            mallShopShippingPollLog.setCreatedTime(new Date());
                            mallShopShippingPollLogDao.insert(mallShopShippingPollLog);
                            mallShopShipping.setPollState(2);
                        } else {
                            //订阅成功，更新poll_state字段
                            mallShopShipping.setPollState(1);
                        }
                    }
                    mallShopShippingDao.updateById(mallShopShipping);

                    //更新待收货临时表
                    MallTempOrderWaitReceive mallTempOrderWaitReceive = new MallTempOrderWaitReceive();
                    Long time = new Date().getTime() / 1000;
                    mallTempOrderWaitReceive.setCloseAt(time + 7 * 24 * 60 * 60);
                    mallTempOrderWaitReceive.setOrderNo(mallShopShippingUAppQuery.getOrderNo());
                    mallTempOrderWaitReceive.setShopNo(mallShopShippingDTO.getShopShippingNo());
                    mallTempOrderWaitReceive.setTempOrderNo(String.valueOf(IDGeneratorUtils.getLongId()));
                    mallTempOrderWaitReceiveDao.insert(mallTempOrderWaitReceive);

                    //未签收的临时物流表
                    MallTempShipping mallTempShipping = mallTempShippingDao.selectOne(new QueryWrapper<MallTempShipping>().eq("com_code", mallShopShippingUAppQuery.getCompanyCode()).eq("number", mallShopShippingUAppQuery.getNumber()));
                    if (mallTempShipping == null) {
                        mallTempShipping = new MallTempShipping();
                        mallTempShipping.setComCode(mallShopShippingUAppQuery.getCompanyCode());
                        mallTempShipping.setCompany(mallShopShippingUAppQuery.getCompany());
                        mallTempShipping.setCreatedAt(time);
                        mallTempShipping.setNumber(mallShopShippingUAppQuery.getNumber());
                        mallTempShippingDao.insert(mallTempShipping);
                    }
                }
            }
        //更新订单表
//        MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no", mallShopShippingUAppQuery.getOrderNo()));

            mallOrder.setStatus(30);
            mallOrder.setSubStatus(3010);
            Long timeAt = new Date().getTime() / 1000;
            mallOrder.setSendAt(timeAt.intValue()); //更新商家发货时间
            mallOrderDao.updateById(mallOrder);
        }

        //查询订单详情，返回前端
        return mallOrderService.queryOrderDetailByUApp(mallShopShippingUAppQuery.getOrderNo());
    }

    @Override
    public ReturnResponse<List<MallShopShippingUAppVO>> queryShippingLogListByNo(String companyCode, String number) {
        List<MallShopShippingDTO> mallShopShippingDTOS = mallShopShippingDao.queryByCodeAndNum(companyCode,number);
        List<MallShopShippingUAppVO> list = new ArrayList<>();
        if (mallShopShippingDTOS!=null && mallShopShippingDTOS.size()>0){
            mallShopShippingDTOS.forEach(dto->{
                MallShopShippingUAppVO mallShopShippingUAppVO = new MallShopShippingUAppVO();
                BeanUtils.copyProperties(dto,mallShopShippingUAppVO);
                list.add(mallShopShippingUAppVO);
            });
        }
        return ReturnResponse.success(list);
    }

    @Override
    public ReturnResponse<BaseResult<List<MallShopShippingUAppVO>>> verShipmentNoByUApp(String shipNumber) {
        String result = HttpUtil.get(autonumberUrl.replace("NUM",shipNumber).replace("KEY",kuaidiKey));
        List<MallShopShippingUAppVO> list = new ArrayList<>();
        if (result!=null && !"".equals(result)){
            JSONObject jsonObject = JSONUtil.parseArray(result).getJSONObject(0);
            if (jsonObject!=null) {
                if (jsonObject.getStr("comCode") != null && !"".equals(jsonObject.getStr("comCode"))) {
                    String value = ExpressEnum.getValue(jsonObject.getStr("comCode"));
                    MallShopShippingUAppVO mallShopShippingVO = new MallShopShippingUAppVO();
                    mallShopShippingVO.setCompany(value);
                    mallShopShippingVO.setCompanyCode(jsonObject.getStr("comCode"));
                    mallShopShippingVO.setNumber(shipNumber);
                    list.add(mallShopShippingVO);
                }
            }
        }
        return ReturnResponse.success(new BaseResult(list));
    }

    @Override
    public CommonResult<List<MallShopShippingLogUAppVO>> shippingLogList(String companyCode, String shipNumber) {
        List<MallShopShippingLog> mallShopShippingLogs = mallShopShippingLogDao.selectList(new QueryWrapper<MallShopShippingLog>().eq("company_code",companyCode).eq("number",shipNumber).orderByDesc("id"));
        List<MallShopShippingLogUAppVO> result = new ArrayList<>();
        if (mallShopShippingLogs!=null && mallShopShippingLogs.size()>0){
            for (MallShopShippingLog mallShopShippingLog : mallShopShippingLogs){
                MallShopShippingLogUAppVO mallShopShippingLogUAppVO = new MallShopShippingLogUAppVO();
                mallShopShippingLogUAppVO.setAreaCode(mallShopShippingLog.getAreaCode());
                mallShopShippingLogUAppVO.setAreaName(mallShopShippingLog.getAreaName());
                mallShopShippingLogUAppVO.setCompany(mallShopShippingLog.getCompany());
                mallShopShippingLogUAppVO.setCompanyCode(mallShopShippingLog.getCompanyCode());
                mallShopShippingLogUAppVO.setContext(mallShopShippingLog.getContext());
                mallShopShippingLogUAppVO.setCreatedTime(mallShopShippingLog.getCreatedTime());
                mallShopShippingLogUAppVO.setId(mallShopShippingLog.getId());
                mallShopShippingLogUAppVO.setIsCheck(mallShopShippingLog.getIsCheck());
                mallShopShippingLogUAppVO.setLogAt(mallShopShippingLog.getLogAt());
                mallShopShippingLogUAppVO.setNumber(mallShopShippingLog.getNumber());
                mallShopShippingLogUAppVO.setShippingMoney(mallShopShippingLog.getShippingMoney());
                mallShopShippingLogUAppVO.setShopShippingNo(mallShopShippingLog.getShopShippingNo());
                mallShopShippingLogUAppVO.setStatus(mallShopShippingLog.getStatus());
                mallShopShippingLogUAppVO.setUpdatedTime(mallShopShippingLog.getUpdatedTime());
                result.add(mallShopShippingLogUAppVO);
            }
        }
        return CommonResult.success(result);
    }

}
