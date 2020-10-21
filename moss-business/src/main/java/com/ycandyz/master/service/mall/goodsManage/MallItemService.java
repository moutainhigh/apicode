package com.ycandyz.master.service.mall.goodsManage;

import com.ycandyz.master.dto.mall.goodsManage.MallItemDTO;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.vo.MallItemOprVO;
import com.ycandyz.master.vo.MallItemVO;

public interface MallItemService {
    void addMallItem(MallItemVO mallItemVO);

    MallItemDTO selMallItemByitemNo(String itemNo);

    int oprbyItemNo(MallItemOprVO mallItemOprVO);

    int delMallItemByItemNo(String itemNo);

    void updateMallItem(MallItemVO mallItemVO);
}
