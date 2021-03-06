package com.ycandyz.master.abstracts;

import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.dao.risk.ContentreviewLogDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.risk.ContentReviewLogVO;
import com.ycandyz.master.domain.query.risk.ReviewExamineParam;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.risk.ContentReview;
import com.ycandyz.master.request.UserRequest;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class AbstractHandler implements InitializingBean {

    @Autowired
    protected ContentreviewDao contentreviewDao;

    @Autowired
    private ContentreviewLogDao contentreviewLogDao;


    abstract public ReturnResponse examine( ReviewParam reviewParam);

    abstract public void handleContentreview(ContentReviewDTO contentReviewDTO, ContentReviewRep contentReviewRep);

    protected void updateOrInsert(Long id,String contentId, int type) {
        UserVO user = UserRequest.getCurrentUser();
        ContentReviewDTO contentReviewDTO = contentreviewDao.selectByIdE(id);
        if (contentReviewDTO != null){
            contentreviewDao.updateAudit(String.valueOf(user.getId()),id);
        }else {
            ContentReview contentReview = new ContentReview();
            contentReview.setType(type);
            contentReview.setAuditResult(2);
            contentReview.setAuditor(user.getId());
            contentReview.setContentId(contentId);
            contentreviewDao.insert(contentReview);
        }
    }

    public abstract ReturnResponse handleExamine(Map<Integer,List<Long>> maps);

    protected void insertAllcontentReviewLog(Long id,String contentId, int type, int contentResult, int auditResult){
        UserVO user = UserRequest.getCurrentUser();
        ContentReviewLogVO contentReviewLogVO = new ContentReviewLogVO();
        contentReviewLogVO.setContentReviewId(id);
        contentReviewLogVO.setContentId(contentId);
        contentReviewLogVO.setType(type);
        contentReviewLogVO.setAuditor(user.getId());
        contentReviewLogVO.setContentAuditResult(contentResult);
        contentReviewLogVO.setAuditResult(auditResult);
        contentreviewLogDao.insertAll(contentReviewLogVO);
    }

}
