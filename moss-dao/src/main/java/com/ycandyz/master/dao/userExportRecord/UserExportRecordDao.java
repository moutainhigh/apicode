package com.ycandyz.master.dao.userExportRecord;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordQuery;
import com.ycandyz.master.domain.response.userExportRecord.UserExportRecordResp;
import com.ycandyz.master.entities.userExportRecord.UserExportRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 */
@Mapper
public interface UserExportRecordDao extends BaseMapper<UserExportRecord> {

    Page<UserExportRecord> selectPages(Page pageQuery,@Param("u") UserExportRecordQuery userExportRecordQuery);

    void insertUserExportRecord(UserExportRecord userExportRecord);
}
