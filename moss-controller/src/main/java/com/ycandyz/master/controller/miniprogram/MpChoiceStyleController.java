package com.ycandyz.master.controller.miniprogram;


import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.model.miniprogram.OrganizeChooseMpConfigPage;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import com.ycandyz.master.vo.OrganizeMenuMpRequestVO;
import com.ycandyz.master.vo.OrganizeMpRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("cms/mp/style/")
@Api(tags="小程序配置-选择修改样式")
public class MpChoiceStyleController {

//    @ApiOperation(value = "企业选择模版样式" , tags = "企业小程序DIY配置",httpMethod = "GET")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="id",value="小程序模版编号",required=true,dataType="Integer")
//    })
//    @GetMapping(value = "{id}")
//    public void chose(@PathVariable Integer id) {
//        log.info("{}",id);
//    }

    @ApiOperation(value = "查询企业小程序全部菜单", tags = "企业小程序DIY配置",httpMethod = "GET")
    @GetMapping(value = "organize")
    public CommonResult<OrganizeMpConfigMenuVO> select() {
        OrganizeMpConfigMenuVO organizeMpConfigMenuVO = new OrganizeMpConfigMenuVO();
        return CommonResult.success(organizeMpConfigMenuVO);
    }

    @ApiOperation(value = "查询企业小程序单个菜单样式", tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="菜单编号",required=true,dataType="Integer")
    })
    @GetMapping(value = "organize/{id}")
    public CommonResult<OrganizeChooseMpConfigPage> select(@PathVariable("id") Integer id) {
        OrganizeChooseMpConfigPage organizeChooseMpConfigPage = new OrganizeChooseMpConfigPage();
        return CommonResult.success(organizeChooseMpConfigPage);
    }

    @ApiOperation(value = "企业小程序编辑单个菜单页面样式" , tags = "企业小程序DIY配置",httpMethod = "PUT")
    @PutMapping("/organize/menupage")
    public CommonResult modify(@RequestBody OrganizeMenuMpRequestVO organizeMenuMpRequestVO) {
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "企业小程序保存到草稿或保存页面" , tags = "企业小程序DIY配置",httpMethod = "POST")
    @PostMapping("/organize")
    public CommonResult save(@RequestBody OrganizeMpRequestVO organizeMpRequestVO) {
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "企业小程序编辑或发布" , tags = "企业小程序DIY配置",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="企业小程序编号",required=true,dataType="Integer")
    })
    @PutMapping("/organize/mp/{id}")
    public CommonResult<Integer> update(@PathVariable Integer id) {
        return CommonResult.success(id);
    }



    //    @ApiOperation(value = "查询企业小程序样式", tags = "企业小程序DIY配置",httpMethod = "GET")
//    @GetMapping(value = "organize/{id}")
//    public CommonResult<MpOrganizeStyleMoudle> selectmp(@PathVariable("id") Integer id) {
//        log.info("{}",id);
//        MpOrganizeStyleMoudle mpOrganizeStyleMoudle = new MpOrganizeStyleMoudle();
//        return CommonResult.success(mpOrganizeStyleMoudle);
//    }

//    @ApiOperation(value = "编辑样式" , tags = "企业小程序DIY配置",httpMethod = "PUT")
//    @PutMapping(value = "modify")
//    public CommonResult modify(@RequestParam("id") Integer id,@RequestParam("name") String name) {
//        log.info("{},{}",id,name);
//        return CommonResult.success("成功");
//    }
//
//    @ApiOperation(value = "删除样式" , tags = "企业小程序DIY配置",httpMethod = "DELETE")
//    @DeleteMapping(value = "{id}")
//    public CommonResult delete(@PathVariable Integer id) {
//        log.info("{}",id);
//        return CommonResult.success("成功");
//    }
//
//    @ApiOperation(value = "拖动样式" , tags = "企业小程序DIY配置",httpMethod = "PUT")
//    @PutMapping(value = "drag")
//    public void drag(@PathVariable("id") Integer id, @RequestBody MpConfigPlanPageModel mpConfigPlanPageModel) {
//        log.info("{},{}",id,mpConfigPlanPageModel);
//    }
}
