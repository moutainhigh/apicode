package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.model.mall.MallAfterSalesVO;
import com.ycandyz.master.model.user.UserVO;

import javax.servlet.http.HttpServletResponse;

public interface MallAfterSalesService extends IService<MallAfterSales> {

    ReturnResponse<Page<MallAfterSalesVO>> querySalesListPage(RequestParams<MallafterSalesQuery> requestParams, UserVO userVO);

    ReturnResponse<MallAfterSalesVO> querySalesDetail(Long id);

    boolean refundAuditFirst(MallafterSalesQuery mallafterSalesQuery, UserVO userVO);

    void exportEXT(MallafterSalesQuery mallafterSalesQuery, UserVO userVO, HttpServletResponse response);
}
