package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import com.ycandyz.master.vo.MallItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallItemDao {

    void addMallItem(MallItem mallItem);

    MallItem selMallItemByitemNo(@Param("shopNo") String shopNo, @Param("itemNo") String itemNo);

    int delMallItemByItemNo(@Param("shopNo")String shopNo, @Param("itemNo") String itemNo);

    int oprbyItemNo(@Param("shopNo") String shopNo, @Param("itemNoList") List<String> itemNoList, @Param("status") Integer status);

    int updateMallItem(@Param("mallItem") MallItem mallItem, @Param("shopNo") String shopNo);

    int updateOneMallItem(String itemNo);
}
