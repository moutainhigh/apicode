package com.ycandyz.master.dao.ad;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.ad.SpecailItemQuery;
import com.ycandyz.master.entities.ad.SpecailItem;
import com.ycandyz.master.entities.mall.MallItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专题-商品表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 */
public interface SpecailItemDao extends BaseMapper<SpecailItem> {

    Page<MallItem> selectHomePage(Page page, @Param("query") SpecailItemQuery query);

    List<String> selectItemNoList(@Param("query") SpecailItemQuery query);

    List<MallItem> selectList(@Param("query") SpecailItemQuery query);

}
