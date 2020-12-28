package com.ycandyz.master.service.leafletTemplate.impl;

import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.leafletTemplate.TemplateClassifyDao;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateClassifyModel;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateClassifyQuery;
import com.ycandyz.master.entities.leafletTemplate.TemplateClassify;
import com.ycandyz.master.service.leafletTemplate.ITemplateClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Description 模板内容表 业务类
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Slf4j
@Service
public class TemplateClassifyServiceImpl extends BaseService<TemplateClassifyDao,TemplateClassify,TemplateClassifyQuery> implements ITemplateClassifyService {

    @Override
    public boolean insert(TemplateClassifyModel model) {
        TemplateClassify t = new TemplateClassify();
        BeanUtils.copyProperties(model,t);
        return super.save(t);
    }

    @Override
    public boolean update(TemplateClassifyModel model) {
        TemplateClassify t = new TemplateClassify();
        BeanUtils.copyProperties(model,t);
        return super.updateById(t);
    }

}
