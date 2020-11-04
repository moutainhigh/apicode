package com.ycandyz.master.service.user.impl;

import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.dao.user.UserExportRecordDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.user.UserExportRecordQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.dto.organize.OrganizeDTO;
import com.ycandyz.master.entities.user.UserExportRecord;
import com.ycandyz.master.service.user.IUserExportRecordService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class UserExportRecordServiceImpl extends BaseService<UserExportRecordDao, UserExportRecord, UserExportRecordQuery> implements IUserExportRecordService {

    @Autowired
    private OrganizeDao organizeDao;

    @Autowired
    private UserExportRecordDao userExportRecordDao;


    public void insertExportRecord(MallOrderExportResp mallOrderExportResp, UserVO userVO) {
        log.info("导入用户记录表入参:{};{}",mallOrderExportResp,userVO);
        String organizeName = null;
        if (userVO != null){
            OrganizeDTO organizeDTO = organizeDao.queryName(userVO.getOrganizeId());
            if (organizeDTO != null){
                organizeName = organizeDTO.getFullNname();
            }
        }
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        OperatingSystem operatingSystem = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getOperatingSystem();
        UserExportRecord userExportRecord = new UserExportRecord();
        userExportRecord.setTerminal(1);
        userExportRecord.setOrganizeId(userVO.getOrganizeId());
        userExportRecord.setOrganizeName(organizeName);
        userExportRecord.setOpertorBrowser(browser.getName());
        userExportRecord.setOperatorId(userVO.getId());
        userExportRecord.setOperatorName(userVO.getName());
        userExportRecord.setOperatorPhone(userVO.getPhone());
        userExportRecord.setOperatorIp(getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));
        userExportRecord.setOpertorSystem(operatingSystem.getName());
        userExportRecord.setCreated_at(System.currentTimeMillis());
        userExportRecord.setExportFileName(mallOrderExportResp.getFileName());
        userExportRecord.setExportFileUrl(mallOrderExportResp.getFielUrl());
        userExportRecordDao.insert(userExportRecord);
        log.info("导入用户记录表完成");
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
