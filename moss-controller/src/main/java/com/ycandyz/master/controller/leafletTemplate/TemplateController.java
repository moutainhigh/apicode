package com.ycandyz.master.controller.leafletTemplate;

import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateModel;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateResp;
import com.ycandyz.master.entities.leafletTemplate.Template;
import com.ycandyz.master.service.leafletTemplate.impl.TemplateServiceImpl;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板定义表 接口
 * @since 2020-12-18
 */

@Slf4j
@RestController
@RequestMapping("leaflet-template")
@Api(tags = "leafletTemplate-模板定义表")
public class TemplateController extends BaseController<TemplateServiceImpl, Template, TemplateQuery> {

    @ApiOperation(value = "新增")
    @PostMapping
    public CommonResult<TemplateModel> insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody TemplateModel model) {
        return result(service.insert(model), model, "保存失败!");
    }

    @ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
    public CommonResult<TemplateModel> updateById(@PathVariable Long id, @Validated(ValidatorContract.OnUpdate.class) @RequestBody TemplateModel model) {
        model.setId(id);
        return result(service.update(model), model, "更改失败!");
    }

    @ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
    public CommonResult<TemplateResp> getById(@PathVariable Long id) {
        return CommonResult.success(service.getDetailById(id));
    }


    @ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    public CommonResult<BasePageResult<Template>> selectPage(PageModel<Template> page, TemplateQuery query) {
        return CommonResult.success(service.getPage(page, query));
    }

    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
    public CommonResult deleteById(@PathVariable Long id) {
        return CommonResult.success(service.deleteTemplate(id), "操作成功!");
    }


}
