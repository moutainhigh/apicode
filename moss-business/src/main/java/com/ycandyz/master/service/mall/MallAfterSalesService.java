package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.model.mall.MallAfterSalesVO;

public interface MallAfterSalesService extends IService<MallAfterSales> {

    CommonResult<Page<MallAfterSalesVO>> querySalesList(PageModel model, MallafterSalesQuery mallafterSalesQuery);

    CommonResult<MallAfterSalesVO> querySalesDetail(Long id);

    boolean refundAuditFirst(Long id, Integer subStatus);
}
