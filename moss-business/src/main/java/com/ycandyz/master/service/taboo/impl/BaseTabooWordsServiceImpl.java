package com.ycandyz.master.service.taboo.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.risk.TabooWordsForReview;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.domain.response.risk.BaseTabooWordsRep;
import com.ycandyz.master.dto.user.UserForExport;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.kafka.KafkaConstant;
import com.ycandyz.master.kafka.KafkaProducer;
import com.ycandyz.master.model.taboo.BaseTabooWordsVO;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.taboo.BaseTabooWordsService;
import com.ycandyz.master.dao.taboo.BaseTabooWordsDao;
import com.ycandyz.master.utils.MyCollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * @Description 基础商品表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Slf4j
@Service
public class BaseTabooWordsServiceImpl extends BaseService<BaseTabooWordsDao, BaseTabooWords, BaseTabooWordsQuery> implements BaseTabooWordsService {

    @Resource
    private BaseTabooWordsDao baseTabooWordsDao;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserDao userDao;

    /**
     * @Description: 新增敏感字
     * @Author li Zhuo
     * @Date 2020/10/26
     * @Version: V1.0
    */
    @Override
    public void addBaseTabooWords(BaseTabooWordsVO baseTabooWordsVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        BaseTabooWords baseTabooWords = new BaseTabooWords();
        String tabooWords = MyCollectionUtils.PraseArraytoString(baseTabooWordsVO.getTabooWords());
        BeanUtils.copyProperties(baseTabooWordsVO,baseTabooWords);
        baseTabooWords.setTabooWords(tabooWords);
        baseTabooWords.setOperator(currentUser.getId());
        baseTabooWords.setTreatmentMethod(0);
        baseTabooWords.setFlag(1);
        kafkaProducer.send(baseTabooWords, KafkaConstant.TABOOTOPIC);
        log.info("新增敏感词组发送kafka消息:topic:{};消息:{}", KafkaConstant.TABOOTOPIC, JSON.toJSON(baseTabooWords));
    }


    /**
     * @Description: 根据id查询敏感字
     * @Author li Zhuo
     * @Date 2020/10/26
     * @Version: V1.0
    */
    @Override
    public BaseTabooWordsRep selById(Long id) {
        BaseTabooWords baseTabooWords = baseTabooWordsDao.selById(id);
        BaseTabooWordsRep baseTabooWordsRep = new BaseTabooWordsRep();
        if (baseTabooWords.getTabooWords() != null){
            baseTabooWordsRep.setTabooWords(MyCollectionUtils.parseIds(baseTabooWords.getTabooWords()));
        }
        BeanUtils.copyProperties(baseTabooWords,baseTabooWordsRep);
        return baseTabooWordsRep;
    }

    @Override
    public void delById(Long id) {
        BaseTabooWords baseTabooWords = baseTabooWordsDao.selById(id);
        baseTabooWords.setFlag(3);
        kafkaProducer.send(baseTabooWords, KafkaConstant.TABOOTOPIC);
        log.info("删除敏感词组发送kafka消息:topic:{};消息:{}", KafkaConstant.TABOOTOPIC, JSON.toJSON(baseTabooWords));
    }

    @Override
    public Page<BaseTabooWordsRep> selectList(RequestParams<BaseTabooWordsQuery> requestParams) {
        BaseTabooWordsQuery baseTabooWordsQuery = requestParams.getT();
        Page pageQuery = new Page(requestParams.getPage(),requestParams.getPage_size());
        Page<BaseTabooWordsRep> page1 =  new Page<>();
        List<BaseTabooWordsRep> recordReps = new ArrayList<>();
        try {
            Page<BaseTabooWords> page = baseTabooWordsDao.selectList(pageQuery, baseTabooWordsQuery);
            List<BaseTabooWords> records = page.getRecords();

            for (BaseTabooWords b: records) {
                BaseTabooWordsRep baseTabooWordsRep = new BaseTabooWordsRep();
                if (b.getTabooWords() != null){
                    baseTabooWordsRep.setTabooWords(MyCollectionUtils.parseIds(b.getTabooWords()));
                }
                BeanUtils.copyProperties(b,baseTabooWordsRep);
                UserForExport userForExport = userDao.selectForExport(Long.valueOf(b.getOperator()));
                String userPhone = null;
                if (userForExport != null){
                    userPhone = userForExport.getName()+ "\u0020" +userForExport.getPhone();
                }
                baseTabooWordsRep.setOperator(userPhone);
                recordReps.add(baseTabooWordsRep);
            }
            page1.setPages(requestParams.getPage());
            page1.setCurrent(requestParams.getPage());
            page1.setRecords(recordReps);
            page1.setSize(requestParams.getPage_size());
            page1.setTotal(page.getTotal());
        }catch (Exception e){
            log.error("error:{}",e.getMessage());
            page1 = new Page<>(0,requestParams.getPage_size(),0);
        }
        return page1;
    }

    @Override
    public ReturnResponse updateBaseTabooWords(BaseTabooWordsVO baseTabooWordsVO) {
        if (baseTabooWordsVO == null){
            log.error("当前更新的入参数据为空");
            return ReturnResponse.failed("当前更新的入参数据为空");
        }else if (baseTabooWordsDao.selById(baseTabooWordsVO.getId()) == null){
            log.error("当前待更新的数据不存在");
            return ReturnResponse.failed("当前待更新的数据不存在");
        }
        UserVO currentUser = UserRequest.getCurrentUser();
        BaseTabooWords baseTabooWords = new BaseTabooWords();
        String tabooWords = MyCollectionUtils.PraseArraytoString(baseTabooWordsVO.getTabooWords());
        BeanUtils.copyProperties(baseTabooWordsVO,baseTabooWords);
        baseTabooWords.setTabooWords(tabooWords);
        baseTabooWords.setOperator(currentUser.getId());
        baseTabooWords.setTreatmentMethod(0);
        baseTabooWords.setFlag(2);
        kafkaProducer.send(baseTabooWords, KafkaConstant.TABOOTOPIC);
        log.info("修改敏感词组发送kafka消息:topic:{};消息:{}", KafkaConstant.TABOOTOPIC, JSON.toJSON(baseTabooWords));
        return ReturnResponse.success("更新成功");
    }


    //敏感词校验
    @Override
    public ReturnResponse selTabooWords(String phraseName, String[] tabooWords) {
        List<BaseTabooWords> baseTabooWords = baseTabooWordsDao.selPhraseName(phraseName);
        if (baseTabooWords != null && baseTabooWords.size() > 0){
            return ReturnResponse.failed("添加失败,敏感词组名称已存在!");
        }
        List<TabooWordsForReview> tabooWordsForReviews = baseTabooWordsDao.selectWords();
        List<String> tabooList = new ArrayList<>();
        if (tabooWordsForReviews != null && tabooWordsForReviews.size() > 0){
            tabooWordsForReviews.stream().forEach(s->tabooList.addAll(MyCollectionUtils.parseIds(s.getTabooWords())));
        }
        for (String s: tabooWords) {
            if (tabooList.contains(s)){
                return ReturnResponse.failed("添加失败,敏感词已存在!");
            }
        }
        return ReturnResponse.success("敏感词组名称和敏感词不存在!");
    }

}
