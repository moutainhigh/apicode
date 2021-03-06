package com.ycandyz.master.dao.mall.goodsManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.entities.mall.goodsManage.MallSpecValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMallSpecValueDao {

    void addMallSpecValue(MallSpecValue mallSpecValue);

    int delMallSpecValueBySpecNo(String specNo);

    List<MallSpecValue> selMallSpecValueBySpecNo(@Param("specNo") String specNo);

    List<MallSpecValue> selMallSpecValueByKeyWord(@Param("keyWord") String keyWord);
}
