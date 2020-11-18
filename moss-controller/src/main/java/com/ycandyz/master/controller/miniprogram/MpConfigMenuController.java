package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.query.mall.MallItemVideoQuery;
import com.ycandyz.master.entities.mall.MallItemVideo;
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
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.entities.miniprogram.MpConfigMenu;
import com.ycandyz.master.domain.query.miniprogram.MpConfigMenuQuery;
import com.ycandyz.master.service.miniprogram.impl.MpConfigMenuServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 小程序配置-菜单配置 接口类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("cms/mp/dict/menus")
@Api(tags="小程序配置-菜单配置")
public class MpConfigMenuController extends BaseController<MpConfigMenuServiceImpl,MpConfigMenu,MpConfigMenuQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<MpConfigMenu> create(@Validated(ValidatorContract.OnCreate.class) MpConfigMenu entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<MpConfigMenu> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpConfigMenu entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MpConfigMenu> getById(@PathVariable Integer id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "分页查询")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MpConfigMenu>> selectPage(PageModel page, MpConfigMenuQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "✓列表查询-默认菜单", tags = "企业小程序DIY配置")
    @GetMapping
    public CommonResult<BaseResult<List<MpConfigMenu>>> selectList(MpConfigMenuQuery query) {
        return CommonResult.success(new BaseResult(service.list(query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult<MpConfigMenu> deleteById(@PathVariable Integer id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "deleteBatch")
	public CommonResult<MpConfigMenu> deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
