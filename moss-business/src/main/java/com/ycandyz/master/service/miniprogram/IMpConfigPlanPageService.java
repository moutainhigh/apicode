package com.ycandyz.master.service.miniprogram;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanPageModel;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;

/**
 * <p>
 * @Description 小程序配置-页面配置 业务接口类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
public interface IMpConfigPlanPageService extends IService<MpConfigPlanPage>{

    /**
     * 添加模块下元素信息
     * @param model
     * @return
     */
    Boolean add(MpConfigPlanPageModel model);
}
