package com.ycandyz.master.controller.leafletTemplate;

import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateDetailQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateTableResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateDetail;
import com.ycandyz.master.service.leafletTemplate.impl.TemplateDetailServiceImpl;
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
 * @Description 模板明细表 接口
 * @since 2020-12-18
 */

@Slf4j
@RestController
@RequestMapping("leaflet-template/component")
@Api(tags = "leafletTemplate-模板明细表")
public class TemplateDetailController extends BaseController<TemplateDetailServiceImpl, TemplateDetail, TemplateDetailQuery> {



}
