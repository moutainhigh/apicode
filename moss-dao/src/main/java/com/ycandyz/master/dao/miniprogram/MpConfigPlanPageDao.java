package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.dto.miniprogram.MpConfigPlanPageBaseDTO;
import com.ycandyz.master.dto.miniprogram.MpConfigPlanPageDTO;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小程序配置-页面配置 Mapper 接口
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 */
public interface MpConfigPlanPageDao extends BaseMapper<MpConfigPlanPage> {

    /**
     * 获取
     * @param menuId
     * @param moduleSort
     * @param moduleId
     * @return
     */
    MpConfigPlanPageDTO getMenuModule(@Param("menuId") Integer menuId, @Param("moduleSort") Integer moduleSort, @Param("moduleId") Integer moduleId);


    MpConfigPlanPageBaseDTO getMenuModuleElement(@Param("menuId") Integer menuId, @Param("moduleSort") Integer moduleSort, @Param("moduleId") Integer moduleId);

}
