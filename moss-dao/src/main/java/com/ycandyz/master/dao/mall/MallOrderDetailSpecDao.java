package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallOrderDetailSpecDTO;
import com.ycandyz.master.entities.mall.MallOrderDetailSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallOrderDetailSpecDao extends BaseMapper<MallOrderDetailSpec> {

    List<MallOrderDetailSpecDTO> queryListByOrderDetailNoList(@Param("list") List<String> orderDetailNoList);

    List<MallOrderDetailSpecDTO> queryListByOrderDetailNo(@Param("orderDetailNo") String orderDetailNo);

}
