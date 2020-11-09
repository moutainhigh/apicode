package com.ycandyz.master.dao.risk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.risk.ContentReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContentreviewDao {
    void insert(ContentReview contentReview);
    ContentReviewDTO selectByContentId(@Param("contentId") Long contentId,@Param("type") Integer type);
    List<ContentReviewDTO> list(@Param("page") long page, @Param("size") long size, @Param("con") ContentReviewQuery contentReviewQuery);
    void updateAuditResult(@Param("auditor") String auditor , @Param("contentId")Long contentId, @Param("type") int type);
    Integer getReviewCount(@Param("con") ContentReviewQuery contentReviewQuery);
}
