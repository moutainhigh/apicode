package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.domain.response.miniprogram.MpConfigMenuResp;
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
import com.ycandyz.master.entities.miniprogram.MpConfigPlan;
import com.ycandyz.master.domain.query.miniprogram.MpConfigPlanQuery;
import com.ycandyz.master.service.miniprogram.impl.MpConfigPlanServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 小程序配置方案 接口类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("miniprogram/config")
@Api(tags="miniprogram-小程序配置方案")
public class MpConfigPlanController extends BaseController<MpConfigPlanServiceImpl,MpConfigPlan,MpConfigPlanQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "plans")
	public ReturnResponse<MpConfigMenuResp> insert() {
        return returnResponse(true,service.add(),"保存成功!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "/plans/{id}")
	public ReturnResponse<MpConfigPlan> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpConfigPlan entity) {
        entity.setId(id);
        return returnResponse(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "/plans/{id}")
	public ReturnResponse<MpConfigPlan> getById(@PathVariable Integer id) {
        return ReturnResponse.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "/plans/page")
    @SuppressWarnings("unchecked")
    public ReturnResponse<Page<MpConfigPlan>> selectPage(Page page, MpConfigPlanQuery query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "/plans")
    public ReturnResponse<List<MpConfigPlan>> selectList(MpConfigPlanQuery query) {
        return ReturnResponse.success(service.list(query));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "/plans/{id}")
	public ReturnResponse<MpConfigPlan> deleteById(@PathVariable Long id) {
        return returnResponse(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "/plans/deleteBatch")
	public ReturnResponse<MpConfigPlan> deleteBatch(String ids) {
        return returnResponse(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
