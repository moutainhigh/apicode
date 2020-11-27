package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.domain.query.mall.uApp.MallPickupUAppQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderUAppVO;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.model.mall.uApp.MallOrderDetailUAppVO;

public interface MallOrderService extends IService<MallOrder> {

    ReturnResponse<Page<MallOrderVO>> queryOrderList(RequestParams<MallOrderQuery> requestParams);

    MallOrderExportResp exportEXT(MallOrderQuery mallOrder);

    ReturnResponse<MallOrderVO> queryOrderDetail(String orderNo);

    ReturnResponse<MallOrderVO> queryDetailByPickupNo(String pickupNo, String orderNo);

    ReturnResponse<String> verPickupNo(String pickupNo, String orderNo);

    CommonResult<BasePageResult<MallOrderUAppVO>> queryMallOrderListByUApp(Long page, Long pageSize, String mallOrderQuery, Integer status, Integer deliverType, Long orderAtBegin, Long orderAtEnd);

    ReturnResponse<MallOrderUAppVO> queryOrderDetailByUApp(String orderNo);

    ReturnResponse<MallOrderUAppVO> queryDetailByPickupNoUApp(String pickupNo, String orderNo);

    ReturnResponse<MallOrderUAppVO> verPickupNoByUApp(MallPickupUAppQuery mallPickupUAppQuery);

    CommonResult<BasePageResult<MallOrderDetailUAppVO>> queryOrderDetailShareFlowListByNo(Long page, Long pageSize, String orderNo);
}
