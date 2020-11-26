package com.ycandyz.master.controller.miniprogram;


import com.alibaba.fastjson.JSON;
import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dto.mall.MallCategoryDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMallCategoryDTO;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigPageSingleMenuVO;
import com.ycandyz.master.service.mall.goodsManage.MallCategoryService;
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

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("cms/mp/style/")
@Api(tags="小程序配置-选择修改样式")
public class MpChooseStyleController {

    @Autowired
    private MpChooseStyleService mpChooseStyleService;

    @Resource
    private MallCategoryService mallCategoryService;


    @ApiOperation(value = "查询企业正在使用的小程序全部菜单", tags = "企业小程序DIY配置",httpMethod = "GET")
    @GetMapping(value = "organize")
    public CommonResult<BaseResult<List<OrganizeMpConfigMenuVO>>> select() {
        List<OrganizeMpConfigMenuVO> organizeMpConfigMenuVO = mpChooseStyleService.select();
        log.info("查询企业正在使用的小程序全部菜单请求响应:{}", JSON.toJSONString(CommonResult.success(new BaseResult<>(organizeMpConfigMenuVO))));
        return CommonResult.success(new BaseResult<>(organizeMpConfigMenuVO));
    }

    @ApiOperation(value = "查询企业小程序全部菜单样式", tags = "企业小程序DIY配置",httpMethod = "GET")
    @GetMapping(value = "organize/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="企业小程序编号",required=true,dataType="Integer")
    })
    public CommonResult<BaseResult<List<OrganizeMpConfigMenuVO>>> select(@PathVariable("id") Integer id) {
        log.info("查询企业小程序全部菜单样式请求响应:{}", id);
        List<OrganizeMpConfigMenuVO> organizeMpConfigMenuVO = mpChooseStyleService.selByOrGanizeMoudleId(id);
        log.info("查询企业小程序全部菜单样式请求响应:{}", JSON.toJSONString(CommonResult.success(new BaseResult<>(organizeMpConfigMenuVO))));
        return CommonResult.success(new BaseResult<>(organizeMpConfigMenuVO));
    }

    @ApiOperation(value = "查询企业小程序单个菜单样式", tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuid",value="企业小程序菜单编号",required=true,dataType="Integer")
    })
    @GetMapping(value = "organize/menu/{menuId}")
    public CommonResult<BaseResult<OrganizeMpConfigPageSingleMenuVO>> selectOrgainizeMenu(@PathVariable("menuId") Integer menuId) {
        log.info("查询企业小程序单个菜单样式请求入参:{}", menuId);
        OrganizeMpConfigPageSingleMenuVO organizeMpConfigPageSingleMenuVO = mpChooseStyleService.selectMenuById(menuId);
        log.info("查询企业小程序单个菜单样式请求响应:{}", JSON.toJSONString(CommonResult.success(new BaseResult<>(organizeMpConfigPageSingleMenuVO))));
        return CommonResult.success(new BaseResult<>(organizeMpConfigPageSingleMenuVO));
    }

    @ApiOperation(value = "企业小程序编辑/保存单个菜单页面样式" , tags = "企业小程序DIY配置",httpMethod = "POST")
    @PostMapping("/organize/menupage")
    public CommonResult saveSinglePage(@RequestBody OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        log.info("企业小程序编辑/保存单个菜单页面样式请求入参:{}", JSON.toJSONString(organizeMenuMpRequestVO));
        mpChooseStyleService.saveSingle(organizeMenuMpRequestVO);
        log.info("企业小程序编辑/保存单个菜单页面样式请求出参:{}", JSON.toJSONString(CommonResult.success("成功")));
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "企业小程序编辑/保存到草稿或保存发布页面" , tags = "企业小程序DIY配置",httpMethod = "POST")
    @PostMapping("/organize")
    public CommonResult saveAllPage(@RequestBody OrganizeMpRequestVO organizeMpRequestVO) {
        log.info("企业小程序编辑/保存到草稿或保存页面请求入参:{}", JSON.toJSONString(organizeMpRequestVO));
        mpChooseStyleService.saveAll(organizeMpRequestVO);
        log.info("企业小程序编辑/保存到草稿或保存页面请求出参:{}", JSON.toJSONString(CommonResult.success("成功")));
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "企业小程序编辑发布" ,notes = "是否有草稿[0 否；1 是]",tags = "企业小程序DIY配置",httpMethod = "GET")
    @GetMapping("/organize/mp")
    public CommonResult<Integer> get() {
        Integer r = mpChooseStyleService.get();
        log.info("企业小程序编辑发布请求出参:{}", r);
        return CommonResult.success(r);
    }


    @ApiOperation(value = "查询商城一级分类",notes = "查询",tags = "企业小程序DIY配置",httpMethod = "GET")
    @GetMapping("/organize/category")
    public CommonResult<BaseResult<OrganizeMallCategoryDTO>> selectCategory(){
        OrganizeMallCategoryDTO organizeMallCategoryDTO = mallCategoryService.selectCategory();
        log.info("查询一级分类请求出参:{}",organizeMallCategoryDTO);
        return CommonResult.success(new BaseResult<>(organizeMallCategoryDTO));
    }



}
