package com.ycandyz.master.controller.mall;

import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseController;
import com.ycandyz.master.domain.query.mall.MallShopShippingQuery;
import com.ycandyz.master.domain.query.mall.uApp.MallShopShippingUAppQuery;
import com.ycandyz.master.domain.shipment.vo.ShipmentResponseDataVO;
import com.ycandyz.master.entities.mall.MallShopShipping;
import com.ycandyz.master.model.mall.MallOrderUAppVO;
import com.ycandyz.master.model.mall.MallShopShippingVO;
import com.ycandyz.master.model.mall.uApp.MallShopShippingLogUAppVO;
import com.ycandyz.master.model.mall.uApp.MallShopShippingUAppVO;
import com.ycandyz.master.model.mall.uApp.MallShopShippingUVO;
import com.ycandyz.master.service.mall.MallShopShippingService;
import com.ycandyz.master.service.mall.impl.MallShopShippingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    @ApiImplicitParams({
            @ApiImplicitParam(name="shipNumber",value="物流单号",required=true,dataType="String")
    })
    @GetMapping("/order/shipment/verification")
    public ReturnResponse<MallShopShippingVO> verShipmentNo(@RequestParam("shipNumber") String shipNumber){
        return mallShopShippingService.verShipmentNo(shipNumber);
    }

    /**
     * 确认发货
     * @param mallShopShippingQuery
     * @return
     */
    @ApiOperation(value = "确认发货",notes = "确认发货",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNo",value="订单编号",required=true,dataType="String"),
            @ApiImplicitParam(name="company",value="快递公司名",required=true,dataType="String"),
            @ApiImplicitParam(name="companyCode",value="快递公司编码",required=true,dataType="String"),
            @ApiImplicitParam(name="number",value="快递单号",required=true,dataType="String")
    })
    @PostMapping("/order/shipment/enterShipping")
    @ResponseBody
    public ReturnResponse<String> enterShipping(@RequestBody MallShopShippingQuery mallShopShippingQuery){
        return mallShopShippingService.enterShipping(mallShopShippingQuery);
    }

    /**
     * 快递订阅回调接口
     * @param sign
     * @param param
     * @return
     */
    @PostMapping("/order/shipment/callback")
    @ResponseBody
    public ShipmentResponseDataVO shipmentCallBack(@RequestParam(value = "sign",required = false) String sign, @RequestParam(value = "param",required = false) String param){
        log.info("sign="+sign+",param="+param);
        ShipmentResponseDataVO shipmentResponseDataVO = mallShopShippingService.shipmentCallBack(param);
        return shipmentResponseDataVO;
    }

    /**
     * 确认发货,UApp
     * @param mallShopShippingUAppQuery
     * @return
     */
    @ApiOperation(value = "确认发货",notes = "确认发货",httpMethod = "POST")
    @PostMapping("/u-app/order/shipment")
    @ResponseBody
    public ReturnResponse<MallOrderUAppVO> enterShippingUApp(@RequestBody MallShopShippingUAppQuery mallShopShippingUAppQuery){
        return mallShopShippingService.enterShippingUApp(mallShopShippingUAppQuery);
    }

    /**
     * 通过物流编号获取物流信息列表，UApp
     * @param companyCode
     * @param number
     * @return
     *//*
    @ApiOperation(value = "物流信息",notes = "通过订单编号或购物车编号获取分佣列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="company_code",value="快递公司编码",required=true,dataType="String"),
            @ApiImplicitParam(name="number",value="快递单号",required=true,dataType="String")
    })
    @GetMapping("/u-app/order/shipment")
    public ReturnResponse<List<MallShopShippingUAppVO>> queryShippingLogListByNo(@RequestParam("company_code") String companyCode, @RequestParam("number") String number){
        if (number==null || !"".equals(number) || companyCode==null || !"".equals(companyCode)){
            return ReturnResponse.failed("传入参数为空");
        }
        number = number.trim();
        companyCode = companyCode.trim();
        return mallShopShippingService.queryShippingLogListByNo(companyCode,number);
    }*/

    /**
     * 通过快递编号，获取物流公司列表,UApp
     * @param shipNumber
     * @return
     */
    @ApiOperation(value = "通过快递编号，获取物流公司列表,uapp",notes = "通过快递编号，获取物流公司列表,uapp",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="ship_number",value="物流单号",required=true,dataType="String")
    })
    @GetMapping("/u-app/order/shipment")
    public ReturnResponse<BaseResult<List<MallShopShippingUVO>>> verShipmentNoByUApp(@RequestParam("ship_number") String shipNumber){
        return mallShopShippingService.verShipmentNoByUApp(shipNumber);
    }

    /**
     * 通过快递编号和快递公司编码，获取快递日志列表,UApp
     * @param shipNumber
     * @return
     */
    @ApiOperation(value = "通过快递编号和快递公司编码，获取快递日志列表,uapp",notes = "通过快递编号，获取物流公司列表,uapp",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="company_code",value="快递公司编码",required=true,dataType="String"),
            @ApiImplicitParam(name="number",value="快递单号",required=true,dataType="String")
    })
    @GetMapping("/u-app/order/shipment/log")
    public CommonResult<List<MallShopShippingLogUAppVO>> shippingLogList(@RequestParam("company_code") String companyCode, @RequestParam("ship_number") String shipNumber){
        return mallShopShippingService.shippingLogList(companyCode,shipNumber);
    }
}
