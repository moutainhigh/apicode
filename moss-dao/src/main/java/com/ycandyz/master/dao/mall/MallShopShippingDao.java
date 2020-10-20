package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallShopShippingDTO;
import com.ycandyz.master.entities.mall.MallShopShipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MallShopShippingDao extends BaseMapper<MallShopShipping> {

    MallShopShippingDTO queryByOrderNo(@Param("orderNo") String orderNo);

}
