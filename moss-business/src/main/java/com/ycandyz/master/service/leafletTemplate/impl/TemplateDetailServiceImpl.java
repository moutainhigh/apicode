package com.ycandyz.master.service.leafletTemplate.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.leafletTemplate.TemplateDao;
import com.ycandyz.master.dao.leafletTemplate.TemplateDetailDao;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateDetailModel;
import com.ycandyz.master.domain.query.leafletTemplate.TemplateDetailQuery;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateComponentPropertiesResp;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateTableResp;
import com.ycandyz.master.entities.leafletTemplate.Template;
import com.ycandyz.master.entities.leafletTemplate.TemplateDetail;
import com.ycandyz.master.service.leafletTemplate.ITemplateDetailService;
import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *
 * @author WenXin
 * @version 2.0
 * @Description 模板明细表 业务类
 * </p>
 * @since 2020-12-17
 */
@Slf4j
@Service
public class TemplateDetailServiceImpl extends BaseService<TemplateDetailDao, TemplateDetail, TemplateDetailQuery> implements ITemplateDetailService {
    @Resource
    private TemplateDao templateDao;
    @Resource
    private TemplateDetailDao templateDetailDao;

    @Override
    public boolean insert(TemplateDetailModel model) {
        TemplateDetail t = new TemplateDetail();
        BeanUtils.copyProperties(model, t);
        return super.save(t);
    }

    @Override
    public boolean update(TemplateDetailModel model) {
        TemplateDetail t = new TemplateDetail();
        BeanUtils.copyProperties(model, t);
        return super.updateById(t);
    }
}
