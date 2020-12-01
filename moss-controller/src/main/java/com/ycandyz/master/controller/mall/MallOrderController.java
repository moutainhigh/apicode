package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.*;
import com.ycandyz.master.controller.base.BaseController;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.domain.query.mall.uApp.MallPickupUAppQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.enums.ExpressEnum;
import com.ycandyz.master.model.mall.MallOrderUAppVO;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.model.mall.uApp.MallOrderDetailUAppVO;
import com.ycandyz.master.service.mall.MallOrderService;
import com.ycandyz.master.service.mall.impl.MaillOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mall")
@Api(tags="mall-订单订单信息表")
public class MallOrderController extends BaseController<MaillOrderServiceImpl, MallOrder, MallOrderQuery> {

    @Autowired
    private MallOrderService mallOrderService;

    /**
     * 获取订单表集合
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "订单列表",notes = "订单列表",httpMethod = "POST")
    @PostMapping("/order/list")
    public ReturnResponse<Page<MallOrderVO>> queryMallOrderList(@RequestBody RequestParams<MallOrderQuery> requestParams){
        if (requestParams.getT()==null){
            return ReturnResponse.failed("请求入参为空");
        }
        return mallOrderService.queryOrderList(requestParams);
    }

    /**
     * 订单列表导出
     * @param mallOrder
     */
    @ApiOperation(value = "订单列表导出",notes = "订单列表导出",httpMethod = "POST")
    @PostMapping("/order/export")
    public ReturnResponse<MallOrderExportResp> exportEXT(@RequestBody MallOrderQuery mallOrder){
        MallOrderExportResp s = mallOrderService.exportEXT(mallOrder);
        return ReturnResponse.success(s);
    }

    /**
     * 查看订单详情
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "查看订单详情",notes = "查看订单详情",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNo",value="订单编号",required=true,dataType="String")
    })
    @GetMapping("/order/detail")
    public ReturnResponse<MallOrderVO> queryOrderDetail(@RequestParam("orderNo") String orderNo){
        return mallOrderService.queryOrderDetail(orderNo);
    }

    /**
     * 通过提货码查询订单
     * @param pickupNo
     * @return
     */
    @ApiOperation(value = "通过提货码查询订单",notes = "通过提货码查询订单",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pickupNo",value="提货码",required=true,dataType="String")
    })
    @GetMapping("/order/pickup/query")
    public ReturnResponse<MallOrderVO> queryDetailByPickupNo(@RequestParam("pickupNo") String pickupNo, @RequestParam(value = "orderNo", required = false) String orderNo){
        if (pickupNo!=null){
            pickupNo = pickupNo.trim();
        }
        return mallOrderService.queryDetailByPickupNo(pickupNo, orderNo);
    }

    /**
     * 验证提货码，出货
     * @param pickupNo
     * @return
     */
    @ApiOperation(value = "验证提货码,出货",notes = "验证提货码,出货",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pickupNo",value="提货码",required=true,dataType="String"),
            @ApiImplicitParam(name="orderNo",value="订单号",dataType="String")
    })
    @GetMapping("/order/pickup/verification")
    public ReturnResponse<String> verPickupNo(@RequestParam("pickupNo") String pickupNo, @RequestParam(value = "orderNo", required = false) String orderNo){
        if (pickupNo!=null){
            pickupNo = pickupNo.trim();
        }
        return mallOrderService.verPickupNo(pickupNo,orderNo);
    }

    /**
     * 获取快递公司列表,UApp
     * @return
     */
    @ApiOperation(value = "获取快递公司列表",notes = "获取快递公司列表",httpMethod = "GET")
    @GetMapping("/u-app/delivery/company")
    public CommonResult<BaseResult<List<Map<String, String>>>> getDeliveryCompanyList(){
        List<Map<String, String>> list = ExpressEnum.getMap();
        return CommonResult.success(new BaseResult<List<Map<String, String>>>(list));
    }

    /**
     * UApp项目订单列表接口,UApp
     * @param page
     * @param pageSize
     * @param mallOrderQuery
     * @param status
     * @return
     */
    @ApiOperation(value = "UApp项目订单列表接口",notes = "UApp项目订单列表接口",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页",required=true,dataType="Long"),
            @ApiImplicitParam(name="page_size",value="条数",required=true,dataType="Long"),
            @ApiImplicitParam(name="mall_order_query",value="查询条件",dataType="String"),
            @ApiImplicitParam(name="status",value="状态：全部传空，10-待支付  20-待发货 30-待收货 40-已收货  50-已取消",dataType="Integer"),
            @ApiImplicitParam(name="deliver_type",value="发货方式 全部传空:1-配送 2-自提",dataType="Integer"),
            @ApiImplicitParam(name="order_at_begin",value="下单开始时间",dataType="Long"),
            @ApiImplicitParam(name="order_at_end",value="下单结束时间",dataType="Long")
    })
    @GetMapping("/u-app/order")
    public CommonResult<BasePageResult<MallOrderUAppVO>> queryMallOrderListByUApp(@RequestParam("page") Long page, @RequestParam("page_size") Long pageSize, @RequestParam(value = "mall_order_query", required = false) String mallOrderQuery, @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "deliver_type", required = false) Integer deliverType, @RequestParam(value = "order_at_begin", required = false) Long orderAtBegin, @RequestParam(value = "order_at_end", required = false) Long orderAtEnd){
        if (page==null || pageSize==null){
            return CommonResult.failed("请求入参为空");
        }
        if (mallOrderQuery!=null && !"".equals(mallOrderQuery)){
            mallOrderQuery = mallOrderQuery.trim();
        }
        return mallOrderService.queryMallOrderListByUApp(page,pageSize,mallOrderQuery,status,deliverType,orderAtBegin,orderAtEnd);
    }

    /**
     * 查看订单详情,UApp
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "查看订单详情",notes = "查看订单详情",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNo",value="订单编号",required=true,dataType="String")
    })
    @GetMapping("/u-app/order/{order_no}")
    public ReturnResponse<MallOrderUAppVO> queryOrderDetailByUApp(@PathVariable("order_no") String orderNo){
        return mallOrderService.queryOrderDetailByUApp(orderNo);
    }

    /**
     * 通过提货码查询订单,UApp
     * type=query
     * @param pickupNo
     * @return
     */
    @ApiOperation(value = "通过提货码查询订单,UApp",notes = "通过提货码查询订单",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="类型，写死query",required=true,dataType="String"),
            @ApiImplicitParam(name="pickup_no",value="提货码",required=true,dataType="String"),
            @ApiImplicitParam(name="order_no",value="订单编号",dataType="String")
    })
    @GetMapping("/u-app/order/pickup")
    public ReturnResponse<MallOrderUAppVO> queryDetailByPickupNoUApp(@RequestParam("type") String type, @RequestParam("pickup_no") String pickupNo, @RequestParam(value = "order_no", required = false) String orderNo){
        if (type==null || "".equals(type)){
            return ReturnResponse.failed("type类型不能为空值");
        }
        if (pickupNo!=null){
            pickupNo = pickupNo.trim();
        }
        return mallOrderService.queryDetailByPickupNoUApp(pickupNo, orderNo);
    }

    /**
     * 验证提货码，出货，UApp
     * @param mallPickupUAppQuery
     * @return
     */
    @ApiOperation(value = "验证提货码,出货",notes = "验证提货码,出货",httpMethod = "PUT")
    @PutMapping("/u-app/order/pickup")
    public ReturnResponse<MallOrderUAppVO> verPickupNoByUApp(@RequestBody MallPickupUAppQuery mallPickupUAppQuery){
        if (mallPickupUAppQuery == null || mallPickupUAppQuery.getOrderNo()==null || "".equals(mallPickupUAppQuery.getOrderNo())){
            return ReturnResponse.failed("请求参数为空");
        }
        mallPickupUAppQuery.setOrderNo(mallPickupUAppQuery.getOrderNo().trim());    //去空格
        return mallOrderService.verPickupNoByUApp(mallPickupUAppQuery);
    }

    /**
     * 通过订单编号或购物车编号获取分佣列表,UApp
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "分佣列表",notes = "通过订单编号或购物车编号获取分佣列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页",required=true,dataType="Long"),
            @ApiImplicitParam(name="page_size",value="条数",required=true,dataType="Long"),
            @ApiImplicitParam(name="order_no",value="订单编号",dataType="String")
    })
    @GetMapping("/u-app/order/commission")
    public CommonResult<BasePageResult<MallOrderDetailUAppVO>> queryOrderDetailShareFlowListByNo(@RequestParam("page") Long page, @RequestParam("page_size") Long pageSize, @RequestParam("order_no") String orderNo){
        if (page==null || pageSize==null){
            return CommonResult.failed("分页参数为空");
        }
        if (orderNo==null || "".equals(orderNo)){
            return CommonResult.failed("传入参数为空");
        }
        orderNo = orderNo.trim();
        return mallOrderService.queryOrderDetailShareFlowListByNo(page,pageSize,orderNo);
    }
}
