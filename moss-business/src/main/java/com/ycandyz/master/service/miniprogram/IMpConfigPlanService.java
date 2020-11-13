package com.ycandyz.master.service.miniprogram;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.response.miniprogram.MpConfigMenuResp;
import com.ycandyz.master.entities.miniprogram.MpConfigPlan;

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

    MpConfigMenuResp add();
}
