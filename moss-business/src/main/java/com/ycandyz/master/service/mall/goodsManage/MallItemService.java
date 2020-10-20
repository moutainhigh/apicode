package com.ycandyz.master.service.mall.goodsManage;

import com.ycandyz.master.dto.mall.goodsManage.MallItemDTO;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.vo.MallItemOprVO;
import com.ycandyz.master.vo.MallItemVO;

public interface MallItemService {
    void addMallItem(MallItemVO mallItemVO, UserVO user);

    MallItemDTO selMallItemByitemNo(UserVO userVO, String itemNo);

    int oprbyItemNo(UserVO userVO, MallItemOprVO mallItemOprVO);

    int delMallItemByItemNo(String itemNo, UserVO userVO);

    void updateMallItem(MallItemVO mallItemVO, UserVO userVO);
}
