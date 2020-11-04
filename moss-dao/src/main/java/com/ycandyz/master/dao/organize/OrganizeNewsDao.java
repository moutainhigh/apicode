package com.ycandyz.master.dao.organize;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.domain.query.organize.OrganizeNewsQuery;
import com.ycandyz.master.entities.organize.OrganizeNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 企业动态 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-27
 */
@Mapper
public interface OrganizeNewsDao extends BaseMapper<OrganizeNews> {
    List<OrganizeNewsQuery> selectFootprintQuery(@Param("pageOffset") int pageOffset, @Param("pageSize") int pageSize);
    int updateOneOrganizeNews(Long contentId);
}
