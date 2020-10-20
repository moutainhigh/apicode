package com.ycandyz.master.controller.mall.goodsManage;

import com.github.pagehelper.PageInfo;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.dto.mall.goodsManage.MallSpecDTO;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.goodsManage.MallSpecService;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value="规格模版",tags={"商品管理-规格模版动态接口"})
@RestController
@RequestMapping("/mallSpec")
@Slf4j
public class MallSpecController {

    @Resource
    private MallSpecService mallSpecService;

    @ApiOperation(value = "添加规格模版",notes = "add",httpMethod = "POST")
    @PostMapping("/spec")
    public CommonResult<List<MallSpecSingleVO>> addMallSpec(@RequestBody MallSpecVO mallSpecVO, @CurrentUser UserVO userVO){
        log.info("添加规格模版请求参数mallSpecVO:{};userVO:{}",mallSpecVO,userVO);
        List<MallSpecSingleVO> mallSpecSingleVOS = mallSpecService.addMallSpec(mallSpecVO,userVO);
        return CommonResult.success(mallSpecSingleVOS);
    }

    @ApiOperation(value = "删除规格模版",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/spec/{specNo}")
    public CommonResult delMallSpecBySpecNo(@PathVariable(value = "specNo") String specNo, @CurrentUser UserVO userVO){
        int count = mallSpecService.delMallSpecBySpecNo(specNo,userVO);
        if (count > 0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed(null);
    }

    @ApiOperation(value = "关键字查询模版",notes = "查询",httpMethod = "GET")
    @GetMapping("/spec")
    public CommonResult<PageInfo<MallSpecKeyWordVO>> selMallShippingByKeyWord(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                           @RequestParam(value = "keyWord") String keyWord,@CurrentUser UserVO userVO){
        PageInfo<MallSpecKeyWordVO> mallSpecVOPageInfo = mallSpecService.selMallSpecByKeyWord(page,pageSize,keyWord,userVO);
        log.info("关键字:{}；查询规格模版结果{}",keyWord,mallSpecVOPageInfo);
        return CommonResult.success(mallSpecVOPageInfo);
    }

    @ApiOperation(value = "查询单个规格模版",notes = "查询",httpMethod = "GET")
    @GetMapping("/spec/{specNo}")
    public CommonResult<MallSpecSingleVO> selMallShippingByShippingNo(@PathVariable(value = "specNo") String specNo, @CurrentUser UserVO userVO){
        MallSpecSingleVO mallSpecSingleVO = mallSpecService.selMallSpecSingleBySpecNo(userVO,specNo);
        log.info("specNo:{}；查询单个规格模版:{}",specNo,mallSpecSingleVO);
        return CommonResult.success(mallSpecSingleVO);
    }

    @ApiOperation(value = "编辑规格模版",notes = "put",httpMethod = "PUT")
    @PutMapping("/spec")
    public CommonResult<List<MallSpecSingleVO>> updateMallSpec(@RequestBody MallSpecVO mallSpecVO, @CurrentUser UserVO userVO){
        log.info("修改规格模版请求参数:{}",mallSpecVO);
        List<MallSpecSingleVO> mallSpecSingleVOS = mallSpecService.updateMallSpec(mallSpecVO,userVO);
        return CommonResult.success(mallSpecSingleVOS);
    }
}
