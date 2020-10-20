package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.entities.mall.MallShopAddress;
import com.ycandyz.master.model.mall.MallShopAddressVO;

import java.util.List;

public interface MallShopAddressService extends IService<MallShopAddress> {

    CommonResult<List<MallShopAddressVO>> queryShopAddressList(String shopNo);
}
