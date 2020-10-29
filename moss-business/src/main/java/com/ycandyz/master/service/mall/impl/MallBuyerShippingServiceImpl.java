package com.ycandyz.master.service.mall.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.mall.MallBuyerShippingLogDao;
import com.ycandyz.master.domain.shipment.query.ShipmentParamLastResultDataQuery;
import com.ycandyz.master.domain.shipment.query.ShipmentParamLastResultQuery;
import com.ycandyz.master.domain.shipment.query.ShipmentParamQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.dto.mall.MallBuyerShippingLogDTO;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.entities.mall.MallBuyerShipping;
import com.ycandyz.master.domain.query.mall.MallBuyerShippingQuery;
import com.ycandyz.master.dao.mall.MallBuyerShippingDao;
import com.ycandyz.master.entities.mall.MallBuyerShippingLog;
import com.ycandyz.master.enums.ExpressEnum;
import com.ycandyz.master.model.mall.MallBuyerShippingVO;
import com.ycandyz.master.service.mall.IMallBuyerShippingService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * @Description 买家寄出的快递表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Slf4j
@Service
public class MallBuyerShippingServiceImpl extends BaseService<MallBuyerShippingDao,MallBuyerShipping,MallBuyerShippingQuery> implements IMallBuyerShippingService {
    @Value("${kuaidi.autonumber.url}")
    private String autonumberUrl;

    @Value("${kuaidi.key}")
    private String kuaidiKey;

    @Autowired
    private MallBuyerShippingLogDao mallBuyerShippingLogDao;

    @Override
    public ReturnResponse<MallBuyerShippingVO> verShipmentNo(String shipNumber) {
        String result = HttpUtil.get(autonumberUrl.replace("NUM",shipNumber).replace("KEY",kuaidiKey));
        JSONObject jsonObject = JSONUtil.parseArray(result).getJSONObject(0);
        if (jsonObject!=null) {
            if (jsonObject.getStr("comCode") != null && !"".equals(jsonObject.getStr("comCode"))) {
                String value = ExpressEnum.getValue(jsonObject.getStr("comCode"));
                MallBuyerShippingVO mallBuyerShippingVO = new MallBuyerShippingVO();
                mallBuyerShippingVO.setCompany(value);
                mallBuyerShippingVO.setCompanyCode(jsonObject.getStr("comCode"));
                mallBuyerShippingVO.setNumber(shipNumber);
                return ReturnResponse.success(mallBuyerShippingVO);
            }
        }
        log.info("shipNumber:{},快递100未查询到快递记录",shipNumber);
        return ReturnResponse.failed("快递100未查询到快递记录");
    }

    @Override
    public ShipmentResponseDataVO shipmentCallBack(String param) {
        ShipmentParamQuery shipmentParamQuery = JSONUtil.toBean(param,ShipmentParamQuery.class);
        log.debug("ShipmentParam: {}",shipmentParamQuery);
        if (shipmentParamQuery.getStatus().equals("abort")){
            //需要通知业务人员进行处理。该订单可能存在问题
            log.info("-----当前快递存在异常，请业务人员进行处理关注。");
            return ShipmentResponseDataVO.failed("快递订单存在异常");
        }else {
            ShipmentParamLastResultQuery shipmentParamLastResultQuery = shipmentParamQuery.getLastResult();
            MallBuyerShippingLogDTO mallBuyerShippingLogDTO = mallBuyerShippingLogDao.selectByShipNumberLast(shipmentParamLastResultQuery);
            if (null != mallBuyerShippingLogDTO){
                if (mallBuyerShippingLogDTO.getStatus()==3) {
                    return ShipmentResponseDataVO.success("当前状态已经签收，无需重复签收");
                }
                MallBuyerShippingLog deleteWrapper = new MallBuyerShippingLog();
                deleteWrapper.setNumber(shipmentParamLastResultQuery.getNu());
                deleteWrapper.setId(mallBuyerShippingLogDTO.getId());
                log.info("Number:{},id:{}",shipmentParamLastResultQuery.getNu(),mallBuyerShippingLogDTO.getId());
                mallBuyerShippingLogDao.deleteByNumber(deleteWrapper);
                List<ShipmentParamLastResultDataQuery> list = shipmentParamLastResultQuery.getData();
                list.forEach(f -> {
                    MallBuyerShippingLog mallBuyerShippingLog = new MallBuyerShippingLog();
                    mallBuyerShippingLog.setBuyerShippingNo(mallBuyerShippingLogDTO.getBuyerShippingNo());
                    mallBuyerShippingLog.setCompanyCode(shipmentParamLastResultQuery.getCom());
                    mallBuyerShippingLog.setCompany(ExpressEnum.getValue(shipmentParamLastResultQuery.getCom()));
                    mallBuyerShippingLog.setNumber(shipmentParamLastResultQuery.getNu());
                    mallBuyerShippingLog.setContext(f.getContext());
                    mallBuyerShippingLog.setStatus(Integer.parseInt(shipmentParamLastResultQuery.getState()));
                    if (shipmentParamLastResultQuery.getState().equals("3")){
                        //已经签收，需要修改is_check字段状态
                        mallBuyerShippingLog.setIsCheck(1);
                    }else {
                        mallBuyerShippingLog.setIsCheck(0);
                    }
                    mallBuyerShippingLog.setLogAt(f.getFtime());
                    log.debug("MallBuyerShippingLog==> {}",mallBuyerShippingLog);
                    mallBuyerShippingLogDao.insert(mallBuyerShippingLog);     //更新卖家物流表
                });

            }
        }
        return ShipmentResponseDataVO.success("更新成功");
    }
}
