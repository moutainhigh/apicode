package com.ycandyz.master.service.mall.goodsManage;

import com.github.pagehelper.PageInfo;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;

import java.util.List;

public interface MallSpecService {
    List<MallSpecSingleVO> addMallSpec(MallSpecVO mallSpecVO,UserVO userVO);
    int delMallSpecBySpecNo(String specNo,UserVO userVO);
    PageInfo<MallSpecKeyWordVO> selMallSpecByKeyWord(Integer page, Integer pageSize, String keyWord,UserVO userVO);
    MallSpecSingleVO selMallSpecSingleBySpecNo(UserVO userVO, String specNo);

    List<MallSpecSingleVO> updateMallSpec(MallSpecVO mallSpecVO,UserVO userVO);
}
