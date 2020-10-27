package com.ycandyz.master.service.mall.goodsManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.dto.mall.MallShippingDTO;
import com.ycandyz.master.dto.mall.MallShippingKwDTO;
import com.ycandyz.master.dto.mall.MallShippingRegionProvinceDTO;
import com.ycandyz.master.vo.MallShippingVO;

import java.util.List;

/**
 * @Description: 运费模版
 * @Author li Zhuo
 * @Date 2020/10/12
 * @Version: V1.0
*/
public interface MallShippingService {

    List<MallShippingDTO> insert(MallShippingVO mallShippingVO);

    List<MallShippingDTO> update(MallShippingVO mallShippingVO);

    MallShippingDTO select(String shippingNo);

    Page<MallShippingKwDTO> selectByKeyWord(PageResult pageResult, String shippingName);

    List<MallShippingDTO> selMallShippingByShopNo();

    List<MallShippingRegionProvinceDTO> selectRegion();

    int delete(String shippingNo);
}
