package com.ycandyz.master.service.risk.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycandyz.master.dao.taboo.BaseTabooWordsDao;
import com.ycandyz.master.domain.query.risk.TabooWordsForReview;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.kafka.KafkaConstant;
import com.ycandyz.master.kafka.Message;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.utils.MyCollectionUtils;
import com.ycandyz.master.utils.TabooCheck;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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

    public static Map<String,List<String>> Taboomaps = new HashMap<>();

    @Resource
    private BaseTabooWordsDao baseTabooWordsDao;

    @Override
    public List<String> check(String txt) {
        List<String> lists = new ArrayList<>();
        List<String> allTaboosLists = new ArrayList<>();
        Taboomaps.forEach((k,v)->{
            allTaboosLists.addAll(v);
        });
        List<String> list = TabooCheck.check(allTaboosLists, txt);
        for(Map.Entry<String, List<String>> it : Taboomaps.entrySet()){
            for (String s:list) {
                if (it.getValue().contains(s)){
                    lists.add(it.getKey());
                }
            }
        }
       return lists;
    }

    @PostConstruct
    public void init(){
        Map<String,String> map = new HashMap<>();
        List<TabooWordsForReview> tabooWordsForReviews = baseTabooWordsDao.selectWords();
        tabooWordsForReviews.forEach(s->map.put(s.getPhraseName(),s.getTabooWords()));
        map.forEach((k,v)->{Taboomaps.put(k,MyCollectionUtils.parseIds(v));});
        log.info("缓存中敏感词现存Taboomaps------->{}",Taboomaps);
    }

    //新增敏感词消费kafka消息
    @KafkaListener(topics = KafkaConstant.TABOOTOPIC, groupId = "group_kafka_taboo")
    public void addTaboo(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Message msg = JSONObject.parseObject(String.valueOf(message.get()), Message.class);
            log.info("kafka接收到消息:Topic:" + topic + ",Message:" + JSON.toJSON(msg));
            BaseTabooWords baseTabooWords = JSON.toJavaObject((JSON) msg.getMsg(),BaseTabooWords.class);
            Integer flag = baseTabooWords.getFlag();
            if (flag == 1){
                baseTabooWordsDao.addBaseTabooWords(baseTabooWords);
                log.info("添加敏感词组baseTabooWords:{}到数据库",baseTabooWords);
            }else if (flag == 2){
                int i = baseTabooWordsDao.updateBaseTabooWords(baseTabooWords);
                if (i > 0){
                    log.info("修改敏感词组baseTabooWords:{}到数据库成功",baseTabooWords);
                }else {
                    log.info("修改敏感词组baseTabooWords:{}到数据库失败",baseTabooWords);
                }
            }else if (flag == 3){
                Long id = baseTabooWords.getId();
                int i =baseTabooWordsDao.delById(id);
                if (i > 0){
                    log.info("删除敏感词组id:{}成功",id);
                }else {
                    log.info("删除敏感词组id:{}失败",id);
                }
            }
            //更新新缓存
            if (flag == 1 || flag == 2){
                Map<String,List<String>> newTabooMaps = new HashMap<>();
                newTabooMaps.put(baseTabooWords.getPhraseName(),MyCollectionUtils.parseIds(baseTabooWords.getTabooWords()));
                if (newTabooMaps != null){
                    newTabooMaps.forEach((k,v)->{
                        if (Taboomaps.keySet().contains(k)){
                            List<String> list = Taboomaps.get(k);
                            list.clear();
                            Taboomaps.put(k,v);
                        }else {
                            Taboomaps.put(k,v);
                        }
                    });
                }
            }else if (flag == 3){
                String phraseName = baseTabooWords.getPhraseName();
                if (Taboomaps.keySet().contains(phraseName)){
                    List<String> list = Taboomaps.get(phraseName);
                    list.clear();
                    Taboomaps.remove(phraseName);
                }
            }
            ack.acknowledge();
        }
    log.info("缓存中敏感词现存Taboomaps------->{}",Taboomaps);
    }

}
