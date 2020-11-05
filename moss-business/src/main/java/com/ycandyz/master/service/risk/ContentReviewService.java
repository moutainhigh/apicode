package com.ycandyz.master.service.risk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;

import java.util.List;

public interface ContentReviewService {
    Page<List<ContentReviewDTO>> listMallItem(RequestParams<ContentReviewQuery> requestParams);

    ReturnResponse updateStatus(ReviewParam reviewParam);

    Page<List<ContentReviewDTO>> listFootprint(RequestParams<ContentReviewQuery> requestParams);

    ReturnResponse<Page<ContentReviewRep>> list(RequestParams<ContentReviewQuery> requestParams);
}
