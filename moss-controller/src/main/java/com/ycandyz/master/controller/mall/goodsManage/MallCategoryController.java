package com.ycandyz.master.controller.mall.goodsManage;

import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.dto.mall.goodsManage.MallCategoryDTO;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.model.user.UserVO;
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
    public CommonResult<List<MallCategoryDTO>> addMallCategory(@RequestBody MallCategoryVO mallCategoryVO, @CurrentUser UserVO user){
        List<MallCategoryDTO> mallCategoryDTOS = mallCategoryService.addMallCategory(mallCategoryVO,user);
        return CommonResult.success(mallCategoryDTOS);
    }

    @ApiOperation(value = "查询单个分类(第二级)信息",notes = "查询",httpMethod = "GET")
    @GetMapping("/category/{categoryNo}")
    public CommonResult<MallCategoryDTO> selCategoryByCategoryNo(@PathVariable(value = "categoryNo") String categoryNo,@CurrentUser UserVO userVO){
        MallCategoryDTO mallCategoryDTO = mallCategoryService.selCategoryByCategoryNo(categoryNo,userVO);
        log.info("categoryNo:{}；根据categoryNo查询单个分类信息结果:{}",categoryNo,mallCategoryDTO);
        return CommonResult.success(mallCategoryDTO);
    }

    @ApiOperation(value = "查询某个分类的子类列表",notes = "查询",httpMethod = "GET")
    @GetMapping("/category/children/{parentCategoryNo}")
    public CommonResult<List<MallCategoryDTO>> selChildCategoryByCategoryNo(@PathVariable(value = "parentCategoryNo") String parentCategoryNo,@CurrentUser UserVO userVO){
        List<MallCategoryDTO> mallCategoryDTOs = mallCategoryService.selCategoryByParentCategoryNo(parentCategoryNo,userVO);
        log.info("parentCategoryNo:{}；根据categoryNo查询某个分类的子类列表结果:{}",parentCategoryNo,mallCategoryDTOs);
        return CommonResult.success(mallCategoryDTOs);
    }


    @ApiOperation(value = "删除分类模版",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/category/{categoryNo}")
    public CommonResult delMallSpecBySpecNo(@PathVariable(value = "categoryNo") String categoryNo,@CurrentUser UserVO userVO){
        int i = mallCategoryService.delCategoryByCategoryNo(categoryNo,userVO);
        if (i > 0){
            return CommonResult.success(null);
        }
        return CommonResult.failed(null);
    }

}
