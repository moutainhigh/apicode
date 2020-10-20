package com.ycandyz.master.service.mall.goodsManage;


import com.ycandyz.master.dto.mall.goodsManage.MallCategoryDTO;
import com.ycandyz.master.model.user.UserVO;
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
    List<MallCategoryDTO> addMallCategory(MallCategoryVO mallCategoryVO, UserVO userVO);

    MallCategoryDTO selCategoryByCategoryNo(String categoryNo,UserVO userVO);

    int delCategoryByCategoryNo(String categoryNo,UserVO userVO);

    List<MallCategoryDTO> selCategoryByParentCategoryNo(String parentCategoryNo,UserVO userVO);

    List<MallCategoryDTO> updateMallCategory(MallCategoryVO mallCategoryVO, UserVO userVO);
}
