package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.model.mall.MallItemModel;
import com.ycandyz.master.domain.model.mall.MallItemShelfModel;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.service.mall.impl.MallHomeItemServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * @Description 商品表 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("pms/product")
@Api(tags="mall-商品")
public class MallItemController extends BaseController<MallHomeItemServiceImpl,MallItem, MallItemBaseQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping()
	public CommonResult<MallItemModel> insert(MallItemModel entity) {
        return result(service.insert(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{item_no}")
	public CommonResult<MallItemModel> updateById(@PathVariable String item_no, MallItemModel entity) {
        entity.setItemNo(item_no);
        return result(service.update(entity),entity,"更改失败!");
	}

    @ApiOperation(value = "上架/下架商品")
    @PutMapping(value = "shelf")
    public CommonResult<MallItemShelfModel> shelfById(MallItemShelfModel entity) {
        return result(service.shelf(entity),entity,"操作失败!");
    }
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{item_no}")
	public CommonResult<MallItemResp> getById(@PathVariable String item_no) {
        return CommonResult.success(service.getByItemNo(item_no));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping()
    @SuppressWarnings("unchecked")
    public CommonResult<Page<MallItemResp>> selectPage(Page page, MallItemQuery query) {
        return CommonResult.success(service.getMallItemPage(page,query));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{item_no}")
	public CommonResult deleteById(@PathVariable String item_no) {
        return result(service.deleteByItemNo(item_no),null,"删除失败!");
	}


}
