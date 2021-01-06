package com.ycandyz.master.controller.organize;

import com.ycandyz.master.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.entities.organize.OrganizeGroup;
import com.ycandyz.master.domain.query.organize.OrganizeGroupQuery;
import com.ycandyz.master.service.organize.impl.OrganizeGroupServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * @author SanGang
 * @version 2.0
 * @Description 集团表 接口类
 * @since 2020-10-23
 */

@Slf4j
@RestController
@RequestMapping("organize-group")
@Api(tags = "organize-集团表")
public class OrganizeGroupController extends BaseController<OrganizeGroupServiceImpl, OrganizeGroup, OrganizeGroupQuery> {

    @ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
    public CommonResult<OrganizeGroup> updateById(@PathVariable Long id, @Validated(ValidatorContract.OnUpdate.class) @RequestBody OrganizeGroup entity) {
        entity.setId(id);
        return result(service.update(entity),entity,"更改失败！");
    }

    @ApiOperation(value = "查询根据集团ID")
    @GetMapping(value = "{organizeId}")
    public CommonResult<OrganizeGroup> getByOrganizeId(@PathVariable Long organizeId) {
        return CommonResult.success(service.getByOrganizeId(organizeId));
    }
}
