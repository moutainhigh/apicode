package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import com.ycandyz.master.entities.mall.goodsManage.MallSku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MallSkuDao {

    void addMallSku(MallSku mallSku);
}
