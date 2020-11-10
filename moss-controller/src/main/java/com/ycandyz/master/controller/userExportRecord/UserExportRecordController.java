package com.ycandyz.master.controller.userExportRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordQuery;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordReq;
import com.ycandyz.master.domain.response.userExportRecord.UserExportRecordResp;
import com.ycandyz.master.entities.userExportRecord.UserExportRecord;
import com.ycandyz.master.service.userExportRecord.IUserExportRecordService;
import com.ycandyz.master.service.userExportRecord.impl.UserExportRecordServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * @Description  接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("userExportRecord")
@Api(tags="导出记录-用户导出记录")
public class UserExportRecordController extends BaseController<UserExportRecordServiceImpl, UserExportRecord, UserExportRecordQuery> {
	

    @Autowired
    private IUserExportRecordService userExportRecordService;

	@ApiOperation(value = "导出记录查询分页")
    @PostMapping(value = "/selectPage")
    public ReturnResponse<Page<UserExportRecordResp>> selectPage(@RequestBody RequestParams<UserExportRecordQuery> requestParams) {
        Page<UserExportRecordResp> userExportRecordRespPage = userExportRecordService.selectPages(requestParams);
        return ReturnResponse.success(userExportRecordRespPage);
    }

    @ApiOperation(value = "接入导出记录")
    @PostMapping(value = "/insert")
    public ReturnResponse<Page<UserExportRecordResp>> insert(@RequestBody RequestParams<UserExportRecordReq> requestParams) {
        userExportRecordService.insert(requestParams);
        return null;
    }
    
}
