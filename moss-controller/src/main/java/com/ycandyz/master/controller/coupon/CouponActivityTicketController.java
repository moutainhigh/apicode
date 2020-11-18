package com.ycandyz.master.controller.coupon;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.entities.coupon.CouponActivityTicket;
import com.ycandyz.master.domain.query.coupon.CouponActivityTicketQuery;
import com.ycandyz.master.service.coupon.impl.CouponActivityTicketServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 发卷宝-优惠卷 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("coupon-activity-ticket")
@Api(tags="coupon-发卷宝-优惠卷")
public class CouponActivityTicketController extends BaseController<CouponActivityTicketServiceImpl,CouponActivityTicket,CouponActivityTicketQuery> {
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}
    
}
