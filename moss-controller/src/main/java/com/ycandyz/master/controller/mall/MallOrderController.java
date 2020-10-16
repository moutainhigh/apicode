package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderDetailVO;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.MallOrderService;
import com.ycandyz.master.service.mall.impl.MaillOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/mall")
public class MallOrderController extends BaseController<MaillOrderServiceImpl, MallOrder, MallOrderQuery> {

    @Autowired
    private MallOrderService mallOrderService;

    /**
     * 获取订单表集合
     * @param model
     * @param mallOrder
     * @return
     */
    @PostMapping("/order/list")
    public CommonResult<Page<MallOrderVO>> queryMallOrderList(PageModel model, @RequestBody MallOrderQuery mallOrder, @CurrentUser UserVO userVO){
        return mallOrderService.queryOrderList(model,mallOrder,userVO);
    }

    /**
     * 订单列表导出
     * @param mallOrder
     * @param response
     */
    @PostMapping("/order/export")
    public void exportEXT(@RequestBody MallOrderQuery mallOrder, @CurrentUser UserVO userVO, HttpServletResponse response){
        mallOrderService.exportEXT(mallOrder,userVO,response);
    }

    /**
     * 查看订单详情
     * @param orderNo
     * @return
     */
    @GetMapping("/order/detail")
    public CommonResult<MallOrderDetailVO> queryOrderDetail(@RequestParam("orderNo") String orderNo){
        return mallOrderService.queryOrderDetail(orderNo);
    }

    /**
     * 订单发货校验物流单号，调用三方接口，查看物流单号所属物流公司
     * @param shipNumber
     * @return
     */
    @GetMapping("/order/shipment/verification")
    public CommonResult<String> verShipmentNo(@RequestParam("shipNumber") String shipNumber, @CurrentUser UserVO userVO){
        boolean flag = mallOrderService.verShipmentNo(shipNumber);
        if (flag){
            return CommonResult.success("发货成功!");
        }
        return CommonResult.failed("发货失败!");
    }

    /**
     * 通过提货码查询订单
     * @param pickupNo
     * @param userVO
     * @return
     */
    @GetMapping("/order/pickup/query")
    public CommonResult<MallOrderVO> queryDetailByPickupNo(@RequestParam("shipN") String pickupNo, @CurrentUser UserVO userVO){
        return mallOrderService.queryDetailByPickupNo(pickupNo, userVO);
    }

    /**
     * 验证提货码，出货
     * @param pickupNo
     * @param userVO
     * @return
     */
    @GetMapping("/order/pickup/verification")
    public CommonResult<String> verPickupNo(@RequestParam("pickupNo") String pickupNo, @CurrentUser UserVO userVO){
        return mallOrderService.verPickupNo(pickupNo, userVO);
    }


}
