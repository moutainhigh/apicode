package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.dto.mall.MallAfterSalesDTO;
import com.ycandyz.master.entities.mall.MallAfterSales;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MallAfterSalesDao extends BaseMapper<MallAfterSales> {

    Page<MallAfterSalesDTO> getTrendMallAfterSalesPage(PageModel model, @Param("p") MallafterSalesQuery mallafterSalesQuery);

    MallAfterSalesDTO querySalesDetail(@Param("id") Long id);
}
