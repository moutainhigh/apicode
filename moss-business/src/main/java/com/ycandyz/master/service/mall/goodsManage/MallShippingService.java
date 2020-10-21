package com.ycandyz.master.service.mall.goodsManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallShippingKwDTO;
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

    List<MallShippingDTO> addMallShipping(MallShippingVO mallShippingVO);

    List<MallShippingDTO> updateMallShipping(MallShippingVO mallShippingVO);

    MallShippingDTO selMallShippingByShippingNo(String shippingNo);

    Page<MallShippingKwDTO> selMallShippingByKeyWord(PageResult pageResult, String shippingName);

    List<MallShippingDTO> selMallShippingByShopNo();

    List<MallShippingRegionProvinceDTO> selMallShippingRegionProvince();

    int delMallShippingByshippingNo(String shippingNo);
}
