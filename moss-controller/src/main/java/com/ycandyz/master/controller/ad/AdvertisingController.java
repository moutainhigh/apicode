package com.ycandyz.master.controller.ad;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.query.ad.AdvertisingQuery;
import com.ycandyz.master.entities.ad.Advertising;
import com.ycandyz.master.service.ad.impl.AdvertisingServiceImpl;
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
 * @Description 轮播图 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */

@ApiVersion(group = ApiVersionConstant.API_ADVERTISING)
@Slf4j
@RestController
@RequestMapping("advertising")
@Api(tags="ad-轮播图")
public class AdvertisingController extends BaseController<AdvertisingServiceImpl,Advertising,AdvertisingQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public CommonResult<Advertising> insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody Advertising entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "update/{id}")
	public CommonResult<Advertising> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) @RequestBody Advertising entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "select/{id}")
	public CommonResult<Advertising> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "select/page")
    @SuppressWarnings("unchecked")
    public CommonResult<Page<Advertising>> selectPage(Page page, AdvertisingQuery query) {
        return CommonResult.success(service.page(page,query));
    }

    @ApiOperation(value = "首页轮播图接口")
    @GetMapping(value = "select/home/list")
    @SuppressWarnings("unchecked")
    public CommonResult<List<Advertising>> selectHomeList(AdvertisingQuery query) {
        return CommonResult.success(service.selectHomeList(query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public CommonResult<List<Advertising>> selectList(AdvertisingQuery query) {
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
