package com.ycandyz.master.service.leafletTemplate.impl;

import com.alibaba.fastjson.JSONObject;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.leafletTemplate.TemplateOriginalDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateOriginalQuery;
import com.ycandyz.master.domain.response.leafletTemplate.OriginalTemplateComponentResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateOriginalResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateOriginal;
import com.ycandyz.master.service.leafletTemplate.ITemplateOriginalService;
import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板内容表 业务类
 * @since 2020-12-18
 */
@Slf4j
@Service
public class TemplateOriginalServiceImpl extends BaseService<TemplateOriginalDao, TemplateOriginal, TemplateOriginalQuery> implements ITemplateOriginalService {

    @Resource
    private TemplateOriginalDao originalDao;

    @Override
    public TemplateOriginalResp getByType(Integer type) {
        UserVO user = getUser();
        Long organizeId = user.getOrganizeId();
        AssertUtils.notNull(type, "请选择模板类型！");
        List<TemplateOriginal> originals = originalDao.getByType(type, organizeId);
        AssertUtils.notEmpty(originals, "当前类型下无默认模板！");
        TemplateOriginal original = originals.get(0);
        String componentsStr = original.getComponents();
        AssertUtils.notEmpty(componentsStr, "当前默认模板无相关组件信息！");
        List<OriginalTemplateComponentResp> components = JSONObject.parseArray(componentsStr, OriginalTemplateComponentResp.class);
        AssertUtils.notEmpty(components, "当前默认模板无相关组件信息！");
        components.sort(Comparator.comparingInt(OriginalTemplateComponentResp::getComponentOrder));
        TemplateOriginalResp templateOriginalResp = new TemplateOriginalResp();
        templateOriginalResp.setTemplateImg(original.getTemplateImg());
        templateOriginalResp.setComponents(components);
        templateOriginalResp.setTemplateName(original.getTemplateName());
        templateOriginalResp.setShareDesc(original.getShareDesc());
        templateOriginalResp.setShareImg(original.getShareImg());
        templateOriginalResp.setShareTitle(original.getShareTitle());
        return templateOriginalResp;
    }

}
