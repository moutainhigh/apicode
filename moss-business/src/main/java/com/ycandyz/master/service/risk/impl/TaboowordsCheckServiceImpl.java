package com.ycandyz.master.service.risk.impl;

import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.domain.query.risk.TabooCheckParams;
import com.ycandyz.master.domain.response.risk.TabooCheckRep;
import com.ycandyz.master.entities.risk.ContentReview;
import com.ycandyz.master.enums.ReviewEnum;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.service.risk.TaboowordsCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;


@Service
@Slf4j
public class  TaboowordsCheckServiceImpl implements TaboowordsCheckService {

    @Resource
    private TabooCheckService tabooCheckService;

    @Resource
    private ContentreviewDao contentreviewDao;

    @Override
    public ReturnResponse check(TabooCheckParams tabooCheckParams) {
        if (tabooCheckParams == null){
            return ReturnResponse.failed("无检测参数");
        }
        ContentReview contentReview = new ContentReview();
        contentReview.setContentId(tabooCheckParams.getContentId());
        contentReview.setType(tabooCheckParams.getType());
        List<String> lists = new ArrayList<>();
        StringBuffer txt = new StringBuffer();
        if (tabooCheckParams.getType() == ReviewEnum.MALLITEM.getCode()){
                lists.add(tabooCheckParams.getItemName());
                lists.add(tabooCheckParams.getItemText());
                lists.add(tabooCheckParams.getShareDescr());
            }else if (tabooCheckParams.getType() == ReviewEnum.FOOTPRINT.getCode()){
                lists.add(tabooCheckParams.getContent());
            }else if (tabooCheckParams.getType() == ReviewEnum.ORGANIZENEWS.getCode()){
                lists.add(tabooCheckParams.getTitle());
                lists.add(tabooCheckParams.getAbstracts());
            }
        lists.stream().forEach(s->txt.append(s));
        List<String> result = tabooCheckService.check(txt.toString());
        TabooCheckRep tabooCheckRep = new TabooCheckRep();
        if (result != null && result.size() >0 ){
//            contentReview.setAuditResult(0);
//            contentreviewDao.insert(contentReview);
            tabooCheckRep.setPhraseNames(result);
            tabooCheckRep.setMessage("检测到提交信息涉嫌违规提交后系统会自动屏蔽，其他人不可见");
            return ReturnResponse.success(tabooCheckRep);
        }
        contentReview.setAuditResult(1);
        contentreviewDao.insert(contentReview);
        tabooCheckRep.setPhraseNames(null);
        tabooCheckRep.setMessage("未检测到违规词");
        return ReturnResponse.success(tabooCheckRep);
    }


}
