package com.ycandyz.master.service.mall.goodsManage;

import com.github.pagehelper.PageInfo;
import com.ycandyz.master.commonResult.CommonResult2;
import com.ycandyz.master.dto.mall.goodsManage.MallSpecDTO;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;

public interface MallSpecService {
    MallSpecDTO addMallSpec(MallSpecVO mallSpecVO);
    CommonResult2 delMallSpecBySpecNo(String specNo);
    PageInfo<MallSpecKeyWordVO> selMallSpecByKeyWord(Integer page, Integer pageSize, String keyWord);
    MallSpecVO selMallSpecBySpecNo(String shopNo,String specNo);
    MallSpecSingleVO selMallSpecSingleBySpecNo(String shopNo, String specNo);
}
