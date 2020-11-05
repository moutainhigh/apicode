package com.ycandyz.master.service.risk.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.footprint.FootprintDao;
import com.ycandyz.master.dao.mall.goodsManage.MallItemDao;
import com.ycandyz.master.dao.organize.OrganizeNewsDao;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.risk.ContentReview;
import com.ycandyz.master.handler.HandlerContext;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.risk.ContentReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ContentReviewServiceImpl implements ContentReviewService {

    @Autowired
    private ContentreviewDao contentreviewDao;

    @Override
    public Page<List<ContentReviewDTO>> listMallItem(RequestParams<ContentReviewQuery> requestParams) {
            ContentReviewQuery contentReviewQuery = requestParams.getT();
            Page pageQuery = new Page(requestParams.getPage(),requestParams.getPage_size());
            Page<List<ContentReviewDTO>> page = new Page<>();
            try {
                page = contentreviewDao.listMallItem(pageQuery,contentReviewQuery);

            }catch (Exception e){
                log.error("error:{}",e.getMessage());
                page = new Page<>(0,10,0);
            }
        return page;
    }

    //通过/屏蔽
    @Override
    public ReturnResponse updateStatus(ReviewParam reviewParam) {
        AbstractHandler handler = HandlerContext.getHandler(reviewParam.getType());
        ReturnResponse returnResponse = handler.handle(reviewParam);
        return returnResponse;
    }

}
