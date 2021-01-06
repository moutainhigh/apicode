package com.ycandyz.master.controller.mall;

import com.ycandyz.master.service.mall.impl.MallShopServiceImpl;
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
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.domain.model.mall.MallShopModel;
import com.ycandyz.master.domain.query.mall.MallShopQuery;
import com.ycandyz.master.base.BaseController;

/**
 * <p>
 * @Description 商城 - 店铺表 接口
 * </p>
 *
 * @author SanGang
 * @since 2021-01-06
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mall/shop")
@Api(tags="mall-商城-店铺表")
public class MallShopController extends BaseController<MallShopServiceImpl,MallShop,MallShopQuery> {

    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallShop>> selectPage(PageModel<MallShop> page, MallShopQuery query) {
        return CommonResult.success(new BasePageResult<>(service.page(new Page<>(page.getPage(),page.getPageSize()),query)));
    }

    
}
