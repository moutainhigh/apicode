package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.domain.model.miniprogram.ConfigPlanAndMenuModel;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanMenuModel;
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
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanMenuQuery;
import com.ycandyz.master.service.miniprogram.impl.MpConfigPlanMenuServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 小程序配置-菜单配置 接口
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-13
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mini-program/config/plan/menus")
@Api(tags="小程序配置-方案菜单配置")
public class MpConfigPlanMenuController extends BaseController<MpConfigPlanMenuServiceImpl,MpConfigPlanMenu,MpConfigPlanMenuQuery> {
	
	@ApiOperation(value="新增方案内菜单", tags = "小程序配置")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResult<MpConfigPlanMenu> create(@RequestBody MpConfigPlanMenuModel model) {
        return CommonResult.success(service.add(model));
	}
	
	@ApiOperation(value = "修改方案内菜单", tags = "小程序配置")
    @PutMapping(value = "{id}")
	public CommonResult<MpConfigPlanMenu> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpConfigPlanMenu entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MpConfigPlanMenu> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MpConfigPlanMenu>> selectPage(PageModel page, MpConfigPlanMenuQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "获取方案菜单", tags = "小程序配置")
    @GetMapping
    public CommonResult<BaseResult<List<MpConfigPlanMenu>>> selectList(MpConfigPlanMenuQuery query) {
        return CommonResult.success(new BaseResult<List<MpConfigPlanMenu>>(service.list(query)));
    }
    
    @ApiOperation(value = "删除方案内菜单", tags = "小程序配置")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "delete")
	public CommonResult deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
