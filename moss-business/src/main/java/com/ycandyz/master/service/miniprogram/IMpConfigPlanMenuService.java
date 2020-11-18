package com.ycandyz.master.service.miniprogram;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.miniprogram.MpConfigPlanMenuModel;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanMenu;

/**
 * <p>
 * @Description 小程序配置-菜单配置 业务接口类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-13
 * @version 2.0
 */
public interface IMpConfigPlanMenuService extends IService<MpConfigPlanMenu>{

    /**
     * 新增方案下菜单
     * @param model
     * @return
     */
    MpConfigPlanMenu add(MpConfigPlanMenuModel model);
}
