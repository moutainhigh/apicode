package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderUAppVO;
import com.ycandyz.master.model.mall.MallOrderVO;

public interface MallOrderService extends IService<MallOrder> {

    ReturnResponse<Page<MallOrderVO>> queryOrderList(RequestParams<MallOrderQuery> requestParams);

    MallOrderExportResp exportEXT(MallOrderQuery mallOrder);

    ReturnResponse<MallOrderVO> queryOrderDetail(String orderNo);

    ReturnResponse<MallOrderVO> queryDetailByPickupNo(String pickupNo, String orderNo);

    ReturnResponse<String> verPickupNo(String pickupNo, String orderNo);

    ReturnResponse<Page<MallOrderUAppVO>> queryMallOrderListByUApp(Long page, Long page_size, String mallOrderQuery, Integer status);

    ReturnResponse<MallOrderUAppVO> queryOrderDetailByUApp(String orderNo);

    ReturnResponse<MallOrderUAppVO> queryDetailByPickupNoUApp(String pickupNo, String orderNo);

    ReturnResponse<MallOrderUAppVO> verPickupNoByUApp(String pickupNo, String orderNo);
}
