package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.domain.shipment.query.ShipmentParamLastResultQuery;
import com.ycandyz.master.dto.mall.MallBuyerShippingDTO;
import com.ycandyz.master.dto.mall.MallBuyerShippingLogDTO;
import com.ycandyz.master.dto.mall.MallShopShippingLogDTO;
import com.ycandyz.master.entities.mall.MallBuyerShipping;
import com.ycandyz.master.entities.mall.MallBuyerShippingLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallBuyerShippingLogDao extends BaseMapper<MallBuyerShippingLog> {

    /**
     * 通过买家退货快递表编号集合获取买家退货快递日志表集合
     * @param buyerShippingNoList
     * @return
     */
    List<MallBuyerShippingLogDTO> queryListByBuyerShippingNoList(@Param("list") List<String> buyerShippingNoList);

    MallBuyerShippingLogDTO selectByShipNumberLast(ShipmentParamLastResultQuery query);

    /**
     * 通过买家退货快递表集合获取买家退货快递日志表集合
     * @param buyerShippingNo
     * @return
     */
    List<MallBuyerShippingLogDTO> queryListByBuyerShippingNo(@Param("buyerShippingNo") String buyerShippingNo);

    int deleteByNumber(MallBuyerShippingLog entity);
}
