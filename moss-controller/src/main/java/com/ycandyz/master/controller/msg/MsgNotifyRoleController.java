package com.ycandyz.master.controller.msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;

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
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.entities.msg.MsgNotifyRole;
import com.ycandyz.master.domain.query.msg.MsgNotifyRoleQuery;
import com.ycandyz.master.service.msg.impl.MsgNotifyRoleServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 业务通知权限表 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("msg-notify-role")
@Api(tags="msg-业务通知权限表")
public class MsgNotifyRoleController extends BaseController<MsgNotifyRoleServiceImpl,MsgNotifyRole,MsgNotifyRoleQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<MsgNotifyRole> insert(@Validated(ValidatorContract.OnCreate.class) MsgNotifyRole entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<MsgNotifyRole> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MsgNotifyRole entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MsgNotifyRole> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MsgNotifyRole>> selectPage(PageModel page, MsgNotifyRoleQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPage(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public CommonResult<BaseResult<List<MsgNotifyRole>>> selectList(MsgNotifyRoleQuery query) {
        return CommonResult.success(new BaseResult<List<MsgNotifyRole>>(service.list(query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    
}
