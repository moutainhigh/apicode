package com.ycandyz.master.controller.organize;

import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.query.mall.MallShopQuery;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.service.mall.MallAfterSalesService;
import com.ycandyz.master.service.mall.impl.MallShopServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ycandyz.master.entities.organize.OrganizeGroup;
import com.ycandyz.master.domain.query.organize.OrganizeGroupQuery;
import com.ycandyz.master.service.organize.impl.OrganizeGroupServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 集团表 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("organize-group")
@Api(tags="organize-集团表")
public class OrganizeGroupController extends BaseController<OrganizeGroupServiceImpl,OrganizeGroup,OrganizeGroupQuery> {

    @Autowired
    private MallShopServiceImpl mallShopService;
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public ReturnResponse<Object> insert(@Validated(ValidatorContract.OnCreate.class) OrganizeGroup entity) {
        return ReturnResponse.success("保存成功!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "update/{id}")
	public ReturnResponse<Object> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) OrganizeGroup entity) {
        entity.setId(id);
        return ReturnResponse.success("更改成功!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "select/{id}")
	public ReturnResponse<Object> getById(@PathVariable Long id) {
        return ReturnResponse.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "select/page")
    @SuppressWarnings("unchecked")
    public ReturnResponse<Page<Object>> selectPage(Page page, OrganizeGroupQuery query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public ReturnResponse<Object> selectList(OrganizeGroupQuery query) {
        return ReturnResponse.success(service.list(query));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "delete/{id}")
	public ReturnResponse<Object> deleteById(@PathVariable Long id) {
        return ReturnResponse.success("删除成功!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "deleteBatch")
	public ReturnResponse<Object> deleteBatch(String ids) {
        return ReturnResponse.success("删除成功!");
	}

    @ApiOperation(value = "查询分页")
    @GetMapping(value = "")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallShop>> selectPage(PageModel<MallShop> page, MallShopQuery query) {
        return CommonResult.success(new BasePageResult<>(service.page(new Page<>(page.getPage(),page.getPageSize()),query)));
    }
    
}
