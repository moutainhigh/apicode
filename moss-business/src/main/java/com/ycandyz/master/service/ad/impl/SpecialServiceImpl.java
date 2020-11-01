package com.ycandyz.master.service.ad.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.ad.SpecialDao;
import com.ycandyz.master.domain.enums.ad.AdvertisingEnum;
import com.ycandyz.master.domain.enums.ad.SpecialEnum;
import com.ycandyz.master.domain.enums.mall.MallItemEnum;
import com.ycandyz.master.domain.query.ad.SpecailItemQuery;
import com.ycandyz.master.domain.query.ad.SpecialQuery;
import com.ycandyz.master.dto.ad.SpecialDTO;
import com.ycandyz.master.entities.ad.HomeCategory;
import com.ycandyz.master.entities.ad.SpecailItem;
import com.ycandyz.master.entities.ad.Special;
import com.ycandyz.master.entities.mall.MallCategory;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.model.ad.SpecialModel;
import com.ycandyz.master.service.ad.ISpecialService;
import com.ycandyz.master.service.mall.goodsManage.impl.MallItemServiceImpl;
import com.ycandyz.master.service.mall.impl.MallHomeCategoryServiceImpl;
import com.ycandyz.master.service.mall.impl.MallHomeItemServiceImpl;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.IDGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * @Description 首页-专题 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-14
 * @version 2.0
 */
@Slf4j
@Service
public class SpecialServiceImpl extends BaseService<SpecialDao,Special,SpecialQuery> implements ISpecialService {

    @Autowired
    private MallHomeItemServiceImpl mallItemService;
    @Autowired
    private HomeCategoryServiceImpl homeCategoryService;
    @Autowired
    private SpecailItemServiceImpl specailItemService;
    @Autowired
    private MallHomeCategoryServiceImpl mallCategoryService;

    @Override
    public boolean removeById(Serializable id) {
        Special special = baseMapper.selectById(id);
        AssertUtils.notNull(special, "为匹配到对应的数据");
        AssertUtils.isTrue(special.getShopNo().equals(getShopNo()), "操作不合法,没有此数据操作权限");
        return SqlHelper.retBool(this.baseMapper.deleteById(id));
    }

    @Override
    public SpecialDTO selectById(Long id) {
        Special entity = baseMapper.selectById(id);
        SpecialDTO f = new SpecialDTO();
        BeanUtils.copyProperties(entity,f);
        SpecailItemQuery query = new SpecailItemQuery();
        query.setShopNo(entity.getShopNo());
        query.setSpecialNo(entity.getSpecialNo());
        List<MallItem> list = specailItemService.selectList(query);
        f.setItems(list);
        processCategory(f);
        return f;
    }

    @Transactional
    @Override
    public boolean insert(SpecialModel entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        entity.setShopNo(getShopNo());
        entity.setSpecialNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
        if(SpecialEnum.Type.GOODS.getCode().equals(entity.getType())){
            AssertUtils.notNull(entity.getItemNoList(), "商品编号不能为空");
            entity.getItemNoList().forEach(i -> {
                SpecailItem specailItem = new SpecailItem();
                specailItem.setItemNo(i);
                specailItem.setShopNo(entity.getShopNo());
                specailItem.setSpecialNo(entity.getSpecialNo());
                specailItemService.save(specailItem);
            });
        }else{
            AssertUtils.notNull(entity.getCategoryNo(), "分类编号不能为空");
            LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<MallCategory>()
                    .in(MallCategory::getCategoryNo, entity.getCategoryNo().split(","));
            List<MallCategory> list = mallCategoryService.list(queryWrapper);
            boolean isShop = list.stream().anyMatch(l -> !entity.getShopNo().equals(l.getShopNo()));
            AssertUtils.isFalse(isShop, "该分类不属于此商店");
        }
        Special t = new Special();
        BeanUtils.copyProperties(entity,t);
        return super.save(t);
    }

    @Override
    public boolean update(SpecialModel entity) {
        AssertUtils.notNull(getShopNo(), "商店编号不能为空");
        entity.setShopNo(getShopNo());
        AssertUtils.notNull(entity.getSpecialNo(), "专题编号不能为空");
        Special special = baseMapper.selectById(entity.getId());
        AssertUtils.notNull(special, "为匹配到对应的数据");
        AssertUtils.isTrue(special.getShopNo().equals(getShopNo()), "操作不合法,没有此数据操作权限");
        if(SpecialEnum.Type.GOODS.getCode().equals(entity.getType())){
            AssertUtils.notNull(entity.getItemNoList(), "商品编号不能为空");
            LambdaQueryWrapper<SpecailItem> queryWrapper = new LambdaQueryWrapper<SpecailItem>()
                    .eq(SpecailItem::getSpecialNo, entity.getSpecialNo());
            specailItemService.remove(queryWrapper);
            entity.getItemNoList().forEach(i -> {
                SpecailItem specailItem = new SpecailItem();
                specailItem.setItemNo(i);
                specailItem.setShopNo(entity.getShopNo());
                specailItem.setSpecialNo(entity.getSpecialNo());
                specailItemService.save(specailItem);
            });
            entity.setCategoryNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
        }else{
            AssertUtils.notNull(entity.getCategoryNo(), "分类编号不能为空");
            LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<MallCategory>()
                    .in(MallCategory::getCategoryNo, entity.getCategoryNo().split(","));
            List<MallCategory> list = mallCategoryService.list(queryWrapper);
            boolean isShop = list.stream().anyMatch(l -> !entity.getShopNo().equals(l.getShopNo()));
            AssertUtils.isFalse(isShop, "该分类不属于此商店");
        }
        Special t = new Special();
        BeanUtils.copyProperties(entity,t);
        return super.updateById(t);
    }
    
    @Override
    public Page<Special> page(Page page, SpecialQuery query) {
        AssertUtils.notNull(query.getShopNo(), "商店编号不能为空");
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
        LambdaQueryWrapper<Special> queryWrapper = new LambdaQueryWrapper<Special>()
                .apply(query.getCreatedTime() != null,
                        "date_format (created_time,'%Y-%m-%d') = date_format('" + DateUtil.formatDate(query.getCreatedTime()) + "','%Y-%m-%d')")
                .apply(null != query.getCreatedTimeS(),
                        "date_format (created_time,'%Y-%m-%d') >= date_format('" + DateUtil.formatDate(query.getCreatedTimeS()) + "','%Y-%m-%d')")
                .apply(null != query.getCreatedTimeE(),
                        "date_format (created_time,'%Y-%m-%d') <= date_format('" + queryEndDate(query.getCreatedTimeE()) + "','%Y-%m-%d')")
                .like(StrUtil.isNotEmpty(query.getTitle()),Special::getTitle,query.getTitle())
                .eq(Special::getShopNo, query.getShopNo())
                .orderByDesc(Special::getSort,Special::getCreatedTime);
        Page<Special> pageResult = (Page<Special>) baseMapper.selectPage(page, queryWrapper);
        pageResult.getRecords().forEach(f -> processCategory(f));
        return pageResult;
    }


    @Override
    public Page<Special> selectHomePage(Page page, SpecialQuery query) {
        AssertUtils.notNull(query.getShopNo(), "商店编号不能为空");
        if (null != query.getCreatedTimeS() && null != query.getCreatedTimeE() && query.getCreatedTimeS().equals(query.getCreatedTimeE())){
            query.setCreatedTime(query.getCreatedTimeS());
            query.setCreatedTimeS(null);
            query.setCreatedTimeE(null);
        }
        LambdaQueryWrapper<Special> queryWrapper = new LambdaQueryWrapper<Special>()
                .apply(query.getCreatedTime() != null,
                        "date_format (created_time,'%Y-%m-%d') = date_format('" + DateUtil.formatDate(query.getCreatedTime()) + "','%Y-%m-%d')")
                .apply(null != query.getCreatedTimeS(),
                        "date_format (created_time,'%Y-%m-%d') >= date_format('" + DateUtil.formatDate(query.getCreatedTimeS()) + "','%Y-%m-%d')")
                .apply(null != query.getCreatedTimeE(),
                        "date_format (created_time,'%Y-%m-%d') <= date_format('" + queryEndDate(query.getCreatedTimeE())+ "','%Y-%m-%d')")
                .like(StrUtil.isNotEmpty(query.getTitle()),Special::getTitle,query.getTitle())
                .eq(Special::getShopNo, query.getShopNo())
                .eq(Special::getEnabled, SpecialEnum.EnabledEnum.DISABLE.getCode())
                .orderByDesc(Special::getSort,Special::getCreatedTime);
        Page<Special> pageResult = (Page<Special>) baseMapper.selectPage(page, queryWrapper);
        pageResult.getRecords().forEach(f -> processCategory(f));
        return pageResult;
    }

    @Override
    public List<Special> selectHomeList(SpecialQuery query) {
        AssertUtils.notNull(query.getShopNo(), "商店编号不能为空");
        LambdaQueryWrapper<Special> queryWrapper = new LambdaQueryWrapper<Special>()
                .select(Special::getTitle,Special::getSpecialNo,Special::getShopNo,Special::getCategoryNo,Special::getType,Special::getAddress,Special::getId,Special::getImg)
                .apply(query.getCreatedTime() != null,
                        "date_format (created_time,'%Y-%m-%d') = date_format('" + DateUtil.formatDate(query.getCreatedTime()) + "','%Y-%m-%d')")
                .apply(null != query.getCreatedTimeS(),
                        "date_format (created_time,'%Y-%m-%d') >= date_format('" + DateUtil.formatDate(query.getCreatedTimeS()) + "','%Y-%m-%d')")
                .apply(null != query.getCreatedTimeE(),
                        "date_format (created_time,'%Y-%m-%d') <= date_format('" + queryEndDate(query.getCreatedTimeE()) + "','%Y-%m-%d')")
                .like(StrUtil.isNotEmpty(query.getTitle()),Special::getTitle,query.getTitle())
                .eq(Special::getShopNo, query.getShopNo())
                .eq(Special::getEnabled, SpecialEnum.EnabledEnum.DISABLE.getCode())
                .orderByDesc(Special::getSort,Special::getCreatedTime);
        List<Special> result = baseMapper.selectList(queryWrapper);
        List<Special> list =result.stream().filter(f -> isDelete(f)).limit(10).collect(Collectors.toList());
        return list;
    }

    private void processCategory(Special f){
        SpecialEnum.Type type = SpecialEnum.Type.parseCode(f.getType());
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

    private boolean isDelete(Special f){
        SpecialEnum.Type type = SpecialEnum.Type.parseCode(f.getType());
        if(type.getCode().equals(AdvertisingEnum.Type.GOODS.getCode())){
            //获取商品是否存在,不存在测去掉
            SpecailItemQuery specailItemQuery = new SpecailItemQuery();
            specailItemQuery.setShopNo(f.getShopNo());
            specailItemQuery.setSpecialNo(f.getSpecialNo());
            List<String> specailItemList = specailItemService.selectItemNoList(specailItemQuery);
            if(CollectionUtil.isEmpty(specailItemList)){
                return false;
            }
            LambdaQueryWrapper<MallItem> mallItemWrapper = new LambdaQueryWrapper<MallItem>()
                    .in(MallItem::getItemNo,specailItemList)
                    .in(MallItem::getStatus, MallItemEnum.Status.START_10.getCode(),MallItemEnum.Status.START_30.getCode());
            List<MallItem> mallItem = mallItemService.list(mallItemWrapper);
            if(CollectionUtil.isEmpty(mallItem)){
                return false;
            }
            return true;
        } else{
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
