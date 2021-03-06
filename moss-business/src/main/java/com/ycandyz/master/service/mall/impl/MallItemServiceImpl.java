package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.MallItemHomeDao;
import com.ycandyz.master.dao.mall.MallShopDao;
import com.ycandyz.master.dao.mall.MallSocialSettingDao;
import com.ycandyz.master.dao.organize.OrganizeRelDao;
import com.ycandyz.master.domain.enums.mall.MallItemEnum;
import com.ycandyz.master.domain.enums.mall.MallItemOriganizeEnum;
import com.ycandyz.master.domain.enums.mall.MallItemVideoEnum;
import com.ycandyz.master.domain.enums.organize.OrganizeEnum;
import com.ycandyz.master.domain.model.mall.*;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.*;
import com.ycandyz.master.dto.mall.MallShopDTO;
import com.ycandyz.master.entities.mall.*;
import com.ycandyz.master.entities.organize.Organize;
import com.ycandyz.master.entities.organize.OrganizeRel;
import com.ycandyz.master.exception.BusinessException;
import com.ycandyz.master.service.mall.IMallItemService;
import com.ycandyz.master.service.organize.impl.OrganizeServiceImpl;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.FileUtil;
import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.utils.RegexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>
 * @Description ????????? ?????????
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
    @Autowired
    private TabooCheckService tabooCheckService;
    @Autowired
    private MallSocialSettingDao mallSocialSettingDao;
    @Autowired
    private MallItemOrganizeServiceImpl mallItemOrganizeService;
    @Autowired
    private OrganizeServiceImpl organizeService;

    @Override
    public boolean edit(MallItemDetailModel model) {
        LambdaQueryWrapper<MallCategory> categoryWrapper = new LambdaQueryWrapper<MallCategory>()
                .eq(MallCategory::getCategoryNo, model.getCategoryNo());
        MallCategory category = mallCategoryService.getOne(categoryWrapper);
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(model.getItemNo());
        AssertUtils.isTrue(category.getShopNo().equals(mio.getShopNo()), "??????????????????????????????????????????????????????");
        if(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode()){
            baseMapper.edit(model);
        }
        return mallItemOrganizeService.edit(model);
    }

    @Override
    public MallItemResp getByItemNo(String itemNo) {
        //?????????????????????????????????????????????
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(itemNo);
        //AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "??????????????????????????????????????????????????????");
        //itemNo = mio.getOrganizeItemNo();
        MallItemResp vo = new MallItemResp();
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo, itemNo);
        MallItem entity = baseMapper.getOneDetailByItemNo(itemNo);
        entity.setBanners(FileUtil.unicodetoString(entity.getBanners()));
        MallItem t = baseMapper.selectMallItemById(entity.getId());
        //entity.setPickupAddressIds(t.getPickupAddressIds());
        //entity.setDeliveryType(t.getDeliveryType());
        BeanUtil.copyProperties(entity,vo);
        if (StrUtil.isNotEmpty(entity.getBanners())) {
            com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(entity.getBanners());
            List<String> banners = new ArrayList<>();
            for (int i=0;i<jsonArray.size();i++){
                banners.add(jsonArray.getString(i));
            }
            vo.setBanners(banners);
        }
        List<Integer> pl = JSONObject.parseArray(t.getPickupAddressIds(), Integer.class);
        List<Integer> dl = JSONObject.parseArray(t.getDeliveryType(), Integer.class);
        vo.setPickupAddrIds(pl);
        vo.setDeliveryType(dl);
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(entity.getType());
        if(MallItemEnum.Type.Type_1.getCode().equals(type.getCode())){
//??????sku
            LambdaQueryWrapper<MallSku> skuWrapper = new LambdaQueryWrapper<MallSku>()
                    .select(MallSku::getSkuNo,MallSku::getSalePrice,MallSku::getPrice,MallSku::getGoodsNo,MallSku::getBarCode,MallSku::getStock,MallSku::getSkuImg)
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

            //??????specs
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

        //TODO ?????????????????????????????????
        LambdaQueryWrapper<MallCategory> categoryWrapper = new LambdaQueryWrapper<MallCategory>()
                .eq(MallCategory::getCategoryNo, vo.getCategoryNo());
        MallCategory category = mallCategoryService.getOne(categoryWrapper);
        vo.setCategoryName(category.getCategoryName());
        vo.setParentCategoryNo(category.getParentCategoryNo());
        if(category.getParentCategoryNo() != null && !category.getParentCategoryNo().trim().equals("")){
            LambdaQueryWrapper<MallCategory> pcWrapper = new LambdaQueryWrapper<MallCategory>()
                    .eq(MallCategory::getCategoryNo, category.getParentCategoryNo());
            MallCategory pc = mallCategoryService.getOne(pcWrapper);
            vo.setParentCategoryName(pc.getCategoryName());
        }

        //??????????????????
        LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                .select(MallItemVideo::getUrl,MallItemVideo::getImg)
                .eq(MallItemVideo::getItemNo, vo.getItemNo())
                .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_0.getCode());
        List<MallItemVideo> topVideo = mallItemVideoService.list(videoWrapper);
        for(MallItemVideo topVo: topVideo){
            topVo.setUrl(FileUtil.unicodetoString(topVo.getUrl()));
        }
        vo.setTopVideoList(topVideo);
        //??????????????????
        LambdaQueryWrapper<MallItemVideo> detailVideoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                .select(MallItemVideo::getUrl,MallItemVideo::getImg)
                .eq(MallItemVideo::getItemNo, vo.getItemNo())
                .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_1.getCode());
        List<MallItemVideo> detailVideo = mallItemVideoService.list(detailVideoWrapper);
        for(MallItemVideo detailVo: detailVideo){
            detailVo.setImg(FileUtil.unicodetoString(detailVo.getImg()));
            detailVo.setUrl(FileUtil.unicodetoString(detailVo.getUrl()));
        }
        vo.setDetailVideoList(detailVideo);

        vo.setSales(entity.getBaseSales()+entity.getRealSales());
        vo.setSalePrice(entity.getLowestSalePrice());

        //?????????????????????unioncode???????????????
        vo.setItemCover(FileUtil.unicodetoString(vo.getItemCover()));
        List<String> itemBannerList = new ArrayList<>();
        List<String> itemBanners = vo.getBanners();
        if (itemBanners!=null && itemBanners.size()>0) {
            itemBanners.forEach(banner -> {
                String ban = FileUtil.unicodetoString(banner);
                itemBannerList.add(ban);
            });
            vo.setBanners(itemBannerList);
        }

        vo.setShareImg(FileUtil.unicodetoString(vo.getShareImg()));
        vo.setQrCodeUrl(FileUtil.unicodetoString(vo.getQrCodeUrl()));
        vo.setItemNo(itemNo);
        vo.setIsCopy(mio.getIsCopy());
        //??????????????????
        MallItemEnum.IsAll isAll = MallItemEnum.IsAll.parseCode(vo.getIsAll());
        if(isAll.getCode() == MallItemEnum.IsAll.Type_1.getCode()){
            List<String> shopNoList = mallShopDao.getByItemNo(itemNo).stream().map(MallShopResp::getShopNo).collect(Collectors.toList());
            vo.setShopNoList(shopNoList);
        }
        return vo;
    }

    @Override
    public CommonResult deleteByItemNo(String itemNo) {
        //?????????????????????????????????????????????
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(itemNo);
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "??????????????????????????????????????????????????????");
        itemNo = mio.getOrganizeItemNo();
        MallItem mallItem = baseMapper.selectOne(new LambdaQueryWrapper<MallItem>().eq(MallItem::getItemNo,itemNo));
        if (mallItem!=null){
            if (!mallItem.getShopNo().equals(getShopNo())){
                return CommonResult.failed("??????????????????????????????");
            }
        }
        MallItem entity = new MallItem();
        entity.setItemNo(itemNo);
        entity.setStatus(MallItemEnum.Status.START_50.getCode());
        int i = baseMapper.updateByItemNo(entity);
        if (i>0){
            return CommonResult.success("????????????");
        }else {
            return CommonResult.failed("????????????");
        }
    }

    @Override
    public Page<MallItemPageResp> getMallItemPage(Page<MallItem> page, MallItemQuery query) {
        AssertUtils.notNull(query.getIsGroup(), "??????????????????????????????");
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
        List<Integer> organizeIds = new ArrayList<>();  //????????????id?????????????????????
        Map<String, Integer> shopNoAndOrganizeId = new HashMap<>(); //??????shopNo???organizeid    map<shopNo,organizeid>
        if (query.getIsGroup().equals("0")){   //???????????????????????????
            query.setShopNoList(Arrays.asList(getShopNo()));
            organizeIds.add(getOrganizeId().intValue());
            shopNoAndOrganizeId.put(getShopNo(),getOrganizeId().intValue());
        }else if (query.getIsGroup().equals("1")){ //??????
            if (query.getChildrenOrganizeId()==null || "".equals(query.getChildrenOrganizeId()) || query.getChildrenOrganizeId().equals("0")){
                //????????????????????????
                Long groupOrganizeId = getOrganizeId();   //??????id
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
                    //??????????????????????????????????????????
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
            f.setEditable(f.getIsCopy());
            LambdaQueryWrapper<MallSku> skuWrapper = new LambdaQueryWrapper<MallSku>()
                    .select(MallSku::getGoodsNo)
                    .eq(MallSku::getItemNo, f.getOrganizeId());
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

    @Transactional
    @Override
    public CommonResult insert(MallItemModel model) {
        //??????????????????
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemName,model.getItemName())
                .eq(MallItem::getShopNo,getShopNo());
        List<MallItem> checkName = baseMapper.selectList(queryWrapper);
        AssertUtils.isNotEmpty(checkName, "??????????????????");

        // TODO shipping_no ????????????
        model.setShippingNo(model.getShippingNo() !=null?model.getShippingNo():"");
        model.setShopNo(getShopNo());
        //????????????
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(model.getType());
        AssertUtils.notNull(type, "?????????????????????");
        AssertUtils.maxLimit(model.getPrice(),new BigDecimal("999999.99"),"??????????????????999999.99");
        AssertUtils.maxLimit(model.getSalePrice(),new BigDecimal("999999.99"),"??????????????????999999.99");

        //???????????????
        List<String> lists = new ArrayList<>();
        StringBuffer txt = new StringBuffer();
        lists.add(model.getItemName());
        lists.add(model.getItemText());
        lists.add(model.getShareDescr());
        lists.stream().forEach(s->txt.append(s));
        List<String> result = tabooCheckService.check(txt.toString());
        if (result != null && result.size() >0 ){
            JSONObject data = new JSONObject();
            data.put("code",2500);
            return CommonResult.success(data,"????????????????????????????????????????????????????????????");
        }

        //??????????????????
        model.setItemNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
        String itemCover = model.getBanners().get(0);
        String banners = JSONUtil.toJsonStr(model.getBanners());
        model.setItemCover(itemCover);
        MallItem t = new MallItem();
        //????????????
        if(type.getCode().equals(MallItemEnum.Type.Type_1.getCode())){
            //????????????
            List<MallItemSkuModel> skuList = model.getSkus();
            if(CollectionUtil.isNotEmpty(skuList)){
                List<MallItemSkuModel> skuMaxModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
                List<MallItemSkuModel> skuMinModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice)).limit(1).collect(Collectors.toList());
                Optional<MallItemSkuModel> mallItemSkuOp = skuList.stream().filter(x->x.getPrice()!=null).max(Comparator.comparing(MallItemSkuModel::getPrice));
                if(mallItemSkuOp.isPresent()){
                    MallItemSkuModel msn = mallItemSkuOp.get();
                    model.setPrice(msn.getPrice());
                }
                MallItemSkuModel skuModel = skuMinModel.get(0);
                AssertUtils.notNull(skuModel.getStock(), "??????????????????");
                model.setStock(skuModel.getStock());
                model.setGoodsNo(skuModel.getGoodsNo());
                model.setBarCode(skuModel.getBarCode());
                model.setHighestSalePrice(skuMaxModel.get(0).getSalePrice());
                model.setLowestSalePrice(skuMinModel.get(0).getSalePrice());
                //??????Sku???sepc
                for (int i=0;i< model.getSkus().size();i++){
                    MallItemSkuModel f = model.getSkus().get(i);
                    AssertUtils.notNull(f.getSkuSpecs(), "Sku??????????????????");
                    AssertUtils.notNull(f.getStock(), "??????????????????");
                    AssertUtils.notNull(f.getSalePrice(),"????????????????????????");
                    AssertUtils.maxLimit(f.getPrice(),new BigDecimal("999999.99"),"??????????????????999999.99");
                    AssertUtils.maxLimit(f.getSalePrice(),new BigDecimal("999999.99"),"??????????????????999999.99");
                    if(StrUtil.isNotEmpty(f.getBarCode())){
                        AssertUtils.isTrue(f.getBarCode().matches(RegexUtils.REGEX_AZ09AZ),"??????????????????????????????????????????????????????");
                    }
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
                AssertUtils.notNull(model.getStock(), "??????????????????");
                if(StrUtil.isNotEmpty(model.getBarCode())){
                    AssertUtils.isTrue(model.getBarCode().matches(RegexUtils.REGEX_AZ09AZ),"??????????????????????????????????????????????????????");
                }
                skuModel.setSalePrice(model.getSalePrice());
                skuModel.setPrice(model.getPrice());
                skuModel.setStock(model.getStock());
                skuModel.setGoodsNo(model.getGoodsNo());
                skuModel.setBarCode(model.getBarCode());
                model.setHighestSalePrice(model.getSalePrice());
                model.setLowestSalePrice(model.getSalePrice());
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
            t.setPickupAddressIds(JSONUtil.toJsonStr(model.getPickupAddrIds()));

            //??????????????????????????????
            MallSocialSetting mallSocialSetting = mallSocialSettingDao.selectOne(new QueryWrapper<MallSocialSetting>().eq("shop_no",getShopNo()));
            if(mallSocialSetting != null){
                socialToItem(t,mallSocialSetting);
            }

            baseMapper.insert(t);

        }else{
            MallItemEnum.NonPriceType nonPriceType = MallItemEnum.NonPriceType.parseCode(model.getNonPriceType());
            if(nonPriceType.getCode() == 1){
                AssertUtils.notNull(nonPriceType, "?????????????????????");
                AssertUtils.notNull(model.getNonSalePrice(), "??????????????????");
            }
            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);
            t.setHighestSalePrice(BigDecimal.ZERO);
            baseMapper.insert(t);

        }

        //????????????
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

        //???????????????????????????
        MallItemOrganizeModel mioModel = new MallItemOrganizeModel();
        mioModel.setShopNo(getShopNo());
        mioModel.setOrganizeItemNo(t.getItemNo());
        mioModel.setItemNo(t.getItemNo());
        mioModel.setCategoryNo(t.getCategoryNo());
        mallItemOrganizeService.insert(mioModel);

        JSONObject dataJSON = new JSONObject();
        dataJSON.put("code",200);
        dataJSON.put("item_no",t.getItemNo());
        return CommonResult.success(dataJSON,"????????????");
    }

    /**
     * ????????????????????????????????????????????????????????????????????????
     * @param t
     * @param mallSocialSetting
     */
    private void socialToItem(MallItem t, MallSocialSetting mallSocialSetting){
        if(mallSocialSetting.getIsDefaultItem()){
            t.setIsEnableShare(1);
        }else{
            t.setIsEnableShare(0);
        }
        t.setShareMethod(mallSocialSetting.getShareMethod());
        t.setShareRate(mallSocialSetting.getShareRate());
        t.setShareAmount(mallSocialSetting.getShareAmount());
        t.setShareLevelMethod(mallSocialSetting.getShareLevelMethod());
        t.setShareLevelRate(mallSocialSetting.getShareLevelRate());
        t.setShareLevelAmount(mallSocialSetting.getShareLevelAmount());
        t.setShareSecondMethod(mallSocialSetting.getShareSecondMethod());
        t.setShareSecondLevelMethod(mallSocialSetting.getShareSecondLevelMethod());
        t.setShareSecondRate(mallSocialSetting.getShareSecondRate());
        t.setShareSecondAmount(mallSocialSetting.getShareSecondAmount());
        t.setShareSecondLevelRate(mallSocialSetting.getShareSecondLevelRate());
        t.setShareSecondLevelAmount(mallSocialSetting.getShareSecondLevelAmount());
        t.setShareThirdMethod(mallSocialSetting.getShareThirdMethod());
        t.setShareThirdLevelMethod(mallSocialSetting.getShareThirdLevelMethod());
        t.setShareThirdRate(mallSocialSetting.getShareThirdRate());
        t.setShareThirdAmount(mallSocialSetting.getShareThirdAmount());
        t.setShareThirdLevelRate(mallSocialSetting.getShareThirdLevelRate());
        t.setShareThirdLevelAmount(mallSocialSetting.getShareThirdLevelAmount());
    }

    @Transactional
    @Override
    public CommonResult update(MallItemModel model) {
        //?????????????????????????????????????????????
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(model.getItemNo());
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "??????????????????????????????????????????????????????");
        model.setItemNo(mio.getOrganizeItemNo());
        //??????????????????
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemName,model.getItemName())
                .eq(MallItem::getShopNo,getShopNo())
                .ne(MallItem::getId,model.getId());
        List<MallItem> checkName = baseMapper.selectList(queryWrapper);
        AssertUtils.isNotEmpty(checkName, "??????????????????");

        // TODO shipping_no ????????????
        model.setShippingNo(model.getShippingNo() !=null?model.getShippingNo():"");
        //????????????
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(model.getType());
        AssertUtils.notNull(type, "?????????????????????");
        //??????????????????
        AssertUtils.notNull(model.getItemNo(), "????????????????????????");

        //???????????????
        List<String> lists = new ArrayList<>();
        StringBuffer txt = new StringBuffer();
        lists.add(model.getItemName());
        lists.add(model.getItemText());
        lists.add(model.getShareDescr());
        lists.stream().forEach(s->txt.append(s));
        List<String> result = tabooCheckService.check(txt.toString());
        if (result != null && result.size() >0 ){
            JSONObject data = new JSONObject();
            data.put("code",2500);
            return CommonResult.success(data,"????????????????????????????????????????????????????????????");
        }

        LambdaQueryWrapper<MallItem> itemWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo, model.getItemNo());
        MallItem item = baseMapper.selectOne(itemWrapper);
        if (!item.getShopNo().equals(getShopNo())){
            return CommonResult.failed("????????????????????????????????????");
        }
        model.setId(item.getId());
        String itemCover = model.getBanners().get(0);
        String banners = JSONUtil.toJsonStr(model.getBanners());
        model.setItemCover(itemCover);
        MallItem t = new MallItem();

        //?????????????????????????????????????????????
        t.setIsScreen(0);
        t.setAuditStatus(0);
        t.setAuditedAt(0);
        t.setAuditorId(0l);

        //????????????
        if(type.getCode().equals(MallItemEnum.Type.Type_1.getCode())){
            //??????????????????????????????
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

            //??????Sku???sepc
            List<MallItemSkuModel> skuList = model.getSkus();
            if(CollectionUtil.isNotEmpty(skuList)){
                List<MallItemSkuModel> skuMaxModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice).reversed()).limit(1).collect(Collectors.toList());
                List<MallItemSkuModel> skuMinModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice)).limit(1).collect(Collectors.toList());
                MallItemSkuModel skuModel = skuMinModel.get(0);

                Optional<MallItemSkuModel> mallItemSkuOp = skuList.stream().filter(x->x.getPrice()!=null).max(Comparator.comparing(MallItemSkuModel::getPrice));
                if(mallItemSkuOp.isPresent()){
                    MallItemSkuModel msn = mallItemSkuOp.get();
                    model.setPrice(msn.getPrice());
                }
                model.setStock(skuModel.getStock());
                model.setGoodsNo(skuModel.getGoodsNo());
                model.setBarCode(skuModel.getBarCode());
                model.setHighestSalePrice(skuMaxModel.get(0).getSalePrice());
                model.setLowestSalePrice(skuMinModel.get(0).getSalePrice());

                //??????Sku???sepc
                for (int i=0;i< model.getSkus().size();i++){
                    MallItemSkuModel f = model.getSkus().get(i);
                    AssertUtils.notNull(f.getSkuSpecs(), "Sku??????????????????");
                    AssertUtils.maxLimit(f.getPrice(),new BigDecimal("999999.99"),"??????????????????999999.99");
                    AssertUtils.maxLimit(f.getSalePrice(),new BigDecimal("999999.99"),"??????????????????999999.99");
                    if(StrUtil.isNotEmpty(f.getBarCode())){
                        AssertUtils.isTrue(f.getBarCode().matches(RegexUtils.REGEX_AZ09AZ),"??????????????????????????????????????????????????????");
                    }
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
                if(StrUtil.isNotEmpty(model.getBarCode())){
                    AssertUtils.isTrue(model.getBarCode().matches(RegexUtils.REGEX_AZ09AZ),"??????????????????????????????????????????????????????");
                }
                MallItemSkuModel skuModel = new MallItemSkuModel();
                skuModel.setSalePrice(model.getSalePrice());
                skuModel.setPrice(model.getPrice());
                skuModel.setStock(model.getStock());
                skuModel.setGoodsNo(model.getGoodsNo());
                skuModel.setBarCode(model.getBarCode());
                skuModel.setSalePrice(model.getSalePrice());
                model.setHighestSalePrice(model.getSalePrice());
                model.setLowestSalePrice(model.getSalePrice());

                MallSku sku = new MallSku();
                BeanUtils.copyProperties(skuModel,sku);
                sku.setSortValue(0);
                sku.setItemNo(model.getItemNo());
                sku.setSkuNo("default_"+model.getItemNo());
                mallSkuService.save(sku);
            }

            BeanUtils.copyProperties(model,t);
            t.setPrice(model.getPrice()==null ? BigDecimal.ZERO : model.getPrice());
            t.setBanners(banners);
            t.setPickupAddressIds(JSONUtil.toJsonStr(model.getPickupAddrIds()));
            t.setDeliveryType(model.getDeliveryType()!=null && model.getDeliveryType().size()>0 ? model.getDeliveryType().toString() : null);
            baseMapper.updateById(t);

        }else{
            MallItemEnum.NonPriceType nonPriceType = MallItemEnum.NonPriceType.parseCode(model.getNonPriceType());
            AssertUtils.notNull(nonPriceType, "?????????????????????");
            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);
            t.setHighestSalePrice(BigDecimal.ZERO);
            baseMapper.updateById(t);

        }

        
        if(CollectionUtil.isEmpty(model.getTopVideoList())){
            LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                    .eq(MallItemVideo::getItemNo, model.getItemNo())
                    .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_0.getCode());
            mallItemVideoService.remove(videoWrapper);
        }

        if(CollectionUtil.isEmpty(model.getDetailVideoList())){
            LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                    .eq(MallItemVideo::getItemNo, model.getItemNo())
                    .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_1.getCode());
            mallItemVideoService.remove(videoWrapper);
        }

        //????????????
        if(CollectionUtil.isNotEmpty(model.getTopVideoList())){
            //??????????????????????????????
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
            //??????????????????????????????
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

        JSONObject dataJSON = new JSONObject();
        dataJSON.put("code",200);
        dataJSON.put("item_no",t.getItemNo());
        return CommonResult.success(dataJSON,"????????????");
    }

    @Override
    public CommonResult shelf(MallItemShelfModel model) {
        MallItemEnum.Status status = MallItemEnum.Status.parseCode(model.getStatus());
        AssertUtils.notNull(status, "???????????????");
        for (String itemNo : model.getItemNoList()) {
            MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(itemNo);
            AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "??????????????????????????????????????????????????????");
            itemNo = mio.getOrganizeItemNo();
            MallItem mallItem = baseMapper.selectOne(new LambdaQueryWrapper<MallItem>().eq(MallItem::getItemNo,itemNo));
            if (mallItem!=null){
                if (!mallItem.getShopNo().equals(getShopNo())){
                    throw new BusinessException("??????????????????????????????,????????????");
                }
            }
            MallItem entity = new MallItem();
            entity.setItemNo(itemNo);
            entity.setStatus(status.getCode());
            baseMapper.updateByItemNo(entity);
        }
        return CommonResult.success("????????????!");
    }

    @Override
    public boolean addOrganize(MallItemOrgModel model) {
        //??????token???????????????
        Organize o = organizeService.getById(getOrganizeId());
        if(o == null || o.getIsGroup() == 0){
            AssertUtils.isTrue(false, "???????????????????????????????????????????????????");
        }
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(model.getItemNo());
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "??????????????????????????????????????????????????????");
        model.setItemNo(mio.getOrganizeItemNo());
        //??????????????????
        MallItemEnum.IsGroupSupply isGroupSupply = MallItemEnum.IsGroupSupply.parseCode(model.getIsGroupSupply());
        AssertUtils.notNull(isGroupSupply, "???????????? ???????????????");
        MallItemEnum.IsAll isAll = MallItemEnum.IsAll.parseCode(model.getIsAll());
        if(isGroupSupply.getCode() == MallItemEnum.IsGroupSupply.Type_1.getCode()){
            AssertUtils.notNull(isAll, "????????????/???????????? ???????????????");
            if(MallItemEnum.IsAll.Type_1.getCode().equals(isAll.getCode())){
                AssertUtils.notNull(model.getShopNos(), "??????????????????????????????");
            }
        }

        LambdaQueryWrapper<MallItem> itemWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo,model.getItemNo());
        MallItem item = baseMapper.selectOne(itemWrapper);
        model.setCategoryNo(item.getCategoryNo());

        //???????????????????????????????????????????????????,?????????
        MallItemOrganize io = new MallItemOrganize();
        io.setOrganizeItemNo(model.getItemNo());
        io.setShopNo(getShopNo());
        io.setIsDel(1);
        mallItemOrganizeService.deleteOrg(io);

        LambdaQueryWrapper<MallShop> shopWrapper = new LambdaQueryWrapper<MallShop>()
                .eq(MallShop::getShopNo,getShopNo());
        MallShop shop = mallShopDao.selectOne(shopWrapper);
        Organize organize = organizeService.getById(shop.getOrganizeId());
        List<MallCategory> mclist = new ArrayList<>();
        List<MallItemOrganize> addIolist = new ArrayList<>();
        List<MallItemOrganize> updateIolist = new ArrayList<>();
        //?????????????????????????????? ????????????
        if(organize.getIsGroup() == OrganizeEnum.IsGroup.Type_1.getCode() && isGroupSupply.getCode().equals(MallItemEnum.IsGroupSupply.Type_1.getCode())){
            //???????????????????????????????????????????????????????????????????????????????????????
            MallCategoryResp mc = mallCategoryService.getByChildCategoryNo(null,model.getCategoryNo());
            if(MallItemEnum.IsAll.Type_0.getCode().equals(isAll.getCode())){
                List<String> shopList = mallShopDao.getByOrganizeId(getOrganizeId()).stream().map(MallShopResp::getShopNo).collect(Collectors.toList());
                shopList.forEach(shopNo -> {
                    MallItemOrganizeCategoryModel moc = createCategory(model.getItemNo(),shopNo,mc,"",new MallItemOrganizeCategoryModel());
                    if(CollectionUtil.isNotEmpty(moc.getMclist())){
                        mclist.addAll(moc.getMclist());
                    }
                    if(CollectionUtil.isNotEmpty(moc.getAddIolist())){
                        addIolist.addAll(moc.getAddIolist());
                    }
                    if(CollectionUtil.isNotEmpty(moc.getUpdateIolist())){
                        updateIolist.addAll(moc.getUpdateIolist());
                    }
                });
            }else {//?????????????????????????????????????????????????????????????????????????????????
                model.getShopNos().forEach(shopNo -> {
                    MallItemOrganizeCategoryModel moc = createCategory(model.getItemNo(),shopNo,mc,"",new MallItemOrganizeCategoryModel());
                    if(CollectionUtil.isNotEmpty(moc.getMclist())){
                        mclist.addAll(moc.getMclist());
                    }
                    if(CollectionUtil.isNotEmpty(moc.getAddIolist())){
                        addIolist.addAll(moc.getAddIolist());
                    }
                    if(CollectionUtil.isNotEmpty(moc.getUpdateIolist())){
                        updateIolist.addAll(moc.getUpdateIolist());
                    }
                });
            }
            if(CollectionUtil.isNotEmpty(mclist)){
                mallCategoryService.insertBatch(mclist);
            }
            if(CollectionUtil.isNotEmpty(addIolist)){
                mallItemOrganizeService.insertBatch(addIolist);
            }
            if(CollectionUtil.isNotEmpty(updateIolist)){
                List<Long> ids = updateIolist.stream().map(MallItemOrganize::getId).collect(Collectors.toList());
                mallItemOrganizeService.updateBatchOrg(ids);
            }
        }

        //??????mall_item????????????????????????:??????
        MallItem t = new MallItem();
        t.setItemNo(model.getItemNo());
        t.setIsGroupSupply(model.getIsGroupSupply());
        t.setIsAll(model.getIsAll());
        baseMapper.updateOrgByItemNo(t);
        return true;
    }

    @Override
    public MallItemShareResp getShareByItemNo(String itemNo) {
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(itemNo);
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "??????????????????????????????????????????????????????");

        LambdaQueryWrapper<MallItem> itemWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo,mio.getOrganizeItemNo());
        MallItem item = baseMapper.selectOne(itemWrapper);

        //???????????????????????????????????????,??????
        LambdaQueryWrapper<MallSocialSetting> socialWrapper = new LambdaQueryWrapper<MallSocialSetting>()
                .eq(MallSocialSetting::getShopNo,item.getShopNo());
        MallSocialSetting social = mallSocialSettingDao.selectOne(socialWrapper);

        MallItemShareResp vo = new MallItemShareResp();
        BeanUtils.copyProperties(item,vo);
        vo.setItemNo(itemNo);
        vo.setIsCopy(mio.getIsCopy());
        vo.setEditable(mio.getIsCopy());
        vo.setShareLevelType(social.getShareLevelType());
        return vo;
    }

    @Override
    public boolean updateShareByItemNo(MallItemShareModel model) {
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(model.getItemNo());
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "??????????????????????????????????????????????????????");

        LambdaQueryWrapper<MallItem> itemWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo,mio.getOrganizeItemNo());
        MallItem i = baseMapper.selectOne(itemWrapper);
        MallItem t = new MallItem();
        BeanUtils.copyProperties(model,t);
        t.setItemNo(mio.getOrganizeItemNo());
        t.setId(i.getId());
        return retBool(baseMapper.updateById(t));
    }

    public MallItemOrganizeCategoryModel createCategory(String itemNo, String shopNo, MallCategoryResp i, String parentCategoryNo, MallItemOrganizeCategoryModel mocModel) {
        LambdaQueryWrapper<MallCategory> cpWrapper = new LambdaQueryWrapper<MallCategory>()
                .eq(MallCategory::getShopNo,shopNo)
                .eq(MallCategory::getCategoryName,i.getCategoryName())
                .eq(MallCategory::getLayer,i.getLayer());
        MallCategory cp = mallCategoryService.getOne(cpWrapper);
        MallCategory cpModel = new MallCategory();
        if(cp == null){
            //????????????????????????
            cpModel.setShopNo(shopNo);
            cpModel.setCategoryNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
            cpModel.setParentCategoryNo(parentCategoryNo);
            cpModel.setCategoryName(i.getCategoryName());
            cpModel.setCategoryImg(i.getCategoryImg());
            cpModel.setLayer(i.getLayer());
            cpModel.setSortValue(0);
            cpModel.setStatus(i.getStatus());
            //mallCategoryService.save(cpModel);
            mocModel.getMclist().add(cpModel);

        }else {
            cpModel.setCategoryNo(cp.getCategoryNo());
        }
        if(null == i.getCategory()){
            //?????????????????????????????????
            //???????????????????????????????????????????????????????????????
            LambdaQueryWrapper<MallItemOrganize> ioWrapper = new LambdaQueryWrapper<MallItemOrganize>()
                    .eq(MallItemOrganize::getOrganizeItemNo,itemNo)
                    .eq(MallItemOrganize::getShopNo,shopNo);
            MallItemOrganize io = mallItemOrganizeService.getOne(ioWrapper);
            if(io == null){
                MallItemOrganize mioModel = new MallItemOrganize();
                mioModel.setShopNo(shopNo);
                mioModel.setOrganizeItemNo(itemNo);
                mioModel.setItemNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
                mioModel.setCategoryNo(cpModel.getCategoryNo());
                mioModel.setIsCopy(MallItemOriganizeEnum.IsCopy.Type_0.getCode());
                //mallItemOrganizeService.save(mioModel);
                mocModel.getAddIolist().add(mioModel);
            }else{
                //mallItemOrganizeService.updateOrg(io);
                mocModel.getUpdateIolist().add(io);
            }
            return mocModel;

        }else{
            return createCategory(itemNo,shopNo,i.getCategory(),cpModel.getCategoryNo(),mocModel);
        }
    }

    @Override
    public MallItem getOneDetailByItemNo(String itemNo) {
        return baseMapper.getOneDetailByItemNo(itemNo);
    }

    @Override
    public List<MallItem> getListByItemNos(List<String> itemNos) {
        return baseMapper.getListByItemNos(itemNos);
    }


    public SpreadMallItemPageResp selectSpreadPage(Page<MallItem> page, MallItemQuery query) {
        AssertUtils.notNull(query.getCardId(), "???????????????????????????");
        AssertUtils.notNull(query.getQueryType(), "???????????????????????????");
        Long organizeId = getUser().getOrganizeId();
        query.setOrganizeId(organizeId);
        List<String> mallItemList = new ArrayList<>();
        if (query.getCardId() > 0) {
            mallItemList = baseMapper.selectByCardId(query.getCardId());
        }
        query.setCardIds(mallItemList);
        List<SpreadMallItemShopInfoResp> shopInfoResps = baseMapper.selectShopInfos(query);
        List<String> itemNos = new ArrayList<>();
        SpreadMallItemPageResp resultData = new SpreadMallItemPageResp();
        if (CollectionUtil.isNotEmpty(shopInfoResps)) {
            shopInfoResps.forEach(vo -> itemNos.add(vo.getItemNo() != null ? vo.getItemNo().toString() : ""));
            query.setCardIds(itemNos);
            Page<SpreadMallItemPageRespInfo> spreadMallItemPageRespInfoPage = baseMapper.selectSpreadPage(page, query);
            List<SpreadMallItemPageRespInfo> records = spreadMallItemPageRespInfoPage.getRecords();
            records.forEach(vo -> {
                vo.setSales(vo.getBaseSales()+vo.getRealSales());
                BigDecimal highestSalePrice = vo.getHighestSalePrice();
                BigDecimal lowestSalePrice = vo.getLowestSalePrice();
                if (null == highestSalePrice && null == lowestSalePrice) {
                    vo.setSalePrice(new BigDecimal(0));
                } else if (null == highestSalePrice) {
                    vo.setSalePrice(lowestSalePrice);
                } else if (null == lowestSalePrice) {
                    vo.setSalePrice(highestSalePrice);
                } else if (lowestSalePrice.compareTo(highestSalePrice) < 1) {
                    vo.setSalePrice(lowestSalePrice);
                } else {
                    vo.setSalePrice(highestSalePrice);
                }
                if (vo.getGlobalIsEnable() == 0) {
                    vo.setIsUpdatedShare(0);
                } else if (vo.getGlobalIsEnable() == 1) {
                    if (vo.getIsUpdatedShare() == 0) {
                        vo.setIsEnableShare(1);
                        vo.setShareMethod(vo.getGlobalShareMethod());
                    }
                } else {
                    vo.setIsEnableShare(0);
                    vo.setShareMethod(vo.getShareMethod() != null ? vo.getShareMethod() : 0);
                }
            });
            spreadMallItemPageRespInfoPage.setRecords(records);
            resultData.setRes(new BasePageResult<>(spreadMallItemPageRespInfoPage));
            resultData.setShopInfo(shopInfoResps.get(0));
        }
        return resultData;
    }
}
