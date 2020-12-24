package com.ycandyz.master.controller.leafletTemplate;

import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateComponentQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateComponentResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateOriginalResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateTableResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateComponent;
import com.ycandyz.master.service.leafletTemplate.impl.TemplateComponentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板组件表 接口
 * @since 2020-12-18
 */

@Slf4j
@RestController
@RequestMapping("leaflet-template/component")
@Api(tags = "leafletTemplate-模板组件表")
public class TemplateComponentController extends BaseController<TemplateComponentServiceImpl, TemplateComponent, TemplateComponentQuery> {

    @ApiOperation(value = "查询全部")
    @GetMapping
    public CommonResult<BaseResult<List<TemplateComponentResp>>> selectList() {
        return CommonResult.success(new BaseResult<>(service.listComponents()));
    }

    @ApiOperation(value = "查询动态列表列")
    @GetMapping(value = "{templateId}")
    public CommonResult<BaseResult<List<TemplateTableResp>>> selectList(@PathVariable Long templateId) {
        return CommonResult.success(new BaseResult<>(service.getTableList(templateId)));
    }
}
