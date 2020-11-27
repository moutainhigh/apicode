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
import com.ycandyz.master.entities.msg.MsgNotifyRelation;
import com.ycandyz.master.domain.query.msg.MsgNotifyRelationQuery;
import com.ycandyz.master.service.msg.impl.MsgNotifyRelationServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 业务通知关系表 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("msg-notify-relation")
@Api(tags="msg-业务通知关系表")
public class MsgNotifyRelationController extends BaseController<MsgNotifyRelationServiceImpl,MsgNotifyRelation,MsgNotifyRelationQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<MsgNotifyRelation> insert(@Validated(ValidatorContract.OnCreate.class) MsgNotifyRelation entity) {
        return result(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<MsgNotifyRelation> updateById(@PathVariable Integer id,@Validated(ValidatorContract.OnUpdate.class) MsgNotifyRelation entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<MsgNotifyRelation> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<MsgNotifyRelation>> selectPage(PageModel page, MsgNotifyRelationQuery query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPage(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public CommonResult<BaseResult<List<MsgNotifyRelation>>> selectList(MsgNotifyRelationQuery query) {
        return CommonResult.success(new BaseResult<List<MsgNotifyRelation>>(service.list(query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    
}
