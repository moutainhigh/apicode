package com.ycandyz.master.service.leafletTemplate.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.leafletTemplate.TemplateComponentDao;
import com.ycandyz.master.dao.leafletTemplate.TemplateDao;
import com.ycandyz.master.dao.leafletTemplate.TemplateDetailDao;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateComponentQuery;
import com.ycandyz.master.domain.response.leafletTemplate.ChildTemplateComponentResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateComponentPropertiesResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateComponentResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateTableResp;
import com.ycandyz.master.entities.leafletTemplate.Template;
import com.ycandyz.master.entities.leafletTemplate.TemplateComponent;
import com.ycandyz.master.entities.leafletTemplate.TemplateDetail;
import com.ycandyz.master.service.leafletTemplate.ITemplateComponentService;
import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板组件表 业务类
 * @since 2020-12-17
 */
@Slf4j
@Service
public class TemplateComponentServiceImpl extends BaseService<TemplateComponentDao, TemplateComponent, TemplateComponentQuery> implements ITemplateComponentService {

    @Resource
    private TemplateComponentDao componentDao;

    @Resource
    private TemplateDao templateDao;

    @Resource
    private TemplateDetailDao templateDetailDao;

    @Override
    public List<TemplateComponentResp> listComponents() {
        List<TemplateComponent> parentComponents = componentDao.selectList(new QueryWrapper<TemplateComponent>().eq("parent_id", 0).eq("component_status", 0));
        AssertUtils.notEmpty(parentComponents, "无可选组件，请联系管理员创建组件！");
        List<TemplateComponentResp> componentResps = new ArrayList<>();
        parentComponents.forEach(vo -> {
            TemplateComponentResp componentResp = new TemplateComponentResp();
            TemplateComponentPropertiesResp propertiesResp = null;
            componentResp.setParentName(vo.getComponentName());
            List<ChildTemplateComponentResp> childList = componentDao.getChildList(vo.getId());
            for (ChildTemplateComponentResp child : childList) {
                String componentProperties = child.getComponentPropertiesStr();
                if (StringUtils.isNotEmpty(componentProperties)) {
                    propertiesResp = JSONObject.parseObject(componentProperties, TemplateComponentPropertiesResp.class);
                }
                if (propertiesResp != null) {
                    child.setComponentProperties(propertiesResp);
                }
            }
            componentResp.setComponents(childList);
            componentResps.add(componentResp);
        });
        return componentResps;
    }

    public List<TemplateTableResp> getTableList(Long templateId) {
        AssertUtils.notNull(templateId, "未选择模板！");
        Template template = templateDao.selectById(templateId);
        AssertUtils.notNull(template, "模板信息不存在！");
        List<TemplateDetail> details = templateDetailDao.selectList(new QueryWrapper<TemplateDetail>().eq("template_id", templateId));
        AssertUtils.notEmpty(details, "动态列信息不存在！");
        List<TemplateTableResp> tableResps = new ArrayList<>();
        details.forEach(vo -> {
            TemplateTableResp tableResp = new TemplateTableResp();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(vo.getComponentProperties())) {
                TemplateComponentPropertiesResp propertiesResp = JSONObject.parseObject(vo.getComponentProperties(), TemplateComponentPropertiesResp.class);
                tableResp.setDetail_id(vo.getId());
                tableResp.setComponentTitle(propertiesResp.getTitle());
                tableResp.setComponentOrder(vo.getComponentOrder());
                tableResp.setComponentType(vo.getComponentType());
                tableResps.add(tableResp);
            }
        });
        tableResps.sort(Comparator.comparingInt(TemplateTableResp::getComponentOrder));
        return tableResps;
    }
}
