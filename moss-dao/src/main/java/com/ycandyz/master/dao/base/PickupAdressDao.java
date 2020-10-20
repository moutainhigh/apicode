package com.ycandyz.master.dao.base;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.entities.BaseRegion;
import com.ycandyz.master.entities.PickupAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 省市
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/

@Mapper
public interface PickupAdressDao extends BaseMapper<PickupAddress> {

    List<PickupAddress> selPickupAddress(@Param("ids") List<Integer> ids);
}
