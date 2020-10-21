package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallShopShippingLogDTO;
import com.ycandyz.master.entities.mall.MallShopShippingLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallShopShippingLogDao extends BaseMapper<MallShopShippingLog> {

    /**
     * 查询当前订单号查询物流信息
     * @return
     */
    List<MallShopShippingLogDTO> selectListByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 通过物流单号查询当前最新的一条记录
     * @param number
     * @return
     */
    MallShopShippingLogDTO selectByShipNumberLast(@Param("number") String number);
}
