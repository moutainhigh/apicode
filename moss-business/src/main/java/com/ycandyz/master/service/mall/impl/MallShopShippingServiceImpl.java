package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.domain.query.mall.MallShopShippingQuery;
import com.ycandyz.master.domain.shipment.query.*;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.dto.mall.MallShopShippingDTO;
import com.ycandyz.master.dto.mall.MallShopShippingLogDTO;
import com.ycandyz.master.entities.mall.*;
import com.ycandyz.master.enums.ExpressEnum;
import com.ycandyz.master.model.mall.MallShopShippingVO;
import com.ycandyz.master.service.mall.MallShopShippingService;
import com.ycandyz.master.utils.IDGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        params.put("schema","json");
        params.put("param",JSONUtil.parseObj(pollShipmentParamQuery).toString());
        PollShipmentRequest pollShipmentRequest = new PollShipmentRequest();
        pollShipmentRequest.setSchema("json");
        pollShipmentRequest.setParam(pollShipmentParamQuery);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
        String str = com.alibaba.fastjson.JSONObject.toJSONString(pollShipmentParamQuery, SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue);
        String result= HttpUtil.createPost(kuaidiPollUrl).addHeaders(headers).form(params).execute().body();
        log.info("快递订阅接口请求response返回"+result);
        if (StringUtils.isNotEmpty(result)){
            JSONObject resultObject = JSONUtil.parseObj(result);
            if (!resultObject.getBool("result")){
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
            }else {
                //订阅成功，更新poll_state字段
                mallShopShipping.setPollState(1);
            }
        }
        mallShopShippingDao.updateById(mallShopShipping);

        //更新订单表
        MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no",mallShopShippingQuery.getOrderNo()));
        if (mallOrder!=null){
            mallOrder.setStatus(30);
            mallOrder.setSubStatus(3010);
            Long timeAt = new Date().getTime()/1000;
            mallOrder.setSendAt(timeAt.intValue()); //更新商家发货时间
            mallOrderDao.updateById(mallOrder);

            //更新卖家发货物流日志表
//            MallShopShippingLog mallShopShippingLog = new MallShopShippingLog();
//            mallShopShippingLog.setLogAt(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
//            mallShopShippingLog.setIsCheck(0);
//            mallShopShippingLog.setShippingMoney(mallOrder.getAllMoney().subtract(mallOrder.getAllMoney().subtract(mallOrder.getRealMoney())));
//            mallShopShippingLog.setContext("已发货");
//            mallShopShippingLog.setNumber(mallShopShippingQuery.getNumber());
//            mallShopShippingLog.setCompany(mallShopShippingQuery.getCompany());
//            mallShopShippingLog.setCompanyCode(mallShopShippingQuery.getCompanyCode());
//            mallShopShippingLog.setStatus(0);
//            mallShopShippingLog.setShopShippingNo(mallShopShipping.getShopShippingNo());
//            mallShopShippingLogDao.insert(mallShopShippingLog);

            //更新待收货临时表
            MallTempOrderWaitReceive mallTempOrderWaitReceive = new MallTempOrderWaitReceive();
            Long time = new Date().getTime()/1000;
            mallTempOrderWaitReceive.setCloseAt(time+7*24*60*60);
            mallTempOrderWaitReceive.setOrderNo(mallOrder.getOrderNo());
            mallTempOrderWaitReceive.setShopNo(mallShopShippingDTO.getShopShippingNo());
            mallTempOrderWaitReceive.setTempOrderNo(String.valueOf(IDGeneratorUtils.getLongId()));
            mallTempOrderWaitReceiveDao.insert(mallTempOrderWaitReceive);

            //未签收的临时物流表
            MallTempShipping mallTempShipping = new MallTempShipping();
            mallTempShipping.setComCode(mallShopShippingQuery.getCompanyCode());
            mallTempShipping.setCompany(mallShopShippingQuery.getCompany());
            mallTempShipping.setCreatedAt(time);
            mallTempShipping.setNumber(mallShopShippingQuery.getNumber());
            mallTempShippingDao.insert(mallTempShipping);
        }

        return ReturnResponse.success("发货成功");
    }

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
                            mallShopShippingLogDao.deleteByMap(map);
                        }

                        //后新增
                        List<MallShopShippingLogDTO> dataList = new ArrayList();
                        List<ShipmentParamLastResultDataQuery> list = shipmentParamLastResultQuery.getData();
                        Collections.reverse(list);
                        //更新数据到MallShopShippingLog表
                        MallShopShippingLogDTO mallShopShippingLogdto = null;
                        int i = 0;
                        for(ShipmentParamLastResultDataQuery data : list) {
                            mallShopShippingLogdto = new MallShopShippingLogDTO();
                            mallShopShippingLogdto.setShopShippingNo(mallShopShippingDTO.getShopShippingNo());
                            mallShopShippingLogdto.setCompanyCode(shipmentParamLastResultQuery.getCom());
                            mallShopShippingLogdto.setCompany(ExpressEnum.getValue(shipmentParamLastResultQuery.getCom()));
                            mallShopShippingLogdto.setNumber(shipmentParamLastResultQuery.getNu());
                            mallShopShippingLogdto.setContext(data.getContext());
                            mallShopShippingLogdto.setShippingMoney(mallShopShippingDTO.getShippingMoney());
                            if (i == list.size()-1) {
                                mallShopShippingLogdto.setStatus(Integer.parseInt(shipmentParamLastResultQuery.getState()));
                            }else {
                                mallShopShippingLogdto.setStatus(0);
                            }
                            mallShopShippingLogdto.setLogAt(data.getFtime());
                            if (shipmentParamLastResultQuery.getState().equals("3")){
                                //已经签收，需要修改is_check字段状态
                                mallShopShippingLogdto.setIsCheck(1);
                            }else {
                                mallShopShippingLogdto.setIsCheck(0);
                            }
                            dataList.add(mallShopShippingLogdto);
                            i++;
                        }
                        mallShopShippingLogDao.insertBatch(dataList);   //更新卖家物流表
                    }
                }

            }
        }
        return ShipmentResponseDataVO.success("更新成功");
    }

}
