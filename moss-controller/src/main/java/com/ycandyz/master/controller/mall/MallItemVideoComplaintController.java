package com.ycandyz.master.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;
import com.ycandyz.master.api.ReturnResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.entities.mall.MallItemVideoComplaint;
import com.ycandyz.master.domain.query.mall.MallItemVideoComplaintQuery;
import com.ycandyz.master.service.mall.impl.MallItemVideoComplaintServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 商品视频投诉 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mall-item-video-complaint")
@Api(tags="mall-商品视频投诉")
public class MallItemVideoComplaintController extends BaseController<MallItemVideoComplaintServiceImpl,MallItemVideoComplaint,MallItemVideoComplaintQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public ReturnResponse<MallItemVideoComplaint> insert(@Validated(ValidatorContract.OnCreate.class) MallItemVideoComplaint entity) {
        return returnResponse(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public ReturnResponse<MallItemVideoComplaint> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) MallItemVideoComplaint entity) {
        entity.setId(id);
        return returnResponse(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public ReturnResponse<MallItemVideoComplaint> getById(@PathVariable Long id) {
        return ReturnResponse.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public ReturnResponse<Page<MallItemVideoComplaint>> selectPage(Page page, MallItemVideoComplaintQuery query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public ReturnResponse<List<MallItemVideoComplaint>> selectList(MallItemVideoComplaintQuery query) {
        return ReturnResponse.success(service.list(query));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public ReturnResponse<MallItemVideoComplaint> deleteById(@PathVariable Long id) {
        return returnResponse(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "delete")
	public ReturnResponse<MallItemVideoComplaint> deleteBatch(String ids) {
        return returnResponse(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}