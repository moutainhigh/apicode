package com.ycandyz.master.controller.coupon;

import com.ycandyz.master.api.*;
import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.query.coupon.CouponDetailQuery;
import com.ycandyz.master.domain.query.coupon.CouponQuery;
import com.ycandyz.master.entities.coupon.Coupon;
import com.ycandyz.master.model.coupon.CouponDetailVO;
import com.ycandyz.master.service.coupon.ICouponService;
import com.ycandyz.master.service.coupon.impl.CouponServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.controller.base.BaseController;

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
@Api(tags="coupon")
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
    public ReturnResponse<Page<CouponDetailVO>> selectPageList(@RequestBody RequestParams<CouponQuery> requestParams) {
        return iCouponService.selectPageList(requestParams);
    }

    /**
     * 优惠券的启用和停用
     * @param id
     * @param state
     * @return
     */
    @ApiOperation(value = "优惠券启用停用")
    @GetMapping(value = "/audit")
	public ReturnResponse<String> auditState(@RequestParam("id") Long id, @RequestParam("state") Integer state) {
        if (id==null || id==0 || state==null){
            return ReturnResponse.failed("传入参数为空");
        }
        return iCouponService.auditState(id,state);
	}

    /**
     * 优惠券的启用和停用
     * @param ticketNo 优惠券编号
     * @return
     */
    @ApiOperation(value = "优惠券启用停用")
    @GetMapping(value = "/{ticket_no}")
    public ReturnResponse<CouponDetailVO> ticketDetail(@PathVariable("ticket_no") String ticketNo) {
        if (ticketNo==null || "".equals(ticketNo)){
            return ReturnResponse.failed("传入参数为空");
        }
        return iCouponService.ticketDetail(ticketNo);
    }

    /**
     * 优惠券的编辑或新增
     * @param couponDetailQuery
     * @return
     */
    @ApiOperation(value = "优惠券的新增或修改")
    @PutMapping(value = "")
    public ReturnResponse<String> saveTicket(@RequestBody CouponDetailQuery couponDetailQuery){
        ReturnResponse<String> returnResponse = iCouponService.saveTicket(couponDetailQuery);
        return returnResponse;
    }

//    public ReturnResponse<String> itemList(String type, String keyword, String itemType){
//
//    }
}
