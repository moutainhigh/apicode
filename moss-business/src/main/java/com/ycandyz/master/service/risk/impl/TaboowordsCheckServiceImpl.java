package com.ycandyz.master.service.risk.impl;

import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.domain.query.risk.TabooCheckParams;
import com.ycandyz.master.entities.risk.ContentReview;
import com.ycandyz.master.service.risk.TabooCheckService;
import com.ycandyz.master.service.risk.TaboowordsCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class TaboowordsCheckServiceImpl implements TaboowordsCheckService {

    @Resource
    private TabooCheckService tabooCheckService;

    @Resource
    private ContentreviewDao contentreviewDao;

    @Override
    public ReturnResponse check(RequestParams<TabooCheckParams> requestParams) {
        TabooCheckParams tabooCheckParams = requestParams.getT();
        StringBuffer txt = new StringBuffer();
        txt.append(tabooCheckParams.getAbstracts())
                .append(tabooCheckParams.getContent())
                .append(tabooCheckParams.getItemName())
                .append(tabooCheckParams.getItemText());
        List<String> result = tabooCheckService.check(tabooCheckParams.getAbstracts());
        ContentReview contentReview = null;
        BeanUtils.copyProperties(tabooCheckParams,contentReview);
        if (result != null && result.size() >0 ){
            contentReview.setAuditResult(0);
            contentreviewDao.insert(contentReview);
            return ReturnResponse.failed("检测到提交信息涉嫌违规提交后系统会自动屏蔽，其他人不可见");
        }
        contentReview.setAuditResult(1);
        contentreviewDao.insert(contentReview);
        return ReturnResponse.success("未检测到违规词");
    }


}
