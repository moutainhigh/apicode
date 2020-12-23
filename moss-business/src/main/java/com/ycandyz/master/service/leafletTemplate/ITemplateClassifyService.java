package com.ycandyz.master.service.leafletTemplate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.leafletTemplate.TemplateClassify;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateClassifyModel;
/**
 * <p>
 * @Description 模板内容表 业务接口类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
public interface ITemplateClassifyService extends IService<TemplateClassify>{

    boolean insert(TemplateClassifyModel model);

    boolean update(TemplateClassifyModel model);
}
