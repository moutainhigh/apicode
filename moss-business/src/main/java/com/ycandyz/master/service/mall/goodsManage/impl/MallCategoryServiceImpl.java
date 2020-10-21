package com.ycandyz.master.service.mall.goodsManage.impl;


import com.google.common.collect.Lists;
import com.ycandyz.master.dto.mall.goodsManage.MallCategoryDTO;
import com.ycandyz.master.enums.LayerEnum;
import com.ycandyz.master.enums.SortValueEnum;
import com.ycandyz.master.enums.StatusEnum;
import com.ycandyz.master.entities.mall.goodsManage.MallCategory;
import com.ycandyz.master.entities.mall.goodsManage.MallParentCategory;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.mall.goodsManage.MallCategoryService;
import com.ycandyz.master.dao.mall.goodsManage.MallCategoryDao;
import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.vo.MallCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 商品分类
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/
@Service
@Slf4j
public class MallCategoryServiceImpl implements MallCategoryService {

    @Resource
    private MallCategoryDao mallCategoryDao;

    /**
     * @Description: 添加商品分类
    */
    @Override
    public List<MallCategoryDTO> addMallCategory(MallCategoryVO mallCategoryVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        log.info("添加商品分类入参::mallCategoryVO:{};userVO:{}",mallCategoryVO,userVO);
        String shopNo = userVO.getShopNo();
        String categoryNo = String.valueOf(IDGeneratorUtils.getLongId());
        MallCategory mallCategory = new MallCategory();
        mallCategory.setShopNo(shopNo);
        mallCategory.setCategoryNo(categoryNo);
        mallCategory.setCategoryName(mallCategoryVO.getCategoryName());
        mallCategory.setParentCategoryNo(mallCategoryVO.getParentCategoryNo());
        mallCategory.setCategoryImg(mallCategoryVO.getCategoryImg());
        mallCategory.setSortValue(SortValueEnum.DEFAULT.getCode());
        mallCategory.setStatus(StatusEnum.DEFAULT.getCode());
        if (StringUtils.isBlank(mallCategoryVO.getParentCategoryNo())){
            mallCategory.setLayer(LayerEnum.FITRSTLAYER.getCode());
        }else {
            mallCategory.setLayer(LayerEnum.SECONDLAYER.getCode());
        }
        mallCategoryDao.addMallCategory(mallCategory);
        List<MallCategoryDTO> mallCategoryDTOS = selCategoryByShopNo();
        return mallCategoryDTOS;
    }


    /**
     * @Description: 查询单个分类信息-第二级分类
    */
    @Override
    public MallCategoryDTO selCategoryByCategoryNo(String categoryNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        log.info("根据categoryNo:{};userVO:{}查询单个分类信息",categoryNo,userVO);
        String shopNo = userVO.getShopNo();
        MallCategoryDTO mallCategoryDTO = new MallCategoryDTO();
        MallCategory mallCategory = mallCategoryDao.selCategoryByCategoryNo(shopNo,categoryNo);
        if (mallCategory != null){
            MallParentCategory parentCategory = mallCategoryDao.selParentCategoryByCategoryNo(shopNo, mallCategory.getParentCategoryNo());
            mallCategoryDTO.setCategoryNo(mallCategory.getCategoryNo());
            mallCategoryDTO.setCategoryName(mallCategory.getCategoryName());
            mallCategoryDTO.setParentCategoryNo(mallCategory.getParentCategoryNo());
            mallCategoryDTO.setCategoryImg(mallCategory.getCategoryImg());
            if (parentCategory != null){
                mallCategoryDTO.setParentCategory(parentCategory);
            }
        }
        return mallCategoryDTO;
    }

    /**
     * @Description: 根据商户查询全部分类
    */
    public List<MallCategoryDTO> selCategoryByShopNo() {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        log.info("根据商户shopNo:{}查询全部分类",shopNo);
        List<MallCategoryDTO> mallCategoryDTOS = Lists.newArrayList();
        List<MallCategory> mallCategories = mallCategoryDao.selCategoryNoByShopNo(shopNo);
        if (mallCategories.size() == 0){
            log.info("当前商户shopNo:{}无商品分类",shopNo);
            return null;
        }
        for (MallCategory m: mallCategories) {
            List<MallCategoryDTO>  mallCategoryDTO = selCategoryByParentCategoryNo(m.getCategoryNo());
            mallCategoryDTOS.addAll(mallCategoryDTO);
        }
        return mallCategoryDTOS;
    }

    /**
     * @Description: 删除
    */
    @Override
    public int delCategoryByCategoryNo(String categoryNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        log.info("删除商品分类入参categoryNo:{};user:{}",categoryNo,userVO);
        String shopNo = userVO.getShopNo();
        int i = mallCategoryDao.delCategoryByCategoryNo(shopNo, categoryNo);
        return i;
    }

    /**
     * @Description: 查询某个分类的子类列表
     */
    @Override
    public List<MallCategoryDTO> selCategoryByParentCategoryNo(String parentCategoryNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        log.info("根据parentCategoryNo:{};user:{}查询某个分类的子类列表",parentCategoryNo,userVO);
        String shopNo = userVO.getShopNo();
        ArrayList<MallCategoryDTO> mallCategoryDTOS = Lists.newArrayList();
        MallParentCategory mallParentCategory = mallCategoryDao.selParentCategoryByCategoryNo(shopNo, parentCategoryNo);
        if (mallParentCategory == null){
            log.warn("根据parentCategoryNo:{}查询某个分类的子类列表不存在",parentCategoryNo);
            return null;
        }
        List<MallCategory> mallCategory = mallCategoryDao.selCategoryByParentCategoryNo(shopNo, mallParentCategory.getCategoryNo());
        MallCategoryDTO mallCategoryDTO = null;
        if (mallCategory.size() > 0){
            for (MallCategory m : mallCategory) {
                mallCategoryDTO = new MallCategoryDTO();
                mallCategoryDTO.setCategoryNo(m.getCategoryNo());
                mallCategoryDTO.setCategoryName(m.getCategoryName());
                mallCategoryDTO.setCategoryImg(m.getCategoryImg());
                mallCategoryDTO.setParentCategoryNo(m.getParentCategoryNo());
                mallCategoryDTO.setParentCategory(mallParentCategory);
                mallCategoryDTOS.add(mallCategoryDTO);
            }
        }else {
            mallCategoryDTO = new MallCategoryDTO();
            mallCategoryDTO.setParentCategory(mallParentCategory);
            mallCategoryDTOS.add(mallCategoryDTO);
        }
        return mallCategoryDTOS;
    }

    /**
     * @Description: 编辑商品分类
     * @Author li Zhuo
     * @Date 2020/10/19
     * @Version: V1.0
    */
    @Override
    public List<MallCategoryDTO> updateMallCategory(MallCategoryVO mallCategoryVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        log.info("编辑商品分类入参:{};:{}",mallCategoryVO,shopNo);
        String parentCategoryNo = mallCategoryVO.getParentCategoryNo();
        String categoryNo = mallCategoryVO.getCategoryNo();

        if (StringUtils.isBlank(parentCategoryNo)){
            MallParentCategory mallParentCategory = mallCategoryDao.selParentCategoryByCategoryNo(shopNo, categoryNo);
            if (mallParentCategory != null){
                int i = mallCategoryDao.updateMallParentCategory(mallCategoryVO,shopNo);
            }
        }else {
            MallCategory mallCategory = mallCategoryDao.selCategoryByCategoryNo(shopNo, categoryNo);
            if (mallCategory != null && mallCategory.getParentCategoryNo().equals(parentCategoryNo)){
                int i = mallCategoryDao.updateMallCategory(mallCategoryVO,shopNo);
            }
        }
        List<MallCategoryDTO> mallCategoryDTOS = selCategoryByShopNo();
        return mallCategoryDTOS;
    }


}
