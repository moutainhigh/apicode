package com.ycandyz.master.controller.mall;

import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dto.mall.MallCategoryDTO;
import com.ycandyz.master.service.mall.goodsManage.MallCategoryService;
import com.ycandyz.master.vo.MallCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value="商品分类",tags={"商品管理-商品分类动态接口"})
@RestController
@RequestMapping("/mallCategory")
@Slf4j
public class MallCategoryController {
    @Resource
    private MallCategoryService mallCategoryService;

    @ApiOperation(value = "添加商品分类",notes = "添加",httpMethod = "POST")
    @PostMapping("/category")
    public ReturnResponse<List<MallCategoryDTO>> insert(@RequestBody MallCategoryVO mallCategoryVO){
        List<MallCategoryDTO> mallCategoryDTOS = mallCategoryService.insert(mallCategoryVO);
        return ReturnResponse.success(mallCategoryDTOS);
    }

    @ApiOperation(value = "查询单个分类(第二级)信息",notes = "查询",httpMethod = "GET")
    @GetMapping("/category/{categoryNo}")
    public ReturnResponse<MallCategoryDTO> select(@PathVariable(value = "categoryNo") String categoryNo){
        MallCategoryDTO mallCategoryDTO = mallCategoryService.select(categoryNo);
        log.info("categoryNo:{}；根据categoryNo查询单个分类信息结果:{}",categoryNo,mallCategoryDTO);
        return ReturnResponse.success(mallCategoryDTO);
    }

    @ApiOperation(value = "查询某个分类的子类列表",notes = "查询",httpMethod = "GET")
    @GetMapping("/category/children/{parentCategoryNo}")
    public ReturnResponse<List<MallCategoryDTO>> selectChildren(@PathVariable(value = "parentCategoryNo") String parentCategoryNo){
        List<MallCategoryDTO> mallCategoryDTOs = mallCategoryService.selectChildren(parentCategoryNo);
        log.info("parentCategoryNo:{}；根据categoryNo查询某个分类的子类列表结果:{}",parentCategoryNo,mallCategoryDTOs);
        return ReturnResponse.success(mallCategoryDTOs);
    }


    @ApiOperation(value = "删除分类模版",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/category/{categoryNo}")
    public ReturnResponse delete(@PathVariable(value = "categoryNo") String categoryNo){
        int i = mallCategoryService.delete(categoryNo);
        if (i > 0){
            return ReturnResponse.success(null);
        }
        return ReturnResponse.failed("分类模版不存在");
    }

    @ApiOperation(value = "编辑商品分类",notes = "put",httpMethod = "PUT")
    @PutMapping("/category")
    public ReturnResponse<List<MallCategoryDTO>> update(@RequestBody MallCategoryVO mallCategoryVO){
        log.info("修改运费模版请求参数:{}",mallCategoryVO);
        List<MallCategoryDTO> mallCategoryDTOS = mallCategoryService.update(mallCategoryVO);
        return ReturnResponse.success(mallCategoryDTOS);
    }
}
