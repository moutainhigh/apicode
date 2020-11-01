package com.ycandyz.master.controller.ad;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.ad.SpecialQuery;
import com.ycandyz.master.dto.ad.SpecialDTO;
import com.ycandyz.master.entities.ad.Special;
import com.ycandyz.master.model.ad.SpecialModel;
import com.ycandyz.master.service.ad.impl.SpecialServiceImpl;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * @Description 首页-专题 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-14
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("special")
@Api(tags="ad-专题")
public class SpecialController extends BaseController<SpecialServiceImpl,Special,SpecialQuery> {
	
	@ApiOperation(value="专题新增")
    @PostMapping(value = "insert")
	public CommonResult<SpecialModel> insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody SpecialModel entity) {
        return result(service.insert(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "update/{id}")
	public CommonResult<SpecialModel> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) @RequestBody SpecialModel entity) {
        entity.setId(id);
        return result(service.update(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "select/{id}")
	public CommonResult<SpecialDTO> getById(@PathVariable Long id) {
	    return CommonResult.success(service.selectById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "select/page")
    @SuppressWarnings("unchecked")
    public CommonResult<Page<Special>> selectPage(Page page, SpecialQuery query) {
        return CommonResult.success(service.page(page,query));
    }

    @ApiOperation(value = "首页-查询分页")
    @GetMapping(value = "select/home/page")
    @SuppressWarnings("unchecked")
    public CommonResult<Page<Special>> selectHomePage(Page page, SpecialQuery query) {
        return CommonResult.success(service.selectHomePage(page,query));
    }

    @ApiOperation(value = "查询首页专题")
    @GetMapping(value = "select/home/list")
    @SuppressWarnings("unchecked")
    public CommonResult<List<Special>> selectHomeList(SpecialQuery query) {
        return CommonResult.success(service.selectHomeList(query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public CommonResult<List<Special>> selectList(SpecialQuery query) {
        return CommonResult.success(service.list(query));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "delete/{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
    @ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "deleteBatch")
    public CommonResult deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
    }
}
