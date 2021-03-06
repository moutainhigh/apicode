package com.ycandyz.master.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.service.mall.goodsManage.GoodsMallSpecService;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    private GoodsMallSpecService mallSpecService;

    @ApiOperation(value = "添加规格模版",notes = "add",httpMethod = "POST")
    @PostMapping("/spec")
    public ReturnResponse<List<MallSpecSingleVO>> insert(@RequestBody MallSpecVO mallSpecVO){
        log.info("添加规格模版请求参数mallSpecVO:{};userVO:{}",mallSpecVO);
        List<MallSpecSingleVO> mallSpecSingleVOS = mallSpecService.insert(mallSpecVO);
        return ReturnResponse.success(mallSpecSingleVOS);
    }

    @ApiOperation(value = "删除规格模版",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/spec/{specNo}")
    public ReturnResponse delete(@PathVariable(value = "specNo") String specNo){
        int count = mallSpecService.delete(specNo);
        if (count > 0) {
            return ReturnResponse.success(null);
        }
        return ReturnResponse.failed("删除规格模版失败");
    }

    @ApiOperation(value = "关键字查询规格模版",notes = "查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyWord",value="关键字",required=true,dataType="String")
    })
    @GetMapping("/spec")
    public ReturnResponse<Page<MallSpecKeyWordVO>> selectByKeyWord(PageResult pageResult,@RequestParam(value = "keyWord") String keyWord){
        Page<MallSpecKeyWordVO> mallSpecVOPageInfo = mallSpecService.selectByKeyWord(pageResult,keyWord);
        log.info("关键字:{}；查询规格模版结果{}",keyWord,mallSpecVOPageInfo);
        return ReturnResponse.success(mallSpecVOPageInfo);
    }

    @ApiOperation(value = "查询单个规格模版",notes = "查询",httpMethod = "GET")
    @GetMapping("/spec/{specNo}")
    public ReturnResponse<MallSpecSingleVO> select(@PathVariable(value = "specNo") String specNo){
        MallSpecSingleVO mallSpecSingleVO = mallSpecService.select(specNo);
        log.info("specNo:{}；查询单个规格模版:{}",specNo,mallSpecSingleVO);
        return ReturnResponse.success(mallSpecSingleVO);
    }

    @ApiOperation(value = "编辑规格模版",notes = "put",httpMethod = "PUT")
    @PutMapping("/spec")
    public ReturnResponse<List<MallSpecSingleVO>> update(@RequestBody MallSpecVO mallSpecVO){
        log.info("修改规格模版请求参数:{}",mallSpecVO);
        List<MallSpecSingleVO> mallSpecSingleVOS = mallSpecService.update(mallSpecVO);
        return ReturnResponse.success(mallSpecSingleVOS);
    }
}
