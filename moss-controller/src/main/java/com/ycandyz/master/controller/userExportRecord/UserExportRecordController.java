package com.ycandyz.master.controller.userExportRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordQuery;
import com.ycandyz.master.domain.response.userExportRecord.UserExportRecordResp;
import com.ycandyz.master.entities.userExportRecord.UserExportRecord;
import com.ycandyz.master.service.userExportRecord.IUserExportRecordService;
import com.ycandyz.master.service.userExportRecord.impl.UserExportRecordServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("user-export-record")
@Api(tags="导出记录-用户导出记录")
public class UserExportRecordController extends BaseController<UserExportRecordServiceImpl, UserExportRecord, UserExportRecordQuery> {
	

    @Autowired
    private IUserExportRecordService userExportRecordService;

	@ApiOperation(value = "查询分页")
    @GetMapping(value = "select/page")
    @SuppressWarnings("unchecked")
    public ReturnResponse<Page<UserExportRecordResp>> selectPage(RequestParams<UserExportRecordQuery> requestParams) {
        Page<UserExportRecordResp> userExportRecordRespPage = userExportRecordService.selectPages(requestParams);
        return ReturnResponse.success(userExportRecordRespPage);
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "select/list")
    public ReturnResponse<Object> selectList(UserExportRecordQuery query) {
        return ReturnResponse.success(service.list(query));
    }
    

}
