package com.ycandyz.master.controller.mall;

import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.model.mall.MallItemVideoModel;
import com.ycandyz.master.domain.query.ad.SpecailItemQuery;
import com.ycandyz.master.entities.mall.MallItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.domain.query.mall.MallItemVideoQuery;
import com.ycandyz.master.service.mall.impl.MallItemVideoServiceImpl;
import com.ycandyz.master.controller.base.BaseController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * @Description 商品视频信息 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-12
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mall-item-video")
@Api(tags="mall-商品视频信息")
public class MallItemVideoController extends BaseController<MallItemVideoServiceImpl,MallItemVideo,MallItemVideoQuery> {
	
	@ApiOperation(value="上传视频")
    @PostMapping
    public CommonResult<MallItemVideo> insert(@Validated(ValidatorContract.OnCreate.class) MallItemVideoModel model, @ApiParam(name="videoFile",value="视频文件") @RequestParam(required = false) MultipartFile videoFile, @ApiParam(name="imgFile",value="缩略图") MultipartFile imgFile) {
        MallItemVideo entity = new MallItemVideo();
        BeanUtils.copyProperties(model,entity);
	    return result(service.insert(entity,videoFile,imgFile),entity,"上传失败!");
    }
	
	@ApiOperation(value = "通过视频ID更新视频/缩略图")
    @PostMapping(value = "{id}")
	public CommonResult<MallItemVideo> editById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) MallItemVideoModel model, @ApiParam(name="videoFile",value="视频文件") MultipartFile videoFile, @ApiParam(name="imgFile",value="缩略图") MultipartFile imgFile) {
        MallItemVideo entity = new MallItemVideo();
        BeanUtils.copyProperties(model,entity);
        entity.setId(id);
        return result(service.update(entity,videoFile,imgFile),entity,"更新失败!");
	}

    @ApiOperation(value = "视频审核")
    @PutMapping(value = "audit/{id}")
    public CommonResult<MallItemVideo> rejectById(@PathVariable Long id,MallItemVideoModel model) {
        MallItemVideo entity = new MallItemVideo();
        BeanUtils.copyProperties(model,entity);
        entity.setId(id);
        return result(service.updateById(entity),entity,"更新失败!");
    }
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MallItemVideo> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallItemVideo>> selectPage(PageModel page, MallItemVideoQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }

    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public CommonResult<BaseResult<List<MallItemVideo>>> selectList(MallItemVideoQuery query) {
        return CommonResult.success(new BaseResult(service.list(query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}
    
}
