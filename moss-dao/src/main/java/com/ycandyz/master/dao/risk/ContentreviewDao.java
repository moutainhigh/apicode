package com.ycandyz.master.dao.risk;

import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.risk.ContentReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContentreviewDao {
    List<ContentReviewDTO> listMallItem(@Param("con") ContentReviewQuery c);
    void insert(ContentReview contentReview);
    ContentReviewDTO selectByContentId(Long contentId,int type);
    List<ContentReviewDTO> queryFootprint(@Param("con") ContentReviewQuery contentReviewQuery);
    List<ContentReviewDTO> queryOrganizeNews(@Param("con") ContentReviewQuery contentReviewQuery);

    void update(Long id);

    void updateAuditResult(@Param("auditor") String auditor , @Param("contentId")Long contentId, @Param("type") int type);
}
