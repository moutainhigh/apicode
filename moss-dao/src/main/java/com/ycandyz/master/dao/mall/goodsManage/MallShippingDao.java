package com.ycandyz.master.dao.mall.goodsManage;


import com.ycandyz.master.dto.mall.goodsManage.MallShippingDTO;
import com.ycandyz.master.entities.mall.goodsManage.MallShipping;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MallShippingDao {
    void addMallShipping(MallShipping mallShipping);
    List<MallShipping>  selMallShippingByKeyWord(String shippingName);
    MallShippingDTO selMallShippingByShippingNo(String shippingNo);
    List<MallShipping>  selMallShippingByShopNo(String shopNo);
    int delMallShippingByshippingNo(String shippingNo);
}
