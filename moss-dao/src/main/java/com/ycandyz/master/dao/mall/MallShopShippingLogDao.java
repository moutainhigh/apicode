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
    List<MallShopShippingLogDTO> selectListByShopShippingNo(@Param("shopShippingNo") String shopShippingNo);


    /**
     * 批量插入功能
     * @param list
     * @return
     */
    Integer insertBatch(@Param("list") List<MallShopShippingLogDTO> list);
}
