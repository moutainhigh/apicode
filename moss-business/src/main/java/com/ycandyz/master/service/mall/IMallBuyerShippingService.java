package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.shipment.query.ShipmentParamQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.entities.mall.MallBuyerShipping;

/**
 * <p>
 * @Description 买家寄出的快递表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
public interface IMallBuyerShippingService extends IService<MallBuyerShipping>{
    ShipmentResponseDataVO shipmentCallBack(ShipmentParamQuery shipmentParamQuery);
    ReturnResponse verShipmentNo(String shipNumber);
}
