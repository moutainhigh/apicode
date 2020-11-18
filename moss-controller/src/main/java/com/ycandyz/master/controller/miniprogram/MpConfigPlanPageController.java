package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.domain.model.miniprogram.PlanModuleModel;
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
@RequestMapping("cms/mp/modules")
@Api(tags="小程序配置-方案页面配置")
public class MpConfigPlanPageController extends BaseController<MpConfigPlanPageServiceImpl,MpConfigPlanPage,MpConfigPlanPageQuery> {


    @ApiOperation(value="配置绑定菜单模块", tags = "企业小程序DIY配置")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<Boolean> createBatch(@Validated(ValidatorContract.OnCreate.class) @RequestBody PlanModuleModel entity) {
        return result(service.addBatch(entity),true,"批量保存失败!");
    }
	
	@ApiOperation(value = "编辑绑定菜单模块", tags = "企业小程序DIY配置")
    @PutMapping(value = "{id}")
	public CommonResult<MpConfigPlanPage> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpConfigPlanPage entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}

    @ApiOperation(value = "✓查询菜单下模块信息", tags = "企业小程序DIY配置")
    @GetMapping
    public CommonResult<MpConfigPlanPageResp> getMenuById(@RequestParam Integer menuId) {
        return CommonResult.success(null);
    }
	
	@ApiOperation(value = "✓查询模块下元素信息", tags = "企业小程序DIY配置")
    @GetMapping(value = "/{moduleId}")
	public CommonResult<MpConfigPlanPageResp> getModuleById(@PathVariable Integer moduleId,@RequestParam Integer sortId,@RequestParam Integer menuId) {
        return CommonResult.success(service.getPlanMenuModule(menuId,sortId,moduleId));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public CommonResult<BaseResult<List<MpConfigPlanPage>>> selectList(MpConfigPlanPageQuery query) {
        return CommonResult.success(new BaseResult<List<MpConfigPlanPage>>(service.list(query)));
    }
    
    @ApiOperation(value = "删除页面模块", tags = "企业小程序DIY配置")
    @DeleteMapping(value = "{moduleId}")
	public CommonResult deleteById(@PathVariable Long moduleId) {
        return result(service.removeById(moduleId),null,"删除失败!");
	}

    
}
