package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.dto.mall.MallOrderDetailDTO;
import com.ycandyz.master.entities.mall.MallOrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallOrderDetailDao extends BaseMapper<MallOrderDetail> {

    Page<MallOrderDetailDTO> queryDetailListByOrderNos(Page page, @Param("list") List<String> orderNos);
}
