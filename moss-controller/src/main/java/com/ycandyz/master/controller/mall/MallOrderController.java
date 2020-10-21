package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.controller.base.BaseController;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderDetailVO;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.MallOrderService;
import com.ycandyz.master.service.mall.impl.MaillOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/mall")
@Api(tags="mall-订单订单信息表")
public class MallOrderController extends BaseController<MaillOrderServiceImpl, MallOrder, MallOrderQuery> {

    @Autowired
    private MallOrderService mallOrderService;

    /**
     * 获取订单表集合
     * @param model
     * @param mallOrder
     * @return
     */
    @ApiOperation(value = "订单列表",notes = "订单列表",httpMethod = "POST")
    @PostMapping("/order/list")
    public ReturnResponse<Page<MallOrderVO>> queryMallOrderList(PageResult pageResult, @RequestBody MallOrderQuery mallOrder, @CurrentUser UserVO userVO){
        return mallOrderService.queryOrderList(pageResult,mallOrder,userVO);
    }

    /**
     * 订单列表导出
     * @param mallOrder
     * @param response
     */
    @ApiOperation(value = "订单列表导出",notes = "订单列表导出",httpMethod = "POST")
    @PostMapping("/order/export")
    public void exportEXT(@RequestBody MallOrderQuery mallOrder, @CurrentUser UserVO userVO, HttpServletResponse response){
        mallOrderService.exportEXT(mallOrder,userVO,response);
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
    public ReturnResponse<MallOrderVO> queryDetailByPickupNo(@RequestParam("pickupNo") String pickupNo, @CurrentUser UserVO userVO){
        return mallOrderService.queryDetailByPickupNo(pickupNo, userVO);
    }

    /**
     * 验证提货码，出货
     * @param pickupNo
     * @param userVO
     * @return
     */
    @ApiOperation(value = "验证提货码,出货",notes = "验证提货码,出货",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pickupNo",value="提货码",required=true,dataType="String")
    })
    @GetMapping("/order/pickup/verification")
    public ReturnResponse<String> verPickupNo(@RequestParam("pickupNo") String pickupNo, @CurrentUser UserVO userVO){
        return mallOrderService.verPickupNo(pickupNo, userVO);
    }


}
