package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderDetailVO;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.model.mall.MallShopAddressVO;
import com.ycandyz.master.model.user.UserVO;

import javax.servlet.http.HttpServletResponse;

public interface MallOrderService extends IService<MallOrder> {

    CommonResult<Page<MallOrderVO>> queryOrderList(PageModel model, MallOrderQuery mallOrderQuery, UserVO userVO);

    void exportEXT(MallOrderQuery mallOrder, UserVO userVO, HttpServletResponse response);

    CommonResult<MallOrderDetailVO> queryOrderDetail(String orderNo);

    boolean verShipmentNo(String shipNumber);

    CommonResult<MallOrderVO> queryDetailByPickupNo(String pickupNo, UserVO userVO);

    CommonResult<String> verPickupNo(String orderNo, UserVO userVO);

}
