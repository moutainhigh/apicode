package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.organize.OrganizeNewsDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.enums.ReviewEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizeNewsHandler extends AbstractHandler {

    @Autowired
    private OrganizeNewsDao organizeNewsDao;

    @Override
    public ReturnResponse handle(ReviewParam reviewParam) {
        int i = organizeNewsDao.updateOneOrganizeNews(reviewParam.getContentId(),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(reviewParam.getContentId(), reviewParam.getType());
            return ReturnResponse.success("更新成功");
        }
        return ReturnResponse.failed("更新失败");
    }

    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.ORGANIZENEWS.getCode(), this);
    }
}
