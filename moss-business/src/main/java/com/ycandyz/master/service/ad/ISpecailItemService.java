package com.ycandyz.master.service.ad;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.query.ad.SpecailItemQuery;
import com.ycandyz.master.entities.ad.SpecailItem;
import com.ycandyz.master.entities.mall.MallItem;

import java.util.List;

/**
 * <p>
 * @Description 专题-商品表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
public interface ISpecailItemService extends IService<SpecailItem>{

    /**
     * 查询专题商品
     * @param page 分页参数
     * @param query 查询参数
     * @return 专题商品
     */
    Page<MallItem> selectHomePage(Page page, SpecailItemQuery query);

    List<MallItem> selectList(SpecailItemQuery query);

    List<String> selectItemNoList(SpecailItemQuery query);
	
}
