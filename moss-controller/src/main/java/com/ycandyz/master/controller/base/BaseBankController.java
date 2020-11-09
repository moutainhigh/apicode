package com.ycandyz.master.controller.base;

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
import com.ycandyz.master.entities.base.BaseBank;
import com.ycandyz.master.domain.query.base.BaseBankQuery;
import com.ycandyz.master.service.base.impl.BaseBankServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description base-银行 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("base-bank")
@Api(tags="base-银行")
public class BaseBankController extends BaseController<BaseBankServiceImpl,BaseBank,BaseBankQuery> {
	
	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public ReturnResponse<BaseBank> insert(@Validated(ValidatorContract.OnCreate.class) BaseBank entity) {
        return returnResponse(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public ReturnResponse<BaseBank> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) BaseBank entity) {
        entity.setId(id);
        return returnResponse(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public ReturnResponse<BaseBank> getById(@PathVariable Long id) {
        return ReturnResponse.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public ReturnResponse<Page<BaseBank>> selectPage(Page page, BaseBankQuery query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public ReturnResponse<Object> selectList(BaseBankQuery query) {
        return ReturnResponse.success(service.list(query));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public ReturnResponse<BaseBank> deleteById(@PathVariable Long id) {
        return returnResponse(service.removeById(id),null,"删除失败!");
	}
    
}
