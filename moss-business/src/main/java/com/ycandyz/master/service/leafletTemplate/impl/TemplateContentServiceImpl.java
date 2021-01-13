package com.ycandyz.master.service.leafletTemplate.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.constant.DataConstant;
import com.ycandyz.master.dao.leafletTemplate.TemplateContentDao;
import com.ycandyz.master.dao.leafletTemplate.TemplateDao;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateContentModel;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateContentQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateContentResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateTableContentResp;
import com.ycandyz.master.entities.leafletTemplate.Template;
import com.ycandyz.master.entities.leafletTemplate.TemplateContent;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.service.leafletTemplate.ITemplateContentService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.DateUtils;
import com.ycandyz.master.utils.S3UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板内容表 业务类
 * @since 2020-12-17
 */
@Slf4j
@Service
public class TemplateContentServiceImpl extends BaseService<TemplateContentDao, TemplateContent, TemplateContentQuery> implements ITemplateContentService {

    @Resource
    private TemplateDao templateDao;

    @Resource
    private TemplateContentDao templateContentDao;

    @Resource
    private UserDao userDao;

    @Resource
    private S3UploadFile s3UploadFile;

    @Override
    public boolean insert(TemplateContentModel model) {
        TemplateContent t = new TemplateContent();
        BeanUtils.copyProperties(model, t);
        return super.save(t);
    }

    @Override
    public boolean update(TemplateContentModel model) {
        TemplateContent t = new TemplateContent();
        BeanUtils.copyProperties(model, t);
        return super.updateById(t);
    }

    public Page<TemplateContentResp> getContentPage(PageModel page, TemplateContentQuery query) {
        AssertUtils.notNull(query.getTemplateId(), "请选择模板！");
        Template template = templateDao.selectById(query.getTemplateId());
        AssertUtils.notNull(template, "模板信息不存在！");
        IPage<TemplateContent> templateContentsPage = templateContentDao.selectPage(new Page<>(page.getPage(), page.getPageSize()), new QueryWrapper<TemplateContent>().eq("template_id", query.getTemplateId()).orderByDesc("created_time"));
        List<TemplateContent> templateContents = templateContentsPage.getRecords();
        Page<TemplateContentResp> resultPages = new Page<>();
        if (CollectionUtil.isEmpty(templateContents)) {
            return resultPages;
        }
        List<TemplateContentResp> tableContents = new ArrayList<>();
        templateContents.forEach(vo -> {
            if (StringUtils.isNotEmpty(vo.getComponentContent())) {
                TemplateContentResp templateContentResp = new TemplateContentResp();
                if (null != vo.getUserId()) {
                    User user = userDao.selectById(vo.getUserId());
                    if (user != null) {
                        templateContentResp.setUserPhone(user.getPhone());
                    }
                }
                if (null != vo.getChannel()) {
                    templateContentResp.setChannelStr(DataConstant.CONTENT_CHANNEL_MAP.get(vo.getChannel().toString()));
                }
                if (null != vo.getPlatform()) {
                    templateContentResp.setPlatformStr(DataConstant.CONTENT_PLATFORM_MAP.get(vo.getPlatform().toString()));
                }
                BeanUtils.copyProperties(vo, templateContentResp);
                List<TemplateTableContentResp> contentResps = JSONObject.parseArray(vo.getComponentContent(), TemplateTableContentResp.class);
                templateContentResp.setContents(contentResps);
                tableContents.add(templateContentResp);
            }
        });
        resultPages.setCurrent(page.getPage());
        resultPages.setPages(templateContentsPage.getPages());
        resultPages.setRecords(tableContents);
        resultPages.setSize(page.getPageSize());
        resultPages.setTotal(templateContentsPage.getTotal());
        return resultPages;
    }

    public String exportContent(TemplateContentQuery contentQuery) {
        AssertUtils.notNull(contentQuery.getTemplateId(), "未选择表单！");
        Template template = templateDao.selectById(contentQuery.getTemplateId());
        AssertUtils.notNull(template, "模板信息不存在！");
        String pathPrefix = System.getProperty("user.dir") + "/xls/";
        String fileName = DateUtils.getCurrentTime17() + "/" + template.getTemplateName() + "模板信息报表.xls";
        String path = pathPrefix + fileName;
        ExcelWriter writer = ExcelUtil.getWriter(path);
        List<TemplateContent> contents = templateContentDao.selectList(new QueryWrapper<TemplateContent>().eq("template_id", contentQuery.getTemplateId()).orderByDesc("created_time"));
        List<Map<String, Object>> maps = new ArrayList<>();
        contents.forEach(vo -> {
            if (StringUtils.isNotEmpty(vo.getComponentContent())) {
                List<TemplateTableContentResp> content = JSONObject.parseArray(vo.getComponentContent(), TemplateTableContentResp.class);
                Map<String, Object> contentMap = new LinkedHashMap<>();
                contentMap.put("客户手机号", "");
                contentMap.put("提交时间", DateUtil.format(vo.getCreatedTime(),"yyyy-MM-dd HH:mm:ss"));
                contentMap.put("来源渠道", "");
                contentMap.put("手机系统", "");
                if (vo.getUserId() != null) {
                    User user = userDao.selectById(vo.getUserId());
                    contentMap.put("客户手机号", user != null ? user.getPhone() : null);
                }

                if (vo.getChannel() != null) {
                    contentMap.put("来源渠道", DataConstant.CONTENT_CHANNEL_MAP.get(vo.getChannel().toString()));
                }
                if (vo.getPlatform() != null) {
                    contentMap.put("手机系统", DataConstant.CONTENT_PLATFORM_MAP.get(vo.getPlatform().toString()));
                }
                content.forEach(contentVo -> contentMap.put(contentVo.getTitle(), contentVo.getComponentContent()));
                maps.add(contentMap);
            }
        });
        Collections.sort(maps, (Comparator<Map>) (o1, o2) -> {
            if (o1.size() > o2.size()) {
                return -1;
            } else if (o1.size() == o2.size()) {
                return 0;
            }
            return 1;
        });
        List<Map<String, Object>> exportMaps = new ArrayList<>();
        Map<String, Object> titleMap = maps.get(0);
        maps.forEach(map -> {
            Map<String, Object> exportMap = new LinkedHashMap<>();
            for (String key : titleMap.keySet()) {
                exportMap.put(key, map.get(key));
            }
            exportMaps.add(exportMap);
        });
        writer.write(exportMaps);
        writer.flush();
        writer.close();
        File file = new File(path);
        String url = s3UploadFile.uploadFile(file, fileName);
        boolean delete = file.delete();
        if (!delete) {
            log.error("删除文件失败，文件路径为：{}", path);
        }
        return url;
    }

}
