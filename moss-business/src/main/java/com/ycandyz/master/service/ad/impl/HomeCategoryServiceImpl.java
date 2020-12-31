package com.ycandyz.master.service.ad.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.ad.HomeCategoryDao;
import com.ycandyz.master.domain.query.ad.HomeCategoryQuery;
import com.ycandyz.master.dto.ad.HomeCategoryDTO;
import com.ycandyz.master.entities.ad.HomeCategory;
import com.ycandyz.master.entities.mall.MallCategory;
import com.ycandyz.master.service.ad.IHomeCategoryService;
import com.ycandyz.master.service.mall.impl.MallCategoryServiceImpl;
import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * @Description 首页- 轮播图分类表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
@Slf4j
@Service
public class HomeCategoryServiceImpl extends BaseService<HomeCategoryDao,HomeCategory,HomeCategoryQuery> implements IHomeCategoryService {

    @Autowired
    private MallCategoryServiceImpl mallCategoryService;

    @Override
    public List<HomeCategory> getListByParentCategoryNo(HomeCategoryQuery query) {
        AssertUtils.notNull(query.getCategoryNo(), "分类编号不能为空");
        AssertUtils.notNull(query.getShopNo(), "商店编号不能为空");
        if(StrUtil.isNotEmpty(query.getShopNo()) && !"0".equals(query.getCategoryNo())){
            //查原来分类表
            List<MallCategory> list = mallCategoryService.selectByLayerShopNo(query.getShopNo(),"3".equals(query.getCategoryNo())?null:query.getCategoryNo());
            if (CollectionUtils.isNotEmpty(list)) {
                List<HomeCategory> homeCategoryList = list.stream().map(l -> {
                    HomeCategory vo = new HomeCategory();
                    BeanUtil.copyProperties(l,vo);
                    return vo;
                }).collect(Collectors.toList());
                return homeCategoryList;
            }
            return null;
        }else{
            //只查当前表一级分类
            LambdaQueryWrapper<HomeCategory> queryWrapper = new LambdaQueryWrapper<HomeCategory>()
                    .eq(StrUtil.isNotEmpty(query.getCategoryNo()),HomeCategory::getParentCategoryNo,query.getCategoryNo())
                    .orderByDesc(HomeCategory::getSortValue);
            return baseMapper.selectList(queryWrapper);
        }
    }

    @Override
    public List<HomeCategoryDTO> getListByChildCategoryNo(String categoryNo) {
        AssertUtils.notNull(categoryNo, "分类编号不能为空");
        return getListByChildCategoryNo(null,categoryNo);
    }

    @Override
    public HomeCategoryDTO getByChildCategoryNo(String categoryNo) {
        AssertUtils.notNull(categoryNo, "分类编号不能为空");
        return getByChildCategoryNo(null,categoryNo);
    }

    /**
     *  根据子级编号递归父级,选中父级并返回父级的兄弟级
     * @param list 子集合
     * @param categoryNo 子编号
     * @return 集合嵌套集合
     */
    public List<HomeCategoryDTO> getListByChildCategoryNo(List<HomeCategoryDTO> list, String categoryNo) {
        LambdaQueryWrapper<HomeCategory> queryWrapper = new LambdaQueryWrapper<HomeCategory>()
                .eq(HomeCategory::getCategoryNo,categoryNo);
        HomeCategory entity = baseMapper.selectOne(queryWrapper);
        if(null == entity){
            return list;
        }
        List<HomeCategoryDTO> entityList = baseMapper.selectByParentCategoryNo(entity.getParentCategoryNo());
        for(HomeCategoryDTO i:entityList){
            if(categoryNo.equals(i.getCategoryNo())){
                i.setSelected(true);
                i.setHomeCategoryList(list);
                if(StrUtil.isNotEmpty(i.getParentCategoryNo()) && !"0".equals(i.getParentCategoryNo())){
                    return getListByChildCategoryNo(entityList,i.getParentCategoryNo());
                }
            }else{
                i.setSelected(false);
            }
        }
        return entityList;
    }

    /**
     *  根据子级编号递归父级,选中父级并返回父级的兄弟级
     * @param entityChild 子实体
     * @param categoryNo 子编号
     * @return 集合嵌套集合
     */
    public HomeCategoryDTO getByChildCategoryNo(HomeCategoryDTO entityChild, String categoryNo) {
        HomeCategoryDTO i = baseMapper.selectByCategoryNo(categoryNo);
        if(null == i){
            return entityChild;
        }
        if(categoryNo.equals(i.getCategoryNo())){
            i.setSelected(true);
            i.setHomeCategory(entityChild);
            if(StrUtil.isNotEmpty(i.getParentCategoryNo()) && !"0".equals(i.getParentCategoryNo())){
                return getByChildCategoryNo(i,i.getParentCategoryNo());
            }
        }else{
            i.setSelected(false);
        }
        return i;
    }
}
