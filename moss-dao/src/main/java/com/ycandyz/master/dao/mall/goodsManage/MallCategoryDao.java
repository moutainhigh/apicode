package com.ycandyz.master.dao.mall.goodsManage;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.entities.mall.goodsManage.MallCategory;
import com.ycandyz.master.entities.mall.goodsManage.MallParentCategory;
import com.ycandyz.master.entities.miniprogram.OrganizeMallCategoryDTO;
import com.ycandyz.master.vo.MallCategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallCategoryDao extends BaseMapper<MallCategory> {

    void addMallCategory(MallCategory mallCategory);

    void updateMallCategoryImg(String imgUrl, String categoryNo);

    MallCategory selCategoryByCategoryNo(@Param("shopNo") String shopNo,@Param("categoryNo") String categoryNo);

    MallParentCategory selParentCategoryByCategoryNo(@Param("shopNo") String shopNo, @Param("categoryNo") String categoryNo);

    int delCategoryByCategoryNo(@Param("shopNo") String shopNo, @Param("categoryNo") String categoryNo);

    List<String> selParentCategoryNoByShopNo(String shopNo);

    List<MallCategory> selCategoryNoByShopNo(String shopNo);

    List<MallCategory> selParentCategoryByParentCategoryNo(@Param("shopNo")String shopNo, @Param("parentCategoryNo")String parentCategoryNo);

    List<MallCategory> selCategoryByParentCategoryNo(@Param("shopNo") String shopNo, @Param("parentCategoryNo") String parentCategoryNo);

    int updateMallParentCategory(@Param("mallCategoryVO") MallCategoryVO mallCategoryVO,@Param("shopNo") String shopNo);

    int updateMallCategory(@Param("mallCategoryVO") MallCategoryVO mallCategoryVO, @Param("shopNo") String shopNo);

    OrganizeMallCategoryDTO selectByShopNo(String shopNo);
}
