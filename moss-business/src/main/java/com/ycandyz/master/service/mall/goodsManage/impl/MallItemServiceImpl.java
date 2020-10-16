package com.ycandyz.master.service.mall.goodsManage.impl;

import com.google.common.collect.Lists;
import com.ycandyz.master.dao.mall.goodsManage.MallItemDao;
import com.ycandyz.master.dao.mall.goodsManage.MallSkuDao;
import com.ycandyz.master.dao.mall.goodsManage.MallSkuSpecDao;
import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import com.ycandyz.master.entities.mall.goodsManage.MallSku;
import com.ycandyz.master.entities.mall.goodsManage.MallSkuSpec;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.goodsManage.MallItemService;
import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.vo.MallItemVO;
import com.ycandyz.master.vo.MallSkuSpecsVO;
import com.ycandyz.master.vo.MallSkuVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 商品管理
 * @Author li Zhuo
 * @Date 2020/10/16
 * @Version: V1.0
*/
@Service
@Slf4j
public class MallItemServiceImpl implements MallItemService {


    @Resource
    private MallItemDao mallItemDao;

    @Resource
    private MallSkuDao mallSkuDao;

    @Resource
    private MallSkuSpecDao mallSkuSpecDao;
    /**
     * @Description: 添加商品
    */
    @Override
    @Transactional
    public void addMallItem(MallItemVO mallItemVO, UserVO userVO) {
        log.info("添加商品入参:{}",mallItemVO);
        String itemNo = String.valueOf(IDGeneratorUtils.getLongId());
        MallItem mallItem = new MallItem();
        mallItem.setShopNo(userVO.getShopNo());
        mallItem.setCategoryNo(mallItemVO.getCategorNo());
        mallItem.setItemNo(itemNo);
        mallItem.setItemName(mallItemVO.getItemName());
        mallItem.setItemText(mallItemVO.getItemText());
        //mallItem.setItemCover(mallItemVO.getI)  //前端未传参数
        mallItem.setBanners(ArrayUtils.toString(mallItemVO.getBanners()));
        mallItem.setBaseSales(mallItemVO.getBaseSales());
        // mallItem.setRealSales(mallItemVO.getr) //前端未传参数
        mallItem.setSortValue(mallItemVO.getSortValue());
        mallItem.setShareDescr(mallItemVO.getSharDescr());
        mallItem.setShareImg(mallItemVO.getShareImg());
        mallItem.setPrice(mallItemVO.getPrice());
        //mallItem.setLowestSalePrice(mallItemVO.getlo)
        //mallItem.setHighestSalePrice(mallItemVO.geth)
        mallItem.setStock(mallItemVO.getStock());
        //mallItem.setFrozenStock(mallItemVO.getf)
        mallItem.setSubStockMethod(mallItemVO.getSubStockMethod());
        mallItem.setGoodsNo(mallItemVO.getGoodsNo());
        mallItem.setDeliverMethod(mallItemVO.getDeliverMethod());
        mallItem.setStatus(mallItemVO.getStatus());
        mallItem.setIsUnifyShipping(mallItemVO.getIsUnifyShipping());
        mallItem.setShippingNo(mallItemVO.getShippingNo());
        mallItem.setUnifyShipping(mallItemVO.getUnifyShipping());
        mallItem.setInitialPurchases(mallItemVO.getInitialPurchases());
        mallItem.setLimitCycleType(mallItemVO.getLimitCycleType());
        mallItem.setLimitSkus(mallItemVO.getLimitSkus());
        mallItem.setLimitOrders(mallItemVO.getLimitOrders());
        //mallItem.setQrCodeUrl(mallItemVO.getq);
        //mallItem.setIsEnableShare(mallItemVO.geti)
//        mallItem.setShareSecondAmount();
//        mallItem.setShareSecondMethod();
//        mallItem.setShareSecondRate();
//        mallItem.setShareSecondLevelAmount();
//        mallItem.setShareSecondLevelMethod();
//        mallItem.setShareSecondLevelRate();
//        mallItem.setShareThirdAmount();
//        mallItem.setShareThirdMethod();
//        mallItem.setShareThirdRate();
//        mallItem.setShareThirdLevelAmount();
//        mallItem.setShareThirdLevelMethod();
//        mallItem.setShareThirdLevelRate();
        mallItem.setDeliveryType(ArrayUtils.toString(mallItemVO.getDeliveryType()));
        mallItem.setPickupAddressIds(ArrayUtils.toString(mallItemVO.getPickupDddrIds()));
        mallItem.setDeliveryTypeBak(ArrayUtils.toString(mallItemVO.getDeliveryType()));
        mallItemDao.addMallItem(mallItem);
        MallSkuVO[] skus = mallItemVO.getSkus();
        MallSku mallSku = null;
        if (skus != null && skus.length > 0){
            for (MallSkuVO mvo : skus) {
                mallSku = new MallSku();
                String skuNo = "md5";
                mallSku.setItemNo(itemNo);
                mallSku.setSkuNo(skuNo);  //后面再改getMallSkuNo()
                mallSku.setPrice(mvo.getPrice());
                mallSku.setSalePrice(mvo.getSalePrice());
                mallSku.setStock(mvo.getStock());
                mallSku.setGoodsNo(mvo.getGoodsNo());
                mallSkuDao.addMallSku(mallSku);
                MallSkuSpecsVO[] skuSpecs = mvo.getSkuSpecs();
                MallSkuSpec mallSkuSpec = null;
                if (skuSpecs != null && skuSpecs.length > 0){
                    for (MallSkuSpecsVO mspec: skuSpecs) {
                        mallSkuSpec = new MallSkuSpec();
                        mallSkuSpec.setSkuNo(skuNo);
                        mallSkuSpec.setSpecName(mspec.getSpecName());
                        mallSkuSpec.setSpecValue(mspec.getSpecValue());
                        mallSkuSpecDao.addMallSkuSpec(mallSkuSpec);
                    }
                }

            }
        }
    }

    /**
     * @Description: 查询商品详情
    */
    @Override
    public MallItemVO selMallItemByitemNo(UserVO userVO, String itemNo) {
        MallItem mallItem = mallItemDao.selMallItemByitemNo(userVO.getShopNo(),itemNo);
        MallItemVO mallItemVO = new MallItemVO();
        MallSkuVO mallSkuVO = null;
        List<MallSkuVO> mallSkuVOs = Lists.newArrayList();
        MallSkuSpecsVO mallSkuSpecsVO = null;
        List<MallSkuSpecsVO> mallSkuSpecsVOs = Lists.newArrayList();
        MallSkuVO[] mallSkuVOArr = new MallSkuVO[mallSkuVOs.size()];
        MallSkuSpecsVO[] mallSkuSpecsVOArr = new MallSkuSpecsVO[mallSkuSpecsVOs.size()];
        if (mallItem != null){
            BeanUtils.copyProperties(mallItem,mallItemVO);
        }
        List<MallSku> mallSkus = mallSkuDao.selMallSkuByitemNo(itemNo);
        if (mallSkus != null && mallSkus.size() > 0){
            for (MallSku m: mallSkus) {
                List<MallSkuSpec> mallSkuSpecs = mallSkuSpecDao.selMallSkuSpecBySkuNo(m.getSkuNo());
                if (mallSkuSpecs != null && mallSkuSpecs.size() > 0){
                    for (MallSkuSpec mspec: mallSkuSpecs) {
                        BeanUtils.copyProperties(mspec,mallSkuSpecsVO);
                        mallSkuSpecsVOs.add(mallSkuSpecsVO);
                    }
                }
                mallSkuVO = new MallSkuVO();
                BeanUtils.copyProperties(m,mallSkuVO);
                mallSkuVO.setSkuSpecs(mallSkuSpecsVOs.toArray(mallSkuSpecsVOArr));
                mallSkuVOs.add(mallSkuVO);
            }
        }
        mallItemVO.setSkus(mallSkuVOs.toArray(mallSkuVOArr));
        return mallItemVO;
    }


}
