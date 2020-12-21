package com.ycandyz.master.dao.mall.goodsManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.entities.mall.goodsManage.MallSpec;
import com.ycandyz.master.vo.MallSpecVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMallSpecDao {
    void addMallSpec(MallSpec mallSpec);
    int delMallSpecBySpecNo(@Param("shopNo")String shopNo,@Param("specNo") String specNo);
    Page<MallSpec> selMallSpecByKeyWord(Page page, @Param("shopNo") String shopNo, @Param("keyWord") String keyWord);
    MallSpec selMallSpecBySpecNo(@Param("shopNo") String shopNo, @Param("specNo")String specNo);

    List<String> selMallSpecNoByShopNo(@Param("shopNo") String shopNo);

    int updateMallSpec(@Param("specNo") String specNo,@Param("specName") String specName,@Param("shopNo") String shopNo);
}
