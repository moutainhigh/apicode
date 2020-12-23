package com.ycandyz.master.service.leafletTemplate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateComponentResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateComponent;

import java.util.List;

/**
 * @Description 模板组件表 业务接口类
 * @author WenXin
 * @since 2020-12-17
 * @version 2.0
 */
public interface ITemplateComponentService extends IService<TemplateComponent>{

    List<TemplateComponentResp> listComponents();

}
