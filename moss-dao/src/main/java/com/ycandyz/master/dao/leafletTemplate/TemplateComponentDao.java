package com.ycandyz.master.dao.leafletTemplate;

import com.ycandyz.master.domain.response.leafletTemplate.ChildTemplateComponentResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateComponent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 模板组件表 Mapper 接口
 * @author WenXin
 * @since 2020-12-18
 */
public interface TemplateComponentDao extends BaseMapper<TemplateComponent> {

    List<ChildTemplateComponentResp> getChildList(Long parentId);
}
