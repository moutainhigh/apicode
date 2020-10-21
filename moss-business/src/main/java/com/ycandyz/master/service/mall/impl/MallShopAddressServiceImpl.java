package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.MallShopAddressDao;
import com.ycandyz.master.domain.query.mall.MallShopAddressQuery;
import com.ycandyz.master.dto.mall.MallShopAddressDTO;
import com.ycandyz.master.entities.mall.MallShopAddress;
import com.ycandyz.master.model.mall.MallShopAddressVO;
import com.ycandyz.master.service.mall.MallShopAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MallShopAddressServiceImpl extends BaseService<MallShopAddressDao, MallShopAddress, MallShopAddressQuery> implements MallShopAddressService {

    @Autowired
    private MallShopAddressDao mallShopAddressDao;

    @Override
    public ReturnResponse<List<MallShopAddressVO>> queryShopAddressList(String shopNo) {
        List<MallShopAddressVO> result = new ArrayList<>();
        List<MallShopAddressDTO> list = mallShopAddressDao.queryByShopNo(shopNo);
        if (list!=null && list.size()>0){
            MallShopAddressVO mallShopAddressVO = null;
            for (MallShopAddressDTO mallShopAddressDTO : list){
                mallShopAddressVO = new MallShopAddressVO();
                BeanUtils.copyProperties(mallShopAddressDTO,mallShopAddressVO);
                result.add(mallShopAddressVO);
            }
        }
        return ReturnResponse.success(result);
    }
}
