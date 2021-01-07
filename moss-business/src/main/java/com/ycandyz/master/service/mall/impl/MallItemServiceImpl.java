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
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.ReturnResponse;
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
import com.ycandyz.master.domain.response.mall.MallCategoryResp;
import com.ycandyz.master.domain.response.mall.MallItemPageResp;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.dto.mall.MallShopDTO;
import com.ycandyz.master.entities.ad.HomeCategory;
import com.ycandyz.master.entities.mall.*;
import com.ycandyz.master.entities.organize.Organize;
import com.ycandyz.master.entities.organize.OrganizeRel;
import com.ycandyz.master.enums.ResultEnum;
import com.ycandyz.master.exception.BusinessException;
import com.ycandyz.master.service.mall.IMallItemService;
import com.ycandyz.master.service.organize.impl.OrganizeServiceImpl;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.FileUtil;
import com.ycandyz.master.utils.IDGeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private TabooCheckService tabooCheckService;
    @Autowired
    private MallSocialSettingDao mallSocialSettingDao;
    @Autowired
    private MallItemOrganizeServiceImpl mallItemOrganizeService;
    @Autowired
    private OrganizeServiceImpl organizeService;

    @Override
    public MallItemResp getByItemNo(String itemNo) {
        //校验集团供货商品，切换商品编号
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(itemNo);
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "该商品是集团供货商品，不可执行此操作");
        itemNo = mio.getOrganizeItemNo();
        MallItemResp vo = new MallItemResp();
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo, itemNo);
        MallItem entity = baseMapper.selectOne(queryWrapper);
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
        if(category.getParentCategoryNo() != null && !category.getParentCategoryNo().trim().equals("")){
            LambdaQueryWrapper<MallCategory> pcWrapper = new LambdaQueryWrapper<MallCategory>()
                    .eq(MallCategory::getCategoryNo, category.getParentCategoryNo());
            MallCategory pc = mallCategoryService.getOne(pcWrapper);
            vo.setParentCategoryName(pc.getCategoryName());
        }

        //获取置顶视频
        LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                .select(MallItemVideo::getUrl,MallItemVideo::getImg)
                .eq(MallItemVideo::getItemNo, vo.getItemNo())
                .eq(MallItemVideo::getType, MallItemVideoEnum.Type.TYPE_0.getCode());
        List<MallItemVideo> topVideo = mallItemVideoService.list(videoWrapper);
        for(MallItemVideo topVo: topVideo){
            topVo.setUrl(FileUtil.unicodetoString(topVo.getUrl()));
        }
        vo.setTopVideoList(topVideo);
        //获取详情视频
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

        //解码图片中包含unioncode编码的路径
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
        //集团供货门店
        MallItemEnum.IsAll isAll = MallItemEnum.IsAll.parseCode(vo.getIsAll());
        if(isAll.getCode() == MallItemEnum.IsAll.Type_1.getCode()){
            List<String> shopNoList = mallShopDao.getByItemNo(itemNo).stream().map(MallShop::getShopNo).collect(Collectors.toList());
            vo.setShopNoList(shopNoList);
        }
        return vo;
    }

    @Override
    public CommonResult deleteByItemNo(String itemNo) {
        //校验集团供货商品，切换商品编号
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(itemNo);
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "该商品是集团供货商品，不可执行此操作");
        itemNo = mio.getOrganizeItemNo();
        MallItem mallItem = baseMapper.selectOne(new LambdaQueryWrapper<MallItem>().eq(MallItem::getItemNo,itemNo));
        if (mallItem!=null){
            if (!mallItem.getShopNo().equals(getShopNo())){
                return CommonResult.failed("当前商品不属于该门店");
            }
        }
        MallItem entity = new MallItem();
        entity.setItemNo(itemNo);
        entity.setStatus(MallItemEnum.Status.START_50.getCode());
        int i = baseMapper.updateByItemNo(entity);
        if (i>0){
            return CommonResult.success("删除成功");
        }else {
            return CommonResult.failed("删除失败");
        }
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

    @Transactional
    @Override
    public CommonResult insert(MallItemModel model) {
        //校验商品名称
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemName,model.getItemName())
                .eq(MallItem::getShopNo,getShopNo());
        List<MallItem> checkName = baseMapper.selectList(queryWrapper);
        AssertUtils.isNotEmpty(checkName, "商品名称重复");

        // TODO shipping_no 值从哪来
        model.setShippingNo(model.getShippingNo() !=null?model.getShippingNo():"");
        model.setShopNo(getShopNo());
        //公共校验
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(model.getType());
        AssertUtils.notNull(type, "商品类型不正确");
        AssertUtils.maxLimit(model.getPrice(),new BigDecimal("999999.99"),"原价不能大于999999.99");
        AssertUtils.maxLimit(model.getSalePrice(),new BigDecimal("999999.99"),"原价不能大于999999.99");

        //敏感词校验
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
            return CommonResult.success(data,"检测到提交信息涉嫌违规，请重新确认后提交");
        }

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
                List<MallItemSkuModel> skuMinModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice)).limit(1).collect(Collectors.toList());
                Optional<MallItemSkuModel> mallItemSkuOp = skuList.stream().filter(x->x.getPrice()!=null).max(Comparator.comparing(MallItemSkuModel::getPrice));
                if(mallItemSkuOp.isPresent()){
                    MallItemSkuModel msn = mallItemSkuOp.get();
                    model.setPrice(msn.getPrice());
                }
                MallItemSkuModel skuModel = skuMinModel.get(0);
                AssertUtils.notNull(skuModel.getStock(), "库存不能为空");
                model.setStock(skuModel.getStock());
                model.setGoodsNo(skuModel.getGoodsNo());
                model.setHighestSalePrice(skuMaxModel.get(0).getSalePrice());
                model.setLowestSalePrice(skuMinModel.get(0).getSalePrice());
                //添加Sku，sepc
                for (int i=0;i< model.getSkus().size();i++){
                    MallItemSkuModel f = model.getSkus().get(i);
                    AssertUtils.notNull(f.getSkuSpecs(), "Sku规格不能为空");
                    AssertUtils.notNull(f.getStock(), "库存不能为空");
                    AssertUtils.notNull(f.getSalePrice(),"销售价格不能为空");
                    AssertUtils.maxLimit(f.getPrice(),new BigDecimal("999999.99"),"原价不能大于999999.99");
                    AssertUtils.maxLimit(f.getSalePrice(),new BigDecimal("999999.99"),"原价不能大于999999.99");
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
                AssertUtils.notNull(model.getStock(), "库存不能为空");
                skuModel.setSalePrice(model.getSalePrice());
                skuModel.setPrice(model.getPrice());
                skuModel.setStock(model.getStock());
                skuModel.setGoodsNo(model.getGoodsNo());
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

            //新增默认分销数据入库
            MallSocialSetting mallSocialSetting = mallSocialSettingDao.selectOne(new QueryWrapper<MallSocialSetting>().eq("shop_no",getShopNo()));
            if(mallSocialSetting != null){
                socialToItem(t,mallSocialSetting);
            }

            baseMapper.insert(t);

        }else{
            MallItemEnum.NonPriceType nonPriceType = MallItemEnum.NonPriceType.parseCode(model.getNonPriceType());
            if(nonPriceType.getCode() == 1){
                AssertUtils.notNull(nonPriceType, "价格类型不正确");
                AssertUtils.notNull(model.getNonSalePrice(), "售价不能为空");
            }
            BeanUtils.copyProperties(model,t);
            t.setBanners(banners);
            t.setHighestSalePrice(BigDecimal.ZERO);
            baseMapper.insert(t);

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

        //先添加一条本店数据
        MallItemOrganizeModel mioModel = new MallItemOrganizeModel();
        mioModel.setShopNo(getShopNo());
        mioModel.setOrganizeItemNo(t.getItemNo());
        mioModel.setItemNo(t.getItemNo());
        mioModel.setCategoryNo(t.getCategoryNo());
        mallItemOrganizeService.insert(mioModel);

        JSONObject dataJSON = new JSONObject();
        dataJSON.put("code",200);
        dataJSON.put("item_no",t.getItemNo());
        return CommonResult.success(dataJSON,"保存成功");
    }

    /**
     * 吧当前门店的分销佣金分成比例保存到该门店的商品中
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
        //校验集团供货商品，切换商品编号
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(model.getItemNo());
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "该商品是集团供货商品，不可执行此操作");
        model.setItemNo(mio.getOrganizeItemNo());
        //校验商品名称
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemName,model.getItemName())
                .eq(MallItem::getShopNo,getShopNo())
                .ne(MallItem::getId,model.getId());
        List<MallItem> checkName = baseMapper.selectList(queryWrapper);
        AssertUtils.isNotEmpty(checkName, "商品名称重复");

        // TODO shipping_no 值从哪来
        model.setShippingNo(model.getShippingNo() !=null?model.getShippingNo():"");
        //公共校验
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(model.getType());
        AssertUtils.notNull(type, "商品类型不正确");
        //公共处理赋值
        AssertUtils.notNull(model.getItemNo(), "商品编号不能为空");

        //敏感词校验
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
            return CommonResult.success(data,"检测到提交信息涉嫌违规，请重新确认后提交");
        }

        LambdaQueryWrapper<MallItem> itemWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo, model.getItemNo());
        MallItem item = baseMapper.selectOne(itemWrapper);
        if (!item.getShopNo().equals(getShopNo())){
            return CommonResult.failed("当前门店无权进行修改操作");
        }
        model.setId(item.getId());
        String itemCover = model.getBanners().get(0);
        String banners = JSONUtil.toJsonStr(model.getBanners());
        model.setItemCover(itemCover);
        MallItem t = new MallItem();

        //更新商品时，审核通过初始化字段
        t.setIsScreen(0);
        t.setAuditStatus(0);
        t.setAuditedAt(0);
        t.setAuditorId(0l);

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
                List<MallItemSkuModel> skuMinModel = model.getSkus().stream().sorted(Comparator.comparing(MallItemSkuModel::getSalePrice)).limit(1).collect(Collectors.toList());
                MallItemSkuModel skuModel = skuMinModel.get(0);

                Optional<MallItemSkuModel> mallItemSkuOp = skuList.stream().filter(x->x.getPrice()!=null).max(Comparator.comparing(MallItemSkuModel::getPrice));
                if(mallItemSkuOp.isPresent()){
                    MallItemSkuModel msn = mallItemSkuOp.get();
                    model.setPrice(msn.getPrice());
                }
                model.setStock(skuModel.getStock());
                model.setGoodsNo(skuModel.getGoodsNo());
                model.setHighestSalePrice(skuMaxModel.get(0).getSalePrice());
                model.setLowestSalePrice(skuMinModel.get(0).getSalePrice());

                //添加Sku，sepc
                for (int i=0;i< model.getSkus().size();i++){
                    MallItemSkuModel f = model.getSkus().get(i);
                    AssertUtils.notNull(f.getSkuSpecs(), "Sku规格不能为空");
                    AssertUtils.maxLimit(f.getPrice(),new BigDecimal("999999.99"),"原价不能大于999999.99");
                    AssertUtils.maxLimit(f.getSalePrice(),new BigDecimal("999999.99"),"原价不能大于999999.99");
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
            AssertUtils.notNull(nonPriceType, "价格类型不正确");
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

        JSONObject dataJSON = new JSONObject();
        dataJSON.put("code",200);
        dataJSON.put("item_no",t.getItemNo());
        return CommonResult.success(dataJSON,"编辑成功");
    }

    @Override
    public CommonResult shelf(MallItemShelfModel model) {
        MallItemEnum.Status status = MallItemEnum.Status.parseCode(model.getStatus());
        AssertUtils.notNull(status, "状态不正确");
        for (String itemNo : model.getItemNoList()) {
            MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(itemNo);
            AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "该商品是集团供货商品，不可执行此操作");
            itemNo = mio.getOrganizeItemNo();
            MallItem mallItem = baseMapper.selectOne(new LambdaQueryWrapper<MallItem>().eq(MallItem::getItemNo,itemNo));
            if (mallItem!=null){
                if (!mallItem.getShopNo().equals(getShopNo())){
                    throw new BusinessException("当前商品不属于该门店,无权操作");
                }
            }
            MallItem entity = new MallItem();
            entity.setItemNo(itemNo);
            entity.setStatus(status.getCode());
            baseMapper.updateByItemNo(entity);
        }
        return CommonResult.success("操作成功!");
    }

    @Override
    public boolean addOrganize(MallItemOrgModel model) {
        //校验token是否是集团
        MallItemOrganize mio = mallItemOrganizeService.organizeItemNoToItemNo(model.getItemNo());
        AssertUtils.isTrue(mio.getIsCopy() == MallItemOriganizeEnum.IsCopy.Type_1.getCode(), "该商品是集团供货商品，不可执行此操作");
        model.setItemNo(mio.getOrganizeItemNo());
        //集团供货校验
        MallItemEnum.IsOrganize isOrganize = MallItemEnum.IsOrganize.parseCode(model.getIsOrganize());
        AssertUtils.notNull(isOrganize, "集团供货 状态不正确");
        MallItemEnum.IsAll isAll = MallItemEnum.IsAll.parseCode(model.getIsAll());
        if(isOrganize.getCode() == MallItemEnum.IsOrganize.Type_1.getCode()){
            AssertUtils.notNull(isAll, "全部门店/指定门店 类型不正确");
            if(MallItemEnum.IsAll.Type_1.getCode().equals(isAll.getCode())){
                AssertUtils.notNull(model.getShopNos(), "同步门店编号不能为空");
            }
        }

        LambdaQueryWrapper<MallItem> itemWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo,model.getItemNo());
        MallItem item = baseMapper.selectOne(itemWrapper);
        model.setCategoryNo(item.getCategoryNo());

        //如果已开启集团供货，则先清除旧数据,假删除
        MallItemOrganize io = new MallItemOrganize();
        io.setOrganizeItemNo(model.getItemNo());
        io.setShopNo(getShopNo());
        io.setIsDel(1);
        mallItemOrganizeService.deleteOrg(io);

        LambdaQueryWrapper<MallShop> shopWrapper = new LambdaQueryWrapper<MallShop>()
                .eq(MallShop::getShopNo,getShopNo());
        MallShop shop = mallShopDao.selectOne(shopWrapper);
        Organize organize = organizeService.getById(shop.getOrganizeId());
        //是集团，判断是否开启 集团供货
        if(organize.getIsGroup() == OrganizeEnum.IsGroup.Type_1.getCode() && isOrganize.getCode().equals(MallItemEnum.IsOrganize.Type_1.getCode())){
            //全部门店，查询遍历，处理商品分类，存在则归类，不存在则创建
            MallCategoryResp mc = mallCategoryService.getByChildCategoryNo(null,model.getCategoryNo());
            if(MallItemEnum.IsAll.Type_0.getCode().equals(isAll.getCode())){
                List<String> shopList = mallShopDao.getByOrganizeId(getOrganizeId()).stream().map(MallShop::getShopNo).collect(Collectors.toList());
                shopList.forEach(shopNo -> {
                    createCategory(model.getItemNo(),shopNo,mc,"");
                });
            }else {//指定门店，遍历，处理商品分类，存在则归类，不存在则创建
                model.getShopNos().forEach(shopNo -> {
                    createCategory(model.getItemNo(),shopNo,mc,"");
                });
            }
        }

        //更新mall_item是否集团供货标识:关闭
        MallItem t = new MallItem();
        t.setItemNo(model.getItemNo());
        t.setIsOrganize(model.getIsOrganize());
        t.setIsAll(model.getIsAll());
        baseMapper.updateOrgByItemNo(t);
        return true;
    }

    public void createCategory(String itemNo,String shopNo,MallCategoryResp i,String parentCategoryNo) {
        LambdaQueryWrapper<MallCategory> cpWrapper = new LambdaQueryWrapper<MallCategory>()
                .eq(MallCategory::getShopNo,shopNo)
                .eq(MallCategory::getCategoryName,i.getCategoryName())
                .eq(MallCategory::getLayer,i.getLayer());
        MallCategory cp = mallCategoryService.getOne(cpWrapper);
        MallCategory cpModel = new MallCategory();
        if(cp == null){
            //不存在，创建分类
            cpModel.setShopNo(shopNo);
            cpModel.setCategoryNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
            cpModel.setParentCategoryNo(parentCategoryNo);
            cpModel.setCategoryName(i.getCategoryName());
            cpModel.setCategoryImg(i.getCategoryImg());
            cpModel.setLayer(i.getLayer());
            cpModel.setSortValue(0);
            cpModel.setStatus(i.getStatus());
            mallCategoryService.save(cpModel);
        }else {
            cpModel.setCategoryNo(cp.getCategoryNo());
        }
        if(null == i.getCategory()){
            //最后一级分类，插入数据
            //先判断是否存在，存在则改状态，不存在则新增
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
                mallItemOrganizeService.save(mioModel);
            }else{
                mallItemOrganizeService.updateOrg(io);
            }

        }else{
            createCategory(itemNo,shopNo,i.getCategory(),cpModel.getCategoryNo());
        }
    }

}
