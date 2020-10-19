package com.ycandyz.master.controller.mall;

import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.mall.MallShopShippingQuery;
import com.ycandyz.master.domain.shipment.query.ShipmentParamQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.entities.mall.MallShopShipping;
import com.ycandyz.master.model.mall.MallShopShippingVO;
import com.ycandyz.master.service.mall.MallShopShippingService;
import com.ycandyz.master.service.mall.impl.MallShopShippingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mall")
@Api(tags="mall-订单商家快递信息表")
public class MallShopShippingController extends BaseController<MallShopShippingServiceImpl, MallShopShipping, MallShopShippingQuery> {

    @Autowired
    private MallShopShippingService mallShopShippingService;

    /**
     * 订单发货校验物流单号，调用三方接口，查看物流单号所属物流公司，返回快递公司名和编号
     * @param shipNumber
     * @return
     */
    @ApiOperation(value = "订单发货校验物流单号",notes = "订单发货校验物流单号",httpMethod = "GET")
    @GetMapping("/order/shipment/verification")
    public CommonResult<MallShopShippingVO> verShipmentNo(@RequestParam("shipNumber") String shipNumber){
        return mallShopShippingService.verShipmentNo(shipNumber);
    }

    /**
     * 确认发货
     * @param mallShopShippingQuery
     * @return
     */
    @ApiOperation(value = "确认发货",notes = "确认发货",httpMethod = "POST")
    @PostMapping("/order/shipment/enterShipping")
    @ResponseBody
    public CommonResult<String> enterShipping(@RequestBody MallShopShippingQuery mallShopShippingQuery){
        return mallShopShippingService.enterShipping(mallShopShippingQuery);
    }

    /**
     * 快递订阅回调接口
     * @param shipmentParamQuery
     * @return
     */
    @PostMapping("/order/shipment/callback")
    @ResponseBody
    public ShipmentResponseDataVO shipmentCallBack(@RequestBody ShipmentParamQuery shipmentParamQuery){
        return mallShopShippingService.shipmentCallBack(shipmentParamQuery);
    }
}
