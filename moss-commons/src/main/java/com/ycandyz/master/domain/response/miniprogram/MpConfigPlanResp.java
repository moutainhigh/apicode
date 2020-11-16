package com.ycandyz.master.domain.response.miniprogram;

import com.ycandyz.master.entities.miniprogram.MpConfigPlan;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 小程序配置方案 Resp
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_plan")
public class MpConfigPlanResp extends MpConfigPlan {

}
