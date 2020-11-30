package com.ycandyz.master.controller.mall;

import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.model.mall.MallItemVideoModel;
import com.ycandyz.master.domain.query.ad.SpecailItemQuery;
import com.ycandyz.master.entities.mall.MallItem;
import io.swagger.annotations.*;
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

import javax.validation.constraints.NotNull;

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
@RequestMapping("mall/item/video")
@Api(tags="mall-商品视频信息")
public class MallItemVideoController extends BaseController<MallItemVideoServiceImpl,MallItemVideo,MallItemVideoQuery> {

	@ApiOperation(value="上传视频/缩略图")
    @PostMapping
    public CommonResult<MallItemVideo> insert(@ApiParam(name="videoFile",value="视频文件") @RequestParam(required = false) MultipartFile videoFile, @ApiParam(name="imgFile",value="缩略图") MultipartFile imgFile) {
        MallItemVideo entity = new MallItemVideo();
	    return result(service.upload(entity,videoFile,imgFile),entity,"上传失败!");
    }

    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "视频ID",required = true, dataType = "int"),
            @ApiImplicitParam(name = "status", value = "审核状态(0通过,1不通过)",required = true, dataType = "int"),
            @ApiImplicitParam(name = "remark", value = "拒绝通过原因", dataType = "string")
    })
    @ApiOperation(value = "视频审核")
    @PutMapping(value = "audit/{id}")
    public CommonResult<String> auditById(@PathVariable Long id, Integer status, String remark) {
        return result(service.audit(id,status,remark),"审核成功","审核失败!");
    }*/
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MallItemVideo> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MallItemVideo>> selectPage(PageModel page, MallItemVideoQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPage(),page.getPageSize()),query)));
    }

    @ApiImplicitParam(name="itemNo",value="商品编号",required=true,dataType="string")
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
