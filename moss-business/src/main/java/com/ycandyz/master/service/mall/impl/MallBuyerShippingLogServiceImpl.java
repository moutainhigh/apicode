package com.ycandyz.master.service.mall.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.shipment.query.ShipmentParamLastResultQuery;
import com.ycandyz.master.domain.shipment.query.ShipmentParamQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.dto.mall.MallBuyerShippingLogDTO;
import com.ycandyz.master.dto.mall.MallShopShippingLogDTO;
import com.ycandyz.master.entities.mall.MallBuyerShippingLog;
import com.ycandyz.master.domain.query.mall.MallBuyerShippingLogQuery;
import com.ycandyz.master.dao.mall.MallBuyerShippingLogDao;
import com.ycandyz.master.entities.mall.MallShopShippingLog;
import com.ycandyz.master.enums.ExpressEnum;
import com.ycandyz.master.model.mall.MallBuyerShippingVO;
import com.ycandyz.master.model.mall.MallShopShippingVO;
import com.ycandyz.master.service.mall.IMallBuyerShippingLogService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 买家寄出的快递物流日志表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Slf4j
@Service
public class MallBuyerShippingLogServiceImpl extends BaseService<MallBuyerShippingLogDao,MallBuyerShippingLog,MallBuyerShippingLogQuery> implements IMallBuyerShippingLogService {



}
