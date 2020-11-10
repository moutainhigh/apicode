package com.ycandyz.master.service.userExportRecord.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.dao.userExportRecord.UserExportRecordDao;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordQuery;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordReq;
import com.ycandyz.master.domain.response.userExportRecord.UserExportRecordResp;
import com.ycandyz.master.dto.organize.OrganizeDTO;
import com.ycandyz.master.dto.user.UserForExport;
import com.ycandyz.master.entities.userExportRecord.UserExportRecord;
import com.ycandyz.master.service.userExportRecord.IUserExportRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description  业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */
@Slf4j
@Service
public class UserExportRecordServiceImpl extends BaseService<UserExportRecordDao, UserExportRecord, UserExportRecordQuery> implements IUserExportRecordService {

    @Autowired
    private UserExportRecordDao userExportRecordDao;

    @Autowired
    private OrganizeDao organizeDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Page<UserExportRecordResp> selectPages(RequestParams<UserExportRecordQuery> requestParams) {
        UserExportRecordQuery userExportRecordQuery = requestParams.getT();
        Page pageQuery = new Page(requestParams.getPage(),requestParams.getPage_size());
        Page<UserExportRecordResp> page = null;
        try {
            page = userExportRecordDao.selectPages(pageQuery, userExportRecordQuery);
        }catch (Exception e){
            log.error("error:{}",e.getMessage());
            page = new Page<>(0,10,0);
        }
        return page;
    }

    @Override
    public ReturnResponse insert(UserExportRecordReq userExportRecordReq) {
        if (userExportRecordReq == null){
            log.error("导出记录导入数据为null");
            return ReturnResponse.failed("导出记录导入数据为null");
        }
        try {
            UserExportRecord userExportRecord = new UserExportRecord();
            OrganizeDTO organizeDTO = organizeDao.queryName(userExportRecordReq.getOrganizeId());
            if (organizeDTO != null){
                userExportRecord.setOrganizeName(organizeDTO.getShortName());
            }
            UserForExport userForExport = userDao.selectForExport(userExportRecordReq.getOperatorId());
            if (userForExport != null){
                userExportRecord.setOperatorName(userForExport.getName());
                userExportRecord.setOperatorPhone(userForExport.getPhone());
            }
            BeanUtils.copyProperties(userExportRecordReq,userExportRecord);
            log.info("导出记录导入数据{}",userExportRecord);
            userExportRecordDao.insertUserExportRecord(userExportRecord);
            return ReturnResponse.success("导出记录导入数据成功");
        }catch (Exception e){
            log.error("导出记录导入有错{}",e.getMessage());
            String str=String.format("导出记录导入数据失败%s", e.getMessage());
            return ReturnResponse.failed(str);
        }
    }
}
