package com.ycandyz.master.dao.mall.goodsManage;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.entities.mall.goodsManage.MallCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MallCategoryDao extends BaseMapper<MallCategory> {

    void addMallCategory(MallCategory mallCategory);
    //void deleteMallCategory();
}
