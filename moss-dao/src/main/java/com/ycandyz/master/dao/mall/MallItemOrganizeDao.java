package com.ycandyz.master.dao.mall;

import com.ycandyz.master.domain.model.mall.MallItemDetailModel;
import com.ycandyz.master.entities.mall.MallItemOrganize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品-集团 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2021-01-05
 */
public interface MallItemOrganizeDao extends BaseMapper<MallItemOrganize> {

    int deleteOrg(MallItemOrganize t);

    int updateOrg(MallItemOrganize t);

    int updateBatchOrg(List<Long> ids);

    int edit(MallItemDetailModel model);

}
