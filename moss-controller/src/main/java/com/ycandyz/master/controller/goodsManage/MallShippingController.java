package com.ycandyz.master.controller.goodsManage;

import com.github.pagehelper.PageInfo;
import com.ycandyz.master.commonResult.CommonResult2;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingRegionProvinceDTO;
import com.ycandyz.master.entities.mall.goodsManage.MallShipping;
import com.ycandyz.master.service.mall.goodsManage.MallShippingService;
import com.ycandyz.master.vo.MallShippingVO;
import io.swagger.annotations.Api;
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
    public List<MallShippingDTO> addMallShipping(@RequestBody MallShippingVO mallShippingVO){
        log.info("添加运费模版请求:{}",mallShippingVO);
        List<MallShippingDTO> mallShippingDTOS = mallShippingService.addMallShipping(mallShippingVO);
        return mallShippingDTOS;
    }



    @ApiOperation(value = "关键字查询模版",notes = "查询",httpMethod = "GET")
    @GetMapping("/shipping")
    public PageInfo<MallShipping> selMallShippingByKeyWord(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                         @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,@RequestParam(value = "keyWord") String keyWord){
        PageInfo<MallShipping> mallShipping = mallShippingService.selMallShippingByKeyWord(page,pageSize,keyWord);
        log.info("关键字:{}；查询运费模版结果{}",keyWord,mallShipping);
        return mallShipping;
    }

    @ApiOperation(value = "查询配送区域",notes = "查询",httpMethod = "GET")
    @GetMapping("/shipping/region")
    public List<MallShippingRegionProvinceDTO> selMallShippingRegionProvince(){
        List<MallShippingRegionProvinceDTO> mallShippingRegionProvince = mallShippingService.selMallShippingRegionProvince();
        log.info("查询配送区域结果:{}",mallShippingRegionProvince);
        return mallShippingRegionProvince;
    }

    @ApiOperation(value = "删除运费模版",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/shipping/{shippingNo}")
    public CommonResult2 delMallShippingByshippingNo(@PathVariable(value = "shippingNo") String shippingNo){
        boolean count = mallShippingService.delMallShippingByshippingNo(shippingNo);
        if (count) {
            return CommonResult2.success();
        }
        return CommonResult2.failed();
    }

    @ApiOperation(value = "查询单个运费模版",notes = "查询",httpMethod = "GET")
    @GetMapping("/shipping/{shippingNo}")
    public MallShippingDTO selMallShippingByShippingNo(@PathVariable(value = "shippingNo") String shippingNo){
        MallShippingDTO mallShippingDTO = mallShippingService.selMallShippingByShippingNo(shippingNo);
        log.info("shippingNo:{}；根据shippingNo查询模版结果:{}",shippingNo,mallShippingDTO);
        return mallShippingDTO;
    }
}
