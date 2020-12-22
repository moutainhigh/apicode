package com.ycandyz.master.controller.leafletTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.entities.leafletTemplate.TemplateContent;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateContentModel;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateContentQuery;
import com.ycandyz.master.service.leafletTemplate.impl.TemplateContentServiceImpl;
import com.ycandyz.master.base.BaseController;

/**
 * <p>
 * @Description 模板内容表 接口
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("leaflet-template/content")
@Api(tags="leafletTemplate-模板内容表")
public class TemplateContentController extends BaseController<TemplateContentServiceImpl,TemplateContent,TemplateContentQuery> {

	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{templateId}")
	public CommonResult<TemplateContent> getById(@PathVariable Long templateId) {
        return CommonResult.success(service.getById(templateId));
    }

}
