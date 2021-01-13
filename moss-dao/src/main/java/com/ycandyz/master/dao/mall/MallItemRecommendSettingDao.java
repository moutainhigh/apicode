package com.ycandyz.master.dao.mall;

import com.ycandyz.master.domain.response.mall.MallItemRecommendRelationResp;
import com.ycandyz.master.entities.mall.MallItemRecommendSetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品推荐设置表 Mapper 接口
 * </p>
 *
 * @author WangWx
 * @since 2021-01-12
 */
public interface MallItemRecommendSettingDao extends BaseMapper<MallItemRecommendSetting> {
    List<MallItemRecommendRelationResp> getList(@Param("settingNo")String settingNo);

}
