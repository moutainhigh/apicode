package com.ycandyz.master.service.userExportRecord;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordQuery;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordReq;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordVo;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
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

    Page<UserExportRecordVo> selectPages(RequestParams<UserExportRecordQuery> requestParams);

    ReturnResponse insert(UserExportRecordReq requestParams);

    void insertExportRecord(MallOrderExportResp mallOrderExportResp, UserVO userVO);
}
