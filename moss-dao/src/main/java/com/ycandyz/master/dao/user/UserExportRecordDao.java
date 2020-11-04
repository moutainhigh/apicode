package com.ycandyz.master.dao.user;

import com.ycandyz.master.entities.user.UserExportRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 */
@Mapper
public interface UserExportRecordDao{

    void insert(UserExportRecord userExportRecord);

}
