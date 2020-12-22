package com.ycandyz.master.service.mall.goodsManage;

import com.ycandyz.master.dto.mall.MallItemDTO;
import com.ycandyz.master.vo.MallItemOprVO;
import com.ycandyz.master.vo.MallItemVO;

public interface GoodsMallItemService {
    void insert(MallItemVO mallItemVO);

    MallItemDTO select(String itemNo);

    int opr(MallItemOprVO mallItemOprVO);

    int delete(String itemNo);

    void update(MallItemVO mallItemVO);
}
