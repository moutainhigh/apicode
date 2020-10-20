package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.controller.base.BaseController;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.model.mall.MallAfterSalesVO;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.MallAfterSalesService;
import com.ycandyz.master.service.mall.impl.MaillOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mall")
public class MallAfterSalesController extends BaseController<MaillOrderServiceImpl, MallAfterSales, MallafterSalesQuery> {

    @Autowired
    private MallAfterSalesService mallAfterSalesService;

    /**
     * 获取售后订单列表
     * @param pageModel
     * @param mallafterSalesQuery
     * @return
     */
    @PostMapping("/sales/list")
    public CommonResult<Page<MallAfterSalesVO>> querySalesList(PageModel pageModel, @RequestBody MallafterSalesQuery mallafterSalesQuery){
        return mallAfterSalesService.querySalesList(pageModel,mallafterSalesQuery);
    }

    /**
     * 查看售后订单详情
     * @param id
     * @return
     */
    @GetMapping("/sales/detail")
    public CommonResult<MallAfterSalesVO> querySalesDetail(@RequestParam("id") Long id, @CurrentUser UserVO user){
        if (id!=null && id>0){
            return mallAfterSalesService.querySalesDetail(id);
        }
        return CommonResult.failed("传入id为空");
    }

    @GetMapping("/")
    public CommonResult refundAuditFirst(@RequestParam("orderNo") Long id, @RequestParam("subStatus") Integer subStatus){
        boolean flag = mallAfterSalesService.refundAuditFirst(id,subStatus);
        if (flag){
            return CommonResult.success("审核成功");
        }
        return CommonResult.failed("审核失败");
    }
}
