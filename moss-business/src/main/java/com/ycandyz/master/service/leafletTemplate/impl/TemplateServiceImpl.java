package com.ycandyz.master.service.leafletTemplate.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
 * @Description ??????????????? ?????????
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
        log.info("??????????????????");
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
        log.info("??????????????????????????????{}", JSONObject.toJSONString(t));
        templateDao.saveTemplate(t);
        log.info("???????????????????????????????????????{}???????????????????????????{}", user.getId(), user.getOrganizeId());
        log.info("????????????????????????");
        List<TemplateDetailModel> detailModels = model.getComponents();
        if (CollectionUtil.isEmpty(detailModels)) {
            log.error("??????????????????");
            throw new BusinessException("????????????????????????");
        }
        IntStream.range(0, detailModels.size()).forEach(i -> {
            TemplateDetailModel vo = detailModels.get(i);
            TemplateDetail templateDetail = new TemplateDetail();
            if (vo == null) {
                log.error("??????????????????");
                throw new BusinessException("????????????????????????");
            }
            if (vo.getComponentProperties() == null) {
                log.error("????????????????????????");
                throw new BusinessException("??????????????????????????????");
            }
            this.checkContentLength(vo.getComponentProperties());
            vo.setTemplateId(t.getId());
            vo.setComponentOrder(i);
            String componentStr = JSONObject.toJSONString(vo.getComponentProperties());
            BeanUtils.copyProperties(vo, templateDetail);
            templateDetail.setComponentProperties(componentStr);
            templateDetailDao.insert(templateDetail);
        });
        log.info("??????????????????????????????????????????{}", detailModels.size());
        log.info("??????????????????");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(TemplateModel model) {
        log.info("??????????????????");
        Template t = new Template();
        BeanUtils.copyProperties(model, t);
        if (model.getEndTime() != null) {
            Date date = DateUtil.stampToDate(model.getEndTime());
            t.setEndTime(date);
        }
        if (null == model.getId()) {
            log.error("????????????id??????");
            throw new BusinessException("????????????????????????");
        }
        Template template = templateDao.selectById(t.getId());
        if (template == null) {
            log.error("????????????id???{}???????????????????????????null", t.getId());
            throw new BusinessException("????????????????????????");
        }
        UserVO user = getUser();
        t.setUserId(user.getId());
        templateDao.updateById(t);
        log.info("???????????????????????????????????????{}???????????????????????????{}", user.getId(), user.getOrganizeId());
        if (CollectionUtil.isNotEmpty(model.getDelArray())) {
            //??????????????????
            for (Long detailId : model.getDelArray()) {
                TemplateDetail templateDetail = new TemplateDetail();
                templateDetail.setId(detailId);
                templateDetail.setComponentStatus(0);
                templateDetailDao.updateById(templateDetail);
            }
            log.info("???????????????????????????????????????????????????{}???", model.getDelArray().size());
        }
        List<TemplateDetailModel> detailModels = model.getComponents();
        if (CollectionUtil.isNotEmpty(detailModels)) {
            IntStream.range(0, detailModels.size()).forEach(i -> {
                TemplateDetailModel vo = detailModels.get(i);
                TemplateDetail templateDetail = new TemplateDetail();
                AssertUtils.notNull(vo.getComponentProperties(), "?????????????????????");
                this.checkContentLength(vo.getComponentProperties());
                vo.setTemplateId(model.getId());
                vo.setComponentOrder(i);
                String componentStr = JSONObject.toJSONString(vo.getComponentProperties());
                BeanUtils.copyProperties(vo, templateDetail);
                templateDetail.setComponentProperties(componentStr);
                if (null == templateDetail.getId()) {
                    templateDetailDao.insert(templateDetail);
                } else {
                    templateDetailDao.updateById(templateDetail);
                }
            });
        }
        log.info("?????????????????????????????????????????????{}???", detailModels.size());
        log.info("??????????????????");
        return true;
    }

    private void checkContentLength(TemplateComponentPropertiesModel componentProperties) {
        if (componentProperties.getContentCustomLength() > componentProperties.getContentMaxLength()) {
            log.error("??????????????????????????????????????????????????????????????????{}????????????????????????{}", componentProperties.getContentCustomLength(), componentProperties.getContentMaxLength());
            throw new BusinessException("????????????" + componentProperties.getTitle() + "??????????????????????????????");
        }
    }

    public BasePageResult<Template> getPage(PageModel<Template> page, TemplateQuery query) {
        UserVO user = getUser();
        String source = user.getSource();
        Page<Template> templateIPage;
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        if (!DataConstant.TEMPLATE_PLATFORM_WEB.equals(source)) {
            queryWrapper.eq("organize_id", user.getOrganizeId());
            queryWrapper.eq("template_status", "1");
            if (query.getClassifyId() != null) {
                queryWrapper.eq("classify_id", query.getClassifyId());
            }
            if (query.getId() != null) {
                queryWrapper.lt("id", query.getId());
            }
            queryWrapper.orderByDesc("created_time");
            templateIPage = (Page<Template>) baseMapper.selectPage(new Page<>(page.getPage(), page.getPageSize()), queryWrapper);
        } else {
            queryWrapper.eq("organize_id", user.getOrganizeId());
            queryWrapper.orderByDesc("created_time");
            if (query.getBeginCreateTime() != null) {
                queryWrapper.ge("created_time", cn.hutool.core.date.DateUtil.offsetHour(query.getBeginCreateTime(), -8));
            }
            if (query.getEndCreateTime() != null) {
                queryWrapper.le("created_time", cn.hutool.core.date.DateUtil.offsetHour(query.getEndCreateTime(), -8));
            }
            if (query.getBeginExpireTime() != null) {
                queryWrapper.ge("end_time", cn.hutool.core.date.DateUtil.offsetHour(query.getBeginExpireTime(), -8));
            }
            if (query.getEndExpireTime() != null) {
                queryWrapper.le("end_time", cn.hutool.core.date.DateUtil.offsetHour(query.getEndExpireTime(), -8));
            }
            if (StringUtils.isNotEmpty(query.getTemplateName())) {
                queryWrapper.like("template_name", query.getTemplateName());
            }
            templateIPage = (Page<Template>) baseMapper.selectPage(new Page<>(page.getPage(), page.getPageSize()), queryWrapper);
        }
        return new BasePageResult<>(templateIPage);
    }

    public TemplateResp getDetailById(Long id) {
        Template template = super.getById(id);
        AssertUtils.notNull(template, "?????????????????????");
        TemplateClassify templateClassify = templateClassifyDao.selectById(template.getClassifyId());
        AssertUtils.notNull(templateClassify, "???????????????????????????");
        TemplateResp templateResp = new TemplateResp();
        BeanUtils.copyProperties(template, templateResp);
        templateResp.setClassifyName(templateClassify.getClassifyName());
        templateResp.setClassifyId(templateClassify.getId());
        templateResp.setMaxComponentsCount(templateClassify.getMaxComponentsCount());
        if (template.getEndTime().compareTo(DateUtil.getNowDateShort()) < 0) {
            templateResp.setTemplateStatus(3);
        }
        List<TemplateDetail> templateDetails = templateDetailDao.selectList(new QueryWrapper<TemplateDetail>().eq("template_id", id).eq("component_status", 1));
        List<TemplateDetailResp> detailResps = new ArrayList<>();
        templateDetails.forEach(vo -> {
            String componentProperties = vo.getComponentProperties();
            TemplateComponentPropertiesResp propertiesResp = null;
            if (StringUtils.isNotEmpty(componentProperties)) {
                propertiesResp = JSONObject.parseObject(componentProperties, TemplateComponentPropertiesResp.class);
            }
            TemplateDetailResp templateDetailResp = new TemplateDetailResp();
            templateDetailResp.setId(vo.getId());
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
        log.info("?????????????????????????????????id??????{}", id);
        Template template = new Template();
        template.setUpdatedTime(cn.hutool.core.date.DateUtil.offsetHour(new Date(), -8));
        int i = templateDao.update(template, new UpdateWrapper<Template>().set("template_status", "0").eq("id", id));
        log.info("???????????????????????????????????????{}???", i);
        return i == 1;
    }

}
