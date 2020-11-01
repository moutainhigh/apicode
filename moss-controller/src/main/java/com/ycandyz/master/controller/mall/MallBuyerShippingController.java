package com.ycandyz.master.controller.mall;

import com.ycandyz.master.domain.shipment.query.ShipmentParamQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.model.mall.MallBuyerShippingVO;
import com.ycandyz.master.service.mall.IMallBuyerShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import com.ycandyz.master.api.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ycandyz.master.entities.mall.MallBuyerShipping;
import com.ycandyz.master.domain.query.mall.MallBuyerShippingQuery;
import com.ycandyz.master.service.mall.impl.MallBuyerShippingServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 买家寄出的快递表 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mall-buyer-shipping")
@Api(tags="mall-买家寄出的快递表")
public class MallBuyerShippingController extends BaseController<MallBuyerShippingServiceImpl,MallBuyerShipping,MallBuyerShippingQuery> {

    @Autowired
    private MallBuyerShippingServiceImpl mallBuyerShippingService;

    /**
     * 订单发货校验物流单号，调用三方接口，查看物流单号所属物流公司，返回快递公司名和编号
     * @param shipNumber
     * @return
     */
    @ApiOperation(value = "订单发货校验物流单号",notes = "订单发货校验物流单号",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shipNumber",value="物流单号",required=true,dataType="String")
    })
    @GetMapping("/mallBuyer/shipment/verification")
    public ReturnResponse<MallBuyerShippingVO> verShipmentNo(@RequestParam("shipNumber") String shipNumber){
        return mallBuyerShippingService.verShipmentNo(shipNumber);
    }

    /**
     * 快递订阅回调接口
     * @param param
     * @return
     */
    @PostMapping("/mallBuyer/shipment/callback")
    @ResponseBody
    public ShipmentResponseDataVO shipmentCallBack(@RequestParam(value = "param",required = false) String param){
        return mallBuyerShippingService.shipmentCallBack(param);
    }
}
