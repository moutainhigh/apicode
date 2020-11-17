package com.ycandyz.master.controller.miniprogram;


import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.entities.miniprogram.MpOrganizeStyleMoudle;
import com.ycandyz.master.model.miniprogram.OrganizeChooseMpConfigPage;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("mini-program/style/choose")
@Api(tags="小程序配置-选择修改样式")
public class MpChoiceStyleController {

    @ApiOperation(value = "选择模版样式" , tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="企业小程序编号",required=true,dataType="Integer")
    })
    @GetMapping(value = "{id}")
    public void chose(@PathVariable Integer id) {
        log.info("{}",id);
    }

    @ApiOperation(value = "查询企业小程序全部菜单", tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="organizId",value="企业编号",required=true,dataType="Long")
    })
    @GetMapping(value = "organize/{organizId}")
    public CommonResult<OrganizeMpConfigMenuVO> select(@PathVariable Long organizId) {
        log.info("{}",organizId);
        OrganizeMpConfigMenuVO organizeMpConfigMenuVO = new OrganizeMpConfigMenuVO();
        return CommonResult.success(organizeMpConfigMenuVO);
    }

    @ApiOperation(value = "查询企业小程序单个菜单样式", tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="organizId",value="企业编号",required=true,dataType="Long"),
            @ApiImplicitParam(name="id",value="菜单编号",required=true,dataType="Integer")
    })
    @GetMapping(value = "organize")
    public CommonResult<OrganizeChooseMpConfigPage> select(@RequestParam("organizId") Long organizId,@RequestParam("id") Integer id) {
        log.info("{},{}",organizId,id);
        OrganizeChooseMpConfigPage organizeChooseMpConfigPage = new OrganizeChooseMpConfigPage();
        return CommonResult.success(organizeChooseMpConfigPage);
    }

    @ApiOperation(value = "查询企业小程序样式", tags = "企业小程序DIY配置",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="organizId",value="企业编号",required=true,dataType="Long"),
            @ApiImplicitParam(name="id",value="企业小程序编号",required=true,dataType="Integer")
    })
    @GetMapping(value = "organize/mp")
    public CommonResult<MpOrganizeStyleMoudle> selectmp(@RequestParam("organizId") Long organizId,@RequestParam("id") Integer id) {
        log.info("{}",id);
        MpOrganizeStyleMoudle mpOrganizeStyleMoudle = new MpOrganizeStyleMoudle();
        return CommonResult.success(mpOrganizeStyleMoudle);
    }

    @ApiOperation(value = "编辑样式" , tags = "企业小程序DIY配置",httpMethod = "PUT")
    @PutMapping(value = "modify")
    public CommonResult modify(@RequestParam("id") Integer id,@RequestParam("name") String name) {
        log.info("{},{}",id,name);
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "删除样式" , tags = "企业小程序DIY配置",httpMethod = "DELETE")
    @DeleteMapping(value = "{id}")
    public CommonResult delete(@PathVariable Integer id) {
        log.info("{}",id);
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "拖动样式" , tags = "企业小程序DIY配置",httpMethod = "PUT")
    @PutMapping(value = "drag")
    public void drag(@PathVariable("id") Integer id, @RequestBody MpConfigPlanPageModel mpConfigPlanPageModel) {
        log.info("{},{}",id,mpConfigPlanPageModel);
    }
}
