package com.ycandyz.master.controller.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallCategory;
import com.ycandyz.master.service.mall.goodsManage.MallCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value="商品分类",tags={"商品管理-商品分类动态接口"})
@RestController("/mall")
@Slf4j
public class MallCategoryController {
    @Resource
    private MallCategoryService mallCategoryService;

    @ApiOperation(value = "添加商品分类",notes = "添加",httpMethod = "POST")
    @PostMapping("/category")
    public void addMallCategory(@RequestBody MallCategory mallCategory){
        mallCategory.setShopNo("22212");
        mallCategory.setCategoryNo("2212");
        mallCategory.setLayer(1);
        mallCategory.setStatus(true);
        mallCategory.setSortValue(1);
        mallCategoryService.addMallCategory(mallCategory);
    }

}
