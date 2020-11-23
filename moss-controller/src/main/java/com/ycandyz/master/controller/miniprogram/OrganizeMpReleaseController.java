package com.ycandyz.master.controller.miniprogram;

import com.ycandyz.master.model.miniprogram.OrganizeMpReleaseVO;
import com.ycandyz.master.service.miniprogram.IOrganizeMpReleaseService;
import com.ycandyz.master.service.miniprogram.MpChooseStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @ApiOperation(value = "查询全部发布记录", tags = "企业小程序DIY配置")
    @GetMapping
    public CommonResult<List<OrganizeMpReleaseVO>> list() {
        List<OrganizeMpReleaseVO> list = organizeMpReleaseService.listAll();
        return CommonResult.success(list);
    }

}
