package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseController;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.model.mall.MallAfterSalesVO;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.MallAfterSalesService;
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
@Api(tags="mall-售后订单表")
public class MallAfterSalesController extends BaseController<MaillOrderServiceImpl, MallAfterSales, MallafterSalesQuery> {

    @Autowired
    private MallAfterSalesService mallAfterSalesService;

    /**
     * 获取售后订单列表
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "获取售后订单列表",notes = "获取售后订单列表",httpMethod = "POST")
    @PostMapping("/sales/list")
    public ReturnResponse<Page<MallAfterSalesVO>> querySalesListPage(@RequestBody RequestParams<MallafterSalesQuery> requestParams, @CurrentUser UserVO userVO){
        return mallAfterSalesService.querySalesListPage(requestParams, userVO);
    }

    /**
     * 查看售后订单详情
     * @param afterSalesNo
     * @return
     */
    @ApiOperation(value = "查看售后订单详情",notes = "查看售后订单详情",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="订单id",required=true,dataType="Long")
    })
    @GetMapping("/sales/detail")
    public ReturnResponse<MallAfterSalesVO> querySalesDetail(@RequestParam("afterSalesNo") String afterSalesNo, @CurrentUser UserVO userVO){
        if (afterSalesNo!=null && !"".equals(afterSalesNo)){
            return mallAfterSalesService.querySalesDetail(afterSalesNo,userVO);
        }
        return ReturnResponse.failed("传入id为空");
    }

    /**
     * 第一次审核按钮
     * @param mallafterSalesQuery
     * @param user
     * @return
     */
    @ApiOperation(value = "第一次审核按钮",notes = "第一次审核按钮",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="订单id",required=true,dataType="Long"),
            @ApiImplicitParam(name="subStatus",value="审核状态",required=true,dataType="Integer"),
            @ApiImplicitParam(name="receiverPhone",value="手机号",required=true,dataType="String"),
            @ApiImplicitParam(name="receiverAddress",value="收货地址",required=true,dataType="String")
    })
    @PostMapping("/sales/audit/first")
    public ReturnResponse refundAuditFirst(@RequestBody MallafterSalesQuery mallafterSalesQuery, @CurrentUser UserVO user){
        boolean flag = mallAfterSalesService.refundAuditFirst(mallafterSalesQuery,user);
        if (flag){
            return ReturnResponse.success("审核成功");
        }
        return ReturnResponse.failed("审核失败");
    }

    /**
     * 售后订单导出
     * @param mallafterSalesQuery
     */
    @ApiOperation(value = "售后订单导出",notes = "售后订单导出",httpMethod = "POST")
    @PostMapping("/sales/export")
    public void exportEXT(@RequestBody MallafterSalesQuery mallafterSalesQuery, @CurrentUser UserVO userVO){
        mallAfterSalesService.exportEXT(mallafterSalesQuery, userVO);
    }

    /**
     * 售后详情点击确认退款按钮，显示运费
     * @param orderNo
     * @param userVO
     * @return
     */
    @GetMapping("/order/refund/detail")
    public ReturnResponse<String> refundDetail(@RequestParam("orderNo") String orderNo, @CurrentUser UserVO userVO){
        if (orderNo==null || "".equals(orderNo)){
            return ReturnResponse.failed("传入参数为空");
        }
        return mallAfterSalesService.refundDetail(orderNo,userVO);
    }
}
