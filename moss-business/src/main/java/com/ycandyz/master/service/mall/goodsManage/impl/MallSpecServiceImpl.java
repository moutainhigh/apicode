package com.ycandyz.master.service.mall.goodsManage.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ycandyz.master.commonResult.CommonResult2;
import com.ycandyz.master.dao.mall.goodsManage.MallSpecDao;
import com.ycandyz.master.dao.mall.goodsManage.MallSpecValueDao;
import com.ycandyz.master.dto.mall.goodsManage.MallSpecDTO;
import com.ycandyz.master.enmus.SortValueEnum;
import com.ycandyz.master.enmus.StatusEnum;
import com.ycandyz.master.entities.mall.goodsManage.MallSpec;
import com.ycandyz.master.entities.mall.goodsManage.MallSpecValue;
import com.ycandyz.master.service.mall.goodsManage.MallSpecService;
import com.ycandyz.master.utils.DateUtil;
import com.ycandyz.master.utils.SnowFlakeUtil;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MallSpecServiceImpl implements MallSpecService {

    @Resource
    private MallSpecDao mallSpecDao;

    @Resource
    private MallSpecValueDao mallSpecValueDao;


    /**
     * @Description: 添加规格模版
    */
    @Override
    @Transactional
    public MallSpecDTO addMallSpec(MallSpecVO mallSpecVO) {
        SnowFlakeUtil snowFlake = new SnowFlakeUtil(1, 1);
        String shopNo = "61763436278748160000";
        String specNo = String.valueOf(snowFlake.nextId());
        MallSpecValue mallSpecValue = new MallSpecValue();

        String[] specValues = mallSpecVO.getSpecValues();
        for (String s: specValues) {
            mallSpecValue.setSpecNo(specNo);
            mallSpecValue.setSpecValue(s);
            mallSpecValue.setSortValue(SortValueEnum.DEFAULT.getCode());
            mallSpecValue.setStatus(StatusEnum.DEFAULT.getCode());
            mallSpecValueDao.addMallSpecValue(mallSpecValue);
        }
        MallSpec mallSpec= new MallSpec();
        mallSpec.setShopNo(shopNo);
        mallSpec.setSpecNo(specNo);
        mallSpec.setSpecName(mallSpecVO.getSpecName());
        mallSpec.setSortValue(SortValueEnum.DEFAULT.getCode());
        mallSpec.setStatus(StatusEnum.DEFAULT.getCode());
        mallSpecDao.addMallSpec(mallSpec);
        return null;
    }


    /**
     * @Description: 删除模版
    */
    @Override
    public CommonResult2 delMallSpecBySpecNo(String specNo) {
        String shopNo2 = "61763436278748160000";
        int r = mallSpecDao.delMallSpecBySpecNo(shopNo2,specNo);
        int r2 = mallSpecValueDao.delMallSpecValueBySpecNo(specNo);
        if (r > 0 & r2 >0) {
            return CommonResult2.success();
        }
        return CommonResult2.failed();
    }

    /**
     * @Description: 根据关键字查询模版
     */
    @Override
    public PageInfo<MallSpecKeyWordVO> selMallSpecByKeyWord(Integer page, Integer pageSize, String keyWord) {
        String shopNo = "61763436278748160000";
        PageHelper.startPage(page,pageSize);
        List<MallSpec> mallSpecs = mallSpecDao.selMallSpecByKeyWord(shopNo,keyWord);
        log.info("shopNo:{};keyWord:{};规格模版表查询数据库结果:{}",shopNo,keyWord,mallSpecs);
        List<MallSpecValue> mallSpecValues = mallSpecValueDao.selMallSpecValueByKeyWord(keyWord);
        log.info("keyWord:{};规格模版value表查询数据库结果:{}",keyWord,mallSpecValues);
        HashMap<String, String> specNoNameMap = Maps.newHashMap();
        HashMap<String, String> specNoValueMap = Maps.newHashMap();
        List<String> spspecNovalues = Lists.newArrayList();
        List<String> spvspecNovalues = Lists.newArrayList();
        List<String> values = Lists.newArrayList();
        List<MallSpecKeyWordVO> mallSpecKeyWordVOvalues = Lists.newArrayList();

        if (mallSpecs.size() >0 && mallSpecValues.size() == 0){
            mallSpecs.stream().forEach(m->specNoNameMap.put(m.getSpecName(),m.getSpecNo()));
            for (Map.Entry<String, String> e : specNoNameMap.entrySet()) {
                List<MallSpecValue> mallSpecValues1 = mallSpecValueDao.selMallSpecValueBySpecNo(e.getValue());
                log.info("specNo:{};规格模版value表查询数据库结果:{}",e.getValue(),mallSpecValues1);
                mallSpecValues1.stream().forEach(mv->values.add(mv.getSpecValue()));
                String[] mallSpecValues1Array = new String[mallSpecValues1.size()];
                MallSpecKeyWordVO mallSpecKeyWordVO = new MallSpecKeyWordVO();
                mallSpecKeyWordVO.setSpecNo(e.getValue());
                mallSpecKeyWordVO.setSpecName(e.getKey());
                mallSpecKeyWordVO.setSpecValues(values.toArray(mallSpecValues1Array));
                mallSpecKeyWordVO.setCreatedStr(DateUtil.defaultFormatDate(mallSpecs.get(0).getCreatedTime()));
                mallSpecKeyWordVO.setUpdatedStr(DateUtil.defaultFormatDate(mallSpecs.get(0).getUpdatedTime()));
                mallSpecKeyWordVOvalues.add(mallSpecKeyWordVO);
            }
        }else if (mallSpecs.size() == 0 && mallSpecValues.size() > 0){
            mallSpecValues.stream().forEach(mv->specNoValueMap.put(mv.getSpecNo(),mv.getSpecValue()));
            List<String> specNovalues = Lists.newArrayList();
            for (Map.Entry<String, String> e : specNoValueMap.entrySet()) {
                MallSpecVO mallSpecVO = selMallSpecBySpecNo(shopNo, e.getKey());
                log.info("shopNo:{};specNo:{};规格模版表查询selMallSpecBySpecNo接口结果:{}",shopNo,e.getKey(),mallSpecVO);
                specNovalues.add(e.getValue());
                MallSpecKeyWordVO mallSpecKeyWordVO = new MallSpecKeyWordVO();
                if (mallSpecVO != null) {
                    mallSpecKeyWordVO.setSpecNo(mallSpecVO.getSpecNo());
                    mallSpecKeyWordVO.setSpecName(mallSpecVO.getSpecName());
                    mallSpecKeyWordVO.setSpecValues(mallSpecVO.getSpecValues());
                    mallSpecKeyWordVO.setCreatedStr(mallSpecVO.getCreatedTime());
                    mallSpecKeyWordVO.setUpdatedStr(mallSpecVO.getUpdatedTime());
                }
                mallSpecKeyWordVOvalues.add(mallSpecKeyWordVO);
            }
        }else if (mallSpecs.size() > 0 && mallSpecValues.size() > 0){
            List<String> specNos = mallSpecDao.selMallSpecByShopNo(shopNo);
            mallSpecs.stream().forEach(m->spspecNovalues.add(m.getSpecNo()));
            mallSpecValues.stream().forEach(m->spvspecNovalues.add(m.getSpecNo()));
            List<String> spcollect = spspecNovalues.stream().filter(s -> specNos.contains(s)).collect(Collectors.toList());
            List<String> spvcollect = spvspecNovalues.stream().filter(s -> specNos.contains(s)).collect(Collectors.toList());
            Set<String> set = new HashSet<>();
            spcollect.stream().forEach(c->set.add(c));
            spvcollect.stream().forEach(c->set.add(c));
            for (String s: set) {
                MallSpecVO mallSpecVO = selMallSpecBySpecNo(shopNo, s);
                log.info("shopNo:{};specNo:{};规格模版表查询selMallSpecBySpecNo接口结果:{}",shopNo,s,mallSpecVO);
                MallSpecKeyWordVO mallSpecKeyWordVO = new MallSpecKeyWordVO();
                if (mallSpecVO != null) {
                    mallSpecKeyWordVO.setSpecNo(mallSpecVO.getSpecNo());
                    mallSpecKeyWordVO.setSpecName(mallSpecVO.getSpecName());
                    mallSpecKeyWordVO.setSpecValues(mallSpecVO.getSpecValues());
                    mallSpecKeyWordVO.setCreatedStr(mallSpecVO.getCreatedTime());
                    mallSpecKeyWordVO.setUpdatedStr(mallSpecVO.getUpdatedTime());
                }
                mallSpecKeyWordVOvalues.add(mallSpecKeyWordVO);
            }
        }
        log.info("keyWord:{};规格模版查询数据库整合结果:{}",keyWord,mallSpecKeyWordVOvalues);
        PageInfo<MallSpecKeyWordVO> pageInfo = new PageInfo<>(mallSpecKeyWordVOvalues);
        return pageInfo;
    }


    @Override
    public MallSpecVO selMallSpecBySpecNo(String shopNo,String specNo) {
        shopNo = "61763436278748160000";
        List<MallSpecValue>  mallSpecValues = mallSpecValueDao.selMallSpecValueBySpecNo(specNo);
        log.info("specNo:{};规格模版值表查询数据库结果:{}",specNo,mallSpecValues);
        MallSpec mallSpec = mallSpecDao.selMallSpecBySpecNo(shopNo,specNo);
        log.info("specNo:{};规格模版表查询数据库结果:{}",specNo,mallSpec);
        MallSpecVO mallSpecVO = new MallSpecVO();
        List<String> values = Lists.newArrayList();
        String[] valuesarray = new String[mallSpecValues.size()];
        if (mallSpec != null && mallSpecValues != null){
            mallSpecValues.stream().forEach(m->values.add(m.getSpecValue()));
            mallSpecVO.setSpecNo(mallSpec.getSpecNo());
            mallSpecVO.setSpecName(mallSpec.getSpecName());
            mallSpecVO.setSpecValues(values.toArray(valuesarray));
            mallSpecVO.setCreatedTime(DateUtil.defaultFormatDate(mallSpec.getCreatedTime()));
            mallSpecVO.setUpdatedTime(DateUtil.defaultFormatDate(mallSpec.getUpdatedTime()));
        }

        log.info("specNo:{};规格模版查询数据库结果:{}",specNo,mallSpecVO);
        return mallSpecVO;
    }

    /**
     * @Description: 查询规格模版
     */
    @Override
    public MallSpecSingleVO selMallSpecSingleBySpecNo(String shopNo, String specNo) {
        shopNo = "61763436278748160000";
        List<MallSpecValue>  mallSpecValues = mallSpecValueDao.selMallSpecValueBySpecNo(specNo);
        log.info("specNo:{};规格模版值表查询数据库结果:{}",specNo,mallSpecValues);
        MallSpec mallSpec = mallSpecDao.selMallSpecBySpecNo(shopNo,specNo);
        log.info("specNo:{};规格模版表查询数据库结果:{}",specNo,mallSpec);
        MallSpecSingleVO mallSpecSingleVO = new MallSpecSingleVO();
        List<String> values = Lists.newArrayList();
        String[] valuesarray = new String[mallSpecValues.size()];
        if (mallSpec != null && mallSpecValues != null){
            mallSpecValues.stream().forEach(m->values.add(m.getSpecValue()));
            mallSpecSingleVO.setSpecNo(mallSpec.getSpecNo());
            mallSpecSingleVO.setSpecName(mallSpec.getSpecName());
            mallSpecSingleVO.setSpecValues(values.toArray(valuesarray));

        }

        log.info("specNo:{};规格模版查询数据库结果:{}",specNo,mallSpecSingleVO);
        return mallSpecSingleVO;
    }


}
