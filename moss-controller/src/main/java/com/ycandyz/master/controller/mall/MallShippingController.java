package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.dto.mall.MallShippingDTO;
import com.ycandyz.master.dto.mall.MallShippingKwDTO;
import com.ycandyz.master.dto.mall.MallShippingRegionProvinceDTO;
import com.ycandyz.master.service.mall.goodsManage.GoodsMallShippingService;
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
    private GoodsMallShippingService mallShippingService;

    @ApiOperation(value = "添加运费模版",notes = "添加",httpMethod = "POST")
    @PostMapping("/shipping")
    public ReturnResponse<List<MallShippingDTO>> insert(@RequestBody MallShippingVO mallShippingVO){
        log.info("添加运费模版请求:{}",mallShippingVO);
        List<MallShippingDTO> mallShippingDTOS = mallShippingService.insert(mallShippingVO);
        return ReturnResponse.success(mallShippingDTOS);
    }

    @ApiOperation(value = "关键字查询运费模版",notes = "查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyWord",value="关键字",required=true,dataType="String")
    })
    @GetMapping("/shipping")
    public ReturnResponse<Object> selectByKeyWord(PageResult pageResult, @RequestParam(value = "keyWord") String keyWord){
        Page<MallShippingKwDTO> mallShipping = mallShippingService.selectByKeyWord(pageResult,keyWord);
        if (mallShipping != null){
            log.info("关键字:{}；查询运费模版结果{}",keyWord,mallShipping);
            return ReturnResponse.success(mallShipping);
        }
        return ReturnResponse.failed("查询的运费模版不存在");
    }

    @ApiOperation(value = "查询配送区域",notes = "查询",httpMethod = "GET")
    @GetMapping("/shipping/region")
    public ReturnResponse<List<MallShippingRegionProvinceDTO>> selectRegion(){
        List<MallShippingRegionProvinceDTO> mallShippingRegionProvince = mallShippingService.selectRegion();
        log.info("查询配送区域结果:{}",mallShippingRegionProvince);
        return ReturnResponse.success(mallShippingRegionProvince);
    }

    @ApiOperation(value = "删除运费模版",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/shipping/{shippingNo}")
    public ReturnResponse delete(@PathVariable(value = "shippingNo") String shippingNo, @CurrentUser UserVO userVO){
        int count = mallShippingService.delete(shippingNo);
        if (count > 0) {
            return ReturnResponse.success(null);
        }
        return ReturnResponse.failed("删除运费模版失败");
    }

    @ApiOperation(value = "查询单个运费模版",notes = "查询",httpMethod = "GET")
    @GetMapping("/shipping/{shippingNo}")
    public ReturnResponse<Object> select(@PathVariable(value = "shippingNo") String shippingNo,@CurrentUser UserVO userVO){
        MallShippingDTO mallShippingDTO = mallShippingService.select(shippingNo);
        if (mallShippingDTO != null){
            log.info("shippingNo:{}；根据shippingNo查询模版结果:{}",shippingNo,mallShippingDTO);
            return ReturnResponse.success(mallShippingDTO);
        }
        return ReturnResponse.failed("查询的运费模版不存在");
    }

    @ApiOperation(value = "编辑运费模版",notes = "put",httpMethod = "PUT")
    @PutMapping("/shipping")
    public ReturnResponse<List<MallShippingDTO>> update(@RequestBody MallShippingVO mallShippingVO, @CurrentUser UserVO userVO){
        log.info("修改运费模版请求参数:{}",mallShippingVO);
        List<MallShippingDTO> mallShippingDTOS = mallShippingService.update(mallShippingVO);
        return ReturnResponse.success(mallShippingDTOS);
    }
}
