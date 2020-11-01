package com.ycandyz.master.service.mall.goodsManage.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.dao.mall.goodsManage.MallSpecDao;
import com.ycandyz.master.dao.mall.goodsManage.MallSpecValueDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.enums.SortValueEnum;
import com.ycandyz.master.enums.StatusEnum;
import com.ycandyz.master.entities.mall.goodsManage.MallSpec;
import com.ycandyz.master.entities.mall.goodsManage.MallSpecValue;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.mall.goodsManage.MallSpecService;
import com.ycandyz.master.utils.DateUtil;
import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public List<MallSpecSingleVO> insert(MallSpecVO mallSpecVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        String specNo = String.valueOf(IDGeneratorUtils.getLongId());
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
        List<MallSpecSingleVO> mallSpecSingleVOS = selMallSpecByShopNo();
        return mallSpecSingleVOS;
    }


    /**
     * @Description: 删除规格模版
    */
    @Override
    public int delete(String specNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        int r = mallSpecDao.delMallSpecBySpecNo(shopNo,specNo);
        return r;
    }

    /**
     * @Description: 根据关键字查询规格模版
     */
    @Override
    public Page<MallSpecKeyWordVO> selectByKeyWord(PageResult pageResult, String keyWord) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        Page pageQuery = new Page(pageResult.getPage(),pageResult.getPage_size(),pageResult.getTotal());
        Page<MallSpec> mallSpecsPage = mallSpecDao.selMallSpecByKeyWord(pageQuery,shopNo,keyWord);
        log.info("shopNo:{};keyWord:{};规格模版表查询数据库结果:{}",shopNo,keyWord,mallSpecsPage);
        List<MallSpecValue> mallSpecValues = mallSpecValueDao.selMallSpecValueByKeyWord(keyWord);
        log.info("keyWord:{};规格模版value表查询数据库结果:{}",keyWord,mallSpecValues);
        HashMap<String, String> specNoNameMap = Maps.newHashMap();
        HashMap<String, String> specNoValueMap = Maps.newHashMap();
        List<String> spspecNovalues = Lists.newArrayList();
        List<String> spvspecNovalues = Lists.newArrayList();
        List<String> values = Lists.newArrayList();
        List<MallSpecKeyWordVO> mallSpecKeyWordVOvalues = Lists.newArrayList();
        Page<MallSpecKeyWordVO> page1 = new Page<>();
        if (mallSpecsPage !=null && mallSpecsPage.getRecords().size() >0 && mallSpecValues != null && mallSpecValues.size() == 0){
            mallSpecsPage.getRecords().stream().forEach(m->specNoNameMap.put(m.getSpecName(),m.getSpecNo()));
            for (Map.Entry<String, String> e : specNoNameMap.entrySet()) {
                List<MallSpecValue> mallSpecValues1 = mallSpecValueDao.selMallSpecValueBySpecNo(e.getValue());
                log.info("specNo:{};规格模版value表查询数据库结果:{}",e.getValue(),mallSpecValues1);
                mallSpecValues1.stream().forEach(mv->values.add(mv.getSpecValue()));
                String[] mallSpecValues1Array = new String[mallSpecValues1.size()];
                MallSpecKeyWordVO mallSpecKeyWordVO = new MallSpecKeyWordVO();
                mallSpecKeyWordVO.setSpecNo(e.getValue());
                mallSpecKeyWordVO.setSpecName(e.getKey());
                mallSpecKeyWordVO.setSpecValues(values.toArray(mallSpecValues1Array));
                mallSpecKeyWordVO.setCreatedStr(DateUtil.defaultFormatDate(mallSpecsPage.getRecords().get(0).getCreatedTime()));
                mallSpecKeyWordVO.setUpdatedStr(DateUtil.defaultFormatDate(mallSpecsPage.getRecords().get(0).getUpdatedTime()));
                mallSpecKeyWordVOvalues.add(mallSpecKeyWordVO);
            }
        }else if (mallSpecsPage !=null && mallSpecsPage.getRecords().size() == 0 && mallSpecValues != null && mallSpecValues.size() > 0){
            mallSpecValues.stream().forEach(mv->specNoValueMap.put(mv.getSpecNo(),mv.getSpecValue()));
            List<String> specNovalues = Lists.newArrayList();
            List<String> list = mallSpecDao.selMallSpecNoByShopNo(shopNo);
            for (Map.Entry<String, String> e : specNoValueMap.entrySet()) {
                if(list.contains(e.getKey())){
                    MallSpecVO mallSpecVO = selMallSpecBySpecNo(e.getKey());
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
            }
        }else if (mallSpecsPage !=null && mallSpecsPage.getRecords().size() > 0 && mallSpecValues != null && mallSpecValues.size() > 0){
            List<String> specNos = mallSpecDao.selMallSpecNoByShopNo(shopNo);
            List<MallSpec> records = mallSpecsPage.getRecords();
            for (MallSpec m: records) {
                spspecNovalues.add(m.getSpecNo());
            }
            records.stream().forEach(m->spspecNovalues.add(m.getSpecNo()));
            mallSpecValues.stream().forEach(m->spvspecNovalues.add(m.getSpecNo()));
            List<String> spcollect = spspecNovalues.stream().filter(s -> specNos.contains(s)).collect(Collectors.toList());
            List<String> spvcollect = spvspecNovalues.stream().filter(s -> specNos.contains(s)).collect(Collectors.toList());
            Set<String> set = new HashSet<>();
            spcollect.stream().forEach(c->set.add(c));
            spvcollect.stream().forEach(c->set.add(c));
            for (String s: set) {
                MallSpecVO mallSpecVO = selMallSpecBySpecNo(s);
                log.info("shopNo:{};specNo:{};规格模版表查询selMallSpecBySpecNo接口结果:{}",shopNo,s,mallSpecVO);
                MallSpecKeyWordVO mallSpecKeyWordVO = new MallSpecKeyWordVO();
                if (mallSpecVO != null) {
                    BeanUtils.copyProperties(mallSpecVO,mallSpecKeyWordVO);
                }
                mallSpecKeyWordVOvalues.add(mallSpecKeyWordVO);
            }
        }
        log.info("keyWord:{};规格模版查询数据库整合结果:{}",keyWord,mallSpecKeyWordVOvalues);
        BeanUtils.copyProperties(mallSpecsPage,page1);
        page1.setRecords(mallSpecKeyWordVOvalues);
        return page1;
    }
//    public PageInfo<MallSpecKeyWordVO> selMallSpecByKeyWord2(Integer page, Integer pageSize, String keyWord,UserVO userVO) {
//        String shopNo = userVO.getShopNo();
//        PageHelper.startPage(page,pageSize);
//        List<MallSpec> mallSpecs = mallSpecDao.selMallSpecByKeyWord(shopNo,keyWord);
//        log.info("shopNo:{};keyWord:{};规格模版表查询数据库结果:{}",shopNo,keyWord,mallSpecs);
//        List<MallSpecValue> mallSpecValues = mallSpecValueDao.selMallSpecValueByKeyWord(keyWord);
//        log.info("keyWord:{};规格模版value表查询数据库结果:{}",keyWord,mallSpecValues);
//
//        return null;
//    }

    /**
     * @Description: 查询规格模版
    */
    public MallSpecVO selMallSpecBySpecNo(String specNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        List<MallSpecValue>  mallSpecValues = mallSpecValueDao.selMallSpecValueBySpecNo(specNo);
        log.info("specNo:{};规格模版值表查询数据库结果:{}",specNo,mallSpecValues);
        MallSpec mallSpec = mallSpecDao.selMallSpecBySpecNo(userVO.getShopNo(),specNo);
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
     * @Description: 查询单个规格模版
     */
    @Override
    public MallSpecSingleVO select(String specNo) {
        UserVO userVO = UserRequest.getCurrentUser();
        List<MallSpecValue>  mallSpecValues = mallSpecValueDao.selMallSpecValueBySpecNo(specNo);
        log.info("specNo:{};规格模版值表查询数据库结果:{}",specNo,mallSpecValues);
        MallSpec mallSpec = mallSpecDao.selMallSpecBySpecNo(userVO.getShopNo(),specNo);
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
        log.info("specNo:{};单个规格模版查询数据库结果:{}",specNo,mallSpecSingleVO);
        return mallSpecSingleVO;
    }

    /**
     * @Description: 编辑规格模版
     * @Author li Zhuo
     * @Date 2020/10/19
     * @Version: V1.0
    */
    @Override
    public List<MallSpecSingleVO> update(MallSpecVO mallSpecVO) {
        UserVO userVO = UserRequest.getCurrentUser();
        String shopNo = userVO.getShopNo();
        log.info("编辑规格模版入参:{};{}",mallSpecVO,shopNo);
        String specNo = mallSpecVO.getSpecNo();
        String specName = mallSpecVO.getSpecName();
        String[] specValues = mallSpecVO.getSpecValues();
        int i = mallSpecDao.updateMallSpec(specNo,specName,shopNo);
        if (specValues.length > 0){
            int i1 = mallSpecValueDao.delMallSpecValueBySpecNo(specNo);
            for (String s: specValues) {
                mallSpecValueDao.addMallSpecValue(new MallSpecValue(specNo,s,SortValueEnum.DEFAULT.getCode(),StatusEnum.DEFAULT.getCode()));
            }
        }
        List<MallSpecSingleVO> mallSpecSingleVOS = selMallSpecByShopNo();
        return mallSpecSingleVOS;
    }

    /**
     * @Description: 根据商户号查询所有规格模版
    */
    public List<MallSpecSingleVO> selMallSpecByShopNo() {
        UserVO userVO = UserRequest.getCurrentUser();
        log.info("根据商户号查询所有规格模版入参shopNo:{}",userVO.getShopNo());
        List<String> specNos = mallSpecDao.selMallSpecNoByShopNo(userVO.getShopNo());
        ArrayList<MallSpecSingleVO> mallSpecSingleVOS = Lists.newArrayList();
        for (String no:specNos) {
            MallSpecSingleVO mallSpecSingleVO = select(no);
            mallSpecSingleVOS.add(mallSpecSingleVO);
        }
        return mallSpecSingleVOS;
    }

}
