package com.ycandyz.master.controller.coupon;

import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
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
import com.ycandyz.master.entities.coupon.CouponTicketInfoItem;
import com.ycandyz.master.domain.query.coupon.CouponTicketInfoItemQuery;
import com.ycandyz.master.service.coupon.impl.CouponTicketInfoItemServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 优惠券详情关联商品表 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */

@ApiVersion(group = ApiVersionConstant.API_COUPON)
@Slf4j
@RestController
@RequestMapping("coupon-ticket-info-item")
@Api(tags="coupon-ticket-info-item")
public class CouponTicketInfoItemController extends BaseController<CouponTicketInfoItemServiceImpl,CouponTicketInfoItem,CouponTicketInfoItemQuery> {
    
}
