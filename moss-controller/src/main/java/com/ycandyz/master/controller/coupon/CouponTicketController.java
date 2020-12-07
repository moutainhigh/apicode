package com.ycandyz.master.controller.coupon;

import com.ycandyz.master.api.*;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.coupon.CouponTicketInfoQuery;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.model.coupon.CouponTicketInfoVO;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.service.coupon.ICouponTicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.entities.coupon.CouponTicket;
import com.ycandyz.master.domain.query.coupon.CouponTicketQuery;
import com.ycandyz.master.service.coupon.impl.CouponTicketServiceImpl;
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
@RequestMapping("coupon/ticket")
@Api(tags="coupon/ticket")
public class CouponTicketController extends BaseController<CouponTicketServiceImpl,CouponTicket,CouponTicketQuery> {

    @Autowired
    private ICouponTicketService iCouponTicketService;

    /**
     * 优惠券列表查询
     * @param requestParams
     * @param requestParams
     * @return
     */
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "")
    public ReturnResponse<Page<CouponTicketInfoVO>> selectPageList(@RequestBody RequestParams<CouponTicketQuery> requestParams) {
        return iCouponTicketService.selectPageList(requestParams);
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
        return iCouponTicketService.auditState(id,state);
	}

    /**
     * 优惠券的启用和停用
     * @param ticketNo 优惠券编号
     * @return
     */
    @ApiOperation(value = "优惠券启用停用")
    @GetMapping(value = "/{ticket_no}")
    public ReturnResponse<CouponTicketInfoVO> ticketDetail(@PathVariable("ticket_no") String ticketNo) {
        if (ticketNo==null || "".equals(ticketNo)){
            return ReturnResponse.failed("传入参数为空");
        }
        return iCouponTicketService.ticketDetail(ticketNo);
    }

    /**
     * 优惠券的编辑或新增
     * @param couponTicketInfoQuery
     * @return
     */
    @ApiOperation(value = "优惠券的新增或修改")
    @PutMapping(value = "")
    public ReturnResponse<String> saveTicket(@RequestBody CouponTicketInfoQuery couponTicketInfoQuery){
        ReturnResponse<String> returnResponse = iCouponTicketService.saveTicket(couponTicketInfoQuery);
        return returnResponse;
    }

//    public ReturnResponse<String> itemList(String type, String keyword, String itemType){
//
//    }
}
