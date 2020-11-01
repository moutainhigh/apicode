package com.ycandyz.master.dao.ad;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.ad.HomeCategoryDTO;
import com.ycandyz.master.entities.ad.HomeCategory;

import java.util.List;

/**
 * <p>
 * 首页- 轮播图分类表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 */
public interface HomeCategoryDao extends BaseMapper<HomeCategory> {

    List<HomeCategoryDTO> selectByParentCategoryNo(String categoryNo);

    HomeCategoryDTO selectByCategoryNo(String categoryNo);


}
