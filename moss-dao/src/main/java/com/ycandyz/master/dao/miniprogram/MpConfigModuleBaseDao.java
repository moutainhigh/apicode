package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.entities.miniprogram.MpConfigModuleBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 */
public interface MpConfigModuleBaseDao extends BaseMapper<MpConfigModuleBase> {
    MpConfigModuleBase selectByBaseId(Integer id);
}
