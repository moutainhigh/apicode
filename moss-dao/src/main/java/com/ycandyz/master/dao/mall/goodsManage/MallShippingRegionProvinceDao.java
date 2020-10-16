package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallShippingRegionProvince;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MallShippingRegionProvinceDao {
    void addMallShippingRegionProvince(MallShippingRegionProvince mallShippingRegionProvince);
    List<String> selMallShippingRegionProvinceByRegionNo(String regionNo);
    int delMallShippingRegionProvinceByshippingNo(String regionNo);
}
