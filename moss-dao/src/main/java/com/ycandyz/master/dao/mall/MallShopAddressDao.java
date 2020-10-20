package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.mall.MallShopAddressDTO;
import com.ycandyz.master.entities.mall.MallShopAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallShopAddressDao extends BaseMapper<MallShopAddress> {

    List<MallShopAddressDTO> queryByShopNo(@Param("shopNo") String shopNo);
}
