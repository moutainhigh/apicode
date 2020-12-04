package com.ycandyz.master.controller.coupon;

import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.model.coupon.CouponActivityPutModel;
import com.ycandyz.master.domain.query.coupon.CouponActivityTicketQuery;
import com.ycandyz.master.domain.query.coupon.CouponUserActivityTicketQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityTicketResp;
import com.ycandyz.master.domain.response.coupon.CouponUserTicketResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.entities.coupon.CouponActivity;
import com.ycandyz.master.domain.query.coupon.CouponActivityQuery;
import com.ycandyz.master.service.coupon.impl.CouponActivityServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 发卷宝 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */

@ApiVersion(group = ApiVersionConstant.API_COUPON)
@Slf4j
@RestController
@RequestMapping("activity")
@Api(tags="优惠卷")
public class CouponActivityController extends BaseController<CouponActivityServiceImpl,CouponActivity,CouponActivityQuery> {

	@ApiOperation(value="新增",notes="新增")
    @PostMapping
	public CommonResult<CouponActivityModel> insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody CouponActivityModel entity) {
	    return result(service.insert(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "更新",notes="更新")
    @PutMapping(value = "{id}")
	public CommonResult<CouponActivityModel> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) @RequestBody CouponActivityModel entity) {
        entity.setId(id);
        return result(service.update(entity),entity,"更改失败!");
	}

    @ApiOperation(value = "启用/停止")
    @PutMapping(value = "switch/{id}")
    public CommonResult<String> switchById(@PathVariable Long id, @Validated(ValidatorContract.OnUpdate.class) @RequestBody CouponActivityPutModel model) {
	    model.setId(id);
        return result(service.switchById(model),null,"停止失败!");
    }
	
	@ApiOperation(value = "查询单条数据",notes="查询单条数据")
    @GetMapping(value = "{id}")
	public CommonResult<CouponActivity> getById(@PathVariable Long id) {
        return CommonResult.success(service.selectById(id));
    }
    
	@ApiOperation(value = "发卷宝-分页")
    @GetMapping
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<CouponActivity>> getPage(PageModel page,CouponActivityQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPage(),page.getPageSize()),query)));
    }

    @ApiOperation(value = "发卷宝-优惠卷-分页")
    @GetMapping(value = "coupon")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<CouponActivityTicketResp>> getTicketPage(PageModel page,CouponActivityTicketQuery query) {
        return CommonResult.success(new BasePageResult(service.selectTicketPage(new Page(page.getPage(),page.getPageSize()),query)));
    }

    @ApiOperation(value = "活动参与人-分页")
    @GetMapping(value = "user")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<CouponUserTicketResp>> getUserPage(PageModel page, CouponUserActivityTicketQuery query) {
        return CommonResult.success(new BasePageResult(service.selectUserActivityTicketPage(new Page(page.getPage(),page.getPageSize()),query)));
    }

    @ApiVersion(group = {ApiVersionConstant.API_COUPON_100})
    @ApiOperation(value = "删除发卷宝的-优惠卷")
    @DeleteMapping(value = "coupon/{activity_ticket_id}")
	public CommonResult deleteTicketById(@PathVariable Long activity_ticket_id) {
        return result(service.removeTicketById(activity_ticket_id),"删除成功！","删除失败!");
	}

    
}
