package com.ycandyz.master.controller.mall;

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
import com.ycandyz.master.entities.mall.MallItemVideoComplaint;
import com.ycandyz.master.domain.query.mall.MallItemVideoComplaintQuery;
import com.ycandyz.master.service.mall.impl.MallItemVideoComplaintServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 商品视频投诉 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-12
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mall-item-video-complaint")
@Api(tags="mall-商品视频投诉")
public class MallItemVideoComplaintController extends BaseController<MallItemVideoComplaintServiceImpl,MallItemVideoComplaint,MallItemVideoComplaintQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<MallItemVideoComplaint> insert(@Validated(ValidatorContract.OnCreate.class) MallItemVideoComplaint entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MallItemVideoComplaint> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallItemVideoComplaint>> selectPage(PageModel page, MallItemVideoComplaintQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    
}
