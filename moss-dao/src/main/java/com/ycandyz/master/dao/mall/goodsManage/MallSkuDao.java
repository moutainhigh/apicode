package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import com.ycandyz.master.entities.mall.goodsManage.MallSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MallSkuDao {

    void addMallSku(MallSku mallSku);

    List<MallSku> selMallSkuByitemNo(String itemNo);

    int deleteSkuDao(String itemNo);
}
