package com.ycandyz.master.controller.ad;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.query.ad.HomeCategoryQuery;
import com.ycandyz.master.entities.ad.HomeCategory;
import com.ycandyz.master.service.ad.impl.HomeCategoryServiceImpl;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * @Description 首页- 轮播图分类表 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */

@ApiVersion(group = ApiVersionConstant.API_ADVERTISING)
@Slf4j
@RestController
@RequestMapping("home-category")
@Api(tags="ad-分类页面")
public class HomeCategoryController extends BaseController<HomeCategoryServiceImpl,HomeCategory,HomeCategoryQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public CommonResult<HomeCategory> insert(@Validated(ValidatorContract.OnCreate.class) HomeCategory entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "update/{id}")
	public CommonResult<HomeCategory> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) HomeCategory entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "select/{id}")
	public CommonResult<HomeCategory> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "select/page")
    @SuppressWarnings("unchecked")
    public CommonResult<Page<HomeCategory>> selectPage(Page page, HomeCategoryQuery query) {
        return CommonResult.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public CommonResult<List<HomeCategory>> selectList(HomeCategoryQuery query) {
        return CommonResult.success(service.list(query));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopNo", value = "商店编号",required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryNo", value = "分类编号(一级传0)", required = true, dataType = "string")
    })
    @ApiOperation(value = "根据分类编号查询子级分类集合,一级传0")
    @GetMapping(value = "select/childList")
    public CommonResult<List<HomeCategory>> getListByParentCategoryNo(HomeCategoryQuery query) {
        return CommonResult.success(service.getListByParentCategoryNo(query));
    }


}
