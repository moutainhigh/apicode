package com.ycandyz.master.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.entities.mall.MallItemRecommendRelation;
import com.ycandyz.master.domain.model.mall.MallItemRecommendRelationModel;
import com.ycandyz.master.domain.query.mall.MallItemRecommendRelationQuery;
import com.ycandyz.master.service.mall.impl.MallItemRecommendRelationServiceImpl;
import com.ycandyz.master.base.BaseController;

/**
 * @Description 商品推荐关联表 接口
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mall-item-recommend-relation")
@Api(tags="mall-商品推荐关联表")
public class MallItemRecommendRelationController extends BaseController<MallItemRecommendRelationServiceImpl,MallItemRecommendRelation,MallItemRecommendRelationQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<MallItemRecommendRelationModel> insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody MallItemRecommendRelationModel model) {
        return result(service.insert(model),model,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<MallItemRecommendRelationModel> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) @RequestBody MallItemRecommendRelationModel model) {
        model.setId(id);
        return result(service.update(model),model,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MallItemRecommendRelation> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallItemRecommendRelation>> selectPage(PageModel<MallItemRecommendRelation> page, MallItemRecommendRelationQuery query) {
        return CommonResult.success(new BasePageResult<>(service.page(new Page<>(page.getPage(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping
    public CommonResult<BaseResult<List<MallItemRecommendRelation>>> selectList(MallItemRecommendRelationQuery query) {
        return CommonResult.success(new BaseResult<List<MallItemRecommendRelation>>(service.list(query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "delete")
	public CommonResult deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
