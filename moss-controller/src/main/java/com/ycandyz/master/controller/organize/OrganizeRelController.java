package com.ycandyz.master.controller.organize;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.entities.organize.OrganizeRel;
import com.ycandyz.master.domain.query.organize.OrganizeRelQuery;
import com.ycandyz.master.service.organize.impl.OrganizeRelServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description  接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("organize-rel")
@Api(tags="organize-")
public class OrganizeRelController extends BaseController<OrganizeRelServiceImpl,OrganizeRel,OrganizeRelQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public ReturnResponse<Object> insert(@Validated(ValidatorContract.OnCreate.class) OrganizeRel entity) {
        return ReturnResponse.success("保存成功!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "update/{id}")
	public ReturnResponse<Object> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) OrganizeRel entity) {
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
    public ReturnResponse<Page<Object>> selectPage(Page page, OrganizeRelQuery query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public ReturnResponse<Object> selectList(OrganizeRelQuery query) {
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
    
}
