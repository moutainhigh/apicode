package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallShopShippingDTO;
import com.ycandyz.master.entities.mall.MallShopShipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallShopShippingDao extends BaseMapper<MallShopShipping> {

    /**
     * 通过订单号查询物流信息表
     * @param orderNo
     * @return
     */
    MallShopShippingDTO queryByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 通过物流公司编号和物流单号查询
     * @param companyCode
     * @param number
     * @return
     */
    List<MallShopShippingDTO> queryByCodeAndNum(@Param("companyCode") String companyCode, @Param("number") String number);

}
