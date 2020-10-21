package com.ycandyz.master.controller.mall.goodsManage;

import com.github.pagehelper.PageInfo;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingKwDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingRegionProvinceDTO;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.entities.mall.goodsManage.MallShipping;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.goodsManage.MallShippingService;
import com.ycandyz.master.vo.MallShippingVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Api(value="运费模版",tags={"商品管理-运费模版动态接口"})
@RestController
@RequestMapping("/mallShipping")
@Slf4j
public class MallShippingController {

    @Resource
    private MallShippingService mallShippingService;

    @ApiOperation(value = "添加运费模版",notes = "添加",httpMethod = "POST")
    @PostMapping("/shipping")
    public CommonResult<List<MallShippingDTO>> addMallShipping(@RequestBody MallShippingVO mallShippingVO){
        log.info("添加运费模版请求:{}",mallShippingVO);
        List<MallShippingDTO> mallShippingDTOS = mallShippingService.addMallShipping(mallShippingVO);
        return CommonResult.success(mallShippingDTOS);
    }

    @ApiOperation(value = "关键字查询模版",notes = "查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyWord",value="关键字",required=true,dataType="String")
    })
    @GetMapping("/shipping")
    public CommonResult<Object> selMallShippingByKeyWord(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                         @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,@RequestParam(value = "keyWord") String keyWord){
        PageInfo<MallShippingKwDTO> mallShipping = mallShippingService.selMallShippingByKeyWord(page,pageSize,keyWord);
        if (mallShipping != null){
            log.info("关键字:{}；查询运费模版结果{}",keyWord,mallShipping);
            return CommonResult.success(mallShipping);
        }
        return CommonResult.success("查询的运费模版不存在");
    }

    @ApiOperation(value = "查询配送区域",notes = "查询",httpMethod = "GET")
    @GetMapping("/shipping/region")
    public CommonResult<List<MallShippingRegionProvinceDTO>> selMallShippingRegionProvince(){
        List<MallShippingRegionProvinceDTO> mallShippingRegionProvince = mallShippingService.selMallShippingRegionProvince();
        log.info("查询配送区域结果:{}",mallShippingRegionProvince);
        return CommonResult.success(mallShippingRegionProvince);
    }

    @ApiOperation(value = "删除运费模版",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/shipping/{shippingNo}")
    public CommonResult delMallShippingByshippingNo(@PathVariable(value = "shippingNo") String shippingNo, @CurrentUser UserVO userVO){
        int count = mallShippingService.delMallShippingByshippingNo(shippingNo);
        if (count > 0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed(null);
    }

    @ApiOperation(value = "查询单个运费模版",notes = "查询",httpMethod = "GET")
    @GetMapping("/shipping/{shippingNo}")
    public CommonResult<Object> selMallShippingByShippingNo(@PathVariable(value = "shippingNo") String shippingNo,@CurrentUser UserVO userVO){
        MallShippingDTO mallShippingDTO = mallShippingService.selMallShippingByShippingNo(shippingNo);
        if (mallShippingDTO != null){
            log.info("shippingNo:{}；根据shippingNo查询模版结果:{}",shippingNo,mallShippingDTO);
            return CommonResult.success(mallShippingDTO);
        }
        return CommonResult.success("查询的运费模版不存在");
    }

    @ApiOperation(value = "编辑运费模版",notes = "put",httpMethod = "PUT")
    @PutMapping("/shipping")
    public CommonResult<List<MallShippingDTO>> updateMallShipping(@RequestBody MallShippingVO mallShippingVO, @CurrentUser UserVO userVO){
        log.info("修改运费模版请求参数:{}",mallShippingVO);
        List<MallShippingDTO> mallShippingDTOS = mallShippingService.updateMallShipping(mallShippingVO);
        return CommonResult.success(mallShippingDTOS);
    }
}
