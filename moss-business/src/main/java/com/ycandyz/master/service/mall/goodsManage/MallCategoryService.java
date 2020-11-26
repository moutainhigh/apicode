package com.ycandyz.master.service.mall.goodsManage;


import com.ycandyz.master.dto.mall.MallCategoryDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMallCategoryDTO;
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
    List<MallCategoryDTO> insert(MallCategoryVO mallCategoryVO);

    MallCategoryDTO select(String categoryNo);

    List<OrganizeMallCategoryDTO> selectCategory();

    int delete(String categoryNo);

    List<MallCategoryDTO> selectChildren(String parentCategoryNo);

    List<MallCategoryDTO> update(MallCategoryVO mallCategoryVO);
}
