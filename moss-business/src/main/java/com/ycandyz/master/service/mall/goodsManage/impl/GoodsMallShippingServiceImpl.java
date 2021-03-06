package com.ycandyz.master.service.mall.goodsManage.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.dao.mall.goodsManage.GoodsMallShippingDao;
import com.ycandyz.master.dao.mall.goodsManage.GoodsMallShippingRegionDao;
import com.ycandyz.master.dao.mall.goodsManage.GoodsMallShippingRegionProvinceDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.dto.mall.MallShippingDTO;
import com.ycandyz.master.dto.mall.MallShippingKwDTO;
import com.ycandyz.master.dto.mall.MallShippingRegionDTO;
import com.ycandyz.master.dto.mall.MallShippingRegionProvinceDTO;
import com.ycandyz.master.enums.RegionEnum;
import com.ycandyz.master.enums.SortValueEnum;
import com.ycandyz.master.enums.StatusEnum;
import com.ycandyz.master.entities.mall.goodsManage.MallShipping;
import com.ycandyz.master.entities.mall.goodsManage.MallShippingRegion;
import com.ycandyz.master.entities.mall.goodsManage.MallShippingRegionProvince;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.mall.goodsManage.GoodsMallShippingService;
import com.ycandyz.master.utils.DateUtil;
import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.vo.MallShippingRegionVO;
import com.ycandyz.master.vo.MallShippingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class GoodsMallShippingServiceImpl implements GoodsMallShippingService {


    @Resource
    private GoodsMallShippingRegionProvinceDao mallShippingRegionProvinceDao;

    @Resource
    private GoodsMallShippingDao mallShippingDao;

    @Resource
    private GoodsMallShippingRegionDao mallShippingRegionDao;

    /**
     * @Description: 添加运费模版
    */
    @Override
    @Transactional
    public List<MallShippingDTO> insert(MallShippingVO mallShippingVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        String shippingNo = String.valueOf(IDGeneratorUtils.getLongId());

        MallShipping mallShipping = new MallShipping();
        mallShipping.setShopNo(shopNo);
        mallShipping.setShippingNo(shippingNo);
        mallShipping.setShippingName(mallShippingVO.getShippingName());
        mallShipping.setShippingMethod(mallShippingVO.getShippingMethod());
        mallShipping.setSortValue(SortValueEnum.DEFAULT.getCode());
        mallShipping.setStatus(StatusEnum.DEFAULT.getCode());
        log.info("添加运费模版表mall_shipping:{}",mallShipping);
        mallShippingDao.addMallShipping(mallShipping);
        MallShippingRegionVO[] regions = mallShippingVO.getRegions();
        updateMallShippingRegion(shippingNo, regions);
        //添加完后展示全部
        List<MallShippingDTO> mallShippingDTOS = selMallShippingByShopNo();
        return mallShippingDTOS;
    }


    /**
     * @Description: 编辑模版
     * @Author li Zhuo
     * @Date 2020/10/19
     * @Version: V1.0
    */
    @Override
    public List<MallShippingDTO> update(MallShippingVO mallShippingVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        log.info("编辑模版入参:{};:{}",mallShippingVO,shopNo);
        String shippingNo = mallShippingVO.getShippingNo();
        MallShippingRegionVO[] regions = mallShippingVO.getRegions();
        int r = mallShippingDao.updateMallShipping(shopNo,mallShippingVO);
        if (regions.length > 0){
            int i = mallShippingRegionDao.delMallShippingRegionByshippingNo(shippingNo);
            updateMallShippingRegion(shippingNo, regions);
        }
        List<MallShippingDTO> mallShippingDTOS = selMallShippingByShopNo();
        return mallShippingDTOS;
    }

    private void updateMallShippingRegion(String shippingNo, MallShippingRegionVO[] regions) {
        for (MallShippingRegionVO m: regions) {
            MallShippingRegion mallShippingRegion = new MallShippingRegion();
            String regionNo = String.valueOf(IDGeneratorUtils.getLongId());
            mallShippingRegion.setShippingNo(shippingNo);
            mallShippingRegion.setFirstCount(m.getFirstCount());
            mallShippingRegion.setFirstPrice(m.getFirstPrice());
            mallShippingRegion.setMoreCount(m.getMoreCount());
            mallShippingRegion.setMorePrice(m.getMorePrice());
            mallShippingRegion.setRegionNo(regionNo);
            log.info("添加运费模版表mall_shipping_region:{}",mallShippingRegion);
            mallShippingRegionDao.addmallShippingRegion(mallShippingRegion);
            for (String p: m.getProvinces()) {
                MallShippingRegionProvince mallShippingRegionProvince = new MallShippingRegionProvince();
                mallShippingRegionProvince.setRegionNo(regionNo);
                mallShippingRegionProvince.setProvince(p);
                log.info("添加运费模版表mall_shipping_region_province:{}",mallShippingRegionProvince);
                mallShippingRegionProvinceDao.addMallShippingRegionProvince(mallShippingRegionProvince);
            }
        }
    }

    /**
     * @Description: 根据shippingNo查询运费模版
    */
    @Override
    public MallShippingDTO select(String shippingNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
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
    public Page<MallShippingKwDTO>  selectByKeyWord(PageResult pageResult, String shippingName) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        log.info("根据关键字查询模版:shopNo:{};shippingName:{}",shopNo,shippingName);
        Page pageQuery = new Page(pageResult.getPage(),pageResult.getPage_size(),pageResult.getTotal());
        Page<MallShipping> page = mallShippingDao.selMallShippingByKeyWord(pageQuery,shippingName,shopNo);
        List<MallShippingKwDTO> mallShippingKwDTOS = new ArrayList<>();
        Page<MallShippingKwDTO> page1 = new Page<>();
        MallShippingKwDTO mallShippingKwDTO = null;
        if (page.getRecords()!=null && page.getRecords().size()>0) {
            for (MallShipping m : page.getRecords()) {
                mallShippingKwDTO = new MallShippingKwDTO();
                mallShippingKwDTO.setShippingName(m.getShippingName());
                mallShippingKwDTO.setShippingMethod(m.getShippingMethod());
                List<MallShippingRegion> mallShippingRegions = mallShippingRegionDao.selMallShippingRegionByShippingNo(m.getShippingNo());
                MallShippingRegionDTO mallShippingRegionDTO = null;
                ArrayList<MallShippingRegionDTO> mallShippingRegionDTOList = Lists.newArrayList();
                for (MallShippingRegion mr : mallShippingRegions) {
                    mallShippingRegionDTO = new MallShippingRegionDTO();
                    BeanUtils.copyProperties(mr,mallShippingRegionDTO);
                    List<String> provinces = mallShippingRegionProvinceDao.selMallShippingRegionProvinceByRegionNo(mr.getRegionNo());
                    String[] pAr = new String[provinces.size()];
                    mallShippingRegionDTO.setProvinces(provinces.toArray(pAr));
                    mallShippingRegionDTOList.add(mallShippingRegionDTO);
                }
                MallShippingRegionDTO[] mallShippingRegionDTOS = new MallShippingRegionDTO[mallShippingRegionDTOList.size()];
                mallShippingKwDTO.setRegions(mallShippingRegionDTOList.toArray(mallShippingRegionDTOS));
                mallShippingKwDTO.setCreatedStr(DateUtil.defaultFormatDate(m.getCreatedTime()));
                mallShippingKwDTO.setUpdatedStr(DateUtil.defaultFormatDate(m.getUpdatedTime()));
                mallShippingKwDTOS.add(mallShippingKwDTO);
            }
        }
        BeanUtils.copyProperties(page,page1);
        page1.setRecords(mallShippingKwDTOS);
        return page1;
    }

    /**
     * @Description:根据商户号查询运费模版
    */
    @Override
    public List<MallShippingDTO> selMallShippingByShopNo() {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
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
    public List<MallShippingRegionProvinceDTO> selectRegion() {
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
    public int delete(String shippingNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        int mp = mallShippingDao.delMallShippingByshippingNo(shopNo,shippingNo);
        return mp;
    }
}
