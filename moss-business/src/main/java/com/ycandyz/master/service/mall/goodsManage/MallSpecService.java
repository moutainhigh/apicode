package com.ycandyz.master.service.mall.goodsManage;

import com.github.pagehelper.PageInfo;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;

import java.util.List;

public interface MallSpecService {
    List<MallSpecSingleVO> addMallSpec(MallSpecVO mallSpecVO);
    int delMallSpecBySpecNo(String specNo);
    PageInfo<MallSpecKeyWordVO> selMallSpecByKeyWord(Integer page, Integer pageSize, String keyWord);
    MallSpecSingleVO selMallSpecSingleBySpecNo(String shopNo, String specNo);
}
