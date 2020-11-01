package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.entities.mall.MallCategory;

import java.util.List;

/**
 * <p>
 * 商城 - 商品分类表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 */
public interface MallHomeCategoryDao extends BaseMapper<MallCategory> {

    /**
     * 获取当前商店的二级分类,按一级分类排序,只取8条
     * @param shopNo 商店编号
     * @return 分类集合
     */
    List<MallCategory> selectMallCategoryList(String shopNo);

}
