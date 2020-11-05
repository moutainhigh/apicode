package com.ycandyz.master.abstracts;

import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.risk.ContentReview;
import com.ycandyz.master.request.UserRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractHandler implements InitializingBean {

    @Autowired
    protected ContentreviewDao contentreviewDao;

    abstract public ReturnResponse handle( ReviewParam reviewParam);

    protected void updateOrInsert(Long contentId, int type) {
        UserVO user = UserRequest.getCurrentUser();
        ContentReviewDTO contentReviewDTO = contentreviewDao.selectByContentId(contentId, type);
        if (contentReviewDTO != null){
            contentreviewDao.updateAuditResult(String.valueOf(user.getId()), contentId, type);
        }else {
            ContentReview contentReview = new ContentReview();
            contentReview.setType(type);
            contentReview.setAuditResult(2);
            contentReview.setAuditor(user.getId());
            contentReview.setContentId(contentId);
            contentreviewDao.insert(contentReview);
        }
    }
}
