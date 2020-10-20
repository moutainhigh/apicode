package com.ycandyz.master.controller.base;

import com.ycandyz.master.entities.BaseRegion;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.service.base.BaseRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Api(value="省市表",tags={"省-商品分类动态接口"})
@RestController
@RequestMapping("/basecity")
@Slf4j
public class BaseRegionController {
    @Resource
    private BaseRegionService baseRegionService;

    @ApiOperation(value = "查询省市",notes = "查询",httpMethod = "GET")
    @GetMapping("/basecity2")
    public CommonResult<List<BaseRegion>> selBaseCity(){
        List<BaseRegion> baseCities = baseRegionService.selBaseRegion();
        return CommonResult.success(baseCities);
    }

}
