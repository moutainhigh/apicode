package com.ycandyz.master.service.mall.goodsManage;


import com.ycandyz.master.dto.mall.goodsManage.MallCategoryDTO;
import com.ycandyz.master.vo.MallCategoryVO;

import java.util.List;

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
    List<MallCategoryDTO> addMallCategory(MallCategoryVO mallCategoryVO);

    MallCategoryDTO selCategoryByCategoryNo(String categoryNo);

    int delCategoryByCategoryNo(String categoryNo);

    List<MallCategoryDTO> selCategoryByParentCategoryNo(String parentCategoryNo);
}
