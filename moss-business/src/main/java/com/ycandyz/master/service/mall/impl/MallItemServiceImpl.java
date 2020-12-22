package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.MallItemHomeDao;
import com.ycandyz.master.domain.enums.mall.MallItemEnum;
import com.ycandyz.master.domain.model.mall.MallItemModel;
import com.ycandyz.master.domain.model.mall.MallItemShelfModel;
import com.ycandyz.master.domain.model.mall.MallItemSkuModel;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.service.mall.IMallItemService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.IDGeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public MallItemResp getByItemNo(String itemNo) {
        LambdaQueryWrapper<MallItem> queryWrapper = new LambdaQueryWrapper<MallItem>()
                .eq(MallItem::getItemNo, itemNo);
        MallItem entity = baseMapper.selectOne(queryWrapper);
        MallItemResp vo = new MallItemResp();
        BeanUtils.copyProperties(entity,vo);
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
    public Page<MallItemResp> getMallItemPage(Page<MallItem> page, MallItemQuery query) {
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
        Page<MallItemResp> p =baseMapper.getMallItemPage(page,query);
        return p;
    }

    @Override
    public boolean insert(MallItemModel model) {
        //公共校验
        MallItemEnum.Type type = MallItemEnum.Type.parseCode(model.getType());
        AssertUtils.notNull(type, "销售非销售商品类型不正确");
        //公共处理赋值
        model.setItemNo(StrUtil.toString(IDGeneratorUtils.getLongId()));
        String itemCover = model.getBanners()[0];
        model.setItemCover(itemCover);
        MallItem t = new MallItem();
        //销售商品
        if(type.getCode().equals(MallItemEnum.Type.Type_0.getCode())){
            //添加Sku，sepc
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
            baseMapper.insert(t);
            //添加视频
        }else{
            MallItemEnum.NonPriceType nonPriceType = MallItemEnum.NonPriceType.parseCode(model.getNonPriceType());
            AssertUtils.notNull(nonPriceType, "价格类型不正确");

        }





        return false;
    }

    @Override
    public boolean update(MallItemModel entity) {
        return false;
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
