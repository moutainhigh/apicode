package com.ycandyz.master.controller.userExportRecord;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordQuery;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordReq;
import com.ycandyz.master.domain.response.userExportRecord.UserExportRecordResp;
import com.ycandyz.master.entities.userExportRecord.UserExportRecord;
import com.ycandyz.master.service.userExportRecord.IUserExportRecordService;
import com.ycandyz.master.service.userExportRecord.impl.UserExportRecordServiceImpl;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping("/sys/audit/export-record")
@Api(tags="导出记录-用户导出记录")
public class UserExportRecordController extends BaseController<UserExportRecordServiceImpl, UserExportRecord, UserExportRecordQuery> {
	

    @Autowired
    private IUserExportRecordService userExportRecordService;

	@ApiOperation(value = "导出记录查询分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="terminal",value="操作终端:1-U客企业后台 2-有传运营后台",dataType="Integer"),
            @ApiImplicitParam(name="organizeName",value="企业名称",dataType="String"),
            @ApiImplicitParam(name="operatorId",value="操作人id",dataType="Long"),
            @ApiImplicitParam(name="createdAtStart",value="创建时间开始",dataType="Long"),
            @ApiImplicitParam(name="createdAtEnd",value="创建时间截止",dataType="Long"),
            @ApiImplicitParam(name="page_size",value="每页显示条数",dataType="Long"),
            @ApiImplicitParam(name="page",value="当前页",dataType="Long")
    })
    @GetMapping
    public ReturnResponse<Page<UserExportRecordResp>> selectPage(@RequestParam(value = "terminal",required = false) Integer terminal,
                                                                 @RequestParam(value = "organizeName",required = false) String organizeName,
                                                                 @RequestParam(value = "operatorId",required = false) Long operatorId,
                                                                 @RequestParam(value = "createdAtStart",required = false) Long createdAtStart,
                                                                 @RequestParam(value = "createdAtEnd",required = false) Long createdAtEnd,
                                                                 @RequestParam(value = "page_size",defaultValue = "10",required = false) Long page_size,
                                                                 @RequestParam(value = "page",defaultValue = "1",required = false) Long page) {
        RequestParams<UserExportRecordQuery> requestParams = new RequestParams<>();
        UserExportRecordQuery userExportRecordQuery = new UserExportRecordQuery();
        userExportRecordQuery.setTerminal(terminal);
        userExportRecordQuery.setOrganizeName(organizeName);
        userExportRecordQuery.setOperatorId(operatorId);
        userExportRecordQuery.setCreatedAtStart(createdAtStart);
        userExportRecordQuery.setCreatedAtEnd(createdAtEnd);
        requestParams.setPage_size(page_size);
        requestParams.setPage(page);
        requestParams.setT(userExportRecordQuery);
	    log.info("导出记录-用户导出记录查询分页请求入参:{}", JSON.toJSONString(requestParams));
        Page<UserExportRecordResp> userExportRecordRespPage = userExportRecordService.selectPages(requestParams);
        log.info("导出记录-用户导出记录查询分页请求响应:{}", JSON.toJSONString(ReturnResponse.success(userExportRecordRespPage)));
        return ReturnResponse.success(userExportRecordRespPage);
    }

    @ApiOperation(value = "接入导出记录-内部使用")
    @PostMapping
    public ReturnResponse insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody UserExportRecordReq userExportRecordReq) {
        log.info("导出记录-用户导出记录接入导出记录请求入参:{}", JSON.toJSONString(userExportRecordReq));
        ReturnResponse returnResponse = userExportRecordService.insert(userExportRecordReq);
        log.info("导出记录-用户导出记录接入导出记录请求响应:{}", JSON.toJSONString(returnResponse));
        return returnResponse;
    }
    
}
