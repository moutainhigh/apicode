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
    Page<List<ContentReviewDTO>> listMallItem(Page pageQuery, @Param("con") ContentReviewQuery c);
    void insert(ContentReview contentReview);
    ContentReviewDTO selectByContentId(@Param("contentId") Long contentId,@Param("type") Integer type);
    Page<List<ContentReviewDTO>> listFootprint(Page pageQuery, @Param("con") ContentReviewQuery contentReviewQuery);
    Page<List<ContentReviewDTO>> listOrganizeNews(Page pageQuery, @Param("con") ContentReviewQuery contentReviewQuery);
    void updateAuditResult(@Param("auditor") String auditor , @Param("contentId")Long contentId, @Param("type") int type);
}
