package com.ycandyz.master.controller.miniprogram;

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
import com.ycandyz.master.entities.miniprogram.MpConfigModule;
import com.ycandyz.master.domain.query.miniprogram.MpConfigModuleQuery;
import com.ycandyz.master.service.miniprogram.impl.MpConfigModuleServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description  接口
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mini-program/config/modules")
@Api(tags="小程序配置-模块信息")
public class MpConfigModuleController extends BaseController<MpConfigModuleServiceImpl,MpConfigModule,MpConfigModuleQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<MpConfigModule> insert(@Validated(ValidatorContract.OnCreate.class) MpConfigModule entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<MpConfigModule> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpConfigModule entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MpConfigModule> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MpConfigModule>> selectPage(PageModel page, MpConfigModuleQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "获取基础模块", tags = "小程序配置")
    @GetMapping
    public CommonResult<BaseResult<List<MpConfigModule>>> selectList() {
        return CommonResult.success(new BaseResult<List<MpConfigModule>>(service.list()));
    }
    
    @ApiOperation(value = "通过ID删除")
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
