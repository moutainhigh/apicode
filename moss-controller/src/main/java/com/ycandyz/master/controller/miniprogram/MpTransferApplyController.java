package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import cn.hutool.core.convert.Convert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.entities.miniprogram.MpTransferApply;
import com.ycandyz.master.domain.query.miniprogram.MpTransferApplyQuery;
import com.ycandyz.master.service.miniprogram.impl.MpTransferApplyServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 小程序转交接申请 接口
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-16
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("mini-program/transfer/applies")
@Api(tags="小程序配置-小程序转交接申请")
public class MpTransferApplyController extends BaseController<MpTransferApplyServiceImpl,MpTransferApply,MpTransferApplyQuery> {
<<<<<<< HEAD

    @ApiOperation(value="创建转交接申请", tags = "小程序配置")
=======
	
	@ApiOperation(value="创建转交接申请", tags = "企业小程序DIY配置")
>>>>>>> 1934f1ae32bc19fe7db493a95104d6c58a7fe124
    @PostMapping
	public CommonResult<MpTransferApply> create(@Validated(ValidatorContract.OnCreate.class) MpTransferApply entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "修改转交接申请", tags = "企业小程序DIY配置")
    @PutMapping(value = "{id}")
	public CommonResult<MpTransferApply> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MpTransferApply entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询转交接申请详情", tags = "企业小程序DIY配置")
    @GetMapping(value = "{id}")
	public CommonResult<MpTransferApply> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页转交接申请", tags = "企业小程序DIY配置")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MpTransferApply>> selectPage(PageModel page, MpTransferApplyQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPageNum(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public CommonResult<BaseResult<List<MpTransferApply>>> selectList(MpTransferApplyQuery query) {
        return CommonResult.success(new BaseResult<List<MpTransferApply>>(service.list(query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "delete")
	public CommonResult deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
