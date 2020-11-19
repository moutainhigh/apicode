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

@Slf4j
@RestController
@RequestMapping("coupon-activity")
@Api(tags="coupon-发卷宝")
public class CouponActivityController extends BaseController<CouponActivityServiceImpl,CouponActivity,CouponActivityQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<CouponActivity> insert(@Validated(ValidatorContract.OnCreate.class) CouponActivity entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<CouponActivity> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) CouponActivity entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}

    @ApiOperation(value = "通过ID停止")
    @PutMapping(value = "stop/{id}")
    public CommonResult<String> stopById(@PathVariable Long id) {
        return result(service.stopById(id),"停止成功！","停止失败!");
    }

    @ApiOperation(value = "通过ID启用")
    @PutMapping(value = "start/{id}")
    public CommonResult<String> startById(@PathVariable Long id) {
        return result(service.startById(id),"启用成功！","启用失败!");
    }
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<CouponActivity> getById(@PathVariable Long id) {
        return CommonResult.success(service.selectById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<CouponActivity>> selectPage(PageModel page, CouponActivityQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "通过ID删除发卷宝-优惠卷")
    @DeleteMapping(value = "ticket/{id}")
	public CommonResult deleteTicketById(@PathVariable Long id) {
        return result(service.removeTicketById(id),"删除成功！","删除失败!");
	}

    
}
