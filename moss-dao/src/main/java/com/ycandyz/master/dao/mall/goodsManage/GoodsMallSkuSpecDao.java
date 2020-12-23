package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallSkuSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMallSkuSpecDao {

    void addMallSkuSpec(MallSkuSpec mallSkuSpec);

    List<MallSkuSpec> selMallSkuSpecBySkuNo(String skuNo);
}
