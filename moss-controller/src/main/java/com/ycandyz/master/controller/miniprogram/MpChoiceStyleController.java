package com.ycandyz.master.controller.miniprogram;


import com.amazonaws.services.dynamodbv2.xspec.M;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.entities.miniprogram.MpOrganizeStyleMoudle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("mini-program/style/choose")
@Api(tags="小程序配置-选择修改样式")
public class MpChoiceStyleController {

    @ApiOperation(value = "选择模版样式" , tags = "企业小程序DIY配置")
    @GetMapping(value = "{id}")
    public void chose(@PathVariable Long id) {
        log.info("{}",id);
    }

    @ApiOperation(value = "查询企业模块样式", tags = "企业小程序DIY配置")
    @GetMapping(value = "organize/{id}")
    public CommonResult<MpOrganizeStyleMoudle> select(@PathVariable Long id) {
        log.info("{}",id);
        MpOrganizeStyleMoudle mpOrganizeStyleMoudle = new MpOrganizeStyleMoudle();
        return CommonResult.success(mpOrganizeStyleMoudle);
    }

    @ApiOperation(value = "编辑样式" , tags = "企业小程序DIY配置")
    @PutMapping(value = "modify")
    public CommonResult modify(@RequestParam("id") Integer id,@RequestParam("name") String name) {
        log.info("{},{}",id,name);
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "删除样式" , tags = "企业小程序DIY配置")
    @DeleteMapping(value = "{id}")
    public CommonResult delete(@PathVariable Integer id) {
        log.info("{}",id);
        return CommonResult.success("成功");
    }

    @ApiOperation(value = "拖动样式" , tags = "企业小程序DIY配置")
    @PutMapping(value = "drag")
    public void drag(@PathVariable("id") Integer id, @RequestBody MpConfigPlanPageModel mpConfigPlanPageModel) {
        log.info("{},{}",id,mpConfigPlanPageModel);
    }
}
