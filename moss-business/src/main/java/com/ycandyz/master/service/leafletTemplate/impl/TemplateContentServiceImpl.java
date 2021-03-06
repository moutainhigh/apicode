package com.ycandyz.master.service.leafletTemplate.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.constant.DataConstant;
import com.ycandyz.master.dao.leafletTemplate.TemplateContentDao;
import com.ycandyz.master.dao.leafletTemplate.TemplateDao;
import com.ycandyz.master.dao.leafletTemplate.TemplateDetailDao;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateContentModel;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateContentQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateComponentPropertiesResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateContentResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateTableContentResp;
import com.ycandyz.master.entities.leafletTemplate.Template;
import com.ycandyz.master.entities.leafletTemplate.TemplateContent;
import com.ycandyz.master.entities.leafletTemplate.TemplateDetail;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.service.leafletTemplate.ITemplateContentService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.DateUtils;
import com.ycandyz.master.utils.S3UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * @author WenXin
 * @version 2.0
 * @Description ??????????????? ?????????
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
    private TemplateDetailDao templateDetailDao;

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
        AssertUtils.notNull(query.getTemplateId(), "??????????????????");
        Template template = templateDao.selectById(query.getTemplateId());
        AssertUtils.notNull(template, "????????????????????????");
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
        AssertUtils.notNull(contentQuery.getTemplateId(), "??????????????????");
        Template template = templateDao.selectById(contentQuery.getTemplateId());
        AssertUtils.notNull(template, "????????????????????????");
        String pathPrefix = System.getProperty("user.dir") + "/xls/";
        String fileName = DateUtils.getCurrentTime17() + "/" + template.getTemplateName() + "??????????????????.xls";
        String path = pathPrefix + fileName;
        ExcelWriter writer = ExcelUtil.getWriter(path);
        List<TemplateContent> contents = templateContentDao.selectList(new QueryWrapper<TemplateContent>().eq("template_id", contentQuery.getTemplateId()).orderByDesc("created_time"));
        List<TemplateDetail> details = templateDetailDao.selectList(new QueryWrapper<TemplateDetail>().eq("template_id", contentQuery.getTemplateId()).orderByDesc("component_status").orderByAsc("component_order"));
        Map<String, Object> titleMap = new LinkedHashMap<>();
        titleMap.put("01", "???????????????");
        titleMap.put("02", "????????????");
        titleMap.put("03", "????????????");
        titleMap.put("04", "????????????");
        details.forEach(vo -> {
            if (StringUtils.isNotEmpty(vo.getComponentProperties())) {
                TemplateComponentPropertiesResp componentProperty = JSONObject.parseObject(vo.getComponentProperties(), TemplateComponentPropertiesResp.class);
                if (StringUtils.isNotEmpty(DataConstant.TEMPLATE_COMPONENT_MAP.get(vo.getComponentType()))) {
                    titleMap.put(vo.getId().toString(), componentProperty.getTitle());
                }
            }
        });
        List<Map<String, Object>> maps = new ArrayList<>();
        contents.forEach(vo -> {
            if (StringUtils.isNotEmpty(vo.getComponentContent())) {
                List<TemplateTableContentResp> content = JSONObject.parseArray(vo.getComponentContent(), TemplateTableContentResp.class);
                Map<String, Object> contentMap = new LinkedHashMap<>();
                contentMap.put("01", "");
                contentMap.put("02", DateUtil.format(vo.getCreatedTime(), "yyyy-MM-dd HH:mm:ss"));
                contentMap.put("03", "");
                contentMap.put("04", "");
                if (vo.getUserId() != null) {
                    User user = userDao.selectById(vo.getUserId());
                    contentMap.put("01", user != null ? user.getPhone() : null);
                }

                if (vo.getChannel() != null) {
                    contentMap.put("03", DataConstant.CONTENT_CHANNEL_MAP.get(vo.getChannel().toString()));
                }
                if (vo.getPlatform() != null) {
                    contentMap.put("04", DataConstant.CONTENT_PLATFORM_MAP.get(vo.getPlatform().toString()));
                }
                content.forEach(cVo -> contentMap.put(cVo.getDetailId().toString(), cVo.getComponentContent()));
                maps.add(contentMap);
            }
        });
        List<List<Object>> rows = new ArrayList<>();
        Collection<Object> values = titleMap.values();
        List<Object> valueList = new ArrayList<>(values);
        rows.add(valueList);
        maps.forEach(map -> {
            List<Object> exportStr = new ArrayList<>();
            for (String key : titleMap.keySet()) {
                exportStr.add(map.get(key));
            }
            rows.add(exportStr);
        });
        writer.write(rows);
        writer.flush();
        writer.close();
        File file = new File(path);
        String url = s3UploadFile.uploadFile(file, fileName);
        boolean delete = file.delete();
        if (!delete) {
            log.error("???????????????????????????????????????{}", path);
        }
        return url;
    }

}
