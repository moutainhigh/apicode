package com.ycandyz.master.controller.miniprogram;


import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.model.miniprogram.OrganizeChooseMpConfigPage;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import com.ycandyz.master.service.miniprogram.MpChooseStyleService;
import com.ycandyz.master.vo.OrganizeMenuMpRequestVO;
import com.ycandyz.master.vo.OrganizeMpRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("cms/mp/style/")
@Api(tags="小程序配置-选择修改样式")
public class MpChooseStyleController {

    @Autowired
    private MpChooseStyleService mpChooseStyleService;

    @ApiOperation(value = "查询企业小程序全部菜单", tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="查询企业小程序编号",required=true,dataType="Integer")
    })
    @GetMapping(value = "organize/{id}")
    public CommonResult<List<OrganizeMpConfigMenuVO>> select(@PathVariable("id") Integer id) {
        List<OrganizeMpConfigMenuVO> organizeMpConfigMenuVO = mpChooseStyleService.selByOrGanizeMoudleId(id);
        return CommonResult.success(organizeMpConfigMenuVO);
    }

    @ApiOperation(value = "查询企业小程序单个菜单样式", tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuid",value="企业小程序菜单编号",required=true,dataType="Integer")
    })
    @GetMapping(value = "organize/menu/{menuId}")
    public CommonResult<OrganizeChooseMpConfigPage> selectOrgainizeMenu(@PathVariable("menuId") Integer menuId) {
        OrganizeChooseMpConfigPage organizeChooseMpConfigPage = mpChooseStyleService.selectMenuById(menuId);
        return CommonResult.success(organizeChooseMpConfigPage);
    }

    @ApiOperation(value = "企业小程序编辑/保存单个菜单页面样式" , tags = "企业小程序DIY配置",httpMethod = "POST")
    @PostMapping("/organize/menupage")
    public CommonResult saveSinglePage(@RequestBody OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        mpChooseStyleService.saveSingle(organizeMenuMpRequestVO);
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "企业小程序编辑/保存到草稿或保存页面" , tags = "企业小程序DIY配置",httpMethod = "POST")
    @PostMapping("/organize")
    public CommonResult saveAllPage(@RequestBody OrganizeMpRequestVO organizeMpRequestVO) {
        mpChooseStyleService.saveAll(organizeMpRequestVO);
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "企业小程序编辑发布" , tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="企业小程序草稿编号",required=true,dataType="Integer")
    })
    @GetMapping("/organize/mp/{id}")
    //0 跳转选择模版
    //1 跳转编辑页面
    public CommonResult<Integer> get(@PathVariable Integer id) {
        Integer r = mpChooseStyleService.get(id);
        return CommonResult.success(id);
    }



}
