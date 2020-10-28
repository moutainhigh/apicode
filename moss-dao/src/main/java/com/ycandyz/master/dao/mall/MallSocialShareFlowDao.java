package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallSocialShareFlowDTO;
import com.ycandyz.master.entities.mall.MallSocialShareFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallSocialShareFlowDao extends BaseMapper<MallSocialShareFlow> {

    /**
     * 根据订单编号获取一级分销人集合
     * @param orderNo
     * @return
     */
    List<MallSocialShareFlowDTO> queryByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据订单详情编号获取所有分销人集合
     * @param orderDetailNo
     * @return
     */
    List<MallSocialShareFlowDTO> queryByOrderDetailNo(@Param("orderDetailNo") String orderDetailNo);

    /**
     * 根据订单编号获取所有分销人集合（一到三级）
     * @param orderNo
     * @return
     */
    List<MallSocialShareFlowDTO> queryAllShareByOrderNo(@Param("orderNo") String orderNo);
}
