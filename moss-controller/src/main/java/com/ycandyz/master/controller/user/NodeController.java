package com.ycandyz.master.controller.user;

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
import com.ycandyz.master.entities.user.Node;
import com.ycandyz.master.domain.query.user.NodeQuery;
import com.ycandyz.master.service.user.impl.NodeServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 权限节点表 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-01
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("node")
@Api(tags="user-权限节点表")
public class NodeController extends BaseController<NodeServiceImpl,Node,NodeQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public ReturnResponse<Object> insert(@Validated(ValidatorContract.OnCreate.class) Node entity) {
        return ReturnResponse.success("保存成功!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "update/{id}")
	public ReturnResponse<Object> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) Node entity) {
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
    public ReturnResponse<Page<Object>> selectPage(Page page, NodeQuery query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public ReturnResponse<Object> selectList(NodeQuery query) {
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
