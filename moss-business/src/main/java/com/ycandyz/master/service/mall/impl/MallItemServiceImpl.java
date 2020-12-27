package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.MallItemHomeDao;
import com.ycandyz.master.dao.mall.MallShopDao;
import com.ycandyz.master.dao.organize.OrganizeRelDao;
import com.ycandyz.master.domain.enums.mall.MallItemEnum;
import com.ycandyz.master.domain.enums.mall.MallItemVideoEnum;
import com.ycandyz.master.domain.model.mall.*;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallCategoryResp;
import com.ycandyz.master.domain.response.mall.MallItemPageResp;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.dto.mall.MallShopDTO;
import com.ycandyz.master.entities.mall.*;
import com.ycandyz.master.entities.organize.OrganizeRel;
import com.ycandyz.master.service.mall.IMallItemService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.IDGeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>
 * @Description 商品表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Service
public class MallItemServiceImpl extends BaseService<MallItemHomeDao, MallItem, MallItemBaseQuery> implements IMallItemService {

    @Autowired
    private MallItemVideoServiceImpl mallItemVideoService;
    @Autowired
    private MallSkuServiceImpl mallSkuService;
    @Autowired
    private MallSkuSpecServiceImpl mallSkuSpecService;
    @Autowired
    private MallCategoryServiceImpl mallCategoryService;
    @Autowired
    private OrganizeRelDao organizeRelDao;
    @Autowired
    private MallShopDao mallShopDao;

    @Override
    public MallItemResp getByItemNo(String itemNo) {
        MallItemResp vo = new MallItemResp();
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo, itemNo);
        MallItem entity = baseMapper.selectOne(queryWrapper);
        if(entity != null){
            if(StrUtil.isNotEmpty(entity.getBanners())){
                entity.setBanners(entity.getBanners().replaceAll("\"",""));
            }
        }
        MallItem t = baseMapper.selectMallItemById(entity.getId());
        //entity.setPickupAddressIds(t.getPickupAddressIds());
        //entity.setDeliveryType(t.getDeliveryType());
        BeanUtil.copyProperties(entity,vo);
        List<Integer> pl = JSONObject.parseArray(t.getPickupAddressIds(), Integer.class);
        List<Integer> dl = JSONObject.parseArray(t.getDeliveryType(), Integer.class);
        vo.setPickupAddressIds(pl);
        vo.setDeliveryType(dl);
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(entity.getType());
        if(MallItemEnum.Type.Type_1.getCode().equals(type.getCode())){
//获取sku
            LambdaQueryWrapper<MallSku> skuWrapper = new LambdaQueryWrapper<MallSku>()
                    .select(MallSku::getSkuNo,MallSku::getSalePrice,MallSku::getPrice,MallSku::getGoodsNo,MallSku::getStock,MallSku::getSkuImg)
                    .eq(MallSku::getItemNo, vo.getItemNo());
            List<MallSku> skus = mallSkuService.list(skuWrapper);
            skus.stream().forEach(f -> {
                LambdaQueryWrapper<MallSkuSpec> skuSpecWrapper = new LambdaQueryWrapper<MallSkuSpec>()
                        .select(MallSkuSpec::getSpecName,MallSkuSpec::getSpecValue,MallSkuSpec::getExistImg)
                        .eq(MallSkuSpec::getSkuNo, f.getSkuNo());
                List<MallSkuSpec> skuSpeclist = mallSkuSpecService.list(skuSpecWrapper);
                f.setSkuSpecs(skuSpeclist);
            });
            vo.setSkus(skus);

            //处理specs
            List<MallSpecsModel> specs = new ArrayList<>();
            List<MallSkuSpec> skuSpecs = new ArrayList<>();
            skus.stream().forEach(s -> {
                s.getSkuSpecs().stream().forEach(i -> {
                    if(i.getExistImg()==1){
                        i.setSpecImg(s.getSkuImg());
                        i.setExistImg(null);
                    }else {
                        i.setExistImg(null);
                        i.setSpecImg("");
                    }
                });
            });
            skus.stream().forEach(s -> skuSpecs.addAll(s.getSkuSpecs()));
            Map<String, List<MallSkuSpec>> map = skuSpecs.stream().collect(Collectors.groupingBy(MallSkuSpec::getSpecName));
            for(String key : map.keySet()){
                MallSpecsModel m = new MallSpecsModel();
                m.setSpecName(key);
                List<MallSkuSpec> sk = map.get(key).stream().collect(
                        collectingAndThen(
                                toCollection(() -> new TreeSet<>(Comparator.comparing(MallSkuSpec::getSpecValue))), ArrayList::new)
                );
                //sk.stream().forEach(s -> s.setSpecName(null));
                m.setSpecList(sk);
                specs.add(m);
            }
            vo.setSpecs(specs);

        }

        //TODO 处理分类名称与一级分类
        LambdaQueryWrapper<MallCategory> categoryWrapper = new LambdaQueryWrapper<MallCategory>()
                .eq(MallCategory::getCategoryNo, vo.getCategoryNo());
        MallCategory category = mallCategoryService.getOne(categoryWrapper);
        vo.setCategoryName(category.getCategoryName());
        vo.setParentCategoryNo(category.getParentCategoryNo());
        LambdaQueryWrapper<MallCategory> pcWrapper = new LambdaQueryWrapper<MallCategory>()
                .eq(MallCategory::getCategoryNo, category.getParentCategoryNo());
        MallCategory pc = mallCategoryService.getOne(pcWrapper);
        vo.setParentCategoryName(pc.getCategoryName());

        //获取置顶视频
        LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                .select(MallItemVideo::getUrl,MallItemVideo::getImg)
                .eq(MallItemVideo::getItemNo, vo.getItemNo())
                .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_0.getCode());
        List<MallItemVideo> topVideo = mallItemVideoService.list(videoWrapper);
        vo.setTopVideoList(topVideo);
        //获取详情视频
        LambdaQueryWrapper<MallItemVideo> detailVideoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                .select(MallItemVideo::getUrl,MallItemVideo::getImg)
                .eq(MallItemVideo::getItemNo, vo.getItemNo())
                .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_1.getCode());
        List<MallItemVideo> detailVideo = mallItemVideoService.list(detailVideoWrapper);
        vo.setDetailVideoList(detailVideo);

        vo.setSales(entity.getBaseSales()+entity.getRealSales());

        return vo;
    }

    @Override
    public boolean deleteByItemNo(String itemNo) {
        MallItem entity = new MallItem();
        entity.setItemNo(itemNo);
        entity.setStatus(MallItemEnum.Status.START_50.getCode());
        return SqlHelper.retBool(baseMapper.updateByItemNo(entity));
    }

    @Override
    public Page<MallItemPageResp> getMallItemPage(Page<MallItem> page, MallItemQuery query) {
        AssertUtils.notNull(query.getIsGroup(), "是否企业标识不能为空");
        if(StrUtil.isNotEmpty(query.getItemName()) && StrUtil.isNotEmpty(query.getItemName().trim())){
            if(query.getItemName().contains("%")){
                String itemName = query.getItemName().replace("%","\\%");
                query.setItemName(itemName);
            }else{
                query.setItemName(query.getItemName().trim());
            }
        }else{
            query.setItemName(null);
        }
        //
        List<Integer> organizeIds = new ArrayList<>();  //保存企业id，用于批量查询
        Map<String, Integer> shopNoAndOrganizeId = new HashMap<>(); //保存shopNo和organizeid    map<shopNo,organizeid>
        if (query.getIsGroup().equals("0")){   //当前登陆为企业账户
            query.setShopNoList(Arrays.asList(getShopNo()));
            organizeIds.add(getOrganizeId().intValue());
            shopNoAndOrganizeId.put(getShopNo(),getOrganizeId().intValue());
        }else if (query.getIsGroup().equals("1")){ //集团
            if (query.getChildrenOrganizeId()==null || "".equals(query.getChildrenOrganizeId()) || query.getChildrenOrganizeId().equals("0")){
                //查询集团所有数据
                Long groupOrganizeId = getOrganizeId();   //集团id
                if (groupOrganizeId!=null) {
                    List<OrganizeRel> organizeRels = organizeRelDao.selectList(new QueryWrapper<OrganizeRel>().eq("group_organize_id", groupOrganizeId.intValue()).eq("status",2));
                    if (organizeRels != null && organizeRels.size() > 0) {
                        List<Integer> oids = organizeRels.stream().map(OrganizeRel::getOrganizeId).collect(Collectors.toList());
                        organizeIds.addAll(oids);
//                        organizeIds.add(groupOrganizeId.intValue());
                        List<MallShopDTO> mallShopDTOS = mallShopDao.queryByOrganizeIdList(organizeIds);
                        if (mallShopDTOS!=null && mallShopDTOS.size()>0){
                            List<String> shopNos = mallShopDTOS.stream().map(MallShopDTO::getShopNo).collect(Collectors.toList());
                            query.setShopNoList(shopNos);
                            Map<String, Integer> map = mallShopDTOS.stream().collect(Collectors.toMap(MallShopDTO::getShopNo, MallShopDTO::getOrganizeId));
                            shopNoAndOrganizeId.putAll(map);
                        }
                    }
                    //登陆用户所在企业加入初始化中
                    organizeIds.add(groupOrganizeId.intValue());
                    if (query.getShopNoList()!=null && query.getShopNoList().size()>0) {
                        query.getShopNoList().add(getShopNo());
                    }else {
                        query.setShopNoList(Arrays.asList(getShopNo()));
                    }
                    shopNoAndOrganizeId.put(getShopNo(),groupOrganizeId.intValue());
                }
            }else {
                query.setShopNoList(Arrays.asList(getShopNo()));
                organizeIds.add(Integer.valueOf(query.getChildrenOrganizeId()));
                MallShop mallShop = mallShopDao.selectOne(new QueryWrapper<MallShop>().eq("organize_id", query.getChildrenOrganizeId()));
                if (mallShop!=null){
                    query.setShopNoList(Arrays.asList(mallShop.getShopNo()));
                    shopNoAndOrganizeId.put(mallShop.getShopNo(), Integer.valueOf(query.getChildrenOrganizeId()));
                }else {
                    Page<MallItemPageResp> p = new Page<>();
                    p.setSize(page.getSize());
                    p.setRecords(new ArrayList<>());
                    p.setCurrent(page.getCurrent());
                    p.setPages(page.getPages());
                    return p;
                }
            }
        }

        Page<MallItemPageResp> p =baseMapper.getMallItemPage(page,query);
        p.getRecords().stream().forEach(f -> {
            f.setCreatedStr(f.getCreatedTime());
            LambdaQueryWrapper<MallSku> skuWrapper = new LambdaQueryWrapper<MallSku>()
                    .select(MallSku::getGoodsNo)
                    .eq(MallSku::getItemNo, f.getItemNo());
            List<String> goodsNoList = mallSkuService.list(skuWrapper).stream().map(MallSku::getGoodsNo).collect(Collectors.toList());
            f.setGoodsNoList(goodsNoList);
            MallCategoryResp categoryResp = mallCategoryService.getByChildCategoryNo(null,f.getCategoryNo());
            if(categoryResp != null){
                String categoryTxt = mallCategoryService.getByChildCategoryNo(categoryResp.getCategoryName(),categoryResp.getCategory());
                f.setCategoryTxt(categoryTxt);
            }
        });
        return p;
    }

    @Override
    public boolean insert(MallItemModel model) {
        // TODO shipping_no 值从哪来
        model.setShippingNo(model.getShippingNo() !=null?model.getShippingNo():"");
        model.setShopNo(getShopNo());
        //公共校验
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(model.getType());
        AssertUtils.notNull(type, "商品类型不正确");
        //公共处理赋值
        model.setItemNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
        String itemCover = model.getBanners().get(0);
        String banners = JSONUtil.toJsonStr(model.getBanners());
        model.setItemCover(itemCover);
        MallItem t = new MallItem();
        //销售商品
        if(type.getCode().equals(MallItemEnum.Type.Type_1.getCode())){
            //商品赋值
            List<MallItemSkuModel> skuList = model.getSkus();
            if(CollectionUtil.isNotEmpty(skuList)){
                List<MallItemSkuModel> skuMaxModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
                List<MallItemSkuModel> skuMinModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
                MallItemSkuModel skuModel = skuMinModel.get(0);
                model.setSalePrice(skuModel.getSalePrice());
                model.setPrice(skuModel.getPrice());
                model.setStock(skuModel.getStock());
                model.setGoodsNo(skuModel.getGoodsNo());
                model.setHighestSalePrice(skuMaxModel.get(0).getSalePrice());

                //添加Sku，sepc
                for (int i=0;i< model.getSkus().size();i++){
                    MallItemSkuModel f = model.getSkus().get(i);
                    AssertUtils.notNull(f.getSkuSpecs(), "Sku规格不能为空");
                    MallSku sku = new MallSku();
                    sku.setItemNo(model.getItemNo());
                    sku.setSkuNo(model.getItemNo()+"_"+StrUtil.toString(IDGeneratorUtils.getLongId()));
                    BeanUtils.copyProperties(f,sku);
                    sku.setSortValue(i);
                    mallSkuService.save(sku);
                    f.getSkuSpecs().stream().forEach(s -> {
                        MallSkuSpec skuSpec = new MallSkuSpec();
                        skuSpec.setSkuNo(sku.getSkuNo());
                        BeanUtils.copyProperties(s,skuSpec);
                        mallSkuSpecService.save(skuSpec);
                    });
                }
            }else{
                MallItemSkuModel skuModel = new MallItemSkuModel();
                skuModel.setSalePrice(model.getSalePrice());
                skuModel.setPrice(model.getPrice());
                skuModel.setStock(model.getStock());
                skuModel.setGoodsNo(model.getGoodsNo());
                model.setHighestSalePrice(model.getSalePrice());

                MallSku sku = new MallSku();
                BeanUtils.copyProperties(skuModel,sku);
                sku.setSortValue(0);
                sku.setItemNo(model.getItemNo());
                sku.setSkuNo("default_"+model.getItemNo());
                mallSkuService.save(sku);
            }

            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);
            t.setDeliveryType(JSONUtil.toJsonStr(model.getDeliveryType()));
            t.setPickupAddressIds(JSONUtil.toJsonStr(model.getPickupAddressIds()));
            baseMapper.insert(t);

            //添加视频
            if(CollectionUtil.isNotEmpty(model.getTopVideoList())){
                MallItemVideoModel videoModel = model.getTopVideoList().get(0);
                MallItemVideo video = new MallItemVideo();
                videoModel.setItemNo(t.getItemNo());
                videoModel.setType(MallItemVideoEnum.Type.TYPE_0.getCode());
                BeanUtils.copyProperties(videoModel,video);
                video.setVideoNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
                video.setImg("");
                mallItemVideoService.save(video);
            }
            if(CollectionUtil.isNotEmpty(model.getDetailVideoList())){
                MallItemVideoModel videoModel = model.getDetailVideoList().get(0);
                MallItemVideo video = new MallItemVideo();
                videoModel.setItemNo(t.getItemNo());
                videoModel.setType(MallItemVideoEnum.Type.TYPE_1.getCode());
                BeanUtils.copyProperties(videoModel,video);
                video.setVideoNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
                mallItemVideoService.save(video);
            }
        }else{
            MallItemEnum.NonPriceType nonPriceType = MallItemEnum.NonPriceType.parseCode(model.getNonPriceType());
            AssertUtils.notNull(nonPriceType, "价格类型不正确");
            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);

            //非空字段填充
            t.setItemText("");
            t.setHighestSalePrice(BigDecimal.ZERO);
            baseMapper.insert(t);

        }
        return true;
    }

    @Override
    public boolean update(MallItemModel model) {
        // TODO shipping_no 值从哪来
        model.setShippingNo(model.getShippingNo() !=null?model.getShippingNo():"");
        //公共校验
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(model.getType());
        AssertUtils.notNull(type, "商品类型不正确");
        //公共处理赋值
        AssertUtils.notNull(model.getItemNo(), "商品编号不能为空");
        LambdaQueryWrapper<MallItem> itemWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo, model.getItemNo());
        MallItem item = baseMapper.selectOne(itemWrapper);
        model.setId(item.getId());
        String itemCover = model.getBanners().get(0);
        String banners = JSONUtil.toJsonStr(model.getBanners());
        model.setItemCover(itemCover);
        MallItem t = new MallItem();
        //销售商品
        if(type.getCode().equals(MallItemEnum.Type.Type_1.getCode())){
            //先删除旧数据，再添加
            LambdaQueryWrapper<MallSku> skuWrapper = new LambdaQueryWrapper<MallSku>()
                    .eq(MallSku::getItemNo, model.getItemNo());
            List<MallSku> skulist = mallSkuService.list(skuWrapper);
            skulist.stream().forEach(f -> {
                LambdaQueryWrapper<MallSkuSpec> skuSpecWrapper = new LambdaQueryWrapper<MallSkuSpec>()
                        .eq(MallSkuSpec::getSkuNo, f.getSkuNo());
                List<MallSkuSpec> skuSpeclist = mallSkuSpecService.list(skuSpecWrapper);
                skuSpeclist.stream().forEach(i -> mallSkuSpecService.removeById(i.getId()));
            });
            mallSkuService.remove(skuWrapper);

            //添加Sku，sepc
            List<MallItemSkuModel> skuList = model.getSkus();
            if(CollectionUtil.isNotEmpty(skuList)){
                List<MallItemSkuModel> skuMaxModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
                List<MallItemSkuModel> skuMinModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
                MallItemSkuModel skuModel = skuMinModel.get(0);
                model.setSalePrice(skuModel.getSalePrice());
                model.setPrice(skuModel.getPrice());
                model.setStock(skuModel.getStock());
                model.setGoodsNo(skuModel.getGoodsNo());
                model.setHighestSalePrice(skuMaxModel.get(0).getSalePrice());

                //添加Sku，sepc
                for (int i=0;i< model.getSkus().size();i++){
                    MallItemSkuModel f = model.getSkus().get(i);
                    AssertUtils.notNull(f.getSkuSpecs(), "Sku规格不能为空");
                    MallSku sku = new MallSku();
                    sku.setItemNo(model.getItemNo());
                    sku.setSkuNo(model.getItemNo()+"_"+StrUtil.toString(IDGeneratorUtils.getLongId()));
                    BeanUtils.copyProperties(f,sku);
                    sku.setSortValue(i);
                    mallSkuService.save(sku);
                    f.getSkuSpecs().stream().forEach(s -> {
                        MallSkuSpec skuSpec = new MallSkuSpec();
                        skuSpec.setSkuNo(sku.getSkuNo());
                        BeanUtils.copyProperties(s,skuSpec);
                        mallSkuSpecService.save(skuSpec);
                    });
                }
            }else{
                MallItemSkuModel skuModel = new MallItemSkuModel();
                skuModel.setSalePrice(model.getSalePrice());
                skuModel.setPrice(model.getPrice());
                skuModel.setStock(model.getStock());
                skuModel.setGoodsNo(model.getGoodsNo());
                model.setHighestSalePrice(model.getSalePrice());

                MallSku sku = new MallSku();
                BeanUtils.copyProperties(skuModel,sku);
                sku.setSortValue(0);
                sku.setItemNo(model.getItemNo());
                sku.setSkuNo("default_"+model.getItemNo());
                mallSkuService.save(sku);
            }

            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);
            baseMapper.updateById(t);

            //添加视频
            if(CollectionUtil.isNotEmpty(model.getTopVideoList())){
                //先删除旧数据，再添加
                LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                        .eq(MallItemVideo::getItemNo, model.getItemNo())
                        .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_0.getCode());
                mallItemVideoService.remove(videoWrapper);

                MallItemVideoModel videoModel = model.getTopVideoList().get(0);
                MallItemVideo video = new MallItemVideo();
                videoModel.setItemNo(t.getItemNo());
                videoModel.setType(MallItemVideoEnum.Type.TYPE_0.getCode());
                BeanUtils.copyProperties(videoModel,video);
                video.setVideoNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
                video.setImg("");
                mallItemVideoService.save(video);
            }
            if(CollectionUtil.isNotEmpty(model.getDetailVideoList())){
                //先删除旧数据，再添加
                LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                        .eq(MallItemVideo::getItemNo, model.getItemNo())
                        .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_1.getCode());
                mallItemVideoService.remove(videoWrapper);

                MallItemVideoModel videoModel = model.getDetailVideoList().get(0);
                MallItemVideo video = new MallItemVideo();
                videoModel.setItemNo(t.getItemNo());
                videoModel.setType(MallItemVideoEnum.Type.TYPE_1.getCode());
                BeanUtils.copyProperties(videoModel,video);
                video.setVideoNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
                mallItemVideoService.save(video);
            }
        }else{
            MallItemEnum.NonPriceType nonPriceType = MallItemEnum.NonPriceType.parseCode(model.getNonPriceType());
            AssertUtils.notNull(nonPriceType, "价格类型不正确");
            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);
            baseMapper.updateById(t);

        }
        return true;
    }

    @Override
    public boolean shelf(MallItemShelfModel model) {
        MallItemEnum.Status status = MallItemEnum.Status.parseCode(model.getStatus());
        AssertUtils.notNull(status, "状态不正确");
        for (String itemNo : model.getItemNoList()) {
            MallItem entity = new MallItem();
            entity.setItemNo(itemNo);
            entity.setStatus(status.getCode());
            SqlHelper.retBool(baseMapper.updateByItemNo(entity));
        }
        return true;
    }
}
