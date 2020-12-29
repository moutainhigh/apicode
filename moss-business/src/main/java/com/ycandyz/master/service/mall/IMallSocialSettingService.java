package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.mall.MallSocialSetting;
import com.ycandyz.master.domain.model.mall.MallSocialSettingModel;

/**
 * <p>
 * @Description 分销设置表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-29
 * @version 2.0
 */
public interface IMallSocialSettingService extends IService<MallSocialSetting>{

    boolean insert(MallSocialSettingModel model);

    boolean update(MallSocialSettingModel model);
}
