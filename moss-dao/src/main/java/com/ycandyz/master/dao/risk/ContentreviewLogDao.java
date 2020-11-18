package com.ycandyz.master.dao.risk;

import com.ycandyz.master.domain.query.risk.ContentReviewLogVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentreviewLogDao {
    void insert(ContentReviewLogVO contentReviewLogVO);

    void insertAll(ContentReviewLogVO contentReviewLogVO);
}
