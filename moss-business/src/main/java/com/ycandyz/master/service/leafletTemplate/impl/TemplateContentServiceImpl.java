package com.ycandyz.master.service.leafletTemplate.impl;

import com.ycandyz.master.entities.leafletTemplate.TemplateContent;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateContentModel;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateContentQuery;
import com.ycandyz.master.dao.leafletTemplate.TemplateContentDao;
import com.ycandyz.master.service.leafletTemplate.ITemplateContentService;
import com.ycandyz.master.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * @Description 模板内容表 业务类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-17
 * @version 2.0
 */
@Slf4j
@Service
public class TemplateContentServiceImpl extends BaseService<TemplateContentDao,TemplateContent,TemplateContentQuery> implements ITemplateContentService {

    @Override
    public boolean insert(TemplateContentModel model) {
        TemplateContent t = new TemplateContent();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(TemplateContentModel model) {
        TemplateContent t = new TemplateContent();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }



}
