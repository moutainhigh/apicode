package com.ycandyz.master.service.leafletTemplate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.leafletTemplate.Template;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateModel;
/**
 * <p>
 * @Description 模板定义表 业务接口类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-17
 * @version 2.0
 */
public interface ITemplateService extends IService<Template>{

    boolean insert(TemplateModel model);

    boolean update(TemplateModel model);
}
