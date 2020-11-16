package com.ycandyz.master.controller.mp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import cn.hutool.core.convert.Convert;
import com.ycandyz.master.api.ReturnResponse;
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
import com.ycandyz.master.entities.mp.OrganizeMpConfigPlan;
import com.ycandyz.master.domain.query.mp.OrganizeMpConfigPlanQuery;
import com.ycandyz.master.service.mp.impl.OrganizeMpConfigPlanServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 企业小程序配置方案 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("organize-mp-config-plan")
@Api(tags="mp-企业小程序配置方案")
public class OrganizeMpConfigPlanController extends BaseController<OrganizeMpConfigPlanServiceImpl,OrganizeMpConfigPlan,OrganizeMpConfigPlanQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public ReturnResponse<OrganizeMpConfigPlan> insert(@Validated(ValidatorContract.OnCreate.class) OrganizeMpConfigPlan entity) {
        return returnResponse(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public ReturnResponse<OrganizeMpConfigPlan> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) OrganizeMpConfigPlan entity) {

        return returnResponse(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public ReturnResponse<OrganizeMpConfigPlan> getById(@PathVariable Long id) {
        return ReturnResponse.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public ReturnResponse<Page<OrganizeMpConfigPlan>> selectPage(Page page, OrganizeMpConfigPlanQuery query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public ReturnResponse<OrganizeMpConfigPlan> selectList(OrganizeMpConfigPlanQuery query) {
	    return null;
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public ReturnResponse<OrganizeMpConfigPlan> deleteById(@PathVariable Long id) {
        return returnResponse(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "delete")
	public ReturnResponse<OrganizeMpConfigPlan> deleteBatch(String ids) {
        return returnResponse(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
