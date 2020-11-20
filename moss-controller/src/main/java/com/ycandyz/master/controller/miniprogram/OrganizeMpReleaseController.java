package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.model.miniprogram.OrganizeMpReleaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import cn.hutool.core.convert.Convert;

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
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.ycandyz.master.domain.query.miniprogram.OrganizeMpReleaseQuery;
import com.ycandyz.master.service.miniprogram.impl.OrganizeMpReleaseServiceImpl;
import com.ycandyz.master.controller.base.BaseController;

/**
 * <p>
 * @Description 企业小程序发布记录 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("cms/mp/release")
@Api(tags="小程序配置-企业小程序发布记录")
public class OrganizeMpReleaseController extends BaseController<OrganizeMpReleaseServiceImpl,OrganizeMpRelease,OrganizeMpReleaseQuery> {
	
//	@ApiOperation(value="新增发布记录", tags = "企业小程序DIY配置")
//    @PostMapping
//	public CommonResult<OrganizeMpRelease> insert(@Validated(ValidatorContract.OnCreate.class) OrganizeMpReleaseVO entity) {
//        return result(service.save(entity),entity,"保存失败!");
//	}
	

    @ApiOperation(value = "查询全部发布记录", tags = "企业小程序DIY配置")
    @GetMapping
    public CommonResult<List<OrganizeMpReleaseVO>> list() {
	    List<OrganizeMpReleaseVO> list = new ArrayList<>();
        return CommonResult.success(list);
    }

}
