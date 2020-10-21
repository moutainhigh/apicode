package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderDetailVO;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.model.user.UserVO;

import javax.servlet.http.HttpServletResponse;

public interface MallOrderService extends IService<MallOrder> {

    ReturnResponse<Page<MallOrderVO>> queryOrderList(PageResult pageResult, MallOrderQuery mallOrderQuery, UserVO userVO);

    void exportEXT(MallOrderQuery mallOrder, UserVO userVO, HttpServletResponse response);

    ReturnResponse<MallOrderDetailVO> queryOrderDetail(String orderNo);

    ReturnResponse<MallOrderVO> queryDetailByPickupNo(String pickupNo, UserVO userVO);

    ReturnResponse<String> verPickupNo(String orderNo, UserVO userVO);

}
