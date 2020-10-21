package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.WxMallSocialShareFlowDTO;
import com.ycandyz.master.entities.mall.WxMallSocialShareFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WxMallSocialShareFlowDao extends BaseMapper<WxMallSocialShareFlow> {

    List<WxMallSocialShareFlowDTO> queryByOrderNo(@Param("orderNo") String orderNo);
}
