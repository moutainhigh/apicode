package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.domain.response.miniprogram.MpConfigPlanPageResp;
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
import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanPageQuery;
import com.ycandyz.master.service.miniprogram.impl.MpConfigPlanPageServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 小程序配置-页面配置 接口
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mini-program/config/plan/pages")
@Api(tags="小程序配置-方案页面配置")
public class MpConfigPlanPageController extends BaseController<MpConfigPlanPageServiceImpl,MpConfigPlanPage,MpConfigPlanPageQuery> {
	
	@ApiOperation(value="新增绑定菜单模块", tags = "企业小程序DIY配置")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResult<Boolean> create(@Validated(ValidatorContract.OnCreate.class) @RequestBody MpConfigPlanPageModel entity) {
        return result(service.add(entity),true,"保存失败!");
	}
	
	@ApiOperation(value = "编辑绑定菜单模块", tags = "企业小程序DIY配置")
    @PutMapping(value = "{id}")
	public CommonResult<MpConfigPlanPage> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpConfigPlanPage entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询模块下元素信息", tags = "企业小程序DIY配置")
    @GetMapping(value = "menu/{menuId}/sort/{sortId}/module/{moduleId}")
	public CommonResult<MpConfigPlanPageResp> getById(@PathVariable Integer menuId,@PathVariable Integer sortId,@PathVariable Integer moduleId) {
        MpConfigPlanPageResp result = new MpConfigPlanPageResp();
        service.getPlanMenuModule(menuId,sortId,moduleId);

        return CommonResult.success(result);
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MpConfigPlanPage>> selectPage(PageModel page, MpConfigPlanPageQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public CommonResult<BaseResult<List<MpConfigPlanPage>>> selectList(MpConfigPlanPageQuery query) {
        return CommonResult.success(new BaseResult<List<MpConfigPlanPage>>(service.list(query)));
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
