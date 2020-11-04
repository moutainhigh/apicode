package com.ycandyz.master.service.risk;

import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.dto.risk.ContentReviewDTO;

import java.util.List;

public interface ContentReviewService {
    List<ContentReviewDTO> listMallItem(RequestParams<ContentReviewQuery> requestParams);

    int updateOneMallItem(Long contentId);
    void updateOneFootprint(Long contentId);
    void updateOneOrganizeNews(Long contentId);
}
