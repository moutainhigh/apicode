package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.mall.MallShopShippingQuery;
import com.ycandyz.master.domain.shipment.query.ShipmentParamQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.entities.mall.MallShopShipping;
import com.ycandyz.master.model.mall.MallOrderUAppVO;
import com.ycandyz.master.model.mall.MallShopShippingVO;

import java.util.List;

public interface MallShopShippingService extends IService<MallShopShipping> {

    ReturnResponse<MallShopShippingVO> verShipmentNo(String shipNumber);

    ReturnResponse<String> enterShipping(MallShopShippingQuery mallShopShippingQuery);

    ShipmentResponseDataVO shipmentCallBack(String param);

    /**
     * 发货按钮，UApp
     * @param mallShopShippingQuery
     * @return
     */
    ReturnResponse<MallOrderUAppVO> enterShippingUApp(MallShopShippingQuery mallShopShippingQuery, UserVO userVO);
}
