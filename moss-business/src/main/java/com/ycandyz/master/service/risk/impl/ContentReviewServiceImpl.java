package com.ycandyz.master.service.risk.impl;

import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.dao.footprint.FootprintDao;
import com.ycandyz.master.dao.mall.goodsManage.MallItemDao;
import com.ycandyz.master.dao.organize.OrganizeNewsDao;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.risk.ContentReview;
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

    @Autowired
    private MallItemDao mallItemDao;

    @Autowired
    private FootprintDao footprintDao;

    @Autowired
    private OrganizeNewsDao organizeNewsDao;

    @Override
    public List<ContentReviewDTO> listMallItem(RequestParams<ContentReviewQuery> requestParams) {
            ContentReviewQuery contentReviewQuery = requestParams.getT();
            List<ContentReviewDTO> contentReviewDTOS = contentreviewDao.listMallItem(contentReviewQuery);


//        List<ContentReviewDTO> all = new ArrayList<>();
//        List<ContentReviewDTO> contentMallItem = contentreviewDao.queryMallItem(contentReviewQuery);
//        log.info("contentMallItem:{}",contentMallItem);
//        List<ContentReviewDTO> contentFootprint = contentreviewDao.queryFootprint(contentReviewQuery);
//        log.info("contentFootprint:{}",contentFootprint);
//        List<ContentReviewDTO> contentOrganizeNews = contentreviewDao.queryOrganizeNews(contentReviewQuery);
//        log.info("contentOrganizeNews:{}",contentOrganizeNews);
//        all.addAll(contentMallItem);
//        all.addAll(contentFootprint);
//        all.addAll(contentOrganizeNews);
        return contentReviewDTOS;
    }

    //屏蔽
    @Override
    public int updateOneMallItem(Long contentId) {

        String itemNo = String.valueOf(contentId);
        int i = mallItemDao.updateOneMallItem(itemNo);
        int type = 0;
        if (i > 0 ){
            updateOrInsert(contentId, type);
        }
        return i;
    }

    private void updateOrInsert(Long contentId,int type) {
        UserVO user = UserRequest.getCurrentUser();
        String userPhone = user.getName() + user.getPhone();
        ContentReviewDTO contentReviewDTO = contentreviewDao.selectByContentId(contentId, type);
        if (contentReviewDTO != null){
            contentreviewDao.updateAuditResult(userPhone, contentId, type);
        }
        ContentReview contentReview = new ContentReview();
        contentReview.setType(type);
        contentReview.setAuditResult(2);
        contentReview.setAuditor(userPhone);
        contentReview.setContentId(contentId);
        contentreviewDao.insert(contentReview);
    }

    //屏蔽
    @Override
    public void updateOneFootprint(Long contentId) {
        int i = footprintDao.updateOneFootprint(contentId);
        int type = 1;
        if (i > 0 ){
            updateOrInsert(contentId, type);
        }
    }

    //屏蔽
    @Override
    public void updateOneOrganizeNews(Long contentId) {
        int i = organizeNewsDao.updateOneOrganizeNews(contentId);
        int type = 2;
        if (i > 0 ){
            updateOrInsert(contentId, type);
        }
    }
}
