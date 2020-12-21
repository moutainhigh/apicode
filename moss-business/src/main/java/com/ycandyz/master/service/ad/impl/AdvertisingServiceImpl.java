package com.ycandyz.master.service.ad.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.ad.AdvertisingDao;
import com.ycandyz.master.domain.enums.ad.AdvertisingEnum;
import com.ycandyz.master.domain.enums.ad.SpecialEnum;
import com.ycandyz.master.domain.enums.mall.MallItemEnum;
import com.ycandyz.master.domain.query.ad.AdvertisingQuery;
import com.ycandyz.master.entities.ad.Advertising;
import com.ycandyz.master.entities.ad.HomeCategory;
import com.ycandyz.master.entities.mall.MallCategory;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.enums.EnabledEnum;
import com.ycandyz.master.service.ad.IAdvertisingService;
import com.ycandyz.master.service.mall.impl.MallCategoryServiceImpl;
import com.ycandyz.master.service.mall.impl.MallItemServiceImpl;
import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * @Description 轮播图 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Slf4j
@Service
public class AdvertisingServiceImpl extends BaseService<AdvertisingDao,Advertising,AdvertisingQuery> implements IAdvertisingService {

    @Autowired
    private HomeCategoryServiceImpl homeCategoryService;
    @Autowired
    private MallItemServiceImpl mallHomeItemService;
    @Autowired
    private MallCategoryServiceImpl mallCategoryService;

    @Override
    public boolean removeById(Serializable id) {
        Advertising advertising = baseMapper.selectById(id);
        AssertUtils.notNull(advertising, "为匹配到对应的数据");
        AssertUtils.isTrue(advertising.getShopNo().equals(getShopNo()), "操作不合法,没有此数据操作权限");
        return SqlHelper.retBool(this.baseMapper.deleteById(id));
    }

    @Override
    public Advertising getById(Long id) {
        Advertising f = super.getById(id);
        if(null != f){
            processCategory(f);
        }
        return f;
    }

    @Transactional
    @Override
    public boolean save(Advertising entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        entity.setShopNo(getShopNo());
        //校验启用的轮播图数量,不能超过10个
        AdvertisingEnum.EnabledEnum enabled = AdvertisingEnum.EnabledEnum.parseCode(entity.getEnabled());
        AssertUtils.notNull(enabled, "是否展示状态类型不正确");
        if(AdvertisingEnum.EnabledEnum.DISABLE.getCode().equals(enabled.getCode())){
            LambdaQueryWrapper<Advertising> advertisingWrapper = new LambdaQueryWrapper<Advertising>()
                    .eq(Advertising::getEnabled,AdvertisingEnum.EnabledEnum.DISABLE.getCode())
                    .eq(Advertising::getShopNo,entity.getShopNo());
            List<Advertising> l = baseMapper.selectList(advertisingWrapper);
            AssertUtils.isFalse(CollectionUtil.isNotEmpty(l) && l.size()>9, "展示的轮播图已经超过最大10张限制,请取消展示或将其他轮播图取消展示");
        }

        if(SpecialEnum.Type.GOODS.getCode().equals(entity.getType())){
            AssertUtils.notNull(entity.getItemNo(), "商品编号不能为空");
        }else{
            AssertUtils.notNull(entity.getCategoryNo(), "分类编号不能为空");
            LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<MallCategory>()
                    .in(MallCategory::getCategoryNo, entity.getCategoryNo().split(","));
            List<MallCategory> list = mallCategoryService.list(queryWrapper);
            boolean isShop = list.stream().anyMatch(l -> !entity.getShopNo().equals(l.getShopNo()));
            AssertUtils.isFalse(isShop, "该分类不属于此商店");
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(Advertising entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        entity.setShopNo(getShopNo());
        //校验启用的轮播图数量,不能超过10个
        AdvertisingEnum.EnabledEnum enabled = AdvertisingEnum.EnabledEnum.parseCode(entity.getEnabled());
        AssertUtils.notNull(enabled, "是否展示状态类型不正确");
        Advertising advertising = baseMapper.selectById(entity.getId());
        AssertUtils.notNull(advertising, "为匹配到对应的数据");
        AssertUtils.isTrue(advertising.getShopNo().equals(getShopNo()), "操作不合法,没有此数据操作权限");
        if(AdvertisingEnum.EnabledEnum.DISABLE.getCode().equals(enabled.getCode())){
            LambdaQueryWrapper<Advertising> advertisingWrapper = new LambdaQueryWrapper<Advertising>()
                    .eq(Advertising::getEnabled,AdvertisingEnum.EnabledEnum.DISABLE.getCode())
                    .eq(Advertising::getShopNo,entity.getShopNo());
            List<Advertising> l = baseMapper.selectList(advertisingWrapper);
            AssertUtils.isFalse(CollectionUtil.isNotEmpty(l) && l.size()>9, "展示的轮播图已经超过最大10张限制,请取消展示或将其他轮播图取消展示");
        }
        if(SpecialEnum.Type.GOODS.getCode().equals(entity.getType())){
            AssertUtils.notNull(entity.getItemNo(), "商品编号不能为空");
        }else{
            AssertUtils.notNull(entity.getCategoryNo(), "分类编号不能为空");
            LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<MallCategory>()
                    .in(MallCategory::getCategoryNo, entity.getCategoryNo().split(","));
            List<MallCategory> list = mallCategoryService.list(queryWrapper);
            boolean isShop = list.stream().anyMatch(l -> !entity.getShopNo().equals(l.getShopNo()));
            AssertUtils.isFalse(isShop, "该分类不属于此商店");
        }
        return super.updateById(entity);
    }

    @Override
    public Page<Advertising> page(Page page, AdvertisingQuery query) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        query.setShopNo(getShopNo());
        if (null != query.getCreatedTimeS() && null != query.getCreatedTimeE() && query.getCreatedTimeS().equals(query.getCreatedTimeE())){
            query.setCreatedTime(query.getCreatedTimeS());
            query.setCreatedTimeS(null);
            query.setCreatedTimeE(null);
        }
        if(StrUtil.isNotEmpty(query.getTitle()) && StrUtil.isNotEmpty(query.getTitle().trim())){
            query.setTitle(query.getTitle().trim());
        }else{
            query.setTitle(null);
        }
        LambdaQueryWrapper<Advertising> queryWrapper = new LambdaQueryWrapper<Advertising>()
                .apply(query.getCreatedTime() != null,
                        "date_format (created_time,'%Y-%m-%d') = date_format('" + DateUtil.formatDate(query.getCreatedTime()) + "','%Y-%m-%d')")
                .apply(null != query.getCreatedTimeS(),
                        "date_format (created_time,'%Y-%m-%d') >= date_format('" + DateUtil.formatDate(query.getCreatedTimeS()) + "','%Y-%m-%d')")
                .apply(query != null && null != query.getCreatedTimeE(),
                        "date_format (created_time,'%Y-%m-%d') <= date_format('" + queryEndDate(query.getCreatedTimeE()) + "','%Y-%m-%d')")
                .like(StrUtil.isNotEmpty(query.getTitle()),Advertising::getTitle,query.getTitle())
                .eq(Advertising::getShopNo, query.getShopNo())
                .orderByDesc(Advertising::getSort,Advertising::getCreatedTime);
        Page<Advertising> pageResule = (Page<Advertising>) baseMapper.selectPage(page, queryWrapper);
        pageResule.getRecords().forEach(f -> processCategory(f));
        return pageResule;
    }

    @Override
    public List<Advertising> selectHomeList(AdvertisingQuery query) {
        AssertUtils.notNull(query.getShopNo(), "商店编号不能为空");
        LambdaQueryWrapper<Advertising> queryWrapper = new LambdaQueryWrapper<Advertising>()
                .select(Advertising::getTitle,Advertising::getAddress,Advertising::getShopNo,Advertising::getCategoryNo,Advertising::getItemNo,Advertising::getType,Advertising::getId,Advertising::getImg)
                .apply(query.getCreatedTime() != null,
                        "date_format (created_time,'%Y-%m-%d') = date_format('" + DateUtil.formatDate(query.getCreatedTime()) + "','%Y-%m-%d')")
                .like(StrUtil.isNotEmpty(query.getTitle()),Advertising::getTitle,query.getTitle())
                .eq(Advertising::getShopNo, query.getShopNo())
                .eq(Advertising::getEnabled, EnabledEnum.ENABLED.value())
                .orderByDesc(Advertising::getSort,Advertising::getCreatedTime);
        List<Advertising> result = baseMapper.selectList(queryWrapper);
        List<Advertising> AdvertisingList =result.stream().filter(f -> isDelete(f)).limit(10).collect(Collectors.toList());
        return AdvertisingList;
    }

    private void processCategory(Advertising f){
        AdvertisingEnum.Type type = AdvertisingEnum.Type.parseCode(f.getType());
        if(type.getCode().equals(AdvertisingEnum.Type.PAGE.getCode())){
            //获取分类页面是否存在,父级存在则存在,子集不存在则去掉子集
            StringBuffer sb = new StringBuffer();
            StringBuffer sbName = new StringBuffer();
            if(StrUtil.isNotEmpty(f.getCategoryNo())){
                for (String s: f.getCategoryNo().split(",")){
                    if(s.length() < 10){
                        LambdaQueryWrapper<HomeCategory> homeCategoryWrapper = new LambdaQueryWrapper<HomeCategory>()
                                .eq(HomeCategory::getCategoryNo,s);
                        HomeCategory homeCategory = homeCategoryService.getOne(homeCategoryWrapper);
                        log.info("分类页面未找到 CategoryNo ==> {}",s);
                        AssertUtils.notNull(homeCategory, "分类页面未找到");
                        sbName.append(homeCategory.getCategoryName());
                        sb.append(s);
                        log.debug("一级分类页面 CategoryNo {},CategoryName {}",s,homeCategory.getCategoryName());
                    }else{
                        LambdaQueryWrapper<MallCategory> mallCategoryWrapper = new LambdaQueryWrapper<MallCategory>()
                                .eq(MallCategory::getCategoryNo,s)
                                .eq(MallCategory::getStatus, 1);
                        MallCategory mallCategory = mallCategoryService.getOne(mallCategoryWrapper);
                        if(null != mallCategory){
                            sb.append(",");
                            sb.append(s);
                            sbName.append(",");
                            sbName.append(mallCategory.getCategoryName());
                            log.debug("二级分类页面 CategoryNo {},CategoryName {}",s,mallCategory.getCategoryName());
                        }else{
                            log.info("二级分类页面不存在 CategoryNo {}",s);
                            break;
                        }
                    }
                }
                log.debug("处理后的分类页面 CategoryNo {},CategoryName {}",sb.toString(),sbName.toString());
                f.setCategoryNo(sb.toString());
                f.setAddress(sbName.toString());
            }
        }
    }

    private boolean isDelete(Advertising f){
        AdvertisingEnum.Type type = AdvertisingEnum.Type.parseCode(f.getType());
        if(type.getCode().equals(AdvertisingEnum.Type.GOODS.getCode())){
            //获取商品是否存在,不存在测去掉
            LambdaQueryWrapper<MallItem> mallItemWrapper = new LambdaQueryWrapper<MallItem>()
                    .eq(MallItem::getIsScreen,MallItemEnum.IsScreen.START_0.getCode())
                    .eq(MallItem::getItemNo,f.getItemNo())
                    .in(MallItem::getStatus, MallItemEnum.Status.START_10.getCode(),MallItemEnum.Status.START_30.getCode());
            MallItem mallItem = mallHomeItemService.getOne(mallItemWrapper);
            if(null == mallItem){
                return false;
            }
            return true;
        }else{
            //获取分类页面是否存在,父级存在则存在,子集不存在则去掉子集
            StringBuffer sb = new StringBuffer();
            StringBuffer sbName = new StringBuffer();
            if(StrUtil.isNotEmpty(f.getCategoryNo())){
                boolean pageType = false;
                for (String s: f.getCategoryNo().split(",")){
                    if(s.length() < 10){
                        LambdaQueryWrapper<HomeCategory> homeCategoryWrapper = new LambdaQueryWrapper<HomeCategory>()
                                .eq(HomeCategory::getCategoryNo,s);
                        HomeCategory homeCategory = homeCategoryService.getOne(homeCategoryWrapper);
                        if("1".equals(homeCategory.getType())){
                            pageType = true;
                        }
                        log.info("分类页面未找到 CategoryNo ==> {}",s);
                        AssertUtils.notNull(homeCategory, "分类页面未找到");
                        sbName.append(homeCategory.getCategoryName());
                        sb.append(s);
                        log.debug("一级分类页面 CategoryNo {},CategoryName {}",s,homeCategory.getCategoryName());
                    }else{
                        LambdaQueryWrapper<MallCategory> mallCategoryWrapper = new LambdaQueryWrapper<MallCategory>()
                                .eq(MallCategory::getCategoryNo,s)
                                .eq(MallCategory::getStatus, 1);
                        MallCategory mallCategory = mallCategoryService.getOne(mallCategoryWrapper);
                        if(null != mallCategory){
                            sb.append(",");
                            sb.append(s);
                            sbName.append(",");
                            sbName.append(mallCategory.getCategoryName());
                            log.debug("二级分类页面 CategoryNo {},CategoryName {}",s,mallCategory.getCategoryName());
                        }else{
                            log.info("二级分类页面不存在 CategoryNo {}",s);
                            break;
                        }
                    }
                }
                if(pageType && sb.toString().split(",").length < 2){
                    log.info("分类页面不存在 CategoryNo {}",sb.toString());
                    return false;
                }else{
                    log.debug("处理后的分类页面 CategoryNo {},CategoryName {}",sb.toString(),sbName.toString());
                    f.setCategoryNo(sb.toString());
                    f.setAddress(sbName.toString());
                    return true;
                }
            }
            else{
                return false;
            }
        }
    }

}
