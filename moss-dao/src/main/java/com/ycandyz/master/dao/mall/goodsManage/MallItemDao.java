package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MallItemDao {

    void addMallItem(MallItem mallItem);

    MallItem selMallItemByitemNo(@Param("shopNo") String shopNo, @Param("itemNo") String itemNo);
}
