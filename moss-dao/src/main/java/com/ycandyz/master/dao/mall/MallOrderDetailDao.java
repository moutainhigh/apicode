package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallOrderDetailDTO;
import com.ycandyz.master.entities.mall.MallOrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MallOrderDetailDao extends BaseMapper<MallOrderDetail> {

}
