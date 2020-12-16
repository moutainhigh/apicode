package com.ycandyz.master.controller.coupon;

import com.ycandyz.master.api.*;
import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.query.coupon.CouponDetailQuery;
import com.ycandyz.master.domain.query.coupon.CouponQuery;
import com.ycandyz.master.entities.coupon.Coupon;
import com.ycandyz.master.model.coupon.CouponDetailVO;
import com.ycandyz.master.model.mall.MallCategoryVO;
import com.ycandyz.master.service.coupon.ICouponService;
import com.ycandyz.master.service.coupon.impl.CouponServiceImpl;
import com.ycandyz.master.vo.MallItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ycandyz.master.controller.base.BaseController;

import java.util.List;

/**
 * <p>
 * @Description 优惠卷 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */

@ApiVersion(group = ApiVersionConstant.API_COUPON)
@Slf4j
@RestController
@RequestMapping("coupon")
@Api(tags="优惠卷")
public class CouponTicketController extends BaseController<CouponServiceImpl,Coupon,CouponQuery> {

    @Autowired
    private ICouponService iCouponService;

    /**
     * 优惠券列表查询
     * @param requestParams
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "查询分页")
    @GetMapping(value = "")
    public CommonResult<BasePageResult<CouponDetailVO>> selectPageList(PageModel page, CouponQuery requestParams) {

        return iCouponService.selectPageList(page,requestParams);
    }

    /**
     * 优惠券的启用和停用
     * @param id
     * @param state
     * @return
     */
    @ApiOperation(value = "优惠券启用停用")
    @PutMapping(value = "{id}/switch")
    public CommonResult<String> auditState(@PathVariable("id") Long id, @RequestParam("state") Integer state) {
        if (id==null || id==0 || state==null){
            return CommonResult.failed("传入参数为空");
        }
        return iCouponService.auditState(id,state);
    }

    /**
     * 优惠券详情
     * @param id 优惠券编号
     * @return
     */
    @ApiOperation(value = "优惠券详情")
    @GetMapping(value = "/{id}")
    public CommonResult<CouponDetailVO> ticketDetail(@PathVariable("id") Long id) {
        if (id==null || id>0){
            return CommonResult.failed("传入参数为空");
        }
        return iCouponService.ticketDetail(id);
    }

    /**
     * 优惠券的新增
     * @param couponDetailQuery
     * @return
     */
    @ApiOperation(value = "优惠券的新增")
    @PostMapping(value = "")
    public CommonResult<String> insertTicket(@RequestBody CouponDetailQuery couponDetailQuery){
        CommonResult<String> returnResponse = iCouponService.insertTicket(couponDetailQuery);
        return returnResponse;
    }

    /**
     * 优惠券的编辑
     * @param couponDetailQuery
     * @return
     */
    @ApiOperation(value = "优惠券的修改")
    @PutMapping(value = "/{id}")
    public CommonResult<String> updateTicket(@PathVariable("id") Long id, @RequestBody CouponDetailQuery couponDetailQuery){
        if (id==null || id==0){
            return CommonResult.failed("传入id为空");
        }
        CommonResult<String> returnResponse = iCouponService.updateTicket(id,couponDetailQuery);
        return returnResponse;
    }

    /**
     * 获取分类树形结构列表
     * @return
     */
    @ApiOperation(value = "获取分类树形结构列表")
    @GetMapping(value = "/item/category")
    public CommonResult<List<MallCategoryVO>> getCategoryList(){
        return iCouponService.getCategoryList();
    }

    /**
     * 获取所有分类
     * @return
     */
    @ApiOperation(value = "获取所有分类")
    @GetMapping(value = "/item")
    public CommonResult<BasePageResult<MallItemVO>> itemList(@RequestParam(value = "id",required = false) Long id, @RequestParam("page") Long page, @RequestParam("page_size") Long pageSize, @RequestParam("type") String type, @RequestParam("keyword") String keyword, @RequestParam("category") String category){
        return iCouponService.itemList(id,page,pageSize,type,keyword,category);
    }
}
