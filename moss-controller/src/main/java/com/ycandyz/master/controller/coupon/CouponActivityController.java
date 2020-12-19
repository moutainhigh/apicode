package com.ycandyz.master.controller.coupon;

import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.model.coupon.CouponActivityModel;
import com.ycandyz.master.domain.model.coupon.CouponActivityPutModel;
import com.ycandyz.master.domain.query.coupon.CouponActivityCouponQuery;
import com.ycandyz.master.domain.query.coupon.CouponDetailUserQuery;
import com.ycandyz.master.domain.response.coupon.CouponActivityCouponResp;
import com.ycandyz.master.domain.response.coupon.CouponDetailUserResp;
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
@RequestMapping("activity/coupon")
@Api(tags="活动-优惠卷")
public class CouponActivityController extends BaseController<CouponActivityServiceImpl,CouponActivity,CouponActivityQuery> {

    @ApiVersion(group = {ApiVersionConstant.API_COUPON_100})
	@ApiOperation(value="新增",notes="新增")
    @PostMapping
	public CommonResult<CouponActivityModel> insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody CouponActivityModel entity) {
	    return result(service.insert(entity),entity,"保存失败!");
	}

    @ApiVersion(group = {ApiVersionConstant.API_COUPON_100})
	@ApiOperation(value = "更新",notes="更新")
    @PutMapping(value = "{id}")
	public CommonResult<CouponActivityModel> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) @RequestBody CouponActivityModel entity) {
        entity.setId(id);
        return result(service.update(entity),entity,"更改失败!");
	}

    @ApiVersion(group = {ApiVersionConstant.API_COUPON_100})
    @ApiImplicitParam(name="status",value="操作类型(1启用,0停止)",required=true,dataType="int")
    @ApiOperation(value = "启用/停止")
    @PutMapping(value = "{id}/switch")
    public CommonResult<String> switchById(@PathVariable Long id, @RequestBody Integer status) {
        CouponActivityPutModel model = new CouponActivityPutModel();
        model.setId(id);
        model.setEnabled(status);
        return result(service.switchById(model),null,"操作失败!");
    }

    @ApiVersion(group = {ApiVersionConstant.API_COUPON_100})
	@ApiOperation(value = "查询单条数据",notes="查询单条数据")
    @GetMapping(value = "{id}")
	public CommonResult<CouponActivity> getById(@PathVariable Long id) {
        return CommonResult.success(service.selectById(id));
    }

    @ApiVersion(group = {ApiVersionConstant.API_COUPON_100})
	@ApiOperation(value = "发卷宝-分页")
    @GetMapping
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<CouponActivity>> getPage(PageModel page,CouponActivityQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPage(),page.getPageSize()),query)));
    }

    @ApiVersion(group = {ApiVersionConstant.API_COUPON_100})
    @ApiOperation(value = "发卷宝-优惠卷-分页")
    @GetMapping(value = "{id}/coupon")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<CouponActivityCouponResp>> getCouponPage(PageModel page,@PathVariable Long id, CouponActivityCouponQuery query) {
        query.setId(id);
        return CommonResult.success(new BasePageResult(service.selectCouponPage(new Page(page.getPage(),page.getPageSize()),query)));
    }

    @ApiVersion(group = {ApiVersionConstant.API_COUPON_100})
    @ApiOperation(value = "活动参与人-分页")
    @GetMapping(value = "{id}/user")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<CouponDetailUserResp>> getUserPage(@PathVariable Long id,PageModel page, CouponDetailUserQuery query) {
	    query.setId(id);
        return CommonResult.success(new BasePageResult(service.selectUserActivityCouponPage(new Page(page.getPage(),page.getPageSize()),query)));
    }

    @ApiOperation(value = "删除发卷宝的-优惠卷")
    @DeleteMapping(value = "coupon/{activity_coupon_id}")
	public CommonResult deleteCouponById(@PathVariable Long activity_coupon_id) {
        return result(service.removeCouponById(activity_coupon_id),"删除成功！","删除失败!");
	}

    
}
