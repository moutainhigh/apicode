package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallSocialShareFlowDTO;
import com.ycandyz.master.entities.mall.MallSocialShareFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallSocialShareFlowDao extends BaseMapper<MallSocialShareFlow> {

    List<MallSocialShareFlowDTO> queryByOrderNo(@Param("orderNo") String orderNo);
}
