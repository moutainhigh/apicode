package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.model.mall.*;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallItemPageResp;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.domain.response.mall.MallItemShareResp;
import com.ycandyz.master.domain.response.mall.SpreadMallItemPageResp;
import com.ycandyz.master.entities.base.BaseBank;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.service.mall.impl.MallItemServiceImpl;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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

@ApiVersion(group = {ApiVersionConstant.API_MALL_ITEM})
@Slf4j
@RestController
@RequestMapping("pms/product")
@Api(tags="集团供货")
public class MallItemController extends BaseController<MallItemServiceImpl,MallItem, MallItemBaseQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping()
	public CommonResult insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody MallItemModel entity) {
        return service.insert(entity);
	}

    @ApiVersion(group = {ApiVersionConstant.API_MALL_ITEM_100})
    @ApiOperation(tags = "集团供货",value="集团供货-新增")
    @PostMapping(value = "organize")
    public CommonResult insertOrg(@Validated(ValidatorContract.OnCreate.class) @RequestBody MallItemOrgModel model) {
        return result(service.addOrganize(model),model,"更改失败!");
    }

    @ApiOperation(value = "通过ID")
    @GetMapping(value = "select/{id}")
    public CommonResult<MallItem> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{item_no}")
	public CommonResult updateById(@PathVariable String item_no,@Validated(ValidatorContract.OnUpdate.class) @RequestBody MallItemModel entity) {
        entity.setItemNo(item_no);
        return service.update(entity);
	}

    @ApiOperation(value = "上架/下架商品")
    @PutMapping(value = "shelf")
    public CommonResult shelfById(@Validated(ValidatorContract.OnUpdate.class) @RequestBody MallItemShelfModel entity) {
        return service.shelf(entity);
    }
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{item_no}")
	public CommonResult<MallItemResp> getById(@PathVariable String item_no) {
        return CommonResult.success(service.getByItemNo(item_no));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping()
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallItemPageResp>> selectPage(PageModel page, MallItemQuery query) {
        return CommonResult.success(new BasePageResult(service.getMallItemPage(new Page(page.getPage(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{item_no}")
	public CommonResult deleteById(@PathVariable String item_no) {
        return service.deleteByItemNo(item_no);
	}

    @ApiVersion(group = {ApiVersionConstant.API_MALL_ITEM_100})
    @ApiOperation(tags = "集团供货",value = "更新商品分销")
    @PutMapping(value = "share/{item_no}")
    public CommonResult updateShareById(@PathVariable String item_no,@Validated(ValidatorContract.OnUpdate.class) @RequestBody MallItemShareModel model) {
        model.setItemNo(item_no);
        return result(service.updateShareByItemNo(model),model,"更改失败!");
    }

    @ApiVersion(group = {ApiVersionConstant.API_MALL_ITEM_100})
    @ApiOperation(tags = "集团供货",value = "商品分销信息")
    @GetMapping(value = "share/{item_no}")
    public CommonResult<MallItemShareResp> getShareById(@PathVariable String item_no) {
        return CommonResult.success(service.getShareByItemNo(item_no));
    }


    @ApiOperation(value = "传播查询分页")
    @GetMapping("/spread")
    public CommonResult<BasePageResult<SpreadMallItemPageResp>> selectSpreadPage(PageModel page, MallItemQuery query) {
        return CommonResult.success(new BasePageResult(service.selectSpreadPage(new Page(page.getPage(), page.getPageSize()), query)));
    }
    @ApiVersion(group = {ApiVersionConstant.API_MALL_ITEM_100})
    @ApiOperation(tags = "集团供货",value = "根据商品编号更改商品分类")
    @PutMapping("{item_no}/category")
    public CommonResult edit(@PathVariable String item_no,@Validated(ValidatorContract.OnUpdate.class) @RequestBody MallItemDetailModel model){
        model.setItemNo(item_no);
        return result(service.edit(model),null,"更改失败!");
    }


}
