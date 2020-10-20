package com.ycandyz.master.service.mall.goodsManage;

import com.github.pagehelper.PageInfo;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingRegionProvinceDTO;
import com.ycandyz.master.entities.mall.goodsManage.MallShipping;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.vo.MallShippingVO;

import java.util.List;

/**
 * @Description: 运费模版
 * @Author li Zhuo
 * @Date 2020/10/12
 * @Version: V1.0
*/
public interface MallShippingService {

    List<MallShippingDTO> addMallShipping(MallShippingVO mallShippingVO,UserVO userVO);

    List<MallShippingDTO> updateMallShipping(MallShippingVO mallShippingVO, UserVO userVO);

    MallShippingDTO selMallShippingByShippingNo(String shippingNo,UserVO userVO);

    PageInfo<MallShipping> selMallShippingByKeyWord(Integer page, Integer PageSize, String shippingName);

    List<MallShippingDTO> selMallShippingByShopNo(UserVO userVO);

    List<MallShippingRegionProvinceDTO> selMallShippingRegionProvince();

    int delMallShippingByshippingNo(String shippingNo, UserVO userVO);
}
