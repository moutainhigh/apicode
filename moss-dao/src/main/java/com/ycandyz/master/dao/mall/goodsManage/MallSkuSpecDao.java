package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallSku;
import com.ycandyz.master.entities.mall.goodsManage.MallSkuSpec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MallSkuSpecDao {

    void addMallSkuSpec(MallSkuSpec mallSkuSpec);
}
