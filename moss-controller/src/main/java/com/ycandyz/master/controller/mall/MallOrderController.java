package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderDetailVO;
import com.ycandyz.master.model.mall.MallOrderVO;
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
    public CommonResult<Page<MallOrderVO>> queryMallOrderList(PageModel model, @RequestBody MallOrderQuery mallOrder){
        return mallOrderService.queryOrderList(model,mallOrder);
    }

    /**
     * 订单列表导出
     * @param mallOrder
     * @param response
     */
    @PostMapping("/order/export")
    public void exportEXT(@RequestBody MallOrderQuery mallOrder, HttpServletResponse response){
        mallOrderService.exportEXT(mallOrder,response);
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
     * 订单发货按钮
     * @param orderNo
     * @return
     */
    @GetMapping("/order/shipment")
    public CommonResult<String> shipment(@RequestParam("orderNo") String orderNo){
        boolean flag = mallOrderService.shipment(orderNo);
        if (flag){
            return CommonResult.success("发货成功!");
        }
        return CommonResult.failed("发货失败!");
    }

}
