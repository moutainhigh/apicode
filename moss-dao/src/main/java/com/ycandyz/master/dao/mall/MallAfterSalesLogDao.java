package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallAfterSalesLogDTO;
import com.ycandyz.master.entities.mall.MallAfterSalesLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallAfterSalesLogDao extends BaseMapper<MallAfterSalesLog> {

    List<MallAfterSalesLogDTO> querySalesLogByShopNoAndSalesNoList(@Param("list") List<String> afterSalesNoList);

    List<MallAfterSalesLogDTO> querySalesLogByShopNoAndSalesNo(@Param("afterSalesNo") String afterSalesNo);
}
