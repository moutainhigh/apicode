package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallShopDTO;
import com.ycandyz.master.entities.mall.MallShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MallShopDao extends BaseMapper<MallShop> {

    MallShopDTO queryByShopNo(@Param("shopNo") String shopNo);
}
