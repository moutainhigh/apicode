package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import com.ycandyz.master.vo.MallItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMallItemDao {

    void addMallItem(MallItem mallItem);

    MallItem selMallItemByitemNo(@Param("shopNo") String shopNo, @Param("itemNo") String itemNo);

    int delMallItemByItemNo(@Param("shopNo")String shopNo, @Param("itemNo") String itemNo);

    int oprbyItemNo(@Param("shopNo") String shopNo, @Param("itemNoList") List<String> itemNoList, @Param("status") Integer status);

    int updateMallItem(@Param("mallItem") MallItem mallItem, @Param("shopNo") String shopNo);

    int updateOneMallItem(@Param("contentId") String contentId,@Param("oper") Integer oper);

    int handleExamine(@Param("oper") Integer oper,@Param("ids") List<String> ids);

    MallItem selByitemNo(@Param("itemNo") String itemNo);
}
