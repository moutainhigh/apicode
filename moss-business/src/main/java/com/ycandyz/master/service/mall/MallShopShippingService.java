package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.domain.query.mall.MallShopShippingQuery;
import com.ycandyz.master.domain.shipment.query.ShipmentParamQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.entities.mall.MallShopShipping;
import com.ycandyz.master.model.mall.MallShopShippingVO;

import java.util.List;

public interface MallShopShippingService extends IService<MallShopShipping> {

    CommonResult<MallShopShippingVO> verShipmentNo(String shipNumber);

    CommonResult<String> enterShipping(MallShopShippingQuery mallShopShippingQuery);

    ShipmentResponseDataVO shipmentCallBack(ShipmentParamQuery shipmentParamQuery);
}
