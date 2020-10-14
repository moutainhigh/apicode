package com.ycandyz.master.service.mall.goodsManage.impl;


import com.ycandyz.master.entities.mall.goodsManage.MallCategory;
import com.ycandyz.master.service.mall.goodsManage.MallCategoryService;
import com.ycandyz.master.dao.mall.goodsManage.MallCategoryDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 商品分类
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/
@Service

public class MallCategoryServiceImpl implements MallCategoryService {

    @Resource

    private MallCategoryDao mallCategoryDao;
    @Override

    public void addMallCategory(MallCategory mallCategory) {
        //mallCategory.setCategoryNo();
        mallCategoryDao.addMallCategory(mallCategory);
    }

    @Override
    public MallCategory selectById() {
        return null;
    }
}
