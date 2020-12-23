package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.MallItemHomeDao;
import com.ycandyz.master.domain.enums.mall.MallItemEnum;
import com.ycandyz.master.domain.enums.mall.MallItemVideoEnum;
import com.ycandyz.master.domain.model.mall.*;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallCategoryResp;
import com.ycandyz.master.domain.response.mall.MallItemPageResp;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.entities.mall.MallSku;
import com.ycandyz.master.entities.mall.MallSkuSpec;
import com.ycandyz.master.service.mall.IMallItemService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.IDGeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public MallItemResp getByItemNo(String itemNo) {
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo, itemNo);
        MallItem entity = baseMapper.selectOne(queryWrapper);
        MallItem t = baseMapper.selectMallItemById(entity.getId());
        entity.setPickupAddressIds(t.getPickupAddressIds());
        entity.setDeliveryType(t.getDeliveryType());
        if(entity != null){
            if(StrUtil.isNotEmpty(entity.getBanners())){
                entity.setBanners(entity.getBanners().replaceAll("\"",""));
            }
        }

        MallItemResp vo = new MallItemResp();
        BeanUtil.copyProperties(entity,vo);
        List<Integer> pl = JSONObject.parseArray(t.getPickupAddressIds(), Integer.class);
        List<Integer> dl = JSONObject.parseArray(t.getDeliveryType(), Integer.class);
        vo.setPickupAddressIds(pl);
        vo.setDeliveryType(dl);

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

        //处理specs
        List<MallSpecsModel> specs = new ArrayList<>();
        List<MallSkuSpec> skuSpecs = new ArrayList<>();
        skus.stream().forEach(s -> {
            s.getSkuSpecs().stream().forEach(i -> {
                if(i.getExistImg()==1){
                    i.setSkuImg(s.getSkuImg());
                    i.setExistImg(null);
                }else {
                    i.setExistImg(null);
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
            sk.stream().forEach(s -> s.setSpecName(null));
            m.setSpecList(sk);
            specs.add(m);
        }
        vo.setSpecs(specs);

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
        Page<MallItemPageResp> p =baseMapper.getMallItemPage(page,query);
        p.getRecords().stream().forEach(f -> {
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
            AssertUtils.notNull(model.getSkus(), "商品Sku不能为空");
            List<MallItemSkuModel> skuMaxModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
            List<MallItemSkuModel> skuMinModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
            MallItemSkuModel skuModel = skuMinModel.get(0);
            model.setSalePrice(skuModel.getSalePrice());
            model.setPrice(skuModel.getPrice());
            model.setStock(skuModel.getStock());
            model.setGoodsNo(skuModel.getGoodsNo());
            model.setHighestSalePrice(skuMaxModel.get(0).getSalePrice());

            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);
            t.setDeliveryType(JSONUtil.toJsonStr(model.getDeliveryType()));
            t.setPickupAddressIds(JSONUtil.toJsonStr(model.getPickupAddressIds()));
            baseMapper.insert(t);
            //添加Sku，sepc
            for (int i=0;i< model.getSkus().size();i++){
                MallItemSkuModel f = model.getSkus().get(i);
                AssertUtils.notNull(f.getSkuSpecs(), "Sku规格不能为空");
                MallSku sku = new MallSku();
                sku.setItemNo(t.getItemNo());
                sku.setSkuNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
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
            baseMapper.insert(t);

        }
        return true;
    }

    @Override
    public boolean update(MallItemModel model) {
        // TODO shipping_no 值从哪来
        model.setShippingNo("111");
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
        if(type.getCode().equals(MallItemEnum.Type.Type_0.getCode())){
            //商品赋值
            AssertUtils.notNull(model.getSkus(), "商品Sku不能为空");
            List<MallItemSkuModel> skuMaxModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
            List<MallItemSkuModel> skuMinModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
            MallItemSkuModel skuModel = skuMinModel.get(0);
            model.setSalePrice(skuModel.getSalePrice());
            model.setPrice(skuModel.getPrice());
            model.setStock(skuModel.getStock());
            model.setGoodsNo(skuModel.getGoodsNo());
            model.setHighestSalePrice(skuMaxModel.get(0).getSalePrice());

            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);
            baseMapper.updateById(t);

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
            for (int i=0;i< model.getSkus().size();i++){
                MallItemSkuModel f = model.getSkus().get(i);
                AssertUtils.notNull(f.getSkuSpecs(), "Sku规格不能为空");
                MallSku sku = new MallSku();
                sku.setItemNo(t.getItemNo());
                sku.setSkuNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
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
