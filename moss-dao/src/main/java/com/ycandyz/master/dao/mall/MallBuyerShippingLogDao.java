package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    List<MallBuyerShippingLogDTO> queryListByBuyerShippingNoList(@Param("list") List<String> buyerShippingNoList);
    MallBuyerShippingLogDTO selectByShipNumberLast(@Param("number") String number);
}
