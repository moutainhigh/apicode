package com.ycandyz.master.controller.miniprogram;

import cn.hutool.core.bean.BeanUtil;
import com.ycandyz.master.domain.model.miniprogram.MenuWithinPlan;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanMenuModel;
import com.ycandyz.master.domain.model.miniprogram.PlanMenuModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BaseResult;
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
@RequestMapping("cms/mp/menu")
@Api(tags="小程序配置-方案菜单配置")
public class MpConfigPlanMenuController extends BaseController<MpConfigPlanMenuServiceImpl,MpConfigPlanMenu,MpConfigPlanMenuQuery> {


    @ApiOperation(value="✓配置方案内菜单", tags = "企业小程序DIY配置")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<Boolean> createBatch(@Validated(ValidatorContract.OnCreate.class) @RequestBody PlanMenuModel model) {
        List<MenuWithinPlan> menus = model.getMenus();
        if(menus.size() > 5){
            return CommonResult.validateFailed("当前最多可添加5个菜单");
        }
        if(menus.size() < 2){
            return CommonResult.validateFailed("当前至少要添加2个菜单");
        }
        return CommonResult.success(service.addBatch(model));
    }

	
	@ApiOperation(value = "✓修改方案内菜单", tags = "企业小程序DIY配置")
    @PutMapping(value = "/{id}")
	public CommonResult<MpConfigPlanMenuModel> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpConfigPlanMenuModel entity) {
        MpConfigPlanMenu params = new MpConfigPlanMenu();
        params.setId(id);
        BeanUtil.copyProperties(entity,params);
        return result(service.updatePlanMenu(params),entity,"更改失败!");
	}
	
	@ApiOperation(value = "✓查询方案内菜单明细", tags = "企业小程序DIY配置")
    @GetMapping(value = "/{id}")
	public CommonResult<MpConfigPlanMenu> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
    @ApiOperation(value = "✓获取方案菜单", tags = "企业小程序DIY配置")
    @GetMapping
    public CommonResult<BaseResult<List<MpConfigPlanMenu>>> selectList(MpConfigPlanMenuQuery query) {
        List<MpConfigPlanMenu> planMenuList = service.getMenusByPlanId(query);
        return CommonResult.success(new BaseResult(planMenuList));
    }
    
    @ApiOperation(value = "✓删除方案内菜单", tags = "企业小程序DIY配置")
    @DeleteMapping(value = "/{id}")
	public CommonResult deleteById(@PathVariable Integer id) {
        return result(service.removePlanMenu(id),null,"删除失败!");
	}

    
}
