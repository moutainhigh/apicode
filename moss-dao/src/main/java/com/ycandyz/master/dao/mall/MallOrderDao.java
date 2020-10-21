package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.dto.mall.MallOrderDTO;
import com.ycandyz.master.dto.mall.MallOrderDetailDTO;
import com.ycandyz.master.entities.mall.MallOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallOrderDao extends BaseMapper<MallOrder> {

    /**
     * 订单量趋势统计
     * @param mallOrderQuery
     * @return
     */
    Page<MallOrderDTO> getTrendMallOrderPage(Page page, @Param("p") MallOrderQuery mallOrderQuery);

    List<MallOrderDTO> getTrendMallOrder(@Param("p") MallOrderQuery mallOrderQuery);

    MallOrderDetailDTO queryOrderDetail(@Param("orderNo") String orderNo);

    MallOrderDTO queryDetailByPickupNo(@Param("pickupNo") String pickupNo, @Param("shopNo") String shopNo);

    MallOrderDTO queryDetailByOrderNo(@Param("orderNo") String orderNo, @Param("shopNo") String shopNo);
}
