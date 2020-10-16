package com.ycandyz.master.service.mall.goodsManage;

import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.vo.MallItemVO;

public interface MallItemService {
    void addMallItem(MallItemVO mallItemVO, UserVO user);

    MallItemVO selMallItemByitemNo(UserVO userVO, String itemNo);
}
