package com.ycandyz.master.dao.leafletTemplate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.entities.leafletTemplate.Template;
import org.apache.ibatis.annotations.Options;

/**
 * 模板定义表 Mapper 接口
 *
 * @author WenXin
 * @since 2020-12-18
 */
public interface TemplateDao extends BaseMapper<Template> {

    Integer saveTemplate(Template t);

}
