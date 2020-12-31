package com.ycandyz.master.service.leafletTemplate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.leafletTemplate.TemplateDetailModel;
import com.ycandyz.master.domain.response.leafletTemplate.TemplateTableResp;
import com.ycandyz.master.entities.leafletTemplate.TemplateDetail;

import java.util.List;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板明细表 业务接口类
 * @since 2020-12-17
 */
public interface ITemplateDetailService extends IService<TemplateDetail> {

    boolean insert(TemplateDetailModel model);

    boolean update(TemplateDetailModel model);

}
