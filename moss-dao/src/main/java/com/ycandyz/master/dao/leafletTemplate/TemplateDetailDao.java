package com.ycandyz.master.dao.leafletTemplate;

import com.ycandyz.master.entities.leafletTemplate.TemplateDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 模板明细表 Mapper 接口
 * @author WenXin
 * @since 2020-12-18
 */
public interface TemplateDetailDao extends BaseMapper<TemplateDetail> {

    int deleteByTemplateId(Long templateId);
}
