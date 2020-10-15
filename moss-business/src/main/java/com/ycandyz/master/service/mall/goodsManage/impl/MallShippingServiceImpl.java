package com.ycandyz.master.service.mall.goodsManage.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ycandyz.master.constants.RedisConstants;
import com.ycandyz.master.dao.mall.goodsManage.MallShippingDao;
import com.ycandyz.master.dao.mall.goodsManage.MallShippingRegionDao;
import com.ycandyz.master.dao.mall.goodsManage.MallShippingRegionProvinceDao;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingRegionDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingRegionProvinceDTO;
import com.ycandyz.master.enmus.RegionEnum;
import com.ycandyz.master.enmus.SortValueEnum;
import com.ycandyz.master.enmus.StatusEnum;
import com.ycandyz.master.entities.mall.goodsManage.MallShipping;
import com.ycandyz.master.entities.mall.goodsManage.MallShippingRegion;
import com.ycandyz.master.entities.mall.goodsManage.MallShippingRegionProvince;
import com.ycandyz.master.service.mall.goodsManage.MallShippingService;
import com.ycandyz.master.utils.RedisUtil;
import com.ycandyz.master.utils.SnowFlakeUtil;
import com.ycandyz.master.vo.MallShippingRegionVO;
import com.ycandyz.master.vo.MallShippingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 运费规模模版
 * @Author li Zhuo
 * @Date 2020/10/12
 * @Version: V1.0
*/
@Service
@Slf4j
public class MallShippingServiceImpl implements MallShippingService {


    @Resource
    private MallShippingRegionProvinceDao mallShippingRegionProvinceDao;

    @Resource
    private MallShippingDao mallShippingDao;

    @Resource
    private MallShippingRegionDao mallShippingRegionDao;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @Description: 添加运费模版
    */
    @Override
    @Transactional
    public List<MallShippingDTO> addMallShipping(MallShippingVO mallShippingVO) {
        SnowFlakeUtil snowFlake = new SnowFlakeUtil(1, 1);
        String shopNo = (String) redisUtil.get(RedisConstants.SHOPNO);
        String shippingNo = String.valueOf(snowFlake.nextId());

        MallShipping mallShipping = new MallShipping();
        mallShipping.setShopNo(shopNo);
        mallShipping.setShippingNo(shippingNo);
        mallShipping.setShippingName(mallShippingVO.getShippingName());
        mallShipping.setShippingMethod(mallShippingVO.getShippingMethod());
        mallShipping.setSortValue(SortValueEnum.DEFAULT.getCode());
        mallShipping.setStatus(StatusEnum.DEFAULT.getCode());
        log.info("添加运费模版表mall_shipping:{}",mallShipping);
        mallShippingDao.addMallShipping(mallShipping);

        MallShippingRegion mallShippingRegion = new MallShippingRegion();
        MallShippingRegionVO[] regions = mallShippingVO.getRegions();
        for (MallShippingRegionVO m : regions) {
            String regionNo = String.valueOf(snowFlake.nextId());
            mallShippingRegion.setShippingNo(shippingNo);
            mallShippingRegion.setFirstCount(m.getFirstCount());
            mallShippingRegion.setFirstPrice(m.getFirstPrice());
            mallShippingRegion.setMoreCount(m.getMoreCount());
            mallShippingRegion.setMorePrice(m.getMorePrice());
            mallShippingRegion.setRegionNo(regionNo);
            log.info("添加运费模版表mall_shipping_region:{}",mallShipping);
            mallShippingRegionDao.addmallShippingRegion(mallShippingRegion);
            for (String p: m.getProvinces()) {
                MallShippingRegionProvince mallShippingRegionProvince = new MallShippingRegionProvince();
                mallShippingRegionProvince.setRegionNo(regionNo);
                mallShippingRegionProvince.setProvince(p);
                log.info("添加运费模版表mall_shipping_region_province:{}",mallShipping);
                mallShippingRegionProvinceDao.addMallShippingRegionProvince(mallShippingRegionProvince);
            }
        }
        //添加完后展示全部
        List<MallShippingDTO> mallShippingDTOS = selMallShippingByShopNo(shopNo);
        return mallShippingDTOS;
    }

    @Override
    public void updateMallShipping(MallShipping mallShipping) {

    }
    /**
     * @Description: 根据shippingNo查询运费模版
    */
    @Override
    public MallShippingDTO selMallShippingByShippingNo(String shippingNo) {
        String shopNo = (String) redisUtil.get(RedisConstants.SHOPNO);
        log.info("根据shippingNo查询运费模版入参:shopNo:{},shippingNo:{}",shopNo,shippingNo);
        MallShippingDTO mallShippingDTO = new MallShippingDTO();
        MallShipping mallShipping = mallShippingDao.selMallShippingByShippingNo(shopNo,shippingNo);
        if (mallShipping == null){
            log.warn("根据shippingNo:{}查询运费模版不存在",shippingNo);
            return null;
        }
        mallShippingDTO.setShippingName(mallShipping.getShippingName());
        mallShippingDTO.setShippingMethod(mallShipping.getShippingMethod());
        List<MallShippingRegion> mallShippingRegions = mallShippingRegionDao.selMallShippingRegionByShippingNo(shippingNo);
        MallShippingRegionDTO mallShippingRegionDTO = null;
        ArrayList<MallShippingRegionDTO> mallShippingRegionDTOList = Lists.newArrayList();
        for (MallShippingRegion mr: mallShippingRegions) {
            mallShippingRegionDTO = new MallShippingRegionDTO();
            mallShippingRegionDTO.setFirstCount(mr.getFirstCount());
            mallShippingRegionDTO.setFirstPrice(mr.getFirstPrice());
            mallShippingRegionDTO.setMoreCount(mr.getMoreCount());
            mallShippingRegionDTO.setMorePrice(mr.getMorePrice());
            List<String> provinces = mallShippingRegionProvinceDao.selMallShippingRegionProvinceByRegionNo(mr.getRegionNo());
            String[] pAr = new String[provinces.size()];
            mallShippingRegionDTO.setProvinces(provinces.toArray(pAr));
            mallShippingRegionDTOList.add(mallShippingRegionDTO);
        }
        MallShippingRegionDTO[] mallShippingRegionDTOS = new MallShippingRegionDTO[mallShippingRegionDTOList.size()];
        mallShippingDTO.setRegions(mallShippingRegionDTOList.toArray(mallShippingRegionDTOS));
        log.info("shippingNo:{};根据shippingNo查询数据库结果:{}",shippingNo,mallShippingRegionDTO);
        return  mallShippingDTO;
    }


    /**
     * @Description: 根据关键字查询模版
    */
    @Override
    public PageInfo<MallShipping>  selMallShippingByKeyWord(Integer page, Integer pageSize, String shippingName) {
        PageHelper.startPage(page,pageSize);
        List<MallShipping> mallShippings = mallShippingDao.selMallShippingByKeyWord(shippingName);
        PageInfo<MallShipping> pageInfo = new PageInfo<>(mallShippings);
        return pageInfo;
    }

    /**
     * @Description:根据商户号查询运费模版
    */
    @Override
    public List<MallShippingDTO> selMallShippingByShopNo(String shopNo) {
        List<MallShippingDTO> mallShippingDTOS = new ArrayList<>();
        MallShippingDTO mallShippingDTO = null;
        List<MallShipping> mallShippings = mallShippingDao.selMallShippingByShopNo(shopNo);

        for (MallShipping m:mallShippings) {
            mallShippingDTO = new MallShippingDTO();
            mallShippingDTO.setShippingName(m.getShippingName());
            mallShippingDTO.setShippingMethod(m.getShippingMethod());
            List<MallShippingRegion> mallShippingRegions = mallShippingRegionDao.selMallShippingRegionByShippingNo(m.getShippingNo());
            MallShippingRegionDTO mallShippingRegionDTO = null;
            ArrayList<MallShippingRegionDTO> mallShippingRegionDTOList = Lists.newArrayList();
            for (MallShippingRegion mr: mallShippingRegions) {
                mallShippingRegionDTO = new MallShippingRegionDTO();
                mallShippingRegionDTO.setFirstCount(mr.getFirstCount());
                mallShippingRegionDTO.setFirstPrice(mr.getFirstPrice());
                mallShippingRegionDTO.setMoreCount(mr.getMoreCount());
                mallShippingRegionDTO.setMorePrice(mr.getMorePrice());
                List<String> provinces = mallShippingRegionProvinceDao.selMallShippingRegionProvinceByRegionNo(mr.getRegionNo());
                String[] pAr = new String[provinces.size()];
                mallShippingRegionDTO.setProvinces(provinces.toArray(pAr));
                mallShippingRegionDTOList.add(mallShippingRegionDTO);
            }
            MallShippingRegionDTO[] mallShippingRegionDTOS = new MallShippingRegionDTO[mallShippingRegionDTOList.size()];
            mallShippingDTO.setRegions(mallShippingRegionDTOList.toArray(mallShippingRegionDTOS));
            mallShippingDTOS.add(mallShippingDTO);
        }
        return mallShippingDTOS;
    }

    /**
     * @Description: 查询地区
     * @return
    */
    @Override
    public List<MallShippingRegionProvinceDTO> selMallShippingRegionProvince() {
        List<MallShippingRegionProvinceDTO> mallShippingRegionProvinceDTOS = new ArrayList<>();
        MallShippingRegionProvinceDTO mallShippingRegionProvinceDTO = null;
        for (RegionEnum re: RegionEnum.values()) {
            mallShippingRegionProvinceDTO = new MallShippingRegionProvinceDTO();
            mallShippingRegionProvinceDTO.setRegion(re.getRegion());
            mallShippingRegionProvinceDTO.setProvince(re.getProvince());
            mallShippingRegionProvinceDTOS.add(mallShippingRegionProvinceDTO);
        }
        log.info("查询地区结果:{}",mallShippingRegionProvinceDTOS);
        return mallShippingRegionProvinceDTOS;
    }

    /**
     * @Description: 根据shippingNo删除运费模版
    */
    @Override
    public int delMallShippingByshippingNo(String shippingNo) {
        String shopNo = (String) redisUtil.get(RedisConstants.SHOPNO);
        int mp = mallShippingDao.delMallShippingByshippingNo(shopNo,shippingNo);
        return mp;
    }
}
