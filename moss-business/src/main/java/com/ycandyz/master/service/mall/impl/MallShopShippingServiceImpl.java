package com.ycandyz.master.service.mall.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.MallShopShippingDao;
import com.ycandyz.master.dao.mall.MallShopShippingLogDao;
import com.ycandyz.master.dao.mall.MallShopShippingPollLogDao;
import com.ycandyz.master.domain.query.mall.MallShopShippingQuery;
import com.ycandyz.master.domain.shipment.query.PollShipmentParamQuery;
import com.ycandyz.master.domain.shipment.query.PollShipmentParametersQuery;
import com.ycandyz.master.domain.shipment.query.ShipmentParamLastResultQuery;
import com.ycandyz.master.domain.shipment.query.ShipmentParamQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.dto.mall.MallShopShippingDTO;
import com.ycandyz.master.dto.mall.MallShopShippingLogDTO;
import com.ycandyz.master.entities.mall.MallShopShipping;
import com.ycandyz.master.entities.mall.MallShopShippingLog;
import com.ycandyz.master.entities.mall.MallShopShippingPollLog;
import com.ycandyz.master.enums.ExpressEnum;
import com.ycandyz.master.model.mall.MallShopShippingVO;
import com.ycandyz.master.service.mall.MallShopShippingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MallShopShippingServiceImpl extends BaseService<MallShopShippingDao, MallShopShipping, MallShopShippingQuery> implements MallShopShippingService {

    @Autowired
    private MallShopShippingDao mallShopShippingDao;

    @Autowired
    private MallShopShippingLogDao mallShopShippingLogDao;

    @Autowired
    private MallShopShippingPollLogDao mallShopShippingPollLogDao;

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
        pollShipmentParamQuery.setCompany(mallShopShippingQuery.getCompany());
        pollShipmentParamQuery.setNumber(mallShopShippingQuery.getNumber());
        pollShipmentParamQuery.setKey(kuaidiKey);
        PollShipmentParametersQuery pollShipmentParametersQuery = new PollShipmentParametersQuery();
        pollShipmentParametersQuery.setCallbackurl(kuaidiCallbackUrl);
        pollShipmentParamQuery.setParameters(pollShipmentParametersQuery);
        JSONObject params = new JSONObject();
        params.put("schema","json");
        params.put("param",JSONUtil.parseObj(pollShipmentParamQuery));
        String result = HttpUtil.post(kuaidiPollUrl,params);
        if (StringUtils.isNotEmpty(result)){
            JSONObject resultObject = JSONUtil.parseObj(result);
            if (!resultObject.getBool("result")){
                //订阅失败
                //更新快递推送表poll_state字段
                mallShopShipping.setPollState(2);
                //记录日志表,后续做kafka队列任务处理相关信息
                MallShopShippingPollLog mallShopShippingPollLog = new MallShopShippingPollLog();
                mallShopShippingPollLog.setOrderNo(mallShopShippingDTO.getOrderNo());
                mallShopShippingPollLog.setShopShippingNo(mallShopShippingDTO.getShopShippingNo());
                mallShopShippingPollLog.setLog(resultObject.getStr("message"));
                mallShopShippingPollLog.setCreatedTime(new Date());
                mallShopShippingPollLogDao.insert(mallShopShippingPollLog);
            }else {
                //订阅成功，更新poll_state字段
                mallShopShipping.setPollState(1);
            }
        }
        mallShopShippingDao.updateById(mallShopShipping);
        return ReturnResponse.success("发货成功");
    }

    @Override
    public ShipmentResponseDataVO shipmentCallBack(ShipmentParamQuery shipmentParamQuery) {
        if (shipmentParamQuery.getStatus().equals("abort")){
            //需要通知业务人员进行处理。该订单可能存在问题
            log.info("-----当前快递存在异常，请业务人员进行处理关注。");
            return ShipmentResponseDataVO.failed("快递订单存在异常");
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
