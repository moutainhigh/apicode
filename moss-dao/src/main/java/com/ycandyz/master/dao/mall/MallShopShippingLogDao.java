package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallShopShippingLogDTO;
import com.ycandyz.master.entities.mall.MallShopShippingLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MallShopShippingLogDao extends BaseMapper<MallShopShippingLog> {

    /**
     * 查询当前订单号是否已经被签收
     * @return
     */
    MallShopShippingLogDTO selectByShopShippingNoAndStatus(@Param("shopShippingNo") String shopShippingNo);

    /**
     * 通过物流单号查询当前最新的一条记录
     * @param number
     * @return
     */
    MallShopShippingLogDTO selectByShipNumberLast(@Param("number") String number);
}
