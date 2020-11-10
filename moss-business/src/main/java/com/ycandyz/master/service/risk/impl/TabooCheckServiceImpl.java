package com.ycandyz.master.service.risk.impl;

import com.ycandyz.master.dao.taboo.BaseTabooWordsDao;
import com.ycandyz.master.domain.query.risk.TabooWordsForReview;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.utils.MyCollectionUtils;
import com.ycandyz.master.utils.TabooCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TabooCheckServiceImpl implements TabooCheckService {

   // private static List<String> allTaboosLists = new ArrayList<>();

    private static Map<String,List<String>> maps = new HashMap<>();

    @Resource
    private BaseTabooWordsDao baseTabooWordsDao;

    @Override
    public List<String> check(String txt) {
        List<String> lists = new ArrayList<>();
        List<String> allTaboosLists = new ArrayList<>();
        maps.forEach((k,v)->{
            allTaboosLists.addAll(v);
        });
        List<String> list = TabooCheck.check(allTaboosLists, txt);
        for(Map.Entry<String, List<String>> it : maps.entrySet()){
            for (String s:list) {
                if (it.getValue().contains(s)){
                    lists.add(s);
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
        //map.forEach((k,v)->{allTaboosLists.addAll(MyCollectionUtils.parseIds(v));});
        map.forEach((k,v)->{maps.put(k,MyCollectionUtils.parseIds(v));});
    }
}
