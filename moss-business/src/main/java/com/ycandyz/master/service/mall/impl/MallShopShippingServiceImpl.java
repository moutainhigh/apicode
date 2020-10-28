package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.MallOrderDao;
import com.ycandyz.master.dao.mall.MallShopShippingDao;
import com.ycandyz.master.dao.mall.MallShopShippingLogDao;
import com.ycandyz.master.dao.mall.MallShopShippingPollLogDao;
import com.ycandyz.master.domain.query.mall.MallShopShippingQuery;
import com.ycandyz.master.domain.shipment.query.*;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.dto.mall.MallShopShippingDTO;
import com.ycandyz.master.dto.mall.MallShopShippingLogDTO;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.entities.mall.MallShopShipping;
import com.ycandyz.master.entities.mall.MallShopShippingLog;
import com.ycandyz.master.entities.mall.MallShopShippingPollLog;
import com.ycandyz.master.enums.ExpressEnum;
import com.ycandyz.master.model.mall.MallShopShippingVO;
import com.ycandyz.master.service.mall.MallShopShippingService;
import com.ycandyz.master.utils.QueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        MallOrder mallOrder = mallOrderDao.selectOne(new QueryWrapper<MallOrder>().eq("order_no",mallShopShippingQuery.getOrderNo()));
        if (mallOrder!=null){
            mallOrder.setStatus(30);
            mallOrder.setSubStatus(3010);
            Long timeAt = new Date().getTime()/1000;
            mallOrder.setSendAt(timeAt.intValue()); //更新商家发货时间
            mallOrderDao.updateById(mallOrder);
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
            MallShopShippingLogDTO mallShopShippingLogDTO = mallShopShippingLogDao.selectByShipNumberLast(shipmentParamLastResultQuery.getNu());
            if (mallShopShippingLogDTO!=null){
                if (mallShopShippingLogDTO.getStatus()==3) {
                    return ShipmentResponseDataVO.success("当前状态已经签收，无需重复签收");
                }
                MallShopShippingLog mallShopShippingLog = new MallShopShippingLog();
                mallShopShippingLog.setShopShippingNo(mallShopShippingLogDTO.getShopShippingNo());
                mallShopShippingLog.setCompanyCode(shipmentParamLastResultQuery.getCom());
                mallShopShippingLog.setCompany(ExpressEnum.getValue(shipmentParamLastResultQuery.getCom()));
                mallShopShippingLog.setNumber(shipmentParamLastResultQuery.getNu());
                mallShopShippingLog.setContext(shipmentParamLastResultQuery.getData().get(0).getContext());
                mallShopShippingLog.setShippingMoney(mallShopShippingLogDTO.getShippingMoney());
                mallShopShippingLog.setStatus(Integer.parseInt(shipmentParamLastResultQuery.getState()));
                if (shipmentParamLastResultQuery.getState().equals("3")){
                    //已经签收，需要修改is_check字段状态
                    mallShopShippingLog.setIsCheck(1);
                }else {
                    mallShopShippingLog.setIsCheck(0);
                }
                mallShopShippingLog.setLogAt(shipmentParamLastResultQuery.getData().get(0).getFtime());
                mallShopShippingLogDao.insert(mallShopShippingLog);     //更新卖家物流表
            }
        }
        return ShipmentResponseDataVO.success("更新成功");
    }

}
