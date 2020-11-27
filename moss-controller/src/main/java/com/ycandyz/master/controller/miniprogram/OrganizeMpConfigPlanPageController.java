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
import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanPage;
import com.ycandyz.master.domain.query.miniprogram.OrganizeMpConfigPlanPageQuery;
import com.ycandyz.master.service.miniprogram.impl.OrganizeMpConfigPlanPageServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 企业小程序配置-页面配置 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("organize-mp-config-plan-page")
@Api(tags="miniprogram-企业小程序配置-页面配置")
public class OrganizeMpConfigPlanPageController extends BaseController<OrganizeMpConfigPlanPageServiceImpl,OrganizeMpConfigPlanPage,OrganizeMpConfigPlanPageQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<OrganizeMpConfigPlanPage> insert(@Validated(ValidatorContract.OnCreate.class) OrganizeMpConfigPlanPage entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<OrganizeMpConfigPlanPage> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) OrganizeMpConfigPlanPage entity) {
        //entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<OrganizeMpConfigPlanPage> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<OrganizeMpConfigPlanPage>> selectPage(PageModel page, OrganizeMpConfigPlanPageQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPage(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public CommonResult<BaseResult<List<OrganizeMpConfigPlanPage>>> selectList(OrganizeMpConfigPlanPageQuery query) {
        return CommonResult.success(new BaseResult<List<OrganizeMpConfigPlanPage>>(service.list(query)));
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
