package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.model.mall.MallAfterSalesVO;

public interface MallAfterSalesService extends IService<MallAfterSales> {

    ReturnResponse<Page<MallAfterSalesVO>> querySalesListPage(RequestParams<MallafterSalesQuery> requestParams, UserVO userVO);

    ReturnResponse<MallAfterSalesVO> querySalesDetail(String afterSalesNo, UserVO userVO);

    boolean refundAuditFirst(MallafterSalesQuery mallafterSalesQuery, UserVO userVO);

    MallOrderExportResp exportEXT(MallafterSalesQuery mallafterSalesQuery, UserVO userVO);

    ReturnResponse<String> refundDetail(String orderNo, UserVO userVO);

    void processSubStatus();


}
