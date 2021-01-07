package com.ycandyz.master.controller.organize;

<<<<<<< HEAD
import com.ycandyz.master.api.CommonResult;
=======
import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.query.mall.MallShopQuery;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.service.mall.MallAfterSalesService;
import com.ycandyz.master.service.mall.impl.MallShopServiceImpl;
>>>>>>> pos-hsg
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
<<<<<<< HEAD
=======

import java.util.List;
import cn.hutool.core.convert.Convert;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> pos-hsg
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.entities.organize.OrganizeGroup;
import com.ycandyz.master.domain.query.organize.OrganizeGroupQuery;
import com.ycandyz.master.service.organize.impl.OrganizeGroupServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * @author SanGang
 * @version 2.0
 * @Description 集团表 接口类
 * @since 2020-10-23
 */

@Slf4j
@RestController
@RequestMapping("organize-group")
<<<<<<< HEAD
@Api(tags = "organize-集团表")
public class OrganizeGroupController extends BaseController<OrganizeGroupServiceImpl, OrganizeGroup, OrganizeGroupQuery> {

    @ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
    public CommonResult<OrganizeGroup> updateById(@PathVariable Long id, @Validated(ValidatorContract.OnUpdate.class) @RequestBody OrganizeGroup entity) {
=======
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
>>>>>>> pos-hsg
        entity.setId(id);
        return result(service.update(entity),entity,"更改失败！");
    }

<<<<<<< HEAD
    @ApiOperation(value = "查询根据集团ID")
    @GetMapping(value = "{organizeId}")
    public CommonResult<OrganizeGroup> getByOrganizeId(@PathVariable Long organizeId) {
        return CommonResult.success(service.getByOrganizeId(organizeId));
    }
=======
    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "deleteBatch")
	public ReturnResponse<Object> deleteBatch(String ids) {
        return ReturnResponse.success("删除成功!");
	}

    
>>>>>>> pos-hsg
}
