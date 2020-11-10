package com.ycandyz.master.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.entities.user.UserExportRecord;

public interface IUserExportRecordService extends IService<UserExportRecord> {

    void insertExportRecord(MallOrderExportResp mallOrderExportResp, UserVO userVO);
}
