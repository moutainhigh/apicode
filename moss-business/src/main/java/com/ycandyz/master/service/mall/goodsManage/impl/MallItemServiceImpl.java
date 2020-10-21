package com.ycandyz.master.service.mall.goodsManage.impl;

import com.google.common.collect.Lists;
import com.ycandyz.master.dao.mall.goodsManage.MallItemDao;
import com.ycandyz.master.dao.mall.goodsManage.MallSkuDao;
import com.ycandyz.master.dao.mall.goodsManage.MallSkuSpecDao;
import com.ycandyz.master.dto.mall.goodsManage.MallCategoryDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallItemDTO;
import com.ycandyz.master.enums.SortValueEnum;
import com.ycandyz.master.entities.PickupAddress;
import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import com.ycandyz.master.entities.mall.goodsManage.MallSku;
import com.ycandyz.master.entities.mall.goodsManage.MallSkuSpec;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.base.PickupAdressService;
import com.ycandyz.master.service.mall.goodsManage.MallCategoryService;
import com.ycandyz.master.service.mall.goodsManage.MallItemService;
import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.vo.MallItemOprVO;
import com.ycandyz.master.vo.MallItemVO;
import com.ycandyz.master.vo.MallSkuSpecsVO;
import com.ycandyz.master.vo.MallSkuVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
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

    @Resource
    private MallCategoryService mallCategoryService;

    @Resource
    private PickupAdressService pickupAdressService;


    /**
     * @Description: 添加商品
    */
    @Override
    @Transactional
    public void addMallItem(MallItemVO mallItemVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        log.info("添加商品入参::mallItemVO:{};userVO:{}",mallItemVO,userVO);
        String itemNo = String.valueOf(IDGeneratorUtils.getLongId());
        MallItem mallItem = new MallItem();
        BeanUtils.copyProperties(mallItemVO,mallItem);
        mallItem.setShopNo(userVO.getShopNo());
        mallItem.setItemNo(itemNo);
        mallItem.setBanners(PraseArraytoString(mallItemVO.getBanners()));
        mallItem.setSubStockMethod(mallItemVO.getSubStockMethod());
        mallItem.setDeliveryType(PraseArraytoString(mallItemVO.getDeliveryType()));
        mallItem.setPickupAddressIds(PraseArraytoString(mallItemVO.getPickupAddressIds()));
        mallItemDao.addMallItem(mallItem);
        MallSkuVO[] skus = mallItemVO.getSkus();
        insertMallSku(itemNo, skus);
    }

    private String PraseArraytoString(Object[] integers) {
        List<Object> lists = Lists.newArrayList();
        Arrays.stream(integers).forEach(s -> lists.add(s));
        return lists+"";
    }

    private void insertMallSku(String itemNo, MallSkuVO[] skus) {
        MallSku mallSku = null;
        if (skus != null && skus.length > 0){
            int i = 0;
            for (MallSkuVO mvo : skus) {
                mallSku = new MallSku();
                String skuNobyIdG = String.valueOf(IDGeneratorUtils.getLongId());
                StringBuffer skuNo = new StringBuffer();
                skuNo.append(itemNo)
                        .append("_")
                        .append(skuNobyIdG);
                mallSku.setItemNo(itemNo);
                mallSku.setSkuNo(skuNo.toString());
                mallSku.setPrice(mvo.getPrice());
                mallSku.setSalePrice(mvo.getSalePrice());
                mallSku.setStock(mvo.getStock());
                mallSku.setGoodsNo(mvo.getGoodsNo());
                mallSku.setSortValue(SortValueEnum.DEFAULT.getCode()+i);
                mallSkuDao.addMallSku(mallSku);
                MallSkuSpecsVO[] skuSpecs = mvo.getSkuSpecs();
                MallSkuSpec mallSkuSpec = null;
                if (skuSpecs != null && skuSpecs.length > 0){
                    for (MallSkuSpecsVO mspec: skuSpecs) {
                        mallSkuSpec = new MallSkuSpec();
                        mallSkuSpec.setSkuNo(skuNo.toString());
                        mallSkuSpec.setSpecName(mspec.getSpecName());
                        mallSkuSpec.setSpecValue(mspec.getSpecValue());
                        mallSkuSpecDao.addMallSkuSpec(mallSkuSpec);
                    }
                }
            i++;
            }
        }
    }

    /**
     * @Description: 查询商品详情
    */
    @Override
    public MallItemDTO selMallItemByitemNo(String itemNo) {
        UserVO userVO = UserRequest.getCurrentUser();
       log.info("查询商品详情入参userVO:{};itemNo:{}",userVO,itemNo);
        MallItemDTO mallItemDTO = new MallItemDTO();
        List<MallSkuVO> mallSkuVOs = Lists.newArrayList();
        List<MallSkuSpecsVO> mallSkuSpecsVOs = Lists.newArrayList();
        MallSkuVO[] mallSkuVOArr = new MallSkuVO[mallSkuVOs.size()];
        MallSkuSpecsVO[] mallSkuSpecsVOArr = new MallSkuSpecsVO[mallSkuSpecsVOs.size()];
        MallItem mallItem = mallItemDao.selMallItemByitemNo(userVO.getShopNo(),itemNo);
        if (mallItem == null){
            log.info("根据userVO:{};itemNo:{}查询商品详情不存在",userVO,itemNo);
            return null;
        }
        List<MallSku> mallSkus = mallSkuDao.selMallSkuByitemNo(itemNo);
        if (mallSkus != null && mallSkus.size() > 0){
            for (MallSku m: mallSkus) {
                List<MallSkuSpec> mallSkuSpecs = mallSkuSpecDao.selMallSkuSpecBySkuNo(m.getSkuNo());
                if (mallSkuSpecs != null && mallSkuSpecs.size() > 0){
                    for (MallSkuSpec mspec: mallSkuSpecs) {
                        MallSkuSpecsVO mallSkuSpecsVO =new MallSkuSpecsVO();
                        BeanUtils.copyProperties(mspec,mallSkuSpecsVO);
                        mallSkuSpecsVOs.add(mallSkuSpecsVO);
                    }
                }
                MallSkuVO mallSkuVO = new MallSkuVO();
                BeanUtils.copyProperties(m,mallSkuVO);
                mallSkuVO.setSkuSpecs(mallSkuSpecsVOs.toArray(mallSkuSpecsVOArr));
                mallSkuVOs.add(mallSkuVO);
            }
        }
        MallCategoryDTO mallCategoryDTO = mallCategoryService.selCategoryByCategoryNo(mallItem.getCategoryNo());
        if (mallCategoryDTO != null){
            mallItemDTO.setCategorNo(mallItem.getCategoryNo());
            mallItemDTO.setCategoryName(mallCategoryDTO.getCategoryName());
            mallItemDTO.setParentCategoryNo(mallCategoryDTO.getParentCategoryNo());
            mallItemDTO.setParentCategoryName(mallCategoryDTO.getParentCategory() == null ? null : mallCategoryDTO.getParentCategory().getCategoryName());
        }
        BeanUtils.copyProperties(mallItem,mallItemDTO);
        mallItemDTO.setSkus(mallSkuVOs.toArray(mallSkuVOArr));
        mallItemDTO.setDeliveryType(mallItem.getDeliveryType());
        //查询自提地址
        List<Integer> ids = parseIds(mallItem.getPickupAddressIds());
        List<PickupAddress> pickupAddresses = pickupAdressService.selPickupAddress(ids);
        if (pickupAddresses != null){
            PickupAddress[] pickupAddressesArr = new PickupAddress[pickupAddresses.size()];
            mallItemDTO.setPickupAddressIds(pickupAddresses.toArray(pickupAddressesArr));
        }else {
            mallItemDTO.setPickupAddressIds(null);
        }

        return mallItemDTO;
    }

    private List<Integer> parseIds(String s) {
        int beginIndex = s.indexOf("[") == 0 ? 1 : 0;
        int endIndex = s.lastIndexOf("]") + 1 == s.length() ? s.lastIndexOf("]") : s.length();
        s = s.substring(beginIndex, endIndex);
        if (s.length() <= 0){
            return null;
        }
        List<Integer> ids = Lists.newArrayList();
        Arrays.stream(s.split(",")).forEach(s1 -> ids.add(Integer.parseInt(s1)));
        return ids;
    }


    /**
     * @Description: 上下架商品
    */
    @Override
    public int oprbyItemNo(MallItemOprVO mallItemOprVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        int i = 0;
        if (mallItemOprVO != null){
            i = mallItemDao.oprbyItemNo(shopNo, Arrays.asList(mallItemOprVO.getItemNoList()),mallItemOprVO.getStatus());
        }
        return i;
    }


    /**
     * @Description: 删除商品
    */
    @Override
    public int delMallItemByItemNo(String itemNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        int r = mallItemDao.delMallItemByItemNo(shopNo,itemNo);
        return r;
    }

    /**
     * @Description: 编辑商品
    */
    @Override
    public void updateMallItem(MallItemVO mallItemVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        log.info("编辑商品入参:{};:{}",mallItemVO,shopNo);
        String itemNo = mallItemVO.getItemNo();
        MallSkuVO[] skus = mallItemVO.getSkus();

        MallItem mallItem = new MallItem();
        BeanUtils.copyProperties(mallItemVO,mallItem);
        mallItem.setShopNo(shopNo);
        mallItem.setBanners(PraseArraytoString(mallItemVO.getBanners()));
        mallItem.setSubStockMethod(mallItemVO.getSubStockMethod());
        mallItem.setDeliveryType(PraseArraytoString(mallItemVO.getDeliveryType()));
        mallItem.setPickupAddressIds(PraseArraytoString(mallItemVO.getPickupAddressIds()));

        int i = mallItemDao.updateMallItem(mallItem,shopNo);
        if (skus != null && skus.length > 0){
            int i2 = mallSkuDao.deleteSkuDao(itemNo);
            insertMallSku(itemNo,skus);
        }


    }

}
