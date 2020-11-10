package com.ycandyz.master.service.userExportRecord;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordQuery;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordReq;
import com.ycandyz.master.domain.response.userExportRecord.UserExportRecordResp;
import com.ycandyz.master.entities.userExportRecord.UserExportRecord;

/**
 * <p>
 * @Description  业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */
public interface IUserExportRecordService extends IService<UserExportRecord> {

    Page<UserExportRecordResp> selectPages(RequestParams<UserExportRecordQuery> requestParams);

   void insert(RequestParams<UserExportRecordReq> requestParams);
}
