package com.ycandyz.master.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;
import com.ycandyz.master.api.ReturnResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.domain.query.mall.MallItemVideoQuery;
import com.ycandyz.master.service.mall.impl.MallItemVideoServiceImpl;
import com.ycandyz.master.controller.base.BaseController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * @Description 商品视频信息 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mall-item-video")
@Api(tags="mall-商品视频信息")
public class MallItemVideoController extends BaseController<MallItemVideoServiceImpl,MallItemVideo,MallItemVideoQuery> {
	
	@ApiOperation(value="视频上传")
    @PostMapping(value = "insert")
	public ReturnResponse<MallItemVideo> insert(@Validated(ValidatorContract.OnCreate.class) MallItemVideo entity,@ApiParam(name="file",value="视频文件") @RequestParam(required = false) MultipartFile file) {
        return returnResponse(service.insert(entity,file),entity,"上传失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public ReturnResponse<MallItemVideo> getById(@PathVariable Long id) {
        return ReturnResponse.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public ReturnResponse<Page<MallItemVideo>> selectPage(Page page, MallItemVideoQuery query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public ReturnResponse<List<MallItemVideo>> selectList(MallItemVideoQuery query) {
        return ReturnResponse.success(service.list(query));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public ReturnResponse<MallItemVideo> deleteById(@PathVariable Long id) {
        return returnResponse(service.removeById(id),null,"删除失败!");
	}
    
}
