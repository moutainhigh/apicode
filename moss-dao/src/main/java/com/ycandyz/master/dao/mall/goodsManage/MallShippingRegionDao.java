package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallShippingRegion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MallShippingRegionDao {
    void addmallShippingRegion(MallShippingRegion mallShippingRegion);
    List<MallShippingRegion> selMallShippingRegionByShippingNo(String shippingNo);

    int delMallShippingRegionByshippingNo(String shippingNo);
}
