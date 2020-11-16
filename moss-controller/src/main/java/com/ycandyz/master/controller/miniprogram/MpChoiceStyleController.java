package com.ycandyz.master.controller.miniprogram;


import com.ycandyz.master.api.BaseResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.miniprogram.MpConfigMenuQuery;
import com.ycandyz.master.entities.miniprogram.MpConfigMenu;
import com.ycandyz.master.entities.miniprogram.MpStyleEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("mini-program/style/choice")
@Api(tags="小程序配置-选择并修改样式")
public class MpChoiceStyleController {

    @ApiOperation(value = "选择模版样式")
    @GetMapping(value = "{id}")
    public CommonResult chose(@PathVariable Long id) {
        log.info("{}",id);
        return CommonResult.success("选择样式id:"+id);
    }

    @ApiOperation(value = "查询企业样式", tags = "小程序配置")
    @GetMapping(value = "organize/{id}")
    public CommonResult<BaseResult<List<MpConfigMenu>>> select(@PathVariable Long id) {
        log.info("{}",id);
        BaseResult baseResult = new BaseResult(null);
        return CommonResult.success(baseResult);
    }

    @ApiOperation(value = "编辑样式")
    @PutMapping(value = "modify")
    public CommonResult modify(@RequestParam("id") Integer id,@RequestParam("name") String name) {
        log.info("{},{}",id,name);
        return CommonResult.success("编辑样式:"+"-"+id+"-"+name);
    }

    @ApiOperation(value = "删除样式")
    @DeleteMapping(value = "{id}")
    public CommonResult delete(@PathVariable Integer id) {
        log.info("{}",id);
        return CommonResult.success("删除样式:"+id);
    }

    @ApiOperation(value = "拖动样式")
    @PutMapping(value = "drag")
    public CommonResult drag(@RequestParam("id") Integer id,@RequestParam("sortNum") Integer sortNum) {
        log.info("{},{}",id,sortNum);
        return CommonResult.success("拖动样式:"+id+"-"+sortNum);
    }
}
