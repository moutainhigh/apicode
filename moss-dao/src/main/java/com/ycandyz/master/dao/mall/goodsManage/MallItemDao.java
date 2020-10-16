package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MallItemDao {

    void addMallItem(MallItem mallItem);
}
