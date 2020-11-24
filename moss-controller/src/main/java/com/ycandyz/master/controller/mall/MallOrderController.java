package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.controller.base.BaseController;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.domain.query.mall.MallOrderUAppQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.enums.ExpressEnum;
import com.ycandyz.master.model.mall.MallOrderUAppVO;
import com.ycandyz.master.model.mall.MallOrderVO;
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
    public ReturnResponse<Page<MallOrderVO>> queryMallOrderList(@RequestBody RequestParams<MallOrderQuery> requestParams, @CurrentUser UserVO userVO){
        if (requestParams.getT()==null){
            return ReturnResponse.failed("请求入参为空");
        }
        return mallOrderService.queryOrderList(requestParams,userVO);
    }

    /**
     * 订单列表导出
     * @param mallOrder
     */
    @ApiOperation(value = "订单列表导出",notes = "订单列表导出",httpMethod = "POST")
    @PostMapping("/order/export")
    public ReturnResponse<MallOrderExportResp> exportEXT(@RequestBody MallOrderQuery mallOrder, @CurrentUser UserVO userVO){
        MallOrderExportResp s = mallOrderService.exportEXT(mallOrder, userVO);
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
    public ReturnResponse<MallOrderVO> queryOrderDetail(@RequestParam("orderNo") String orderNo, @CurrentUser UserVO userVO){
        return mallOrderService.queryOrderDetail(orderNo,userVO);
    }

    /**
     * 通过提货码查询订单
     * @param pickupNo
     * @param userVO
     * @return
     */
    @ApiOperation(value = "通过提货码查询订单",notes = "通过提货码查询订单",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pickupNo",value="提货码",required=true,dataType="String")
    })
    @GetMapping("/order/pickup/query")
    public ReturnResponse<MallOrderVO> queryDetailByPickupNo(@RequestParam("pickupNo") String pickupNo, @RequestParam(value = "orderNo", required = false) String orderNo, @CurrentUser UserVO userVO){
        if (pickupNo!=null){
            pickupNo = pickupNo.trim();
        }
        return mallOrderService.queryDetailByPickupNo(pickupNo, orderNo, userVO);
    }

    /**
     * 验证提货码，出货
     * @param pickupNo
     * @param userVO
     * @return
     */
    @ApiOperation(value = "验证提货码,出货",notes = "验证提货码,出货",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pickupNo",value="提货码",required=true,dataType="String"),
            @ApiImplicitParam(name="orderNo",value="订单号",dataType="String")
    })
    @GetMapping("/order/pickup/verification")
    public ReturnResponse<String> verPickupNo(@RequestParam("pickupNo") String pickupNo, @RequestParam(value = "orderNo", required = false) String orderNo, @CurrentUser UserVO userVO){
        if (pickupNo!=null){
            pickupNo = pickupNo.trim();
        }
        return mallOrderService.verPickupNo(pickupNo,orderNo, userVO);
    }

    /**
     * 获取快递公司列表,UApp
     * @return
     */
    @ApiOperation(value = "获取快递公司列表",notes = "获取快递公司列表",httpMethod = "GET")
    @GetMapping("/delivery/company/list")
    public ReturnResponse<List<Map<String, String>>> getDeliveryCompanyList(){
        List<Map<String, String>> list = ExpressEnum.getMap();
        return ReturnResponse.success(list);
    }

    /**
     * UApp项目订单列表接口,UApp
     * @param page
     * @param page_size
     * @param mallOrderQuery
     * @param status
     * @param userVO
     * @return
     */
    @ApiOperation(value = "UApp项目订单列表接口",notes = "UApp项目订单列表接口",httpMethod = "GET")
    @GetMapping("/u-app/order/page")
    public ReturnResponse<Page<MallOrderUAppVO>> queryMallOrderListByUApp(@RequestParam("page") Long page, @RequestParam("page_size") Long page_size, @RequestParam("mallOrderQuery") String mallOrderQuery, @RequestParam("status") Integer status, @CurrentUser UserVO userVO){
        if (page==null || page_size==null){
            return ReturnResponse.failed("请求入参为空");
        }
        if (mallOrderQuery!=null && !"".equals(mallOrderQuery)){
            mallOrderQuery = mallOrderQuery.trim();
        }
        return mallOrderService.queryMallOrderListByUApp(page,page_size,mallOrderQuery,status,userVO);
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
    @GetMapping("/u-app/order/detail/{orderNo}")
    public ReturnResponse<MallOrderUAppVO> queryOrderDetailByUApp(@PathVariable("orderNo") String orderNo, @CurrentUser UserVO userVO){
        return mallOrderService.queryOrderDetailByUApp(orderNo,userVO);
    }

    /**
     * 通过提货码查询订单,UApp
     * @param pickupNo
     * @param userVO
     * @return
     */
    @ApiOperation(value = "通过提货码查询订单",notes = "通过提货码查询订单",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pickupNo",value="提货码",required=true,dataType="String")
    })
    @GetMapping("/u-app/order/pickup/query")
    public ReturnResponse<MallOrderUAppVO> queryDetailByPickupNoUApp(@RequestParam("pickupNo") String pickupNo, @RequestParam(value = "orderNo", required = false) String orderNo, @CurrentUser UserVO userVO){
        if (pickupNo!=null){
            pickupNo = pickupNo.trim();
        }
        return mallOrderService.queryDetailByPickupNoUApp(pickupNo, orderNo, userVO);
    }


}
