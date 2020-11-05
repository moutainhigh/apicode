package com.ycandyz.master.service.risk.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.mall.MallOrderDTO;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.handler.HandlerContext;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.service.risk.ContentReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public ReturnResponse<Page<ContentReviewRep>> list(RequestParams<ContentReviewQuery> requestParams) {
        ContentReviewQuery contentReviewQuery = requestParams.getT();
        Page<ContentReviewRep> page1 = new Page<>();
        List<ContentReviewRep> newlist = new ArrayList<>();
        try{
            //获取总条数
            Integer count = contentreviewDao.getReviewCount(contentReviewQuery);
            page1.setTotal(count);
            if (count!=null && count>0) {
                //分页
                List<ContentReviewDTO> list = contentreviewDao.list((requestParams.getPage() - 1) * requestParams.getPage_size(),
                        requestParams.getPage_size(), contentReviewQuery);
                //List<ContentReviewDTO> list = contentreviewDao.list(contentReviewQuery);
                list.stream().forEach(s -> {
                    ContentReviewRep contentReviewRep = new ContentReviewRep();
                    AbstractHandler handler = HandlerContext.getHandler(s.getType());
                    handler.handleContentreview(s,contentReviewRep);
                    newlist.add(contentReviewRep);
                });
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        page1.setPages(requestParams.getPage());
        page1.setCurrent(requestParams.getPage());
        page1.setRecords(newlist);
        page1.setSize(requestParams.getPage_size());
        return ReturnResponse.success(page1);
    }


    //通过/屏蔽
    @Override
    public ReturnResponse updateStatus(ReviewParam reviewParam) {
        AbstractHandler handler = HandlerContext.getHandler(reviewParam.getType());
        ReturnResponse returnResponse = handler.handle(reviewParam);
        return returnResponse;
    }

    @Override
    public Page<List<ContentReviewDTO>> listFootprint(RequestParams<ContentReviewQuery> requestParams) {
        ContentReviewQuery contentReviewQuery = requestParams.getT();
        Page pageQuery = new Page(requestParams.getPage(),requestParams.getPage_size());
        Page<List<ContentReviewDTO>> listPage = contentreviewDao.listFootprint(pageQuery, contentReviewQuery);
        return null;
    }

}
