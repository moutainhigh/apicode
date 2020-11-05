package com.ycandyz.master.dao.footprint;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.domain.query.footprint.FootprintQuery;
import com.ycandyz.master.entities.footprint.Footprint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商友圈表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-27
 */
@Mapper
public interface FootprintDao extends BaseMapper<Footprint> {
    List<FootprintQuery> selectFootprint(@Param("pageOffset") int pageOffset, @Param("pageSize") int pageSize);
    int updateOneFootprint(@Param("contentId") Long contentId ,@Param("oper") Integer oper);
}
