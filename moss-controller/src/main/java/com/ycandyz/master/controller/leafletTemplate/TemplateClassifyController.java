package com.ycandyz.master.controller.leafletTemplate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateClassifyQuery;
import com.ycandyz.master.entities.leafletTemplate.TemplateClassify;
import com.ycandyz.master.service.leafletTemplate.impl.TemplateClassifyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 模板分类表接口
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Slf4j
@RestController
@RequestMapping("leaflet-template/classify")
@Api(tags="模板分类表")
public class TemplateClassifyController extends BaseController<TemplateClassifyServiceImpl,TemplateClassify,TemplateClassifyQuery> {

    @ApiOperation(value = "查询模板类别分页")
    @GetMapping(value = "")
    public CommonResult<BasePageResult<TemplateClassify>> selectPage(PageModel<TemplateClassify> page, TemplateClassifyQuery query) {
        //查询未删除的模板分类
        query.setStatus(0);
        return CommonResult.success(new BasePageResult<>(service.page(new Page<>(page.getPage(),page.getPageSize()),query)));
    }

}
