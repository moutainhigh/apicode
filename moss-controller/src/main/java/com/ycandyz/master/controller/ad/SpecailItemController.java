package com.ycandyz.master.controller.ad;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.ad.SpecailItemQuery;
import com.ycandyz.master.entities.ad.SpecailItem;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.service.ad.impl.SpecailItemServiceImpl;
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
 * @Description 专题-商品表 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("specail-item")
@Api(tags="ad-专题-商品表")
public class SpecailItemController extends BaseController<SpecailItemServiceImpl,SpecailItem,SpecailItemQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public CommonResult<SpecailItem> insert(@Validated(ValidatorContract.OnCreate.class) SpecailItem entity) {
        return result(service.save(entity),entity,"保存失败!");
	}

	@ApiOperation(value = "查询分页")
    @GetMapping(value = "select/page")
    @SuppressWarnings("unchecked")
    public CommonResult<Page<SpecailItem>> selectPage(Page page, SpecailItemQuery query) {
        return CommonResult.success(service.page(page,query));
    }

    @ApiOperation(value = "专题商品-查询分页")
    @GetMapping(value = "select/hoem/page")
    @SuppressWarnings("unchecked")
    public CommonResult<Page<MallItem>> selectHomePage(Page page, SpecailItemQuery query) {
        return CommonResult.success(service.selectHomePage(page,query));
    }

    @ApiOperation(value = "专题商品-查询全部")
    @GetMapping(value = "select/list")
    public CommonResult<List<MallItem>> selectList(SpecailItemQuery query) {
        return CommonResult.success(service.selectList(query));
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
