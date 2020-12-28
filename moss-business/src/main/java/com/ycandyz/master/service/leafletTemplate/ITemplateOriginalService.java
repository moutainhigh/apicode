package com.ycandyz.master.service.leafletTemplate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateOriginalResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateOriginal;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateOriginalModel;
/**
 * @Description 模板内容表 业务接口类
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
public interface ITemplateOriginalService extends IService<TemplateOriginal>{

    TemplateOriginalResp getByType(Integer type);

}
