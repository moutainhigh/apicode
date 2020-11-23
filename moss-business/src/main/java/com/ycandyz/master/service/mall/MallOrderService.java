package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.domain.query.mall.MallOrderUAppQuery;
import com.ycandyz.master.domain.response.mall.MallOrderExportResp;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderUAppVO;
import com.ycandyz.master.model.mall.MallOrderVO;

public interface MallOrderService extends IService<MallOrder> {

    ReturnResponse<Page<MallOrderVO>> queryOrderList(RequestParams<MallOrderQuery> requestParams, UserVO userVO);

    MallOrderExportResp exportEXT(MallOrderQuery mallOrder, UserVO userVO);

    ReturnResponse<MallOrderVO> queryOrderDetail(String orderNo, UserVO userVO);

    ReturnResponse<MallOrderVO> queryDetailByPickupNo(String pickupNo, String orderNo, UserVO userVO);

    ReturnResponse<String> verPickupNo(String pickupNo, String orderNo, UserVO userVO);

    ReturnResponse<Page<MallOrderUAppVO>> queryMallOrderListByUApp(Long page, Long page_size, String mallOrderQuery, Integer status, UserVO userVO);

    ReturnResponse<MallOrderUAppVO> queryOrderDetailByUApp(String orderNo, UserVO userVO);
}
