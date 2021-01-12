package com.ycandyz.master.controller.mall;

import com.ycandyz.master.domain.response.mall.MallItemRecommendResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import cn.hutool.core.convert.Convert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.entities.mall.MallItemRecommend;
import com.ycandyz.master.domain.model.mall.MallItemRecommendModel;
import com.ycandyz.master.domain.query.mall.MallItemRecommendQuery;
import com.ycandyz.master.service.mall.impl.MallItemRecommendServiceImpl;
import com.ycandyz.master.base.BaseController;

import static com.ycandyz.master.constant.DataConstant.MALL_ITEM_RECOMMEND_IS_RECOMMEND_MAP;
import static com.ycandyz.master.constant.DataConstant.MALL_ITEM_RECOMMEND_WAY_MAP;

/**
 * @author WangWx
 * @version 2.0
 * @Description 商品推荐基础表 接口
 * @since 2021-01-12
 */

@Slf4j
@RestController
@RequestMapping("mall-item-recommend")
@Api(tags = "mall-商品推荐基础表")
public class MallItemRecommendController extends BaseController<MallItemRecommendServiceImpl, MallItemRecommend, MallItemRecommendQuery> {

    @ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{item_recommend_no}")
    public CommonResult<MallItemRecommendModel> updateById(@PathVariable("item_recommend_no") String itemRecommendNo, @Validated(ValidatorContract.OnUpdate.class) @RequestBody MallItemRecommendModel model) {
        model.setItemRecommendNo(itemRecommendNo);
        service.updateRecommend(model);
        return result(true, model, "更改失败!");
    }

    @ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{item_recommend_no}")
    public CommonResult<MallItemRecommendResp> getById(@PathVariable("item_recommend_no") String itemRecommendNo) {
        return CommonResult.success(service.getOne(itemRecommendNo));
    }

    @ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallItemRecommendResp>> selectPage(PageModel<MallItemRecommend> page, MallItemRecommendQuery query) {
        return CommonResult.success(service.selectPage(new Page<>(page.getPage(), page.getPageSize()), query));
    }

    @ApiOperation(value = "查询全部")
    @GetMapping
    public CommonResult<BaseResult<List<MallItemRecommend>>> selectList(MallItemRecommendQuery query) {
        return CommonResult.success(new BaseResult<List<MallItemRecommend>>(service.list(query)));
    }

    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
    public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id), null, "删除失败!");
    }

    @ApiImplicitParam(name = "ids", value = "ID集合(1,2,3)", required = true, allowMultiple = true, dataType = "int")
    @ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "delete")
    public CommonResult deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)), null, "删除失败!");
    }

}
