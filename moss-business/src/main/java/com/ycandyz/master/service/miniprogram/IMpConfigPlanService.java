package com.ycandyz.master.service.miniprogram;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.miniprogram.ConfigPlanAndMenuModel;
import com.ycandyz.master.entities.miniprogram.MpConfigPlan;

import java.util.List;

/**
 * <p>
 * @Description 小程序配置方案 业务接口类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
public interface IMpConfigPlanService extends IService<MpConfigPlan>{

    /**
     * 保存方案，创建方案下菜单
     * @param model
     * @return
     */
    MpConfigPlan add(ConfigPlanAndMenuModel model);

    /**
     * 初始化方案
     * @param planName
     * @return
     */
    Boolean initPlan(String planName);


    /**
     * 企业绑定模板
     * @param organizeId
     * @param planId
     * @return
     */
    int organizeBindPlan(Integer organizeId,Integer planId);
}
