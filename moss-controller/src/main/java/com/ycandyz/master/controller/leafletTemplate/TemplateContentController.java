package com.ycandyz.master.controller.leafletTemplate;

import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateContentQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateContentResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateContent;
import com.ycandyz.master.service.leafletTemplate.impl.TemplateContentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板内容表 接口
 * @since 2020-12-18
 */

@Slf4j
@RestController
@RequestMapping("leaflet-template/content")
@Api(tags = "leafletTemplate-模板内容表")
public class TemplateContentController extends BaseController<TemplateContentServiceImpl, TemplateContent, TemplateContentQuery> {

    @ApiOperation(value = "查询动态列表内容分页")
    @GetMapping(value = "")
    public CommonResult<BasePageResult<TemplateContentResp>> selectPage(PageModel page, TemplateContentQuery contentQuery) {
        return CommonResult.success(new BasePageResult<>(service.getContentPage(page, contentQuery)));
    }

    @PostMapping("/export")
    public CommonResult<String> exportEXT(@RequestBody TemplateContentQuery contentQuery) {
        return CommonResult.success(service.exportContent(contentQuery));
    }

}
