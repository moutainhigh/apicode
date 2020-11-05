package com.ycandyz.master.service.risk.impl;

import com.ycandyz.master.dao.taboo.BaseTabooWordsDao;
import com.ycandyz.master.domain.query.risk.TabooWordsForReview;
import com.ycandyz.master.enums.StatusEnum;
import com.ycandyz.master.enums.TabooWordsEnum;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.utils.EnumUtil;
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

    private static List<String> allTaboosLists = new ArrayList<>();

    private static Map<Integer,List<String>> maps = new HashMap<>();

    @Resource
    private BaseTabooWordsDao baseTabooWordsDao;

    @Override
    public List<String> check(String txt) {
        List<String> lists = new ArrayList<>();
        List<String> list = TabooCheck.check(allTaboosLists, txt);
        for(Map.Entry<Integer, List<String>> it : maps.entrySet()){
            for (String s:list) {
                if (it.getValue().contains(s)){
                    lists.add(EnumUtil.getByCode(TabooWordsEnum.class,it.getKey()).getDesc());
                }
            }
        }
        //maps.forEach((k,v)->{list.stream().forEach(s -> v.contains(s));});
       return lists;
    }

    @PostConstruct
    public void init(){
        Map<Integer,String> map = new HashMap<>();
        List<TabooWordsForReview> tabooWordsForReviews = baseTabooWordsDao.selectWords();
        tabooWordsForReviews.forEach(s->map.put(s.getPhraseName(),s.getTabooWords()));
        map.forEach((k,v)->{allTaboosLists.addAll(MyCollectionUtils.parseIds(v));});
        map.forEach((k,v)->{maps.put(k,MyCollectionUtils.parseIds(v));});
    }
}
