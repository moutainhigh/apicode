package com.ycandyz.master.service.userExportRecord.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.dao.userExportRecord.UserExportRecordDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordQuery;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordReq;
import com.ycandyz.master.domain.query.userExportRecord.UserExportRecordVo;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.domain.response.userExportRecord.UserExportRecordResp;
import com.ycandyz.master.dto.organize.OrganizeDTO;
import com.ycandyz.master.dto.user.UserForExport;
import com.ycandyz.master.entities.userExportRecord.UserExportRecord;
import com.ycandyz.master.enums.TerminalEnum;
import com.ycandyz.master.service.userExportRecord.IUserExportRecordService;
import com.ycandyz.master.utils.EnumUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    public Page<UserExportRecordVo> selectPages(RequestParams<UserExportRecordQuery> requestParams) {
        if (requestParams == null && requestParams.getT() == null){
            log.error("敏感词检测参数为空");
            return null;
        }
        UserExportRecordQuery userExportRecordQuery = requestParams.getT();
        Page pageQuery = new Page(requestParams.getPage(),requestParams.getPage_size());
        Page<UserExportRecord> page = null;
        Page<UserExportRecordVo> page1 = new Page<>();
        try {
            page = userExportRecordDao.selectPages(pageQuery, userExportRecordQuery);
            List<UserExportRecord> records = page.getRecords();
            List<UserExportRecordVo> newrecords = new ArrayList<>();
            for (UserExportRecord k: records) {
                UserExportRecordVo userExportRecordVo = new UserExportRecordVo();
                if (k != null){
                    BeanUtils.copyProperties(k,userExportRecordVo);
                }
                String desc = EnumUtil.getByCode(TerminalEnum.class, k.getTerminal()).getDesc();
                userExportRecordVo.setTerminal(desc);
                newrecords.add(userExportRecordVo);
            }
            BeanUtils.copyProperties(page,page1);
            page1.setRecords(newrecords);
        }catch (Exception e){
            log.error("error:{}",e.getMessage());
            page1 = new Page<>(0,10,0);
        }
        return page1;
    }

    @Override
    public ReturnResponse insert(UserExportRecordReq userExportRecordReq) {
        if (userExportRecordReq == null){
            log.error("导出记录导入数据为null");
            return ReturnResponse.failed("导出记录导入数据为null");
        }
        try {
            UserExportRecord userExportRecord = new UserExportRecord();
            BeanUtils.copyProperties(userExportRecordReq,userExportRecord);
            if (userExportRecordReq.getOrganizeId() != null){
                OrganizeDTO organizeDTO = organizeDao.queryName(userExportRecordReq.getOrganizeId());
                if (organizeDTO != null){
                    userExportRecord.setOrganizeName(organizeDTO.getShortName());
                }
            }else {
                userExportRecord.setOrganizeId(0L);
                userExportRecord.setOrganizeName("");
            }
            UserForExport userForExport = userDao.selectForExport(userExportRecordReq.getOperatorId());
            if (userForExport != null){
                userExportRecord.setOperatorName(userForExport.getName());
                userExportRecord.setOperatorPhone(userForExport.getPhone());
            }
            log.info("导出记录导入数据{}",userExportRecord);
            userExportRecordDao.insertUserExportRecord(userExportRecord);
            return ReturnResponse.success("导出记录导入数据成功");
        }catch (Exception e){
            log.error("导出记录导入有错{}",e.getMessage());
            String str=String.format("导出记录导入数据失败%s", e.getMessage());
            return ReturnResponse.failed(str);
        }
    }

    @Override
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
        userExportRecord.setCreatedAt(System.currentTimeMillis());
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
