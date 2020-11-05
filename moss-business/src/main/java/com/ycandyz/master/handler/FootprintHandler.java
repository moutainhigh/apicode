package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.footprint.FootprintDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.enums.ReviewEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class FootprintHandler extends AbstractHandler {

    @Autowired
    private FootprintDao footprintDao;

    @Override
    public ReturnResponse handle(ReviewParam reviewParam) {

        int i = footprintDao.updateOneFootprint(reviewParam.getContentId(),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(reviewParam.getContentId(), reviewParam.getType());
            return ReturnResponse.success("更新成功");
        }
        return ReturnResponse.failed("更新失败");
    }


    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.FOOTPRINT.getCode(),this);
    }
}
