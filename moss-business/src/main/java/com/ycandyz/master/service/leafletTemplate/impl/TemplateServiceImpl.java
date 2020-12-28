package com.ycandyz.master.service.leafletTemplate.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.constant.DataConstant;
import com.ycandyz.master.dao.leafletTemplate.TemplateClassifyDao;
import com.ycandyz.master.dao.leafletTemplate.TemplateDao;
import com.ycandyz.master.dao.leafletTemplate.TemplateDetailDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateComponentPropertiesModel;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateDetailModel;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateModel;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateComponentPropertiesResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateDetailResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateResp;
import com.ycandyz.master.entities.leafletTemplate.Template;
import com.ycandyz.master.entities.leafletTemplate.TemplateClassify;
import com.ycandyz.master.entities.leafletTemplate.TemplateDetail;
import com.ycandyz.master.exception.BusinessException;
import com.ycandyz.master.service.leafletTemplate.ITemplateService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板定义表 业务类
 * @since 2020-12-17
 */
@Slf4j
@Service
public class TemplateServiceImpl extends BaseService<TemplateDao, Template, TemplateQuery> implements ITemplateService {
    @Resource
    private TemplateDao templateDao;
    @Resource
    private TemplateDetailDao templateDetailDao;
    @Resource
    private TemplateClassifyDao templateClassifyDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(TemplateModel model) {
        log.info("模板创建开始");
        Template t = new Template();
        BeanUtils.copyProperties(model, t);
        if (model.getEndTime() != null) {
            Date date = DateUtil.stampToDate(model.getEndTime());
            t.setEndTime(date);
        }
        UserVO user = getUser();
        t.setCreatedTime(new Date());
        t.setUserId(user.getId());
        t.setOrganizeId(user.getOrganizeId());
        log.info("模板主表创建参数为：{}", JSONObject.toJSONString(t));
        templateDao.saveTemplate(t);
        log.info("模板主表创建完成，创建人：{}，创建人所属企业：{}", user.getId(), user.getOrganizeId());
        log.info("模板副表创建开始");
        List<TemplateDetailModel> detailModels = model.getComponents();
        if (CollectionUtil.isEmpty(detailModels)) {
            log.error("组件信息为空");
            throw new BusinessException("请添加组件信息！");
        }
        IntStream.range(0, detailModels.size()).forEach(i -> {
            TemplateDetailModel vo = detailModels.get(i);
            TemplateDetail templateDetail = new TemplateDetail();
            if (vo == null) {
                log.error("组件信息为空");
                throw new BusinessException("请添加组件信息！");
            }
            if (vo.getComponentProperties() == null) {
                log.error("组件属性信息为空");
                throw new BusinessException("请添加组件属性信息！");
            }
            this.checkContentLength(vo.getComponentProperties());
            vo.setTemplateId(t.getId());
            vo.setComponentOrder(i);
            String componentStr = JSONObject.toJSONString(vo.getComponentProperties());
            BeanUtils.copyProperties(vo, templateDetail);
            templateDetail.setComponentProperties(componentStr);
            templateDetailDao.insert(templateDetail);
        });
        log.info("模板副表创建完成，总组件数：{}", detailModels.size());
        log.info("模板创建结束");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(TemplateModel model) {
        log.info("模板修改开始");
        Template t = new Template();
        BeanUtils.copyProperties(model, t);
        if (model.getEndTime() != null) {
            Date date = DateUtil.stampToDate(model.getEndTime());
            t.setEndTime(date);
        }
        if (null == model.getId()) {
            log.error("入参模板id为空");
            throw new BusinessException("模板信息不存在！");
        }
        Template template = templateDao.selectById(t.getId());
        if (template == null) {
            log.error("根据模板id：{}查询数据库返回值为null", t.getId());
            throw new BusinessException("模板信息不存在！");
        }
        UserVO user = getUser();
        t.setUserId(user.getId());
        templateDao.updateById(t);
        log.info("模板主表修改完成，修改人：{}，修改人所属企业：{}", user.getId(), user.getOrganizeId());
        List<TemplateDetailModel> detailModels = model.getComponents();
        if (CollectionUtil.isNotEmpty(detailModels)) {
            log.info("删除原有模板副表信息，模板id为：{}", t.getId());
            int deleteCount = templateDetailDao.deleteByTemplateId(t.getId());
            log.info("删除原有模板副表信息，删除组件数：{}个", deleteCount);
            IntStream.range(0, detailModels.size()).forEach(i -> {
                TemplateDetailModel vo = detailModels.get(i);
                TemplateDetail templateDetail = new TemplateDetail();
                AssertUtils.notNull(vo.getComponentProperties(), "请添加组件信息");
                this.checkContentLength(vo.getComponentProperties());
                vo.setTemplateId(model.getId());
                vo.setComponentOrder(i);
                String componentStr = JSONObject.toJSONString(vo.getComponentProperties());
                BeanUtils.copyProperties(vo, templateDetail);
                templateDetail.setComponentProperties(componentStr);
                templateDetailDao.insert(templateDetail);
            });
        }
        log.info("模板副表修改完成，共重新添加：{}个", detailModels.size());
        log.info("模板修改结束");
        return true;
    }

    private void checkContentLength(TemplateComponentPropertiesModel componentProperties) {
        if (componentProperties.getContentCustomLength() > componentProperties.getContentMaxLength()) {
            log.error("自定义组件长度超于组件默认长度，自定义长度：{}，组件默认长度：{}", componentProperties.getContentCustomLength(), componentProperties.getContentMaxLength());
            throw new BusinessException("标题为：" + componentProperties.getTitle() + "组件自定义长度不符！");
        }
    }

    public BasePageResult<Template> getPage(PageModel<Template> page, TemplateQuery query) {
        UserVO user = getUser();
        String source = user.getSource();
        Page<Template> templateIPage;
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        if (!DataConstant.TEMPLATE_PLATFORM_WEB.equals(source)) {
            queryWrapper.eq("organize_id", user.getOrganizeId());
            queryWrapper.eq("template_status", "0");
            if (query.getClassifyId() != null) {
                queryWrapper.eq("classify_id", query.getClassifyId());
            }
            if(query.getId()!=null){
                queryWrapper.lt("id",query.getId());
            }
            queryWrapper.orderByDesc("created_time");
            templateIPage = (Page<Template>) baseMapper.selectPage(new Page<>(page.getPage(), page.getPageSize()), queryWrapper);
        } else {
            queryWrapper.eq("organize_id", user.getOrganizeId());
            queryWrapper.orderByDesc("created_time");
            if (query.getBeginCreateTime() != null) {
                queryWrapper.ge("created_time", query.getBeginCreateTime());
            }
            if (query.getEndCreateTime() != null) {
                queryWrapper.le("created_time", query.getEndCreateTime());
            }
            if (query.getBeginExpireTime() != null) {
                queryWrapper.ge("end_time", query.getBeginExpireTime());
            }
            if (query.getEndExpireTime() != null) {
                queryWrapper.le("end_time", query.getEndExpireTime());
            }
            if (StringUtils.isNotEmpty(query.getTemplateName())) {
                queryWrapper.eq("template_name", query.getTemplateName());
            }
            templateIPage = (Page<Template>) baseMapper.selectPage(new Page<>(page.getPage(), page.getPageSize()), queryWrapper);
        }
        return new BasePageResult<>(templateIPage);
    }

    public TemplateResp getDetailById(Long id) {
        Template template = super.getById(id);
        AssertUtils.notNull(template, "模板信息不存在");
        TemplateClassify templateClassify = templateClassifyDao.selectById(template.getClassifyId());
        AssertUtils.notNull(templateClassify, "模板类别信息不存在");
        TemplateResp templateResp = new TemplateResp();
        BeanUtils.copyProperties(template, templateResp);
        templateResp.setClassifyName(templateClassify.getClassifyName());
        templateResp.setClassifyId(templateClassify.getId());
        if (template.getEndTime().compareTo(DateUtil.getNowDateShort()) < 0) {
            templateResp.setTemplateStatus(1);
        }
        List<TemplateDetail> templateDetails = templateDetailDao.selectList(new QueryWrapper<TemplateDetail>().eq("template_id", id).eq("component_status", 0));
        List<TemplateDetailResp> detailResps = new ArrayList<>();
        templateDetails.forEach(vo -> {
            String componentProperties = vo.getComponentProperties();
            TemplateComponentPropertiesResp propertiesResp = null;
            if (StringUtils.isNotEmpty(componentProperties)) {
                propertiesResp = JSONObject.parseObject(componentProperties, TemplateComponentPropertiesResp.class);
            }
            TemplateDetailResp templateDetailResp = new TemplateDetailResp();
            templateDetailResp.setComponentContent(vo.getComponentContent());
            templateDetailResp.setComponentType(vo.getComponentType());
            templateDetailResp.setComponentOrder(vo.getComponentOrder());
            if (null != propertiesResp) {
                templateDetailResp.setComponentProperties(propertiesResp);
            }
            detailResps.add(templateDetailResp);
        });
        detailResps.sort(Comparator.comparingInt(TemplateDetailResp::getComponentOrder));
        templateResp.setComponents(detailResps);
        return templateResp;
    }

    public boolean deleteTemplate(Long id) {
        log.info("逻辑删除模板开始，模板id为：{}", id);
        Template template = super.getById(id);
        AssertUtils.notNull(template, "模板信息不存在");
        template.setTemplateStatus(1);
        int i = templateDao.updateById(template);
        log.info("删除模板完成，删除条数为：{}条", i);
        return i == 1;
    }

}
