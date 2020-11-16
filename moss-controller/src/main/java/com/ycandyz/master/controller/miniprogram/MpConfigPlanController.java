package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.model.miniprogram.ConfigPlanAndMenuModel;
import com.ycandyz.master.domain.query.miniprogram.MpConfigMenuQuery;
import com.ycandyz.master.domain.response.miniprogram.MpConfigMenuResp;
import com.ycandyz.master.domain.response.miniprogram.MpConfigPlanMenuResp;
import com.ycandyz.master.entities.miniprogram.MpConfigMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.entities.miniprogram.MpConfigPlan;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanQuery;
import com.ycandyz.master.service.miniprogram.impl.MpConfigPlanServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 小程序配置方案 接口类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mini-program/config/plans")
@Api(tags="小程序配置-方案配置")
public class MpConfigPlanController extends BaseController<MpConfigPlanServiceImpl,MpConfigPlan,MpConfigPlanQuery> {
	
	@ApiOperation(value="创建方案", tags = "小程序配置")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResult<BaseResult<List<MpConfigPlanMenuResp>>> create(@RequestBody ConfigPlanAndMenuModel model) {
	    return result(true,new BaseResult(service.add(model)),"保存成功!");
	}

	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<MpConfigPlan> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpConfigPlan entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MpConfigPlan> getById(@PathVariable Integer id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MpConfigPlan>> selectPage(PageModel page, MpConfigPlanQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping
    public CommonResult<BaseResult<List<MpConfigPlan>>> selectList(MpConfigPlanQuery query) {
        return CommonResult.success(new BaseResult(service.list(query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult<MpConfigPlan> deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "deleteBatch")
	public CommonResult<MpConfigPlan> deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
