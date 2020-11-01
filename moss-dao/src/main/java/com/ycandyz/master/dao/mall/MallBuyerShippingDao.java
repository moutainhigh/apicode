package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallBuyerShippingDTO;
import com.ycandyz.master.entities.mall.MallBuyerShipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallBuyerShippingDao extends BaseMapper<MallBuyerShipping> {

    /**
     * 根据售后订单编号列表查询
     * @param afterSalesNoList
     * @return
     */
    List<MallBuyerShippingDTO> queryByAfterSalesNoList(@Param("list") List<String> afterSalesNoList);

    /**
     * 根据售后订单编号查询
     * @param afterSalesNo
     * @return
     */
    MallBuyerShippingDTO queryByAfterSalesNo(@Param("afterSalesNo") String afterSalesNo);
}
