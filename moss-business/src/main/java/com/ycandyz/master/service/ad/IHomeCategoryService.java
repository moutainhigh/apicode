package com.ycandyz.master.service.ad;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.query.ad.HomeCategoryQuery;
import com.ycandyz.master.dto.ad.HomeCategoryDTO;
import com.ycandyz.master.entities.ad.HomeCategory;

import java.util.List;

/**
 * <p>
 * @Description 首页- 轮播图分类表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
public interface IHomeCategoryService extends IService<HomeCategory>{

    List<HomeCategory> getListByParentCategoryNo(HomeCategoryQuery query);

    List<HomeCategoryDTO> getListByChildCategoryNo(String categoryNo);

    HomeCategoryDTO getByChildCategoryNo(String categoryNo);
}
