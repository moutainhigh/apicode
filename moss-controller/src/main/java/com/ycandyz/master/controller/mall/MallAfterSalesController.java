package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.base.BaseController;
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
     * @param pageModel
     * @param mallafterSalesQuery
     * @return
     */
    @ApiOperation(value = "获取售后订单列表",notes = "获取售后订单列表",httpMethod = "POST")
    @PostMapping("/sales/list")
    public CommonResult<Page<MallAfterSalesVO>> querySalesListPage(PageModel pageModel, @RequestBody MallafterSalesQuery mallafterSalesQuery, @CurrentUser UserVO userVO){
        return mallAfterSalesService.querySalesListPage(pageModel,mallafterSalesQuery, userVO);
    }

    /**
     * 查看售后订单详情
     * @param id
     * @return
     */
    @ApiOperation(value = "查看售后订单详情",notes = "查看售后订单详情",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="订单id",required=true,dataType="Long")
    })
    @GetMapping("/sales/detail")
    public CommonResult<MallAfterSalesVO> querySalesDetail(@RequestParam("id") Long id){
        if (id!=null && id>0){
            return mallAfterSalesService.querySalesDetail(id);
        }
        return CommonResult.failed("传入id为空");
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
    public CommonResult refundAuditFirst(@RequestBody MallafterSalesQuery mallafterSalesQuery, @CurrentUser UserVO user){
        boolean flag = mallAfterSalesService.refundAuditFirst(mallafterSalesQuery,user);
        if (flag){
            return CommonResult.success("审核成功");
        }
        return CommonResult.failed("审核失败");
    }

    /**
     * 售后订单导出
     * @param mallafterSalesQuery
     * @param response
     */
    @ApiOperation(value = "售后订单导出",notes = "售后订单导出",httpMethod = "POST")
    @PostMapping("/sales/export")
    public void exportEXT(@RequestBody MallafterSalesQuery mallafterSalesQuery, @CurrentUser UserVO userVO, HttpServletResponse response){
        mallAfterSalesService.exportEXT(mallafterSalesQuery, userVO,response);
    }
}
