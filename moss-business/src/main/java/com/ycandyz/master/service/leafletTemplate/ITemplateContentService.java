package com.ycandyz.master.service.leafletTemplate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.leafletTemplate.TemplateContent;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateContentModel;
/**
 * <p>
 * @Description 模板内容表 业务接口类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-17
 * @version 2.0
 */
public interface ITemplateContentService extends IService<TemplateContent>{

    boolean insert(TemplateContentModel model);

    boolean update(TemplateContentModel model);
}
