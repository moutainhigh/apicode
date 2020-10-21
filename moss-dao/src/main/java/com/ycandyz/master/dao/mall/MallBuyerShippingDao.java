package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallBuyerShippingDTO;
import com.ycandyz.master.entities.mall.MallBuyerShipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallBuyerShippingDao extends BaseMapper<MallBuyerShipping> {

    List<MallBuyerShippingDTO> queryByAfterSalesNoList(@Param("list") List<String> afterSalesNoList);
}
