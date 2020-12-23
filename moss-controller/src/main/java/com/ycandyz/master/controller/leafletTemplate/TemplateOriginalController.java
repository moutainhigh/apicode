package com.ycandyz.master.controller.leafletTemplate;

import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateOriginalQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateOriginalResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateOriginal;
import com.ycandyz.master.service.leafletTemplate.impl.TemplateOriginalServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 模板内容表 接口
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Slf4j
@RestController
@RequestMapping("leaflet-template/original")
@Api(tags="leafletTemplate-默认模板表")
public class TemplateOriginalController extends BaseController<TemplateOriginalServiceImpl,TemplateOriginal,TemplateOriginalQuery> {

	@ApiOperation(value = "查询根据类型")
    @GetMapping(value = "{type}")
	public CommonResult<TemplateOriginalResp> getByType(@PathVariable Integer type) {
        return CommonResult.success(service.getByType(type));
    }
    
}
