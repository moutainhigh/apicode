package com.ycandyz.master.controller.miniprogram;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.model.miniprogram.OrganizeMpReleaseVO;
import com.ycandyz.master.service.miniprogram.IOrganizeMpReleaseService;
import com.ycandyz.master.service.miniprogram.MpChooseStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ycandyz.master.api.CommonResult;
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

    @Autowired
    private MpChooseStyleService mpChooseStyleService;

    @Autowired
    private IOrganizeMpReleaseService organizeMpReleaseService;

//    @ApiOperation(value = "其实页面" , tags = "企业小程序DIY配置",httpMethod = "GET")
//    @GetMapping
//    //0 跳转选择模版
//    //1 跳转
//    public CommonResult<Integer> get() {
//        Integer r = mpChooseStyleService.get();
//        return CommonResult.success(r);
//    }

    @ApiOperation(value = "查询企业小程序全部发布记录", tags = "企业小程序DIY配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page_size",value="每页显示条数",dataType="Long"),
            @ApiImplicitParam(name="page",value="当前页",dataType="Long")
    })
    @GetMapping
    public CommonResult<BasePageResult<Page<OrganizeMpReleaseVO>>> list(@RequestParam(value = "page_size",defaultValue = "10",required = false) Long page_size,
                                                                        @RequestParam(value = "page",defaultValue = "1",required = false) Long page) {
        Page<OrganizeMpReleaseVO> list = organizeMpReleaseService.listAll(page_size,page);
        return CommonResult.success(new BasePageResult<>(list));
    }

}
