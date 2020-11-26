package com.ycandyz.master.controller.miniprogram;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.entities.miniprogram.MpConfigPlan;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanQuery;
import com.ycandyz.master.service.miniprogram.impl.MpConfigPlanServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("cms/mp/plans")
@Api(tags="小程序配置-方案配置")
public class MpConfigPlanController extends BaseController<MpConfigPlanServiceImpl,MpConfigPlan,MpConfigPlanQuery> {
	
	@ApiOperation(value="✓创建方案", tags = "企业小程序DIY配置")
    @PostMapping
	public CommonResult<Boolean> create(@RequestParam String planName) {
	    if(StrUtil.isEmpty(planName)){
            return CommonResult.validateFailed("方案名称不能为空");
        }
	    if(planName.length() > 10){
            return CommonResult.validateFailed("方案名称长度不能大于10个字符");
        }
        return result(service.initPlan(planName),true,"创建失败!");
	}

	
	@ApiOperation(value = "✓更新方案", tags = "企业小程序DIY配置")
    @PutMapping(value = "{id}")
	public CommonResult<MpConfigPlanModel> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) @RequestBody MpConfigPlanModel entity) {
        MpConfigPlan params = new MpConfigPlan();
        params.setId(id);
        BeanUtil.copyProperties(entity,params);
        return result(service.updateById(params),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MpConfigPlan> getById(@PathVariable Integer id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "✓分页查询方案信息", tags = "企业小程序DIY配置")
    @GetMapping
    public CommonResult<BasePageResult<MpConfigPlan>> selectPage(PageModel pageModel, MpConfigPlanQuery query) {

        OrderItem orderItem = OrderItem.desc("create_time");
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        orderItemList.add(orderItem);
        Page page = new Page(pageModel.getPageNum(),pageModel.getPageSize());
        page.setOrders(orderItemList);

	    return CommonResult.success(new BasePageResult(service.page(page,query)));
    }
    
}
