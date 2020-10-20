package com.ycandyz.master.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.controller.base.BaseController;
import com.ycandyz.master.domain.query.user.UserQuery;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.service.user.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * @Description 用户表 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("user")
@Api(tags="user-用户表")
public class UserController extends BaseController<UserServiceImpl,User,UserQuery> {

	@ApiOperation(value="新增")
    @PostMapping(value = "insert")
	public CommonResult<User> insert(User entity) {
        return result(service.save(entity),entity,"保存失败!");
	}

	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "update/{id}")
	public CommonResult<User> updateById(@PathVariable Long id,User entity) {
        entity.setId(id);
        return result(service.updateById(entity),entity,"更改失败!");
	}

	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "select/{id}")
	public CommonResult<User> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }

	@ApiOperation(value = "查询分页")
    @GetMapping(value = "select/page")
    @SuppressWarnings("unchecked")
    public CommonResult<Page<User>> selectPage(Page page, UserQuery query) {
        return CommonResult.success(service.page(page,query));
    }

    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public CommonResult<List<User>> selectList(UserQuery query) {
        return CommonResult.success(service.list(query));
    }

    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "delete/{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

   	@ApiOperation(value = "通过ID数组删除")
    @DeleteMapping(value = "delete")
	public CommonResult delete(Long[] ids) {
        return result(service.deleteByIds(ids),null,"删除失败!");
	}

}
