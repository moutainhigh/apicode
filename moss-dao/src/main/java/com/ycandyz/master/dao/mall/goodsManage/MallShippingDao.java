package com.ycandyz.master.dao.mall.goodsManage;


import com.ycandyz.master.entities.mall.goodsManage.MallShipping;
import com.ycandyz.master.vo.MallShippingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallShippingDao {
    void addMallShipping(MallShipping mallShipping);
    List<MallShipping>  selMallShippingByKeyWord(String shippingName);
    MallShipping selMallShippingByShippingNo(@Param("shopNo") String shopNo,@Param("shippingNo") String shippingNo);
    List<MallShipping>  selMallShippingByShopNo(String shopNo);
    int delMallShippingByshippingNo(@Param("shopNo") String shopNo,@Param("shippingNo") String shippingNo);

    int updateMallShipping(@Param("shopNo") String shopNo, @Param("mallShippingVO") MallShippingVO mallShippingVO);
}
