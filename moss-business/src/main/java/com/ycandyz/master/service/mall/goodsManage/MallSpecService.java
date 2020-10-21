package com.ycandyz.master.service.mall.goodsManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import com.ycandyz.master.vo.MallSpecVO;

import java.util.List;

public interface MallSpecService {
    List<MallSpecSingleVO> addMallSpec(MallSpecVO mallSpecVO);
    int delMallSpecBySpecNo(String specNo);
    Page<MallSpecKeyWordVO> selMallSpecByKeyWord(PageResult pageResult, String keyWord);
    MallSpecSingleVO selMallSpecSingleBySpecNo(String specNo);

    List<MallSpecSingleVO> updateMallSpec(MallSpecVO mallSpecVO);
}
