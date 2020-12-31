package com.ycandyz.master.service.mall.goodsManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;

import java.util.List;

public interface GoodsMallSpecService {
    List<MallSpecSingleVO> insert(MallSpecVO mallSpecVO);
    int delete(String specNo);
    Page<MallSpecKeyWordVO> selectByKeyWord(PageResult pageResult, String keyWord);
    MallSpecSingleVO select(String specNo);

    List<MallSpecSingleVO> update(MallSpecVO mallSpecVO);
}
