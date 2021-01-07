package com.ycandyz.master.controller.organize;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.config.ApiVersion;
import com.ycandyz.master.config.ApiVersionConstant;
import com.ycandyz.master.domain.query.mall.MallShopQuery;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.service.mall.impl.MallShopServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "organize-集团表")
public class OrganizeGroupController extends BaseController<OrganizeGroupServiceImpl, OrganizeGroup, OrganizeGroupQuery> {

    @Autowired
    private MallShopServiceImpl mallShopService;

    @ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
    public CommonResult<OrganizeGroup> updateById(@PathVariable Long id, @Validated(ValidatorContract.OnUpdate.class) @RequestBody OrganizeGroup entity) {
        entity.setId(id);
        return result(service.update(entity),entity,"更改失败！");
    }

    @ApiOperation(value = "查询根据集团ID")
    @GetMapping(value = "")
    public CommonResult<OrganizeGroup> getByOrganizeId() {
        return CommonResult.success(service.getByOrganizeId());
    }

    @ApiVersion(group = ApiVersionConstant.API_MALL_ITEM_100)
    @ApiImplicitParam(name="item_no",value="商品编号",dataType="String")
    @ApiOperation(value = "集团供货商品已选门店-分页")
    @GetMapping(value = "shop/item")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallShop>> selectPage(PageModel<MallShop> page,@RequestParam(value = "item_no",required = false) String itemNo) {
        return CommonResult.success(new BasePageResult<>(mallShopService.getByItemNo(new Page<>(page.getPage(),page.getPageSize()),itemNo)));
    }

    @ApiVersion(group = ApiVersionConstant.API_MALL_ITEM_100)
    @ApiOperation(value = "集团供货全部门店-分页")
    @GetMapping(value = "shop")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallShop>> selectOrgPage(PageModel<MallShop> page) {
        return CommonResult.success(new BasePageResult<>(mallShopService.getByOrganizeId(new Page<>(page.getPage(),page.getPageSize()))));
    }
}
