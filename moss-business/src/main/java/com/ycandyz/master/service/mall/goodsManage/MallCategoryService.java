package com.ycandyz.master.service.mall.goodsManage;


import com.ycandyz.master.entities.mall.goodsManage.MallCategory;

/**
 * @Description: 商品分类
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/
public interface MallCategoryService {
    /**
     * @Description: 添加商品
    */
    void addMallCategory(MallCategory mallCategory);

    MallCategory selectById();
}
