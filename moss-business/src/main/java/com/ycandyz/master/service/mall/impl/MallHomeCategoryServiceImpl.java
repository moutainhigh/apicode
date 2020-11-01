package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.MallHomeCategoryDao;
import com.ycandyz.master.domain.enums.mall.MallCategoryEnum;
import com.ycandyz.master.domain.query.mall.MallCategoryQuery;
import com.ycandyz.master.entities.mall.MallCategory;
import com.ycandyz.master.service.mall.IMallHomeCategoryService;
import com.ycandyz.master.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * @Description 商城 - 商品分类表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
@Service
public class MallHomeCategoryServiceImpl extends BaseService<MallHomeCategoryDao, MallCategory, MallCategoryQuery> implements IMallHomeCategoryService {

    @Override
    public List<MallCategory> selectByLayerShopNo(String shopNo,String parentCategoryNo) {
        AssertUtils.notNull(shopNo, "商店编号不能为空!");
        LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<MallCategory>()
                .eq(StrUtil.isEmpty(parentCategoryNo),MallCategory::getParentCategoryNo,"")
                .eq(StrUtil.isNotEmpty(parentCategoryNo),MallCategory::getParentCategoryNo,parentCategoryNo)
                .eq(MallCategory::getShopNo,shopNo)
                .eq(MallCategory::getStatus, MallCategoryEnum.Status.YES.getCode())
                .orderByDesc(MallCategory::getSortValue);
        return super.list(queryWrapper);
    }

    @Override
    public List<MallCategory> selectByShopNo(String shopNo) {
        AssertUtils.notNull(shopNo, "商店编号不能为空!");
        return baseMapper.selectMallCategoryList(shopNo);
    }

    /*@Override
    public List<MallCategory> selectByShopNo(String shopNo) {
        AssertUtils.notNull(shopNo, "商店编号不能为空!");
        LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<MallCategory>()
                .eq(MallCategory::getShopNo,shopNo)
                .eq(MallCategory::getStatus, MallCategoryEnum.Status.YES.getCode())
                .orderByAsc(MallCategory::getLayer,MallCategory::getCreatedTime,MallCategory::getId);
        return super.list(queryWrapper).stream().filter(f -> 0 != f.getLayer()).collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(user -> user.getCategoryName()))), ArrayList::new))
                .stream().limit(8).collect(Collectors.toList());
    }*/
}
