package com.ycandyz.master.service.risk.impl;

import com.ycandyz.master.dao.taboo.BaseTabooWordsDao;
import com.ycandyz.master.domain.query.risk.TabooWordsForReview;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.utils.TabooCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TabooCheckServiceImpl implements TabooCheckService {

    private static List<String> allTaboosList = new ArrayList<>();

    @Resource
    private BaseTabooWordsDao baseTabooWordsDao;



    @Override
    public List<String> check(String txt) {
        List list = TabooCheck.check(allTaboosList, txt);
        return list;
    }

    @PostConstruct
    public void init(){
        List<TabooWordsForReview> tabooWordsForReviews = baseTabooWordsDao.selectWords();
        tabooWordsForReviews.forEach(s->allTaboosList.add(s.getTabooWords()));
    }
}
