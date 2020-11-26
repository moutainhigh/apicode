package com.ycandyz.master.service.risk.impl;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.dao.risk.ContentreviewLogDao;
import com.ycandyz.master.domain.query.risk.ContentReviewLogVO;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.query.risk.TabooCheckNotInsertParams;
import com.ycandyz.master.domain.query.risk.TabooCheckParams;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.domain.response.risk.TabooCheckRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.risk.ContentReview;
import com.ycandyz.master.enums.ResultEnum;
import com.ycandyz.master.enums.ReviewEnum;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.service.risk.TaboowordsCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;


@Service
@Slf4j
public class  TaboowordsCheckServiceImpl  implements TaboowordsCheckService  {

    @Resource
    private TabooCheckService tabooCheckService;

    @Resource
    private ContentreviewDao contentreviewDao;

    @Autowired
    protected ContentreviewLogDao contentreviewLogDao;

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
        ReturnResponse returnResponse = new ReturnResponse();
        if (result != null && result.size() >0 ){
            tabooCheckRep.setPhraseNames(result);
            tabooCheckRep.setMessage("检测到提交信息涉嫌违规提交后系统会自动屏蔽，其他人不可见");
            returnResponse.setCode(Long.valueOf(ResultEnum.TABOO_FAILED.getValue()));
            returnResponse.setData(tabooCheckRep);
            return ReturnResponse.success(returnResponse);
        }
        contentReview.setAuditResult(1);
        contentreviewDao.insert(contentReview);
        //记录日志
        ContentReviewLogVO contentReviewLogVO = new ContentReviewLogVO();
        contentReviewLogVO.setContentId(tabooCheckParams.getContentId());
        contentReviewLogVO.setType(tabooCheckParams.getType());
        contentReviewLogVO.setContentAuditResult(0);
        contentReviewLogVO.setAuditResult(1);
        contentreviewLogDao.insert(contentReviewLogVO);
        tabooCheckRep.setPhraseNames(null);
        tabooCheckRep.setMessage("未检测到违规词");
        returnResponse.setCode(Long.valueOf(ResultEnum.TABOO_PASS.getValue()));
        returnResponse.setData(tabooCheckRep);
        return ReturnResponse.success(returnResponse);
    }


    @Override
    public ReturnResponse checkNotInsert(TabooCheckNotInsertParams tabooCheckParams) {
        if (tabooCheckParams == null){
            return ReturnResponse.failed("无检测参数");
        }
        List<String> lists = new ArrayList<>();
        StringBuffer txt = new StringBuffer();
        lists.add(tabooCheckParams.getItemName());
        lists.add(tabooCheckParams.getItemText());
        lists.add(tabooCheckParams.getShareDescr());
        lists.add(tabooCheckParams.getContent());
        lists.add(tabooCheckParams.getTitle());
        lists.add(tabooCheckParams.getAbstracts());
        lists.stream().forEach(s->txt.append(s));
        List<String> result = tabooCheckService.check(txt.toString());
        TabooCheckRep tabooCheckRep = new TabooCheckRep();
        ReturnResponse returnResponse = new ReturnResponse();
        if (result != null && result.size() >0 ){
            tabooCheckRep.setPhraseNames(result);
            tabooCheckRep.setMessage("检测到提交信息涉嫌违规提交后系统会自动屏蔽，其他人不可见");
            returnResponse.setCode(Long.valueOf(ResultEnum.TABOO_FAILED.getValue()));
            returnResponse.setData(tabooCheckRep);
            return ReturnResponse.success(returnResponse);
        }
        //记录日志
        tabooCheckRep.setPhraseNames(null);
        tabooCheckRep.setMessage("未检测到违规词");
        returnResponse.setCode(Long.valueOf(ResultEnum.TABOO_PASS.getValue()));
        returnResponse.setData(tabooCheckRep);
        return ReturnResponse.success(returnResponse);
    }

}
