package com.ycandyz.master.dao.mall.goodsManage;

import com.ycandyz.master.entities.mall.goodsManage.MallSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallSpecDao {
    void addMallSpec(MallSpec mallSpec);
    int delMallSpecBySpecNo(@Param("shopNo")String shopNo,@Param("specNo") String specNo);
    List<MallSpec> selMallSpecByKeyWord(@Param("shopNo") String shopNo, @Param("keyWord") String keyWord);
    MallSpec selMallSpecBySpecNo(@Param("shopNo") String shopNo, @Param("specNo")String specNo);

    List<String> selMallSpecByShopNo(@Param("shopNo") String shopNo);
}
