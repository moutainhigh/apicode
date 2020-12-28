package com.ycandyz.master.controller.mall;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.mall.MallCategoryQuery;
import com.ycandyz.master.entities.mall.MallCategory;
import com.ycandyz.master.service.mall.impl.MallCategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * @Description 商城-商品分类 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mall-category")
@Api(tags="mall-商品分类表")
public class MallHomeCategoryController extends BaseController<MallCategoryServiceImpl,MallCategory,MallCategoryQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public CommonResult<MallCategory> insert(MallCategory entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "update/{id}")
	public CommonResult<MallCategory> updateById(@PathVariable Long id,MallCategory entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "select/{id}")
	public CommonResult<MallCategory> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "select/page")
    @SuppressWarnings("unchecked")
    public CommonResult<Page<MallCategory>> selectPage(Page page, MallCategoryQuery query) {
        return CommonResult.success(service.page(page,query));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="shopNo",value="商店编号",required=true,dataType="String"),
            @ApiImplicitParam(name="parentCategoryNo",value="父级分类编号(为空时为一级分类)",dataType="string")
    })
    @ApiOperation(value = "根据商店编号与父级分类编号查询分类")
    @GetMapping(value = "select/shopNoParentCategoryNo")
    @SuppressWarnings("unchecked")
    public CommonResult<List<MallCategory>> selectByLayerShopNo(String shopNo,String parentCategoryNo) {
        return CommonResult.success(service.selectByLayerShopNo(shopNo,parentCategoryNo));
    }

    @ApiImplicitParam(name="shopNo",value="商店编号",required=true,dataType="String")
    @ApiOperation(value = "获取指定店铺的二级分类")
    @GetMapping(value = "select/shopNo")
    @SuppressWarnings("unchecked")
    public CommonResult<List<MallCategory>> selectByShopNo(String shopNo) {
        return CommonResult.success(service.selectByShopNo(shopNo));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public CommonResult<List<MallCategory>> selectList(MallCategoryQuery query) {
        return CommonResult.success(service.list(query));
    }

    @Deprecated
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "delete/{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    @Deprecated
    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
    @ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "deleteBatch")
    public CommonResult deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
    }

}
