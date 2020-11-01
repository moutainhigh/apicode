package com.ycandyz.master.service.ad;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.query.ad.SpecialQuery;
import com.ycandyz.master.dto.ad.SpecialDTO;
import com.ycandyz.master.entities.ad.Special;
import com.ycandyz.master.model.ad.SpecialModel;

import java.util.List;

/**
 * <p>
 * @Description 首页-专题 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-14
 * @version 2.0
 */
public interface ISpecialService extends IService<Special>{

    SpecialDTO selectById(Long id);

    boolean insert(SpecialModel entity);

    boolean update(SpecialModel entity);

    List<Special> selectHomeList(SpecialQuery query);

    Page<Special> selectHomePage(Page page, SpecialQuery query);

}
