package com.ycandyz.master.controller.miniprogram;

import cn.hutool.core.bean.BeanUtil;
import com.ycandyz.master.api.*;
import com.ycandyz.master.domain.model.miniprogram.MpTransferApplyModel;
import com.ycandyz.master.domain.model.miniprogram.MpTransferApplyUpdateModel;
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
@RequestMapping("cms/mp/applies")
@Api(tags="小程序配置-小程序转交接申请")
public class MpTransferApplyController extends BaseController<MpTransferApplyServiceImpl,MpTransferApply,MpTransferApplyQuery> {

	@ApiOperation(value="✓创建转交接申请", tags = "企业小程序DIY配置")
    @PostMapping
	public CommonResult<MpTransferApplyModel> create(@Validated(ValidatorContract.OnCreate.class) @RequestBody MpTransferApplyModel entity) {
        return result(service.add(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "✓修改转交接申请", tags = "企业小程序DIY配置")
    @PutMapping(value = "{id}")
	public CommonResult<MpTransferApply> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class)  @RequestBody MpTransferApplyUpdateModel entity) {
        MpTransferApply updateParams = new MpTransferApply();
        updateParams.setId(id);
        BeanUtil.copyProperties(entity,updateParams);
        return result(service.updateTransferApply(updateParams),updateParams,"更改失败!");
	}
	
	@ApiOperation(value = "✓查询转交接申请详情", tags = "企业小程序DIY配置")
    @GetMapping(value = "{id}")
	public CommonResult<MpTransferApply> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "✓查询分页转交接申请", tags = "企业小程序DIY配置")
    @GetMapping
    public CommonResult<BasePageResult<MpTransferApply>> selectPage(PageModel page, MpTransferApplyQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPage(),page.getPageSize()),query)));
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
