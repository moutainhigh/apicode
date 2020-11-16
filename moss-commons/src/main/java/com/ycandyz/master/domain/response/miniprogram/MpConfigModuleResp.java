package com.ycandyz.master.domain.response.miniprogram;

import com.ycandyz.master.entities.miniprogram.MpConfigModule;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description  Resp
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_module")
public class MpConfigModuleResp extends MpConfigModule {

}
