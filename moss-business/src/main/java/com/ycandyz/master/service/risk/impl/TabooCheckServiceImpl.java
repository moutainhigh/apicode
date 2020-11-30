package com.ycandyz.master.service.risk.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycandyz.master.constant.CommonConstant;
import com.ycandyz.master.dao.taboo.BaseTabooWordsDao;
import com.ycandyz.master.domain.query.risk.TabooWordsForReview;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.kafka.KafkaConstant;
import com.ycandyz.master.kafka.Message;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.utils.MyCollectionUtils;
import com.ycandyz.master.utils.RedisUtil;
import com.ycandyz.master.utils.TabooCheck;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class TabooCheckServiceImpl implements TabooCheckService {

//    public static Map<String,List<String>> Taboomaps = new HashMap<>();
//    public static List<String> allTaboosLists = new ArrayList<>();

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private BaseTabooWordsDao baseTabooWordsDao;

    @Override
    public List<String> check(String txt) {
        List<String> lists = new ArrayList<>();
        List<String> allTaboosLists = null;
        if (redisUtil.get(CommonConstant.TABOO_LIST_GROUP+"TabooList")!=null){
            allTaboosLists = (List<String>) redisUtil.get(CommonConstant.TABOO_LIST_GROUP+"TabooList");
        }
        Map<Object, Object> Taboomaps = null;
        if (redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps")!=null){
            Taboomaps = redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps");
        }
        if (allTaboosLists!=null && allTaboosLists.size()>0) {
            List<String> list = TabooCheck.check(allTaboosLists, txt);
            for (Map.Entry<Object, Object> it : Taboomaps.entrySet()) {
                for (String s : list) {
                    if (it.getValue().toString().contains(s)) {
                        lists.add(it.getKey().toString());
                    }
                }
            }
        }
       return lists;
    }

    @Override
    public void getAllToRedis() {
        Map<String,String> map = new HashMap<>();
        List<TabooWordsForReview> tabooWordsForReviews = baseTabooWordsDao.selectWords();
        tabooWordsForReviews.forEach(s->map.put(s.getPhraseName(),s.getTabooWords()));
        Map<Object, Object> Taboomaps = null;
        if (redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps")!=null){
            Taboomaps = redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps");
        }else {
            Taboomaps = new HashMap<>();
        }
        for (Map.Entry<String,String> entry : map.entrySet()){
            Taboomaps.put(entry.getKey(),MyCollectionUtils.parseIds(entry.getValue()));
        }
        redisUtil.hmset(CommonConstant.TABOO_MAP_GROUP+"Taboomaps",Taboomaps);
        List<String> allTaboosLists = null;
        if (redisUtil.get(CommonConstant.TABOO_LIST_GROUP+"TabooList")!=null){
            allTaboosLists = (List<String>) redisUtil.get(CommonConstant.TABOO_LIST_GROUP+"TabooList");
        }else {
            allTaboosLists = new ArrayList<>();
        }
        for (Map.Entry<Object,Object> entry : Taboomaps.entrySet()){
            allTaboosLists.addAll((Collection<? extends String>) entry.getValue());
        }
        redisUtil.set(CommonConstant.TABOO_LIST_GROUP+"TabooList",allTaboosLists);
    }

//    @PostConstruct
//    public void init(){
//        Map<String,String> map = new HashMap<>();
//        List<TabooWordsForReview> tabooWordsForReviews = baseTabooWordsDao.selectWords();
//        tabooWordsForReviews.forEach(s->map.put(s.getPhraseName(),s.getTabooWords()));
//        Map<Object, Object> Taboomaps = null;
//        if (redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP)!=null){
//            Taboomaps = redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP);
//            map.forEach((k,v)->{Taboomaps.put(k,MyCollectionUtils.parseIds(v));});
//            log.info("缓存中敏感词现存Taboomaps------->{}",Taboomaps);
//            Taboomaps.forEach((k,v)->{
//                if (v != null){
//                    allTaboosLists.addAll(v);
//                }
//            });
//        }
//    }

    //新增敏感词消费kafka消息
    @KafkaListener(topics = KafkaConstant.TABOOTOPIC, groupId = "group_kafka_taboo-test")
    public void addTaboo(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Message msg = JSONObject.parseObject(String.valueOf(message.get()), Message.class);
            log.info("kafka接收到消息:Topic:" + topic + ",Message:" + JSON.toJSON(msg));
            BaseTabooWords baseTabooWords = JSON.toJavaObject((JSON) msg.getMsg(),BaseTabooWords.class);
            Integer flag = baseTabooWords.getFlag();
            if (flag == 1){
                baseTabooWordsDao.addBaseTabooWords(baseTabooWords);
                log.info("添加敏感词组->baseTabooWords:{}到数据库",JSON.toJSONString(baseTabooWords));
            }else if (flag == 2){
                int i = baseTabooWordsDao.updateBaseTabooWords(baseTabooWords);
                if (i > 0){
                    log.info("修改敏感词组成功->baseTabooWords:{}",JSON.toJSONString(baseTabooWords));
                }else {
                    log.info("修改敏感词组失败->baseTabooWords:{}",JSON.toJSONString(baseTabooWords));
                }
            }else if (flag == 3){
                Long id = baseTabooWords.getId();
                int i =baseTabooWordsDao.delById(id);
                if (i > 0){
                    log.info("删除敏感词组成功->id:{}",id);
                }else {
                    log.info("删除敏感词组失败->id:{}",id);
                }
            }
            //更新新缓存
            Map<Object, Object> Taboomaps = null;
            if (redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps")!=null){
                Taboomaps = redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps");
            }
            if (flag == 1 || flag == 2){
                Map<String,List<String>> newTabooMaps = new HashMap<>();
                newTabooMaps.put(baseTabooWords.getPhraseName(),MyCollectionUtils.parseIds(baseTabooWords.getTabooWords()));
                if (newTabooMaps != null){

                    for (Map.Entry<String,List<String>> entry : newTabooMaps.entrySet()){
                        String key = entry.getKey();
                        if (Taboomaps!=null){
                            if (Taboomaps.containsKey(key)){
                                Taboomaps.remove(key);
                            }
                            Taboomaps.put(key,entry.getValue());
                        }else {
                            Taboomaps = new HashMap<>();
                            Taboomaps.put(key,entry.getValue());
                        }
                    }
                    redisUtil.hmset(CommonConstant.TABOO_MAP_GROUP+"Taboomaps",Taboomaps);
                }
            }else if (flag == 3){
                String phraseName = baseTabooWords.getPhraseName();

                if (Taboomaps!=null){
                    if(Taboomaps.containsKey(phraseName)){
                        Taboomaps.remove(phraseName);
                    }
                    redisUtil.hmset(CommonConstant.TABOO_MAP_GROUP+"Taboomaps",Taboomaps);
                }
            }
            ack.acknowledge();
        }
        log.info("缓存中敏感词现存Taboomaps------->{}",redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps"));
        List<String> allTaboosLists = null;
        if (redisUtil.get(CommonConstant.TABOO_LIST_GROUP+"TabooList")!=null){
            allTaboosLists = (List<String>) redisUtil.get(CommonConstant.TABOO_LIST_GROUP+"TabooList");
        }
        if (redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps")!=null){
            Map<Object, Object> tab = redisUtil.hmget(CommonConstant.TABOO_MAP_GROUP+"Taboomaps");
            for (Map.Entry<Object, Object> entry : tab.entrySet()){
                if (allTaboosLists != null) {
                    List<String> value = (List<String>) entry.getValue();
                    allTaboosLists.addAll(value);
                }
            }
        }
        redisUtil.set(CommonConstant.TABOO_LIST_GROUP+"TabooList",allTaboosLists);
    }

}
