package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderDetailVO;
import com.ycandyz.master.model.mall.MallOrderVO;

import javax.servlet.http.HttpServletResponse;

public interface MallOrderService extends IService<MallOrder> {

    CommonResult<Page<MallOrderVO>> queryOrderList(PageModel model, MallOrderQuery mallOrderQuery);

    void exportEXT(MallOrderQuery mallOrder, HttpServletResponse response);

    CommonResult<MallOrderDetailVO> queryOrderDetail(String orderNo);

    boolean shipment(String orderNo);
}
