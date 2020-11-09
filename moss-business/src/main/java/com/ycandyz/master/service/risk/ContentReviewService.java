package com.ycandyz.master.service.risk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.domain.query.risk.ReviewBatchExamineParam;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;


public interface ContentReviewService {
    ReturnResponse examine(ReviewParam reviewParam);

    ReturnResponse<Page<ContentReviewRep>> list(RequestParams<ContentReviewQuery> requestParams);

    ReturnResponse batchExamine(ReviewBatchExamineParam reviewBatchExamineParam);
}
