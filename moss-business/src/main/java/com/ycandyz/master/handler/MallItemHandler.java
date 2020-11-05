package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.mall.goodsManage.MallItemDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.enums.ReviewEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MallItemHandler extends AbstractHandler {


    @Autowired
    private MallItemDao mallItemDao;

    @Override
    public ReturnResponse handle(ReviewParam reviewParam) {
        int i = mallItemDao.updateOneMallItem(String.valueOf(reviewParam.getContentId()),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(reviewParam.getContentId(), reviewParam.getType());
            ReturnResponse.success("更新成功");
        }
        return ReturnResponse.failed("更新失败");
    }

    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.MALLITEM.getCode(),this);
    }
}
