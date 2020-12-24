package com.ycandyz.master.dao.leafletTemplate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.entities.leafletTemplate.TemplateOriginal;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 模板内容表 Mapper 接口
 *
 * @author WenXin
 * @since 2020-12-18
 */
public interface TemplateOriginalDao extends BaseMapper<TemplateOriginal> {

    List<TemplateOriginal> getByType(@Param("id") Integer id, @Param("organizeId") Long organizeId);

}
